package track_controller;
import ctc_office.TrainRoute;
import track_model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class PLC8 implements PLCInterface {
	HashMap<Integer, TrackBlock> controlledBlocks;
	TrainRoute r;
	boolean switchCtrlSuccess = false;
		
	public PLC8(HashMap<Integer, TrackBlock> blockList, TrainRoute route){
		this.controlledBlocks = blockList;
		this.r = route;
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
	public boolean ctrlSwitch() {
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
		System.out.println("Running PLC7");
		for (int i = 68; i<75; i++) {
			TrackBlock b = controlledBlocks.get(i);
			b.heater = ctrlHeater(b);
			b.lights = ctrlLights(b);
		}
	}
}