package track_controller;
import ctc_office.TrainRoute;
import track_model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.PriorityQueue;

public class PLC2 implements PLCInterface {
	HashMap<Integer, TrackBlock> controlledBlocks;

		
	public PLC2(HashMap<Integer, TrackBlock> blockList){
		this.controlledBlocks = blockList;

	}	
	
	/*
	 * Determines safe state of the railway crossing and returns the state
	 * true for active, false for inactive
	 */
	public boolean ctrlCrossing(TrainRoute r) {
		//if any of the affected blocks are occupied crossing is active
		if(controlledBlocks.get(16).occupancy | controlledBlocks.get(17).occupancy | controlledBlocks.get(18).occupancy
				| controlledBlocks.get(18).occupancy | controlledBlocks.get(19).occupancy | controlledBlocks.get(20).occupancy 
				|controlledBlocks.get(21).occupancy | controlledBlocks.get(22).occupancy | controlledBlocks.get(23).occupancy) {
			return true;
		}
		//otherwise it's inactive
		else {
			return false;
		}
	}
	
	/*
	 * Determines safe state of the switch and returns the state
	 * true for second block in attach array , false for first block in attach array
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
}
