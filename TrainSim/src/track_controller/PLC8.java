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
	public boolean ctrlCrossing(TrainRoute r) {
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
}