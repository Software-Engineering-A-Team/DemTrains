package ctc_office;

import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;

public class CTCDriver {
	HashMap <String, TrackLayout> lines;
	HashMap <String, HashSet<String>> manuallyRoutedTrains;
	public CTCDriver() {
		lines = new HashMap<String, TrackLayout>();
		manuallyRoutedTrains = new HashMap<String, HashSet<String>>();
	}

	/*
	 * Sets the trackLayout and the scheduler to fixed block mode
	 */
	public boolean disableMBOMode() {
		
		
		return true;
	}

	/*
	 * Sets the trackLayout and the scheduler to MBO mode
	 */
	public boolean enableMBOMode() {
		
		return true;
	}
	
	 /*
	  * returns the trackLayout for the line name passed in
	  */
	public TrackLayout getTrackLayout(String lineName) {
		return lines.get(lineName);
	}

	/*
	 * Manually spawns a new train with the routing data that was passed into the method.
	 * Returns false if a train with that name already exists.
	 */
	public boolean manuallyDispatchNewTrain(String lineName, String trainId, int destinationBlock, double speed, double authority) {
		if (lines.get(lineName).dispatchTrain(trainId, destinationBlock, speed, authority))
			manuallyRoutedTrains.get(lineName).add(trainId);
			return true;
		return false;
	}

	/*
	 * Manually spawns a new train with the routing data that was passed into the method at the fastest speed possible.
	 * Returns false if a train with that name already exists.
	 */
	public boolean manuallyDispatchNewTrain(String lineName, String trainId, int destinationBlock, double authority) {
			
		return this.manuallyDispatchNewTrain(lineName, trainId, destinationBlock, Integer.MAX_VALUE, authority);
	}

	/*
	 * Manually routes a train to its destination block at the user specified speed.
	 * Only trains created manually can be manually routed.
	 */
	public boolean manuallyRouteTrain(String lineName, String trainId, int destinationBlock, double speed, double authority){
		if (!manuallyRoutedTrains.get(lineName).contains(trainId)) {
			return false;
		}
		lines.get(lineName).manuallyRouteTrain(trainId, destinationBlock, speed, authority);
		return true;
	}

	/*
	 * Manually routes a train to its destination as fast as possible.
	 * Only trains created manually can be manually routed.
	 */
	public boolean manuallyRouteTrain(String lineName, String trainId, int destinationBlock, double authority){
		return this.manuallyRouteTrain(lineName, trainId, destinationBlock, Integer.MAX_VALUE, authority);
	}

	/*
	 * Manually routes a train to its destination block at the user specified speed.
	 * Only trains created manually can be manually routed.
	 */
	public boolean manuallyRouteTrain(String lineName, TrainRoute newRoute){
		lines.get(lineName).manuallyRouteTrain(newRoute);
		return true;
	}

	/*
	 * Sends the train routes to the Track Controller
	 * If a route is not approved, it is updated in the CTC
	 */
	public void routeTrains(String lineName) {
		ArrayList<TrainRoute> routes = lines.get(lineName).getUpdatedTrainRoutes();
		for (TrainRoute r : routes) {
			// send the route to the track controller
			// if it is not approved
				//update the route in the TrackLayout class
		}
		
	}

	/*
	 * Used by the MBO to set the actual locations of the trains.
	 * Takes in the total distance each train has traveled and calculates the new location.
	 * Calculates all of the new distances at once
	 */
	public boolean setActualTrainLocations(String lineName, HashMap<String, Double> trainDistanceMap) {
		lines.get(lineName).setActualTainLocations(trainDistanceMap);
		return true;
	}

	/*
	 * Sets block blockNumber on line lineName as Broken or not true.
	 * If status is true, the track will be set to broken
	 * If the status is false, the track will be set to not broken
	 */
	public boolean setBrokenTrackStatus(String lineName, int blockNumber, boolean status) {
		return lines.get(lineName).setBlockBrokenStatus(blockNumber, status);
	}
	
	/*
	 * For the line specified in lineName, this method
	 * takes in the filename of a csv file with lines in the
	 * format <Station Name>, <Time until next stop including dwell time>
	 * Creates a schedule from the data that will be used for routing trains
	 * when in fixed block mode.
	 */
	public void setDefaultSchedule(String lineName, String filename) {
		lines.get(lineName).setFixedBlockSchedule(filename);
	}
	
	/*
	 * Sets block blockNumber on line lineName as Broken or not true.
	 * If status is true, the track will be set to broken
	 * If the status is false, the track will be set to not broken
	 */
	public void setOccupiedBlock(String lineName, int blockNumber) {
		
		lines.get(lineName).setOccupiedBlock(blockNumber);
	}
	
	/*
	 * Creates a new track layout given a graph of blocks and a list of blockData
	 */
	public boolean setTrackLayout(String lineName, DirectedMultigraph layout, ArrayList<track_model.TrackBlock> blockData) {
		lines.put(lineName, new TrackLayout(layout, blockData));
	}

	/*
	 * Toggles the position of a switch
	 */
	public void toggleSwitchPosition(String lineName, int blockNumber) {
		lines.get(lineName).toggleSwitchPosition(blockNumber);
	}

	/*
	 * Toggles the state of a railway crossing
	 */
	public void toggleRailwayCrossing(String lineName, int blockNumber) {
		lines.get(lineName).toggleRailwayCrossing(blockNumber);
	}
}
