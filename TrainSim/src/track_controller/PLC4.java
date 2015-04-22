package track_controller;
import ctc_office.TrainRoute;
import track_model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.PriorityQueue;

public class PLC4 implements PLCInterface {
	HashMap<Integer, TrackBlock> controlledBlocks;
	
	public PLC4(HashMap<Integer, TrackBlock> blockList){
		this.controlledBlocks = blockList;
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
		
		TrackSwitch relSwitch = (TrackSwitch) controlledBlocks.get(86);
		if(r.route != null){
			//compute nextBlock val
			int indNextBlock = r.route.indexOf(86)+1;
			int nextBlock = r.route.get(indNextBlock);			
			
			// if train on 85,84,83 and nothing else within range and next block after 86 in route is 87
			//set switch connected to block 87
			if((controlledBlocks.get(85).occupancy | controlledBlocks.get(84).occupancy | controlledBlocks.get(83).occupancy)
				& (!controlledBlocks.get(100).occupancy | !controlledBlocks.get(99).occupancy| !controlledBlocks.get(98).occupancy 
				| !controlledBlocks.get(86).occupancy) &  nextBlock == 87){
				relSwitch = (TrackSwitch) controlledBlocks.get(86);
				relSwitch.state = false;
			}
			//if train on 98,99,100 and nothing else within range and next block after 86 is 85
			//set switch connected to block 85
			else if ((controlledBlocks.get(98).occupancy | controlledBlocks.get(99).occupancy | controlledBlocks.get(100).occupancy)
					& (!controlledBlocks.get(85).occupancy | !controlledBlocks.get(84).occupancy| !controlledBlocks.get(83).occupancy 
							  | !controlledBlocks.get(86).occupancy) & nextBlock == 85) {
				relSwitch = (TrackSwitch) controlledBlocks.get(86);
				relSwitch.state = true;
			}
			//if there is a conflict, keep switch where it is and set speed and authority of conflicting
			//occupied blocks to 0
			else {
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
	public boolean checkRoute(TrainRoute r) {
		if (r.route == null) return false;
		for (int i : r.route) {
			TrackBlock b = controlledBlocks.get(i);
			if(b.occupancy) return false;
		}
	 return true;
	}
	
	
	/*
	 * Runs all functions of PLC Program
	 */
	public void run(){
		for (int i = 82; i<101; i++) {
			TrackBlock b = controlledBlocks.get(i);
			b.heater = ctrlHeater(b);
			b.lights = ctrlLights(b);
		}
	}
}