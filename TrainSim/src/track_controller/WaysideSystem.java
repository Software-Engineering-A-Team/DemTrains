package track_controller;
import track_model.*;
import ctc_office.*;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class WaysideSystem {
	public  TrackModel tracks;
	public  HashMap<Integer, WaysideController> blockControllerMapGreen = new HashMap<Integer, WaysideController>();
	public  HashMap<Integer, WaysideController> blockControllerMapRed = new HashMap<Integer,WaysideController>();
	
	/*
	 * Creates track controllers for Green Line
	 * determined by set groupings.
	 */
	private boolean createGreenControllers(track_model.TrackLayout t) {
		ArrayList<TrackBlock> tc1 = new ArrayList<TrackBlock>();
		WaysideController wc1 = new WaysideController();
		for (int i=1; i<17; i++) { 
			tc1.add(t.blocks.get(i));
			blockControllerMapGreen.put(i, wc1);
		}
		wc1.addBlocks(tc1);
		PLC1 plc1 = new PLC1(wc1.blockMap);
		wc1.plc = plc1;
		wc1.containsSwitch = true;
		
		WaysideController wc2 = new WaysideController();
		ArrayList<TrackBlock> tc2 = new ArrayList<TrackBlock>();
		for (int i=16; i<24; i++) {
			tc2.add(t.blocks.get(i));
			blockControllerMapGreen.put(i, wc2);
		}
		wc2.addBlocks(tc2);
		PLC2 plc2 = new PLC2(wc2.blockMap);
		wc2.plc = plc2;
		wc2.containsCrossing = true;
		
		
		WaysideController wc3 = new WaysideController();
		ArrayList<TrackBlock> tc3 = new ArrayList<TrackBlock>();
		for (int i=23; i<33; i++) {
			tc3.add(t.blocks.get(i));
			blockControllerMapGreen.put(i, wc3);
		}
		tc3.add(t.blocks.get(150));
		blockControllerMapGreen.put(150, wc3);
		tc3.add(t.blocks.get(149));
		blockControllerMapGreen.put(149, wc3);
		tc3.add(t.blocks.get(148));
		blockControllerMapGreen.put(148, wc3);
		tc3.add(t.blocks.get(147));
		blockControllerMapGreen.put(147, wc3);
		wc3.addBlocks(tc3);
		PLC3 plc3 = new PLC3(wc3.blockMap);
		wc3.plc = plc3;
		wc3.containsSwitch = true;
		
		WaysideController wc4 = new WaysideController();
		ArrayList<TrackBlock> tc4 = new ArrayList<TrackBlock>();
		for (int i=82; i<101; i++) {
			tc4.add(t.blocks.get(i));
			blockControllerMapGreen.put(i, wc4);
		}
		wc4.addBlocks(tc4);
		PLC4 plc4 = new PLC4(wc4.blockMap);
		wc4.plc = plc4;
		wc4.containsSwitch = true;
		
		WaysideController wc5 = new WaysideController();
		ArrayList<TrackBlock> tc5 = new ArrayList<TrackBlock>();
		for (int i=74; i<83; i++) {
			tc5.add(t.blocks.get(i));
			blockControllerMapGreen.put(i, wc5);
		}
		tc5.add(t.blocks.get(101));
		blockControllerMapGreen.put(101, wc5);
		tc5.add(t.blocks.get(102));
		blockControllerMapGreen.put(102, wc5);
		tc5.add(t.blocks.get(103));
		blockControllerMapGreen.put(103, wc5);
		tc5.add(t.blocks.get(104));
		blockControllerMapGreen.put(104, wc5);
		wc5.addBlocks(tc5);
		PLC5 plc5 = new PLC5(wc5.blockMap);
		wc5.plc = plc5;
		wc5.containsSwitch = true;
		
		WaysideController wc6 = new WaysideController();
		ArrayList<TrackBlock> tc6 = new ArrayList<TrackBlock>();
		for (int i=54; i<69; i++) {
			tc6.add(t.blocks.get(i));
			blockControllerMapGreen.put(i, wc6);
		}
		tc6.add(t.blocks.get(151));
		blockControllerMapGreen.put(151, wc6);
		tc6.add(t.blocks.get(152));
		blockControllerMapGreen.put(152, wc6);
		wc6.addBlocks(tc6);
		if(!wc6.blockMap.isEmpty()) {
			PLC6 plc6 = new PLC6(wc6.blockMap);
			wc6.plc = plc6;
			wc6.containsSwitch = true;
		}
		WaysideController wc7 = new WaysideController();
		ArrayList<TrackBlock> tc7 = new ArrayList<TrackBlock>();
		for (int i=32; i<55; i++) {
			tc7.add(t.blocks.get(i));
			blockControllerMapGreen.put(i, wc7);
		}
		wc7.addBlocks(tc7);
		PLC7 plc7 = new PLC7(wc7.blockMap);
		wc7.plc = plc7;
		
		WaysideController wc8 = new WaysideController();
		ArrayList<TrackBlock> tc8 = new ArrayList<TrackBlock>();
		for (int i=68; i<75; i++) {
			tc8.add(t.blocks.get(i));
			blockControllerMapGreen.put(i, wc8);
		}
		wc8.addBlocks(tc8);
		PLC8 plc8 = new PLC8(wc8.blockMap);
		wc8.plc = plc8;
		
		
		WaysideController wc9 = new WaysideController();
		ArrayList<TrackBlock> tc9 = new ArrayList<TrackBlock>();
		for (int i=104; i<148; i++) {
			tc9.add(t.blocks.get(i));
			blockControllerMapGreen.put(i, wc9);
		}
		wc9.addBlocks(tc9);
		PLC9 plc9 = new PLC9(wc9.blockMap);
		wc9.plc = plc9;
		System.out.println("Done creating controllers.");
		return true;
	}
	
	/*
	 * Creates controllers for red line determined
	 * by set groupings based on track layout.
	 */
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
    		createRedControllers(t);
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
	  track_model.TrackLayout green = t.trackLayouts.get("Green");
	  track_model.TrackLayout red = t.trackLayouts.get("Red");
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
	public boolean setBlockClosed(String line, int blockNum) {
		TrackBlock b;
		WaysideController w;
		if (line.equals("Green")) {
			b = tracks.trackLayouts.get("Green").blocks.get(blockNum);
			w = blockControllerMapGreen.get(blockNum);
		}
		else if(line.equals("Red")) {
			b = tracks.trackLayouts.get("Red").blocks.get(blockNum);
			w = blockControllerMapRed.get(blockNum);
		}
		else {
			System.out.println("No such line exists.");
			return false;
		}
		
		boolean plc1Result = w.plc.ctrlBlockClosed(b);
		boolean plc2Result = w.plc.ctrlBlockClosed(b);
		boolean outcome = (plc1Result & plc2Result);
		
		return outcome;
	}
	
	
	/*
	 * Runs PLC to determine if it is safe
	 * to break the provided block number. 
	 * Can be called by user or TrackModel. 
	 */
	public boolean setBlockBroken(String line, int blockNum) {
		TrackBlock b;
		if (line.equals("Green")) {
			b = tracks.trackLayouts.get("Green").blocks.get(blockNum);
		}
		else if(line.equals("Red")) {
			b = tracks.trackLayouts.get("Red").blocks.get(blockNum);
		}
		else {
			System.out.println("No such line exists.");
			return false;
		}
		b.failure = "Broken block";
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
	public boolean setFailureMode (String line, int blockNum, String failureMode) {
		TrackBlock b;
		if (line.equals("Green")) {
			b = tracks.trackLayouts.get("Green").blocks.get(blockNum);
		}
		else if(line.equals("Red")) {
			b = tracks.trackLayouts.get("Red").blocks.get(blockNum);
		}
		else {
			System.out.println("No such line exists.");
			return false;
		}
		b.failure = failureMode;
		return true;
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
