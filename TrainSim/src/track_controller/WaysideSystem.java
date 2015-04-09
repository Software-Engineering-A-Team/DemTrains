package track_controller;
import track_model.*;
import ctc_office.*;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class WaysideSystem {
	public TrackModel tracks;
	public String failure = null;
	public HashMap<Integer, WaysideController> blockControllerMapGreen = new HashMap<Integer, WaysideController>();
	public HashMap<Integer, WaysideController> blockControllerMapRed = new HashMap<Integer,WaysideController>();
	
	
	private boolean createGreenControllers(track_model.TrackLayout t) {
		ArrayList<TrackBlock> tc1 = new ArrayList<TrackBlock>();
		WaysideController wc1 = new WaysideController();
		for (int i=0; i<16; i++) { 
			tc1.add(t.blocks.get(i));
			blockControllerMapGreen.put(i, wc1);
		}
		wc1.addBlocks(tc1);
		PLC1 plc1 = new PLC1(tc1);
		wc1.plc = plc1;
		
		WaysideController wc2 = new WaysideController();
		ArrayList<TrackBlock> tc2 = new ArrayList<TrackBlock>();
		for (int i=15; i<23; i++) {
			tc2.add(t.blocks.get(i));
			blockControllerMapGreen.put(i, wc2);
		}
		wc2.addBlocks(tc2);
		//PLC2 plc2 = new PLC2(tc2);
		//wc2.plc = plc2;
		
		
		WaysideController wc3 = new WaysideController();
		ArrayList<TrackBlock> tc3 = new ArrayList<TrackBlock>();
		for (int i=22; i<32; i++) {
			tc3.add(t.blocks.get(i));
			blockControllerMapGreen.put(i, wc3);
		}
		tc3.add(t.blocks.get(149));
		blockControllerMapGreen.put(149, wc3);
		tc3.add(t.blocks.get(148));
		blockControllerMapGreen.put(148, wc3);
		tc3.add(t.blocks.get(147));
		blockControllerMapGreen.put(147, wc3);
		tc3.add(t.blocks.get(146));
		blockControllerMapGreen.put(146, wc3);
		wc3.addBlocks(tc3);
		
		WaysideController wc4 = new WaysideController();
		ArrayList<TrackBlock> tc4 = new ArrayList<TrackBlock>();
		for (int i=81; i<100; i++) {
			tc4.add(t.blocks.get(i));
			blockControllerMapGreen.put(i, wc4);
		}
		wc4.addBlocks(tc4);
		
		WaysideController wc5 = new WaysideController();
		ArrayList<TrackBlock> tc5 = new ArrayList<TrackBlock>();
		for (int i=73; i<82; i++) {
			tc5.add(t.blocks.get(i));
			blockControllerMapGreen.put(i, wc5);
		}
		tc5.add(t.blocks.get(100));
		blockControllerMapGreen.put(100, wc5);
		tc5.add(t.blocks.get(101));
		blockControllerMapGreen.put(101, wc5);
		tc5.add(t.blocks.get(102));
		blockControllerMapGreen.put(102, wc5);
		tc5.add(t.blocks.get(103));
		blockControllerMapGreen.put(103, wc5);
		wc5.addBlocks(tc5);
		
		
		WaysideController wc6 = new WaysideController();
		ArrayList<TrackBlock> tc6 = new ArrayList<TrackBlock>();
		for (int i=53; i<68; i++) {
			tc6.add(t.blocks.get(i));
			blockControllerMapGreen.put(i, wc6);
		}
		tc6.add(t.blocks.get(150));
		blockControllerMapGreen.put(150, wc6);
		tc6.add(t.blocks.get(151));
		blockControllerMapGreen.put(151, wc6);
		wc6.addBlocks(tc6);
		
		WaysideController wc7 = new WaysideController();
		ArrayList<TrackBlock> tc7 = new ArrayList<TrackBlock>();
		for (int i=31; i<54; i++) {
			tc7.add(t.blocks.get(i));
			blockControllerMapGreen.put(i, wc7);
		}
		wc7.addBlocks(tc7);
		
		WaysideController wc8 = new WaysideController();
		ArrayList<TrackBlock> tc8 = new ArrayList<TrackBlock>();
		for (int i=67; i<74; i++) {
			tc8.add(t.blocks.get(i));
			blockControllerMapGreen.put(i, wc8);
		}
		wc8.addBlocks(tc8);
		
		WaysideController wc9 = new WaysideController();
		ArrayList<TrackBlock> tc9 = new ArrayList<TrackBlock>();
		for (int i=103; i<147; i++) {
			tc9.add(t.blocks.get(i));
			blockControllerMapGreen.put(i, wc9);
		}
		wc9.addBlocks(tc9);
		return true;
	}
	
	private boolean createRedControllers(track_model.TrackLayout t) {
		return false;
	}
	
	/*
     * Creates wayside controllers for a given track layout
     * based on predetermined areas of control. Also adds a 
     * reference to the relevant controllers to each TrackBlock
     */
    private boolean createControllers(track_model.TrackLayout t) {
    	if (t.blocks.size() > 80) {
    		createGreenControllers(t);
    	}
    	else if (t.blocks.size() < 79) {
    		//createRedControllers();
    	}
        return false;
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
		boolean success = false; 
		if (line.equals("Green")) {
			WaysideController temp = blockControllerMapGreen.get(blockNum);
			success = temp.updatePLC(filename);
		}
		else if (line.equals("Red")) {
			WaysideController temp = blockControllerMapRed.get(blockNum);
			success = temp.updatePLC(filename);
		}
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
	
	public void runPLC(WaysideController w) {
		w.runPLC();
	}
}
