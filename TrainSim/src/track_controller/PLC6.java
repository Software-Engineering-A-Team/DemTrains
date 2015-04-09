package track_controller;
import ctc_office.TrainRoute;
import track_model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.PriorityQueue;

public class PLC6 implements PLCInterface {
	HashMap<Integer, TrackBlock> controlledBlocks;
	public PriorityQueue<TrainRoute> routes = new PriorityQueue<TrainRoute>();
	boolean switchCtrlSuccess = false;
	TrackSwitch current;
	
	
	public PLC6(HashMap<Integer, TrackBlock> blockList, TrainRoute route){
		this.controlledBlocks = blockList;
		if(route!= null) this.routes.add(route);
		current = (TrackSwitch) controlledBlocks.get(58);
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
				
		TrackSwitch relSwitch = (TrackSwitch) controlledBlocks.get(62);
		TrackSwitch relSwitch2 = (TrackSwitch) controlledBlocks.get(58);
		if(this.routes.peek() != null){
			switchCtrlSuccess = true;
			TrackSwitch current = relSwitch2;
			//compute nextBlock val
			System.out.println(routes.peek().route.isEmpty());
			int indNextBlock = routes.peek().route.indexOf(76)+1;
			System.out.println(indNextBlock);
			int nextBlock = routes.peek().route.get(indNextBlock);
			System.out.println(nextBlock);
			//int prevBlock = r.route.get(r.route.indexOf(12)-1);
			
			
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
				System.out.println("No criteria met. In else.");
				if(this.routes.peek().route.contains(58)) current = relSwitch2;
				else current = relSwitch;
				return current.state;
			}
		}
		System.out.println("No criteria met.");
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
		if (this.routes.peek() == null) return false;
		for (int i : this.routes.peek().route) {
			TrackBlock b = controlledBlocks.get(i);
			if(b.occupancy) return false;
		}
	 return true;
	}
	
	public PriorityQueue<TrainRoute> getRoutes(){
		return this.routes;
	}
	
	/*
	 * Runs all functions of PLC Program
	 */
	public void run(){
		System.out.println("Running PLC6");
		for (int i = 54; i<69; i++) {
			TrackBlock b = controlledBlocks.get(i);
			b.heater = ctrlHeater(b);
			b.lights = ctrlLights(b);
		}
		
		for (int i = 151; i<153; i++) {
			TrackBlock b = controlledBlocks.get(i);
			b.heater = ctrlHeater(b);
			b.lights = ctrlLights(b);
		}
				
		boolean prevState = current.state;
		int ind;
		if(prevState) ind = 0;
		else ind = 1;
		System.out.println("Switch on block "+current.number+" moved from "+current.out[ind]); 
		current.state = ctrlSwitch();
		if(current.state) ind = 0;
		else ind = 1;
		System.out.print(" to " +current.out[ind]+"\n");
	}
}