package ctcOffice;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class CTCDriver {
	HashMap <String, TrackLayout> lines;
	HashMap <String, HashSet<String>> manuallyRoutedTrains;
	public CTCDriver() {
		lines = new HashMap<String, TrackLayout>();
		manuallyRoutedTrains = new HashMap<String, HashSet<String>>();
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
	public void setOccupiedBlocks(String lineName, List<Integer> occupiedBlocks) {
		for (Integer blockNumber: occupiedBlocks) {
			lines.get(lineName).setOccupiedBlock(blockNumber);
		}
	}
	
}
