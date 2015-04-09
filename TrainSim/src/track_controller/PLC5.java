package track_controller;
import ctc_office.TrainRoute;
import track_model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.PriorityQueue;

public class PLC5 implements PLCInterface {
	HashMap<Integer, TrackBlock> controlledBlocks;
	public PriorityQueue<TrainRoute> routes = new PriorityQueue<TrainRoute>();
	boolean switchCtrlSuccess = false;
	
	
	public PLC5(HashMap<Integer, TrackBlock> blockList, TrainRoute route){
		this.controlledBlocks = blockList;
		if(route!= null) this.routes.add(route);
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
			
		if(this.routes.peek() != null){
			switchCtrlSuccess = true;
			//compute nextBlock val
			int indNextBlock = routes.peek().route.indexOf(76)+1;
			int nextBlock = routes.peek().route.get(indNextBlock);			
			
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
		if (this.routes.peek() == null) return false;
		for (int i : this.routes.peek().route) {
			TrackBlock b = controlledBlocks.get(i);
			if(b.occupancy) return false;
		}
	 return true;
	}
	
	/*
	 * Changes the route.
	 */
	public void changeRoute(TrainRoute route) {
		this.routes.add(route);
	}
	
	public boolean switchCtrl() {
		if (this.routes.peek() != null) return true;
		else return false;
	}
	
	public PriorityQueue<TrainRoute> getRoutes(){
		return this.routes;
	}
	
	/*
	 * Runs all functions of PLC Program
	 */
	public void run(){
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
		boolean prevState = s.state;
		int ind, ind1;
		if(prevState) ind = 0;
		else ind = 1;
		s.state = ctrlSwitch();
		if(s.state) ind1 = 0;
		else ind1 = 1;
		if (prevState != s.state) System.out.print("Switch on block 76 moved from "+s.out[ind] + " to " +s.out[ind1]);
	}
}