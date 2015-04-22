package track_controller;
import javax.tools.*;

import track_model.*;
import ctc_office.TrainRoute;

import java.util.ArrayList;
import java.util.HashMap;

public class WaysideController {
	public ArrayList<TrackBlock> affectedBlocks = new ArrayList<TrackBlock>();
	public HashMap<Integer, TrackBlock> blockMap = new HashMap<Integer, TrackBlock>();
	public ArrayList<TrainRoute> routes = new ArrayList<TrainRoute>();
	public PLCInterface plc;
	public int span = 0;
	public String ctcInfo = null;
	
	public boolean containsSwitch = false;
	public boolean containsCrossing = false;
	
			
	/*
	 * Updates the PLC program for the controller
	 * based on a user input file.
	 */
	public boolean updatePLC(String filename) {
		System.setProperty("java.home", "C:\\Program Files\\Java\\jdk1.7.0_40");
		JavaCompiler comp = ToolProvider.getSystemJavaCompiler();
		int compRes = comp.run(null, null, null, filename);
		if (compRes == 0) {
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
				if(!manual){
					boolean swCtl1 = plc.ctrlSwitch(r);
					boolean swCtl2 = plc.ctrlSwitch(r);
					
					//TO DO: figure out how to determine which switch is affected
					//set switch to swCtl1 && swCtl2
					
					//handle crossing
					boolean xCtrl1 = plc.ctrlCrossing(r);
					boolean xCtrl2 = plc.ctrlCrossing(r);
					
					//TO DO: figure out which block is crossing
					
				}
				
				//check speed and authority
				boolean spAuthCheck1 = plc.ctrlSpeedAuthority(blockMap.get(r.startingBlock), r.speed, r.authority);
				boolean spAuthCheck2 = plc.ctrlSpeedAuthority(blockMap.get(r.startingBlock), r.speed, r.authority);
				
				//speed and authority were declined
				//calculate new values
				if(spAuthCheck1 && spAuthCheck2 == false){
					
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
			//TO DO: add red line switches
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
