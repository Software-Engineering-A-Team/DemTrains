package track_controller;
import ctc_office.TrainRoute;
import track_model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class PLC1 implements PLCInterface {
	HashMap<Integer, TrackBlock> controlledBlocks;
	TrainRoute r;
	
	
	public PLC1(HashMap<Integer, TrackBlock> blockList, TrainRoute route){
		controlledBlocks = blockList;
		r = route;
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
		//compute nextBlock val
		int nextBlock = r.route.get(r.route.indexOf(12)+1);
		//int prevBlock = r.route.get(r.route.indexOf(12)-1);
		TrackSwitch relSwitch = (TrackSwitch) controlledBlocks.get(12);
		
		// if train on 15, 14, or 13 and nothing else within range and next block after 12 in route is 11
		//set switch connected to block 11
		if((controlledBlocks.get(15).occupancy | controlledBlocks.get(14).occupancy | controlledBlocks.get(13).occupancy)
			& (!controlledBlocks.get(12).occupancy | controlledBlocks.get(11).occupancy| controlledBlocks.get(10).occupancy 
			  | controlledBlocks.get(1).occupancy | controlledBlocks.get(2).occupancy | controlledBlocks.get(3).occupancy) &
			  nextBlock == 11){
			relSwitch = (TrackSwitch) controlledBlocks.get(12);
			relSwitch.state = false;
		}
		//if train on 1, 2, or 3 and nothing else within range and next block after 12 is 13
		//set switch connected to block 1
		else if ((controlledBlocks.get(1).occupancy | controlledBlocks.get(2).occupancy | controlledBlocks.get(3).occupancy)
				& (!controlledBlocks.get(12).occupancy | controlledBlocks.get(11).occupancy| controlledBlocks.get(10).occupancy 
						  | controlledBlocks.get(15).occupancy | controlledBlocks.get(14).occupancy | controlledBlocks.get(13).occupancy) &
						  nextBlock == 13) {
			relSwitch = (TrackSwitch) controlledBlocks.get(12);
			relSwitch.state = true;
		}
		//if there is a conflict, keep switch where it is and set speed and authority of conflicting
		//occupied blocks to 0
		else {
			if(!relSwitch.state) {}
			
			return relSwitch.state;
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
	 * Runs all functions of PLC Program
	 */
	public void run(){
		System.out.println("Running PLC1");
		for (int i = 1; i<17; i++) {
			TrackBlock b = controlledBlocks.get(i);
			b.heater = ctrlHeater(b);
			b.lights = ctrlLights(b);
		}
		
	}
}
