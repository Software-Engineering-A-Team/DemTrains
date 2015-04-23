package track_controller;
import javax.tools.*;

import track_model.*;
import ctc_office.TrainRoute;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;

public class WaysideController {
	public ArrayList<TrackBlock> affectedBlocks = new ArrayList<TrackBlock>();
	public HashMap<Integer, TrackBlock> blockMap = new HashMap<Integer, TrackBlock>();
	public ArrayList<TrainRoute> routes = new ArrayList<TrainRoute>();
	public PLCInterface plc;
	public int span = 0;
	public String ctcInfo = "";
	
	public boolean containsSwitch = false;
	public boolean containsCrossing = false;
	
			
	/*
	 * Updates the PLC program for the controller
	 * based on a user input file.
	 */
	public boolean updatePLC(String filename) throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		int i = filename.indexOf(".java");
		String searchPath = "file:\\\\\\" + filename.substring(0, i-5) + "\\";
		System.out.println(searchPath);
		String className = filename.substring(i-4, i);
		System.out.println(className);
		System.setProperty("java.home", "C:\\Program Files\\Java\\jdk1.7.0_40");
		JavaCompiler comp = ToolProvider.getSystemJavaCompiler();
		int compRes = comp.run(null, null, null, filename);
		if (compRes == 0) {
			//create instance of plc file that was loaded and assign it to this controller
			URLClassLoader plcLoader = URLClassLoader.newInstance(new URL[] { new URL(searchPath) });
			Class<?> plcClass = Class.forName(className, true, plcLoader);
			Constructor<?> plcConst = plcClass.getConstructor();
			Object plcInstance = plcConst.newInstance(blockMap);
			this.plc = (PLCInterface) plcInstance;
			return true;
		}
		else {
			return false;
		}
	}
	
	/*
	 * Runs user specified PLC program twice and compares
	 * outputs to ensure safe operation of the system.
	 */
	public void runPLC(boolean manual) {
		for(TrackBlock b: affectedBlocks){
			boolean heatCtrl1 = plc.ctrlHeater(b);
			boolean heatCtrl2 = plc.ctrlHeater(b);
			b.heater= (heatCtrl1 && heatCtrl2);
						
			boolean lightCtrl1 = plc.ctrlLights(b);
			boolean lightCtrl2 = plc.ctrlLights(b);
			b.lights = (lightCtrl1 && lightCtrl2);
		}
		
		//find routes where starting block is affected by this controller	
		for (TrainRoute r : routes){
			if(affectedBlocks.contains(r.startingBlock)){
				//when in manual mode only take user input
				if(!manual){
					//if this controller contains a possible switch block
					if(affectedBlocks.contains(9) || affectedBlocks.contains(12) || affectedBlocks.contains(15) || affectedBlocks.contains(29) 
							|| affectedBlocks.contains(32) || affectedBlocks.contains(38) || affectedBlocks.contains(27) || affectedBlocks.contains(43)
							|| affectedBlocks.contains(52) || affectedBlocks.contains(58) || affectedBlocks.contains(62) || affectedBlocks.contains(76)
							|| affectedBlocks.contains(86)){
							
							
						boolean swCtl1 = plc.ctrlSwitch(r);
						boolean swCtl2 = plc.ctrlSwitch(r);
						
						
						
						//TO DO: figure out how to determine which switch is affected
						//set switch to swCtl1 && swCtl2
					}
					//handle crossing
					int crossingBlock = 0;
					if(affectedBlocks.contains(19) || affectedBlocks.contains(47)){
						if(blockMap.get(19).infrastructure.contains("crossing")){
							crossingBlock = 19;
						}
						else if(blockMap.get(47).infrastructure.contains("crossing")){
							crossingBlock = 47;
						}
					}
					
					boolean xCtrl1 = plc.ctrlCrossing(r);
					boolean xCtrl2 = plc.ctrlCrossing(r);
					
					if(crossingBlock != 0){
						TrackSwitch tempSwitch = (TrackSwitch) blockMap.get(crossingBlock);
						tempSwitch.state = (xCtrl1 && xCtrl2);						
					}
				}
				
				//check speed and authority
				boolean spAuthCheck1 = plc.ctrlSpeedAuthority(r, r.speed, r.authority);
				boolean spAuthCheck2 = plc.ctrlSpeedAuthority(r, r.speed, r.authority);
				
				//speed and authority were declined
				//calculate new values
				if(spAuthCheck1 && spAuthCheck2 == false){
					boolean spdChk1 = plc.checkSpeed(r, r.speed);
					boolean spdChk2 = plc.checkSpeed(r, r.speed);
					
					//if speed limit is acceptable use that
					if(spdChk1 && spdChk2) affectedBlocks.get(r.startingBlock).commandedSpeed = affectedBlocks.get(r.startingBlock).speedLimit;
					//otherwise stop train
					else affectedBlocks.get(r.startingBlock).commandedSpeed = 0;
					
					double safeAuthFinal;
					double safeAuth1 = calculateSafeAuthority(r);
					double safeAuth2 = calculateSafeAuthority(r);
					if(safeAuth1 == safeAuth2) safeAuthFinal = safeAuth1;
					else if(safeAuth1 < safeAuth2) safeAuthFinal = safeAuth1;
					else safeAuthFinal = safeAuth2;
					
					boolean authChk1 = plc.checkAuthority(r, r.authority, safeAuthFinal);
					boolean authChk2 = plc.checkAuthority(r, r.authority, safeAuthFinal);
					
				}
				//otherwise pass to track model
				else {
					blockMap.get(r.startingBlock).commandedAuthority = r.authority;
					blockMap.get(r.startingBlock).commandedSpeed = r.speed;
				}
				
				//then remove route from list
				routes.remove(r);
			}
		}
	}
	
	private double calculateSafeAuthority(TrainRoute r) {
		//TODO: calculate safe authority
		return 0;
	}

	/*
	 * Allows user to manually add a block
	 * to the a certain controller.
	 */
	public boolean addBlocks(ArrayList<TrackBlock> b) {
		for (TrackBlock block: b){
			affectedBlocks.add(block);
			blockMap.put(block.number, block);
			span++;
		}
		return true;
	}
	
	/*
	 * Finds all switches controlled by this controller.
	 */
	public ArrayList<TrackSwitch> findSwitches(String line){
		ArrayList<TrackSwitch> foundSwitches = new ArrayList<TrackSwitch>();
		if(line.equals("Green")){
			for (TrackBlock b : affectedBlocks) {
				if(b.number == 12) foundSwitches.add((TrackSwitch)blockMap.get(12));
				if(b.number == 29) foundSwitches.add((TrackSwitch)blockMap.get(29));
				if(b.number == 58) foundSwitches.add((TrackSwitch)blockMap.get(58));
				if(b.number == 62) foundSwitches.add((TrackSwitch)blockMap.get(62));
				if(b.number == 76) foundSwitches.add((TrackSwitch)blockMap.get(76));
				if(b.number == 86) foundSwitches.add((TrackSwitch)blockMap.get(86));
			}
		}
		else if(line.equals("Red")){
			for (TrackBlock b : affectedBlocks) {
				if(b.number == 9) foundSwitches.add((TrackSwitch)blockMap.get(9));
				if(b.number == 15) foundSwitches.add((TrackSwitch)blockMap.get(15));
				if(b.number == 27) foundSwitches.add((TrackSwitch)blockMap.get(27));
				if(b.number == 32) foundSwitches.add((TrackSwitch)blockMap.get(32));
				if(b.number == 38) foundSwitches.add((TrackSwitch)blockMap.get(38));
				if(b.number == 43) foundSwitches.add((TrackSwitch)blockMap.get(43));
				if(b.number == 52) foundSwitches.add((TrackSwitch)blockMap.get(52));
			}
		}
		return foundSwitches;
	}
	
	/*
	 * Called by CTC to add a route
	 * to a given wayside controller 
	 */	
	public TrainRoute addRoute(TrainRoute r) {
		System.out.println("Adding route with blocks: ");
		for(int i : r.route) {
			System.out.println(i);
		}
		if(r != null) {
			boolean routeStatus1 = this.plc.checkRoute(r);
			boolean routeStatus2 = this.plc.checkRoute(r);

			if(routeStatus1 && routeStatus2){
				this.routes.add(r);
				ctcInfo = ctcInfo + "\n"+ r.lineName + " line";
				ctcInfo = ctcInfo + "\n Suggested authority: "+ r.authority;
				ctcInfo = ctcInfo = ctcInfo + "\n Suggested speed: "+ r.speed;
				ctcInfo = ctcInfo + "\n Start block:  "+ r.startingBlock;
				ctcInfo = ctcInfo + "\n Route:  "+ r.route.toString();
				return null;
			}
			else return r;
		}
		
		return r;
	}
}
