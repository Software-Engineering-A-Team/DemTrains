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
	public void runPLC() {
		plc.run();
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
	public ArrayList<TrackSwitch> findSwitches(){
		ArrayList<TrackSwitch> foundSwitches = new ArrayList<TrackSwitch>();
		for (TrackBlock b : affectedBlocks) {
			if(b.number == 12) foundSwitches.add((TrackSwitch)blockMap.get(12));
			if(b.number == 29) foundSwitches.add((TrackSwitch)blockMap.get(29));
			if(b.number == 58) foundSwitches.add((TrackSwitch)blockMap.get(58));
			if(b.number == 62) foundSwitches.add((TrackSwitch)blockMap.get(62));
			if(b.number == 76) foundSwitches.add((TrackSwitch)blockMap.get(76));
			if(b.number == 86) foundSwitches.add((TrackSwitch)blockMap.get(86));
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
				return null;
			}
			else return r;
		}
		else return r;
	}
}
