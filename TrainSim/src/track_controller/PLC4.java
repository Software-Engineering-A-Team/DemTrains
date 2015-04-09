package track_controller;
import ctc_office.TrainRoute;
import track_model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.PriorityQueue;

public class PLC4 implements PLCInterface {
	HashMap<Integer, TrackBlock> controlledBlocks;
	public PriorityQueue<TrainRoute> routes = new PriorityQueue<TrainRoute>();
	boolean switchCtrlSuccess = false;
	
	public PLC4(HashMap<Integer, TrackBlock> blockList, TrainRoute route){
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
		
		TrackSwitch relSwitch = (TrackSwitch) controlledBlocks.get(86);
		if(this.routes.peek() != null){
			switchCtrlSuccess = true;
			//compute nextBlock val
			System.out.println(routes.peek().route.isEmpty());
			int indNextBlock = routes.peek().route.indexOf(86)+1;
			System.out.println(indNextBlock);
			int nextBlock = routes.peek().route.get(indNextBlock);
			System.out.println(nextBlock);
			//int prevBlock = r.route.get(r.route.indexOf(12)-1);
			
			
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
				System.out.println("No criteria met. In else.");
				if(!relSwitch.state) {}
				
				return relSwitch.state;
			}
		}
		System.out.println("No criteria met.");
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
		System.out.println("Running PLC1");
		for (int i = 82; i<101; i++) {
			TrackBlock b = controlledBlocks.get(i);
			b.heater = ctrlHeater(b);
			b.lights = ctrlLights(b);
		}
		
		TrackSwitch s = (TrackSwitch) controlledBlocks.get(86);
		boolean prevState = s.state;
		int ind;
		if(prevState) ind = 0;
		else ind = 1;
		System.out.println("Switch on block 86 moved from "+s.out[ind]); 
		s.state = ctrlSwitch();
		if(s.state) ind = 0;
		else ind = 1;
		System.out.print(" to " +s.out[ind]+"\n");
	}
}