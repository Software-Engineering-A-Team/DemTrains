package track_controller;
import ctc_office.TrainRoute;
import track_model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.PriorityQueue;

public class PLCA implements PLCInterface {
	private HashMap<Integer, TrackBlock> controlledBlocks;
	
	public PLCA(HashMap<Integer, TrackBlock> blockList){
		this.controlledBlocks = blockList;
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
	public boolean ctrlSwitch(TrainRoute r) {
		TrackSwitch relSwitch = (TrackSwitch) controlledBlocks.get(9);
		TrackSwitch relSwitch2 = (TrackSwitch) controlledBlocks.get(15);
		if(r.route != null){
			TrackSwitch current = relSwitch2;
			//compute nextBlock val
			int indNextBlock = r.route.indexOf(76)+1;
			int nextBlock = r.route.get(indNextBlock);			
			
			// if train on 8,7,6 and nothing on 10,11,12,77 and next block after 9 in route is 77
			//set switch connected to block 77
			if((controlledBlocks.get(8).occupancy || controlledBlocks.get(7).occupancy || controlledBlocks.get(6).occupancy)
				&& (!controlledBlocks.get(10).occupancy || !controlledBlocks.get(11).occupancy || !controlledBlocks.get(12).occupancy
				|| !controlledBlocks.get(9).occupancy || !controlledBlocks.get(77).occupancy) &&  nextBlock == 77){
				relSwitch.state = true;
				current = relSwitch;
				
			}
			//if train on 10,11,12 and nothing on 8,7,6,77 and next block after 9 is 77
			//set switch connected to block 77
			else if ((controlledBlocks.get(10).occupancy || controlledBlocks.get(11).occupancy || controlledBlocks.get(12).occupancy)
					&& (!controlledBlocks.get(8).occupancy || !controlledBlocks.get(7).occupancy || !controlledBlocks.get(6).occupancy
							|| !controlledBlocks.get(9).occupancy || !controlledBlocks.get(77).occupancy) &&  nextBlock == 77){
							relSwitch.state = false;
							current = relSwitch;
							
			}
			
			//if train on 77 and nothing on 8,7,6,10,11,12 and next block after 9 is 10
			//set switch connected to block 10
			else if (controlledBlocks.get(77).occupancy && (!controlledBlocks.get(8).occupancy || !controlledBlocks.get(7).occupancy 
					|| !controlledBlocks.get(6).occupancy|| !controlledBlocks.get(9).occupancy || !controlledBlocks.get(10).occupancy
					|| !controlledBlocks.get(11).occupancy || !controlledBlocks.get(12).occupancy) &&  nextBlock == 10){
							relSwitch.state = false;
							current = relSwitch;
							
			}
			
			//if train on 77 and nothing on 10,11,12,8,7,6 and next block after 9 is 8
			//set switch connected to block 10
			else if (controlledBlocks.get(77).occupancy && (!controlledBlocks.get(10).occupancy || !controlledBlocks.get(11).occupancy 
					|| !controlledBlocks.get(12).occupancy|| !controlledBlocks.get(9).occupancy || !controlledBlocks.get(8).occupancy
					|| !controlledBlocks.get(7).occupancy || !controlledBlocks.get(6).occupancy) &&  nextBlock == 8){
							relSwitch.state = true;
							current = relSwitch;
							
			}
			
			//1 to 14
			//if train on 1,2,3 and nothing on 18,17,16,14,13,12 and next block after 15 is 14
			//set switch connected to block 1
			else if ((controlledBlocks.get(1).occupancy || controlledBlocks.get(2).occupancy || controlledBlocks.get(3).occupancy ) &&  
					(!controlledBlocks.get(18).occupancy || !controlledBlocks.get(17).occupancy ||!controlledBlocks.get(16).occupancy 
					|| !controlledBlocks.get(14).occupancy ||!controlledBlocks.get(13).occupancy || !controlledBlocks.get(12).occupancy 
					|| !controlledBlocks.get(15).occupancy) && nextBlock == 14) {
				relSwitch2.state = false;
				current = relSwitch2;
			}	
			//14 to 16
			//if train on 12,13,14 and nothing on 1,2,3,18,17,16 and next block after 15 is 16
			//set switch connected to block 16
			else if ((controlledBlocks.get(12).occupancy || controlledBlocks.get(13).occupancy || controlledBlocks.get(14).occupancy) && 
					(!controlledBlocks.get(1).occupancy || !controlledBlocks.get(2).occupancy ||!controlledBlocks.get(3).occupancy 
					|| !controlledBlocks.get(18).occupancy ||!controlledBlocks.get(17).occupancy || !controlledBlocks.get(16).occupancy 
					|| !controlledBlocks.get(15).occupancy) && nextBlock == 16) {
				relSwitch2.state = false;
				current = relSwitch2;
			}
			//16 to 14
			//if train on 17,18,16 and nothing on 1,2,3,14,13,12 and next block after 15 is 14
			//set switch connected to block 16
			else if ((controlledBlocks.get(17).occupancy || controlledBlocks.get(18).occupancy || controlledBlocks.get(16).occupancy) && 
					(!controlledBlocks.get(1).occupancy || !controlledBlocks.get(2).occupancy ||!controlledBlocks.get(3).occupancy 
					|| !controlledBlocks.get(14).occupancy ||!controlledBlocks.get(13).occupancy || !controlledBlocks.get(12).occupancy 
					|| !controlledBlocks.get(15).occupancy) && nextBlock == 16) {
						relSwitch2.state = false;
						current = relSwitch2;
			}	
			//14 to 1
			//if train on 12,13,14 and nothing on 1,2,3,16,17,18 and next block after 15 is 1
			//set switch connected to block 1
			else if ((controlledBlocks.get(12).occupancy) && (!controlledBlocks.get(13).occupancy || !controlledBlocks.get(14).occupancy|| 
					!controlledBlocks.get(1).occupancy || !controlledBlocks.get(2).occupancy ||!controlledBlocks.get(3).occupancy 
					|| !controlledBlocks.get(18).occupancy ||!controlledBlocks.get(17).occupancy || !controlledBlocks.get(16).occupancy 
					|| !controlledBlocks.get(15).occupancy) && nextBlock == 1) {
					relSwitch2.state = false;
					current = relSwitch2;
			}	
			
			//if there is a conflict, keep switch where it is and set speed and authority of conflicting
			//occupied blocks to 0
			else {
				if(r.route.contains(9)) current = relSwitch;
				else if (r.route.contains(15)) current = relSwitch2;
				else current = relSwitch;
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
			if(!controlledBlocks.containsKey(i)){
				return false;
			}
			TrackBlock b = controlledBlocks.get(i);
			if(b.occupancy && i>10) return false;
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
