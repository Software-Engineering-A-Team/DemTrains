package ctc_office;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedMultigraph;

import system_wrapper.SystemWrapper;
import track_model.*;
import track_controller.WaysideController;
import train_model.TrainModel;

public class TrackLayout {
	private final ArrayList<DefaultBlock> blockData;
	private final HashMap<String, StationBlock> allStations = new HashMap<String, StationBlock>();
	private final DirectedMultigraph<Integer, DefaultEdge> layout;
	private final HashMap<Integer, List<WaysideController>> blockToControllerMap;
	private final Scheduler scheduler = new Scheduler();
	private final TrainRouter trainRouter;
	private final char trainIdPrefix;
	private HashMap<String, StopData> yardTrainStopData = new HashMap<String,StopData>();

	public TrackLayout(DirectedMultigraph<Integer, DefaultEdge> tLayout, List<TrackBlock> tBlockData,  HashMap<Integer, List<WaysideController>> controllerMap, String tPrefix) {
		layout = tLayout;
		blockToControllerMap = controllerMap;
		trainIdPrefix = tPrefix.toLowerCase().charAt(0);
		blockData = new ArrayList<DefaultBlock>(tBlockData.size());
        blockData.add(0, new Yard());
		for (TrackBlock b : tBlockData) {
		    int blockNum = b.number;
            double blockLen = b.length;
            double speedLimit = b.speedLimit;
            boolean occupiedStatus = b.occupancy;
            boolean brokenStatus = b.hasFailure();
            if (blockData.getClass().equals(TrackCrossing.class)){
	            TrackCrossing crossing = (TrackCrossing) b;
	            boolean crossingStatus = crossing.state;
	            blockData.add(blockNum, new RailwayCrossingBlock(blockNum, blockLen, speedLimit, occupiedStatus, brokenStatus, crossingStatus));
	        }
	        else if (blockData.getClass().equals(TrackSwitch.class)){
                TrackSwitch s = (TrackSwitch) b;
                int[] nextBlocks = s.out;
                blockData.add(blockNum, new SwitchBlock(blockNum, blockLen, speedLimit, occupiedStatus, brokenStatus, nextBlocks));
	        }
            else if (blockData.getClass().equals(TrackStation.class)){
                TrackStation station = (TrackStation) b;
                String stationName = station.stationName;
                StationBlock s = new StationBlock(blockNum, blockLen, speedLimit, occupiedStatus, brokenStatus, stationName);
                blockData.add(blockNum, s);
                allStations.put(stationName, s);
            }
            else { // it is a regular block
                blockData.add(blockNum, new DefaultBlock(blockNum, blockLen, speedLimit, occupiedStatus, brokenStatus));
            }
		}
        trainRouter = new TrainRouter(tLayout, blockData, allStations, tPrefix);
	}
	
	/**
	 * Sets the trackLayout and the scheduler to fixed block mode
	 */
	public void disableMBOMode() {
		scheduler.enableFixedBlockMode();
	}

	/**
	 * Manually spawns a new train with the routing data that was passed into the method.
	 * Returns false if a train with that name already exists.
	 */
	public boolean dispatchTrain(String trainName, StopData stopData) {
		// add the train to the dispatch queue at the yard
	    Yard y = (Yard) blockData.get(0);
		y.addTrainToQueue(trainName);
		yardTrainStopData.put(trainName, stopData);
		return true;
	}
	
	/**
	 * Creates a new train model
	 */
	private short createTrainModel(String trainName) {
		// create a new train and put it in the train list of the system wrapper
		short trainId = (short) SystemWrapper.trainModels.size();
		for (short i = 0; i<trainId;i++) {
			if (SystemWrapper.trainModels.get(i) == null) {
				trainId = i;
				break;
			}
		}
	    SystemWrapper.trainModels.add(trainId, new TrainModel(trainIdPrefix + trainName, trainId));
	    return trainId;
	}

	/**
	 * Sets the trackLayout and the scheduler to MBO mode
	 */
	public void enableMBOMode() {
		scheduler.enableMBOMode();
	}

	/**
	 * gets a list of all the trains currently on the track
	 */
	public ArrayList<Train> getAllTrains() {
		return trainRouter.getAllTrains();
	}
	
	public ArrayList<DefaultBlock> getAllBlocks() {
		return blockData;
	}

	/**
	 * returns a list of beacon strings that need to be updated for trains approaching a station
	 */
	public HashMap<Integer, String> getBeaconStrings() {
		// TODO
		HashMap<Integer, String> beaconStrings = new HashMap<Integer, String>();
		for (StationBlock s : allStations.values()) {
			// find train that will reach the station next
			// build the beaconString
			// add it to the list
		}
		return beaconStrings;
	}

	/**
	 * Gets the train route for all dispatched trains for this tick
	 */
	public ArrayList<TrainRoute> getUpdatedTrainRoutes() {
		// TODO: Get all of the trains that have reached their destination and are ready to depart
		// Get their next destination from the scheduler
		// for every train not still at the yard
			// route the train.
			// send the routing data to the wayside controller for that block
			// if the route was not accepted fix the trains expected route
		// Get the next train in the dispatch queue at the yard
		// route that train
		// send the routing data to the wayside controller for that block
		// if the route was not accepted, fix the trains expected route
		ArrayList<TrainRoute> routes = new ArrayList<TrainRoute>();
		
		return routes;
	}

