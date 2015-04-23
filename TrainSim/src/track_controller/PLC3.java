package track_controller;
import ctc_office.TrainRoute;
import track_model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.PriorityQueue;

public class PLC3 implements PLCInterface {
	HashMap<Integer, TrackBlock> controlledBlocks;
	
	
	public PLC3(HashMap<Integer, TrackBlock> blockList){
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
		
		TrackSwitch relSwitch = (TrackSwitch) controlledBlocks.get(29);
			//compute nextBlock val
		if(r.route != null){
			int indNextBlock = r.route.indexOf(29)+1;
			int nextBlock = r.route.get(indNextBlock);			
			
			// if train on 25,26,27 and nothing else within range and next block after 29 in route is 30
			//set switch connected to block 30
			if((controlledBlocks.get(26).occupancy | controlledBlocks.get(27).occupancy | controlledBlocks.get(28).occupancy)
				& (!controlledBlocks.get(148).occupancy | !controlledBlocks.get(149).occupancy| !controlledBlocks.get(150).occupancy 
				| !controlledBlocks.get(29).occupancy) &  nextBlock == 30){
				relSwitch = (TrackSwitch) controlledBlocks.get(29);
				relSwitch.state = false;
			}
			//if train on 148,149,150 and nothing else within range and next block after 29 is 28
			//set switch connected to block 28
			else if ((controlledBlocks.get(148).occupancy | controlledBlocks.get(149).occupancy | controlledBlocks.get(150).occupancy)
					& (!controlledBlocks.get(26).occupancy | !controlledBlocks.get(27).occupancy| !controlledBlocks.get(28).occupancy 
							  | !controlledBlocks.get(29).occupancy) & nextBlock == 28) {
				relSwitch = (TrackSwitch) controlledBlocks.get(29);
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
		if (speed > b.speedLimit) return false;
		if (authority > b.length) return false;
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
			TrackBlock b = controlledBlocks.get(i);
			if(b.occupancy) return false;
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
		
		return false;
	}
}
