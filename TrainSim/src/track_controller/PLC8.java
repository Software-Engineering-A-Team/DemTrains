package track_controller;
import ctc_office.TrainRoute;
import track_model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.PriorityQueue;

public class PLC8 implements PLCInterface {
	HashMap<Integer, TrackBlock> controlledBlocks;

	public PLC8(HashMap<Integer, TrackBlock> blockList){
		this.controlledBlocks = blockList;
	}	
	
	/*
	 * No crossings in this section.
	 */
	public boolean ctrlCrossing() {
		return false;
	}
	
	/*
	 * No switches in this section.
	 */
	public boolean ctrlSwitch(TrainRoute r) {
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
		if (b.hasFailure() && !b.occupancy) return true;
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