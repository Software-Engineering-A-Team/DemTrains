package ctc_office;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedMultigraph;

import track_controller.WaysideController;
import train_model.TrainModel;

public class TrackLayout {
	private ArrayList<TrainModel> trainModels;
	private final ArrayList<TrackBlock> blockData = new ArrayList<TrackBlock>();
	private final DirectedMultigraph<Integer, DefaultEdge> layout;
	private final HashMap<Integer, WaysideController> blockToControllerMap;
	private final Scheduler scheduler = new Scheduler();
	private final TrainRouter trainRouter = new TrainRouter();
	private final char trainIdPrefix;

	public TrackLayout(DirectedMultigraph<Integer, DefaultEdge> tLayout, List<track_model.TrackBlock> tBlockData,  HashMap<Integer, WaysideController> controllerMap, char tPrefix) {
		// TODO
		layout = tLayout;
		blockToControllerMap = controllerMap;
		trainIdPrefix = tPrefix;
		for (track_model.TrackBlock blockData : tBlockData) {
			// build the ctc trackBlocks from the actual trackBlocks
			// TrackBlock b = new TrackBlock();
			// blockData.add(TrackBlock.blockNumber, b);
		}
	}
	
	/*
	 * Sets the trackLayout and the scheduler to fixed block mode
	 */
	public void disableMBOMode() {
		scheduler.enableFixedBlockMode();
	}

	/*
	 * Manually spawns a new train with the routing data that was passed into the method.
	 * Returns false if a train with that name already exists.
	 */
	public boolean dispatchTrain(String trainId, int destinationBlock, double speed, double authority) {
		// TODO
		// create a new train and put it in the train list of the system wrapper
		// add the train to the dispatch queue at the yard
		this.manuallyRouteTrain(trainId, destinationBlock, speed, authority);
		
		return true;
	}
	
	/*
	 * Sets the trackLayout and the scheduler to MBO mode
	 */
	public void enableMBOMode() {
		scheduler.enableMBOMode();
	}

	/*
	 * returns a list of beacon strings that need to be updated for trains approaching a station
	 */
	public HashMap<Integer, String> getBeaconStrings() {
		// TODO
		HashMap<Integer, String> beaconStrings = new HashMap<Integer, String>();
		// for each station
			// find train that will reach the station next
			// build the beaconString
			// add it to the list
		return beaconStrings;
	}
	
	/*
	 * Gets the train route for all dispatched trains for this tick
	 */
	public ArrayList<TrainRoute> getUpdatedTrainRoutes() {
		// TODO
		ArrayList<TrainRoute> routes = new ArrayList<TrainRoute>();
		
		return routes;
	}
	
	/*
	 * Manually routes a train to its destination block at the user specified speed.
	 * Only trains created manually can be manually routed.
	 */
	public boolean manuallyRouteTrain(String trainId, int destinationBlock, double speed, double authority) {
		// TODO
		// create the route using the TrainRouter
		
		
		// tell the wayside controller to move the train
		
		// handle the error by updating the route the train actually took
		
		return true;
	}

	/*
	 * Used when the system is in MBO mode to set the actual locations of the trains.
	 * Takes in the total distance each train has traveled and calculates the new location.
	 * Calculates all of the new distances at once.
	 */
	public boolean setActualTrainLocations(HashMap<String, Double> trainDistanceMap){
		// TODO
		// for each train in the map, calculate the actual position on its current block
		return true;
	}

	/*
	 * Sets block blockNumber on line lineName as Broken or not true.
	 * If status is true, the track will be set to broken
	 * If the status is false, the track will be set to not broken
	 */
	public boolean setBlockBrokenStatus(int blockNumber, boolean status) {
		blockData.get(blockNumber).broken = status;
		return true;
	}

	/*
	 * For the line specified in lineName, this method
	 * takes in the filename of a csv file with lines in the
	 * format <Station Name>, <Time until next stop including dwell time>
	 * Creates a schedule from the data that will be used for routing trains
	 * when in fixed block mode.
	 */
	public void setDefaultSchedule(String filename) throws IOException {
		scheduler.setFixedBlockSchedule(filename);
	}

	/*
	 * Used when the system is in Fixed Block mode to set the actual locations of the trains.
	 * Calculates the distance each train has traveled based on its speed and authority.
	 * Calculates all of the new distances at once.
	 */
	public boolean setEstimatedTrainLocaitons() {
		// TODO
		// for all of the trains on the track, calculate the estimated position on its current block.
		return true;
	}

	/*
	 * For the line specified in lineName, this method
	 * takes in the schedule formatted as a csv
	 * format <Station Name>, <Total Time elapsed before the train departs the destination station>
	 * Creates a schedule from the data that will be used for routing trains
	 * when in fixed block mode.
	 */
	public void setMBOSchedule(List<String> commaSeparatedList) {
		scheduler.setMBOSchedule(commaSeparatedList);
	}

	/*
	 * Sets block blockNumber on line lineName as Broken or not true.
	 * If status is true, the track will be set to broken
	 * If the status is false, the track will be set to not broken
	 */
	public void setOccupiedBlock(int blockNumber) {
		
		blockData.get(blockNumber).occupied = true;
	}

	/*
	 * Toggles the position of a switch
	 */
	public void toggleSwitchPosition(int blockNumber) {
		if (blockData.get(blockNumber).getClass().equals(SwitchBlock.class)){
			SwitchBlock b = (SwitchBlock) blockData.get(blockNumber);
			b.toggleSwitch();
		}
	}

	/*
	 * Toggles the state of a railway crossing
	 */
	public void toggleRailwayCrossing(int blockNumber) {
		if (blockData.get(blockNumber).getClass().equals(RailwayCrossingBlock.class)){
			RailwayCrossingBlock b = (RailwayCrossingBlock) blockData.get(blockNumber) ;
			b.toggleRailwayCrossing();
		}
	}

	/*
	 * Resets all of the blocks to unoccupied.
	 * If they are still occupied at the next step, the Track controller will set them as occupied
	 */
	public void unsetOccupiedStatusAllBlocks() {
		for (TrackBlock b : blockData) {
			b.occupied = false;
		}
	}

	/*
	 * Dispatches all of the new trains according to the schedule
	 */
    public void dispatchNewTrains() {
        // TODO Auto-generated method stub
        scheduler.getTrainsToDispatch();
    }
 }
 