	/**
	 * Manually routes a train to its destination block at the user specified speed.
	 * Only trains created manually can be manually routed.
	 */
	public boolean manuallyRouteTrain(String trainName, int destinationBlock, double speed, double authority) {
		trainRouter.updateTrainDestination(trainName, destinationBlock, speed, authority);		
		return true;
	}

	/**
	 * Used when the system is in MBO mode to set the actual locations of the trains.
	 * Takes in the total distance each train has traveled and calculates the new location.
	 * Calculates all of the new distances at once.
	 */
	public boolean setActualTrainLocations(HashMap<String, Double> trainDistanceMap){
		// TODO
		// for each train in the map, calculate the actual position on its current block
		return true;
	}

	/**
	 * Sets block blockNumber on line lineName as Broken or not true.
	 * If status is true, the track will be set to broken
	 * If the status is false, the track will be set to not broken
	 */
	public boolean setBlockBrokenStatus(int blockNumber, boolean status) {
		blockData.get(blockNumber).broken = status;
		return true;
	}

	/**
	 * For the line specified in lineName, this method
	 * takes in the filename of a csv file with lines in the
	 * format <Station Name>, <Time until next stop including dwell time>
	 * Creates a schedule from the data that will be used for routing trains
	 * when in fixed block mode.
	 */
	public void setDefaultSchedule(String filename) throws IOException {
		scheduler.setFixedBlockSchedule(filename);
	}

	/**
	 * Used when the system is in Fixed Block mode to set the actual locations of the trains.
	 * Calculates the distance each train has traveled based on its speed and authority.
	 * Calculates all of the new distances at once.
	 * This method doesn't take into account acceleration time,
	 * but when the train reaches the end of a block it will be more accurate
	 */
	public boolean setEstimatedTrainLocaitons() {
		// TODO
		// for all of the trains on the track, calculate the estimated position on its current block.
		return true;
	}

	/**
	 * For the line specified in lineName, this method
	 * takes in the schedule formatted as a csv
	 * format <Station Name>, <Total Time elapsed before the train departs the destination station>
	 * Creates a schedule from the data that will be used for routing trains
	 * when in fixed block mode.
	 */
	public void setMBOSchedule(List<String> commaSeparatedList) {
		scheduler.setMBOSchedule(commaSeparatedList);
	}

	/**
	 * Sets block blockNumber on line lineName as Broken or not true.
	 * If status is true, the track will be set to broken
	 * If the status is false, the track will be set to not broken
	 */
	public void setOccupiedBlock(int blockNumber) {
		
		blockData.get(blockNumber).occupied = true;
	}

	/**
	 * Toggles the position of a switch
	 */
	public void toggleSwitchPosition(int blockNumber) {
		if (blockData.get(blockNumber).getClass().equals(SwitchBlock.class)){
			SwitchBlock b = (SwitchBlock) blockData.get(blockNumber);
			b.toggleSwitch();
		}
	}

	/**
	 * Toggles the state of a railway crossing
	 */
	public void toggleRailwayCrossing(int blockNumber) {
		if (blockData.get(blockNumber).getClass().equals(RailwayCrossingBlock.class)){
			RailwayCrossingBlock b = (RailwayCrossingBlock) blockData.get(blockNumber) ;
			b.toggleRailwayCrossing();
		}
	}

	/**
	 * Resets all of the blocks to unoccupied.
	 * If they are still occupied at the next step, the Track controller will set them as occupied
	 */
	public void unsetOccupiedStatusAllBlocks() {
		for (DefaultBlock b : blockData) {
			b.occupied = false;
		}
	}

	/**
	 * Dispatches all of the new trains according to the schedule
	 */
    public void dispatchNewTrains() {
        HashMap<String, StopData> trainsToDispatch = scheduler.getTrainsToDispatch();
        for (String train : trainsToDispatch.keySet()) {
        	dispatchTrain(train, trainsToDispatch.get(train));
        }
    }

    /**
     * Adds the first train from queue at the yard to the track
     * 
     */
	public void routeTrainFromYard() {
		String trainName = ((Yard) blockData.get(0)).getNextTrain();
		short trainId = createTrainModel(trainName);
		// Create the train in the train router
		trainRouter.spawnNewTrain(trainName, trainId);
		
		if (yardTrainStopData.get(trainName).getClass().equals(BlockStopData.class)){
			// It was dispatched manually, and the route needs to be created accordingly
			BlockStopData stopData = (BlockStopData)yardTrainStopData.get(trainName);
			trainRouter.updateTrainDestination(trainName, stopData.endBlock, stopData.speed, stopData.authority);
		}
		else { // It is a normally dispatched train and will follow a schedule
			StopData stopData = yardTrainStopData.get(trainName);
			trainRouter.updateTrainDestination(trainName, allStations.get(stopData.destinationStation).blockNumber, stopData.travelTime);
			
		}
	}
 }
 
