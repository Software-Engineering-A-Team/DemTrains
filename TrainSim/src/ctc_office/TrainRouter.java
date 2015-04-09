package ctc_office;

import java.util.ArrayList;
import java.util.HashMap;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedMultigraph;

public class TrainRouter {
	private final ArrayList<DefaultBlock> blockData;
	private final HashMap<String, StationBlock> allStations;
	private final DirectedMultigraph<Integer, DefaultEdge> layout;
	private final String lineName;
	private HashMap<Short, TrainRoute> trainRoutes;
	private ArrayList<Train> trains;

	public TrainRouter(DirectedMultigraph<Integer, DefaultEdge> l, ArrayList<DefaultBlock> bData, HashMap<String, StationBlock> stations, String lName) {
		layout = l;
		blockData = bData;
		allStations = stations;
		lineName = lName;
	}

	/**
	 * Calculates the shortest route that will not interfere with another train.
	 */
	public TrainRoute calculateShortestRoute(short trainId) {
		// TODO
		return null;
	}

	/**
	 * Gets all of the trains that have reached their destinations and finished dwelling.
	 */
	public ArrayList<String> getTrainsWithFinishedRoutes() {
		ArrayList<String> finishedTrains = new ArrayList<String>();
		for (Train t : trains) {
			if (t.currentBlock == t.destination) {
				if (t.distanceTraveledOnBlock == blockData.get(t.currentBlock).blockLength/2) { // it is at the station
					if ((t.remainingTravelTime == 0) && (t.currSpeed == 0)) {
						finishedTrains.add(t.trainName);
					}
				}
			}
		}
		if (finishedTrains.size() > 0) {
			return finishedTrains;
		}
		return null;
	}

	/**
	 * Sets the actual train locations sent in from the MBO for all of the trains
	 */
	public void setActualTrainLocations(HashMap<String, Integer> trainLocations) {
		// TODO: for each train
			// calculate the distance traveled on block
			// update the total distance traveled for the train.
	}

	/**
	 * sets an alternate route for the train -- used by the TrackController when a route is not approved
	 */
	public void setAlternateRoute(short trainId, TrainRoute r) {
		trainRoutes.put(trainId, r);
	}

	/**
	 * Sets the estimated train locations calculated using the current speed
	 */
	public void setEstimatedTrainLocations() {
		// TODO: for each train
			// calculate the distance traveled since the last tick
			// calculate the distance traveled on block
			// update the total distance traveled for the train.
	}

	/**
	 * Spawns a new train in the yard with no destination and a speed/authority of 0
	 */
	public boolean spawnNewTrain(String trainName, short trainId) {
		// check that the trainId and Name are unique
		trains.add(new Train(trainId, trainName));
		return true;
	}

	/**
	 * Updates a trains destination block and travel time
	 */
	public boolean updateTrainDestination(String trainName, int destBlock, double travelTime) {
		for (Train t : trains) {
			if (t.trainName.equals(trainName)) {
				t.destination = destBlock;
				t.remainingTravelTime = (int)Math.ceil(travelTime * 60000);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Updates a trains destination block and travel time
	 */
	public boolean updateTrainDestination(String trainName, int destBlock, double maxSpeed, double authority) {
		for (Train t : trains) {
			if (t.trainName.equals(trainName)) {
				t.destination = destBlock;
				t.maxSpeed = maxSpeed;
				t.authority = authority;
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets all of the currently dispatched trains
	 */
	public ArrayList<Train> getAllTrains() {
		ArrayList<Train> dispatchedTrains = new ArrayList<Train>(trains.size());
		for (Train t : dispatchedTrains) {
			if (t.currentBlock != 0) {
				dispatchedTrains.add(t);
			}
		}
		if (dispatchedTrains.size() > 0) {
			return dispatchedTrains;
		}
		return null;
	}
	
	public String getTainApprochingStation() {
		//TODO
		return null;
	}

}
