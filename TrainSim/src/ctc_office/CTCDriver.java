package ctc_office;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedMultigraph;

import track_controller.WaysideController;

public class CTCDriver {
	private HashMap <String, TrackLayout> lines;
	private HashMap <String, HashSet<String>> manuallyRoutedTrains = new HashMap <String, HashSet<String>>();
	public boolean MBOModeEnabled = false;

	public CTCDriver() {
		lines = new HashMap<String, TrackLayout>();
		manuallyRoutedTrains = new HashMap<String, HashSet<String>>();
	}

	/**
	 * Completes all of the functions of the CTC including routing and dispatching new trains
	 */
	 public boolean runCTC() {
	   // for each track
	   for (TrackLayout t : lines.values()) {
	       // if in fixed block mode, calculate the estimated positions of those trains.
	       if (!MBOModeEnabled) {
	           t.setEstimatedTrainLocations();
	       }
	       // dispatch all new trains and calculate their routes.
	       t.dispatchNewTrains();
	       t.routeTrainFromYard();
	       t.getUpdatedTrainRoutes();
	       t.updateBeaconStrings();
	   }
	 	return true;
	 }
	/**
	  * Completes all of the functions of the CTC including routing and dispatching new trains
	  */
	public boolean runCTCStandalone() {
		// for each track
		for (TrackLayout t : lines.values()) {
		// if in fixed block mode, calculate the estimated positions of those trains.
		   t.setEstimatedTrainLocations();
		   // dispatch all new trains and calculate their routes.
		   //t.dispatchNewTrains();
		   t.routeTrainFromYard();
		   t.getUpdatedTrainRoutes();
		   t.updateBeaconStrings();
		}
		 return true;
	}
	/**
	 * Sets the trackLayout and the scheduler to fixed block mode
	 */
	public boolean disableMBOMode() {
		for(TrackLayout t : lines.values())	{
			t.disableMBOMode();
		}
		MBOModeEnabled = false;
		return true;
	}

	/**
	 * Sets the trackLayout and the scheduler to MBO mode
	 */
	public boolean enableMBOMode() {
		for(TrackLayout t : lines.values())	{
			t.enableMBOMode();
		}
		MBOModeEnabled = true;
		return true;
	}

	 /**
	  * returns the trackLayout for all of the lines
	  */
	public HashMap<String, TrackLayout> getAllTrackLayouts() {
		return lines;
	}
	
	 /**
	  * returns the trackLayout for the line name passed in
	  */
	public TrackLayout getTrackLayout(String lineName) {
		return lines.get(lineName);
	}

	/**
	 * Manually spawns a new train with the routing data that was passed into the method.
	 * Returns false if a train with that name already exists.
	 */
	public boolean manuallyDispatchNewTrain(String lineName, String trainName, int destinationBlock, double speed, double authority) {
		if (lines.get(lineName).dispatchTrain(trainName, new BlockStopData(destinationBlock, speed, authority))) {
			manuallyRoutedTrains.get(lineName).add(trainName);
			return true;
		}
		return false;
	}

	/**
	 * Manually spawns a new train with the routing data that was passed into the method at the fastest speed possible.
	 * Returns false if a train with that name already exists.
	 */
	public boolean manuallyDispatchNewTrain(String lineName, String trainName, int destinationBlock) {
			
		return this.manuallyDispatchNewTrain(lineName, trainName, destinationBlock, Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	/*
	 * Manually routes a train to its destination block at the user specified speed.
	 * Only trains created manually can be manually routed.
	 */
	public boolean manuallyRouteTrain(String lineName, String trainName, int destinationBlock, double speed, double authority){
		lines.get(lineName).manuallyRouteTrain(trainName, destinationBlock, speed, authority);
		return true;
	}

	/**
	 * Manually routes a train to its destination as fast as possible.
	 * Only trains created manually can be manually routed.
	 */
	public boolean manuallyRouteTrain(String lineName, String trainName, int destinationBlock){
		return this.manuallyRouteTrain(lineName, trainName, destinationBlock, Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	/**
	 * Sends the train routes to the Track Controller
	 * If a route is not approved, it is updated in the CTC
	 */
	public void routeTrains(String lineName) {
		ArrayList<TrainRoute> routes = lines.get(lineName).getUpdatedTrainRoutes();		
	}

	/**
	 * Used by the MBO to set the actual locations of the trains.
	 * Takes in the total distance each train has traveled and calculates the new location.
	 * Calculates all of the new distances at once
	 */
	public boolean setActualTrainLocations(String lineName, HashMap<String, Double> trainDistanceMap) {
		//lines.get(lineName).setActualTrainLocations(trainDistanceMap);
		return true;
	}

	/**
	 * Sets block blockNumber on line lineName as Broken or not true.
	 * If status is true, the track will be set to broken
	 * If the status is false, the track will be set to not broken
	 */
	public boolean setBrokenTrackStatus(String lineName, int blockNumber, boolean status) {
		return lines.get(lineName).setBlockBrokenStatus(blockNumber, status);
	}

	/**
	 * For the line specified in lineName, this method
	 * takes in the filename of a csv file with lines in the
	 * format <Station Name>, <Time until next stop including dwell time>
	 * Creates a schedule from the data that will be used for routing trains
	 * when in fixed block mode.
	 */
	public void setDefaultSchedule(String lineName, String filename) throws IOException {
		lines.get(lineName).setDefaultSchedule(filename);
	}

	/**
	 * For the line specified in lineName, this method
	 * takes in the schedule formatted as a csv
	 * format <Station Name>, <Total Time elapsed before the train departs the destination station>
	 * Creates a schedule from the data that will be used for routing trains
	 * when in fixed block mode.
	 */
	public void setMBOSchedule(String lineName, List<String> commaSeparatedList) {
		lines.get(lineName).setMBOSchedule(commaSeparatedList);
	}

	/**
	 * Sets block blockNumber on line lineName as Broken or not true.
	 * If status is true, the track will be set to broken
	 * If the status is false, the track will be set to not broken
	 */
	public void setOccupiedBlock(String lineName, int blockNumber) {
		
		lines.get(lineName).setOccupiedBlock(blockNumber);
	}
	
	/**
	 * Creates a new track layout given a graph of blocks and a list of blockData
	 */
	public boolean setTrackLayout(String lineName, DirectedMultigraph<Integer, DefaultEdge> layout, List<track_model.TrackBlock> blockData, HashMap<Integer, WaysideController> controllerMap) {
		lines.put(lineName, new TrackLayout(layout, blockData, controllerMap, lineName));
		manuallyRoutedTrains.put(lineName, new HashSet<String>());
		return true;
	}

	/**
	 * Creates a new track layout given a graph of blocks and a list of blockData
	 */
	/*
	public boolean setTrackLayoutStandalone(String lineName, DirectedMultigraph<Integer, DefaultEdge> layout, List<track_model.TrackBlock> blockData) {
		lines.put(lineName, new TrackLayout(layout, blockData, controllerMap, lineName));
		return true;
	}
	*/

	/**
	 * Toggles the position of a switch
	 */
	public void toggleSwitchPosition(String lineName, int blockNumber) {
		lines.get(lineName).toggleSwitchPosition(blockNumber);
	}

	/**
	 * Toggles the state of a railway crossing
	 */
	public void toggleRailwayCrossing(String lineName, int blockNumber) {
		lines.get(lineName).toggleRailwayCrossing(blockNumber);
	}
}
