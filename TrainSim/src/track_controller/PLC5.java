package track_controller;
import ctc_office.TrainRoute;
import track_model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.PriorityQueue;

public class PLC5 implements PLCInterface {
	HashMap<Integer, TrackBlock> controlledBlocks;
	
	public PLC5(HashMap<Integer, TrackBlock> blockList){
		this.controlledBlocks = blockList;
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
	public boolean ctrlSwitch(TrainRoute r) {
		
		TrackSwitch relSwitch = (TrackSwitch) controlledBlocks.get(76);
			
		if(r.route != null){
			//compute nextBlock val
			int indNextBlock = r.route.indexOf(76)+1;
			int nextBlock = r.route.get(indNextBlock);			
			
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
				if(!relSwitch.state) {}
				
				return relSwitch.state;
			}
		}
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
		if(b.occupancy || b.hasFailure()) return true;
		else return false;
	}
	/*
	 * Determines safe speed and authority and returns 
	 * 
	 */
	public boolean ctrlSpeedAuthority(TrainRoute r, double speed, double authority) { 
		TrackBlock b = controlledBlocks.get(r.startingBlock);
		double minSafeAuth = authority;
		if (speed > b.speedLimit) return false;
		for(int i : r.route){
			TrackBlock temp = controlledBlocks.get(i);
			minSafeAuth = minSafeAuth + temp.length;
			if(temp.occupancy) break;
		}
		if (authority < minSafeAuth) return false;
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
	public boolean checkRoute(TrainRoute r) {
		if (r.route == null) return false;
		for (int i : r.route) {
			if(!controlledBlocks.containsKey(i)){
				return false;
			}
			TrackBlock b = controlledBlocks.get(i);
			if(b.occupancy && i>10) return false;
		}
	 return true;
	}
	
	/*
	 * Determines if speed should be set to speed limit or 0;
	 */
	public boolean checkSpeed(TrainRoute r, double s) {
		boolean failureAhead = false;
		boolean possibleCrash = false;
		for (int i: r.route){
			TrackBlock b = controlledBlocks.get(r.route.get(i));
			if (b.hasFailure())failureAhead = true;
			if ((i-r.route.get(0) < 4) && b.occupancy) possibleCrash = true;
		}
		if (possibleCrash || failureAhead) return false;
		else return true;
	}
	/*
	 * Determines safe authority based on block occupancy
	 * either 
	 */
	public boolean checkAuthority(TrainRoute r, double a, double safeAuth) {
		boolean failureAhead = false;
		boolean possibleCrash = false;
		for (int i: r.route){
			TrackBlock b = controlledBlocks.get(r.route.get(i));
			if (b.hasFailure())failureAhead = true;
			if ((i-r.route.get(0) < 4) && b.occupancy) possibleCrash = true;
		}
		if(failureAhead || possibleCrash) return false;
		else { 
			double minSafeAuth = a;
			for(int i : r.route){
				TrackBlock temp = controlledBlocks.get(i);
				minSafeAuth = minSafeAuth + temp.length;
				if(temp.occupancy) break;
			}
			
			if (minSafeAuth < r.authority) return true;
			else return false;
		}
	}
}