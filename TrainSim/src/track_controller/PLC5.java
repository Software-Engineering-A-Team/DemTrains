package track_controller;
import ctc_office.TrainRoute;
import track_model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class PLC5 implements PLCInterface {
	HashMap<Integer, TrackBlock> controlledBlocks;
	TrainRoute r;
	boolean switchCtrlSuccess = false;
	
	
	public PLC5(HashMap<Integer, TrackBlock> blockList, TrainRoute route){
		this.controlledBlocks = blockList;
		this.r = route;
	}	
	/*
	 * Determines safe state of the railway crossing and returns the state
	 * true for active, false for inactive
	 */
	public boolean ctrlCrossing() {
		return true;
	}
	/*
	 * Determines safe state of the switch and returns the state
	 * true for second block in attach array , false for first block in attach array
	 */
	public boolean ctrlSwitch() {
		
		TrackSwitch relSwitch = (TrackSwitch) controlledBlocks.get(76);
			//compute nextBlock val
			System.out.println(r.route.isEmpty());
			int indNextBlock = r.route.indexOf(76)+1;
			System.out.println(indNextBlock);
			int nextBlock = r.route.get(indNextBlock);
			System.out.println(nextBlock);
			//int prevBlock = r.route.get(r.route.indexOf(12)-1);
			
			
			// if train on 75,74,73 and nothing else within range and next block after 76 in route is 77
			//set switch connected to block 77
			if((controlledBlocks.get(75).occupancy | controlledBlocks.get(74).occupancy | controlledBlocks.get(73).occupancy)
				& (!controlledBlocks.get(78).occupancy | !controlledBlocks.get(79).occupancy| !controlledBlocks.get(77).occupancy 
				| !controlledBlocks.get(76).occupancy) &  nextBlock == 77){
				relSwitch = (TrackSwitch) controlledBlocks.get(76);
				relSwitch.state = false;
			}
			//if train on 77,78,79 and nothing else within range and next block after 76 is 101
			//set switch connected to block 28
			else if ((controlledBlocks.get(77).occupancy | controlledBlocks.get(78).occupancy | controlledBlocks.get(79).occupancy)
					& (!controlledBlocks.get(75).occupancy | !controlledBlocks.get(74).occupancy| !controlledBlocks.get(73).occupancy 
							  | !controlledBlocks.get(76).occupancy) & nextBlock == 101) {
				relSwitch = (TrackSwitch) controlledBlocks.get(76);
				relSwitch.state = true;
			}
			//if there is a conflict, keep switch where it is and set speed and authority of conflicting
			//occupied blocks to 0
			else {
				System.out.println("No criteria met. In else.");
				if(!relSwitch.state) {}
				
				return relSwitch.state;
			}
		System.out.println("No criteria met.");
		return relSwitch.state;
	}
	/*
	 * Determines safe state of the track heater and returns the state
	 * true on, false off
	 */
	public boolean ctrlHeater(TrackBlock b) {
		if(b.hasAdverseWeather()) return true;
		else return false;		
	}
	/*
	 * Determines safe state of the lights and returns the state
	 * true green, false red
	 */
	public boolean ctrlLights(TrackBlock b) {
		if(b.occupancy | b.hasFailure()) return true;
		else return false;
	}
	/*
	 * Determines safe speed and authority and returns 
	 * 
	 */
	public boolean ctrlSpeedAuthority(TrackBlock b, double speed, double authority) { 
		if (speed > b.speedLimit) return false;
		//else if (authority > safeAuthority) return false;
		else return true;
	}
	/*
	 * Determines safe closing of block and returns block state
	 * true for open, false for closed
	 */
	public boolean ctrlBlockClosed(TrackBlock b){
		if (b.hasFailure()) return true;
		else if (!b.occupancy) return true;
		else return false;
	}
	
	/*
	 * Checks this route for possibility of collisions
	 * and other errors.
	 */
	public boolean checkRoute() {
		if (this.r == null) return false;
		for (int i : this.r.route) {
			TrackBlock b = controlledBlocks.get(i);
			if(b.occupancy) return false;
		}
	 return true;
	}
	
	/*
	 * Changes the route.
	 */
	public void changeRoute(TrainRoute route) {
		this.r = route;
	}
	
	public boolean switchCtrl() {
		if (this.r != null) return true;
		else return false;
	}
	
	/*
	 * Runs all functions of PLC Program
	 */
	public void run(){
		System.out.println("Running PLC1");
		for (int i = 74; i<83; i++) {
			TrackBlock b = controlledBlocks.get(i);
			b.heater = ctrlHeater(b);
			b.lights = ctrlLights(b);
		}
		
		for (int i = 101; i<105; i++) {
			TrackBlock b = controlledBlocks.get(i);
			b.heater = ctrlHeater(b);
			b.lights = ctrlLights(b);
		}
		TrackSwitch s = (TrackSwitch) controlledBlocks.get(76);
		System.out.println("Current state is : " + s.state);
		s.state = ctrlSwitch();
		System.out.println("Switch state changed to "+ s.state);
	}
}