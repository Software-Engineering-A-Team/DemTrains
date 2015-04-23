package track_controller;
import ctc_office.TrainRoute;
import track_model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.PriorityQueue;

public class PLC6 implements PLCInterface {
	HashMap<Integer, TrackBlock> controlledBlocks;
	TrackSwitch current;
	
	
	public PLC6(HashMap<Integer, TrackBlock> blockList){
		this.controlledBlocks = blockList;
		current = (TrackSwitch) controlledBlocks.get(58);
	}	
	
		
	/*
	 * Determines safe state of the railway crossing and returns the state
	 * true for active, false for inactive
	 */
	public boolean ctrlCrossing(TrainRoute r) {
		return true;
	}
	/*
	 * Determines safe state of the switch and returns the state
	 * true for second block in attach array , false for first block in attach array
	 */
	public boolean ctrlSwitch(TrainRoute r) {
				
		TrackSwitch relSwitch = (TrackSwitch) controlledBlocks.get(62);
		TrackSwitch relSwitch2 = (TrackSwitch) controlledBlocks.get(58);
		if(r.route != null){
			TrackSwitch current = relSwitch2;
			//compute nextBlock val
			int indNextBlock = r.route.indexOf(76)+1;
			int nextBlock = r.route.get(indNextBlock);			
			
			// if train on 55,56,57 and nothing on 59 and next block after 58 in route is 59
			//set switch connected to block 59
			if((controlledBlocks.get(55).occupancy | controlledBlocks.get(56).occupancy | controlledBlocks.get(57).occupancy)
				& (!controlledBlocks.get(58).occupancy | !controlledBlocks.get(59).occupancy) &  nextBlock == 59){
				relSwitch2 = (TrackSwitch) controlledBlocks.get(58);
				relSwitch2.state = false;
				current = relSwitch2;
				
			}
			//if train on 55,56,57 and nothing on 151 and next block after 58 is 151
			//set switch connected to block 151
			else if ((controlledBlocks.get(55).occupancy | controlledBlocks.get(56).occupancy | controlledBlocks.get(57).occupancy) & 
					(!controlledBlocks.get(151).occupancy | !controlledBlocks.get(58).occupancy) & nextBlock == 151) {
				relSwitch2 = (TrackSwitch) controlledBlocks.get(58);
				relSwitch2.state = true;
				current = relSwitch2;
			}
			//if train on 162 and nothing on 61,60,59 and next block after 62 is 63
			//set switch connected to block 63
			else if ((controlledBlocks.get(162).occupancy) & (!controlledBlocks.get(61).occupancy | !controlledBlocks.get(60).occupancy| 
					!controlledBlocks.get(59).occupancy | !controlledBlocks.get(62).occupancy) & nextBlock == 63) {
				relSwitch = (TrackSwitch) controlledBlocks.get(62);
				relSwitch.state = false;
				current = relSwitch;
			}			
			//if train on 61,60,59 and nothing on 162 and next block after 62 is 63
			//set switch connected to block 63
			else if ((controlledBlocks.get(61).occupancy | controlledBlocks.get(60).occupancy | controlledBlocks.get(59).occupancy) & 
					(!controlledBlocks.get(162).occupancy | !controlledBlocks.get(62).occupancy) & nextBlock == 63) {
				relSwitch = (TrackSwitch) controlledBlocks.get(62);
				relSwitch.state = true;
				current=relSwitch;
			}
			//if there is a conflict, keep switch where it is and set speed and authority of conflicting
			//occupied blocks to 0
			else {
				if(r.route.contains(58)) current = relSwitch2;
				else if (r.route.contains(62)) current = relSwitch;
				else current = relSwitch2;
				return current.state;
			}
		}
		return false;
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