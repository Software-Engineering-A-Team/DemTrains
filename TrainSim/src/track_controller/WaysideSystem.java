package track_controller;
import track_model.*;
import ctc_office.*;

import java.util.HashMap;

public class WaysideSystem {
	TrackModel tracks;
	String failure = null;
	HashMap<String, HashMap<Integer, WaysideController>> controllerMap;
	
	/*
     * Creates wayside controllers for a given track layout
     * based on predetermined areas of control. Also adds a 
     * reference to the relevant controllers to each TrackBlock
     */
    private boolean createControllers(track_model.TrackLayout t) {
        return true;
    }
    
	
	
	/*
	 * Constructs a wayside system with a given track model
	 */
	public WaysideSystem(TrackModel t) {
	  //store track model
	  tracks = t;
	  //get lines from track model
	  track_model.TrackLayout green = t.getLine("Green");
	  track_model.TrackLayout red = t.getLine("Red");
	  //createControllers for the lines
	  createControllers(green);
	  createControllers(red);	  
	}
	
	
	/*
	 * Updates the PLCProgram for the controller of
	 * the block number provided
	 */
	public boolean updatePLC(String line, int blockNum, String filename) {
		WaysideController temp = controllerMap.get(line).get(blockNum);
		boolean success = temp.updatePLC(filename);
		return success;
	}
	
	/*
	 * Runs PLC to determine if it is safe
	 * to close the provided block number. 
	 * Can be called by user or CTC. 
	 */
	public boolean setBlockClosed(int blockNum) {
		return true;
	}
	
	
	/*
	 * Runs PLC to determine if it is safe
	 * to break the provided block number. 
	 * Can be called by user or TrackModel. 
	 */
	public boolean setBlockBroken(int blockNum) {
		return true;
	}
	
	/*
	 * Runs PLC to determine if it is safe
	 * to place a train on the provided block number. 
	 * Can be called by user or CTC. 
	 */
	public boolean setOccupancy(int blockNum) {
		return true;
	}

	
	/*
	 * Receives string failure mode from TrackModel
	 * and informs the wayside controller to effectively handle
	 * the failure
	 */
	public boolean setFailureMode (int blockNum, String failureMode) {
		return true;
	}
	
	/*
	 * Called by CTC to add a route
	 * to a given wayside controller 
	 */	
	public TrainRoute addRoute(TrainRoute r, int startBlock) {
		return null;
	}
	
	/*
	 * Called by CTC to set beacon on a given block 
	 */
	public boolean setBeacon(String beacon, int blockNum) {
		return true;
	}
}
