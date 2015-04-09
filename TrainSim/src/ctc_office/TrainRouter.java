package ctc_office;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedMultigraph;

import track_model.TrackSwitch;

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
	public TrainRoute calculateShortestRoute(Train train) {
		Set<Integer> allVertices = layout.vertexSet();
		HashSet<Integer> visitedNodes = new HashSet<Integer>(layout.vertexSet().size());
		double totalWeight = blockData.get(train.currentBlock).blockLength - train.distanceTraveledOnBlock;
		Integer currentBlock = null;
		Integer destinationBlock = null;
		LinkedList<Integer> path = new LinkedList<Integer>();
		HashMap<LinkedList<Integer>, Double> allSimplePaths = new HashMap<LinkedList<Integer>, Double>();
		for (Integer i : allVertices) {
			if (i.equals(train.currentBlock)) {
				currentBlock = i;
			}
			if (i.equals(train.destination)) {
				destinationBlock = i;
			}
		}
		findSimplePaths(currentBlock, destinationBlock, totalWeight, path, allVertices, visitedNodes, allSimplePaths);
		
		// find the shortest path of all the calculated paths
		double min = Double.MAX_VALUE;
		LinkedList<Integer> minPath = null;
		for (LinkedList<Integer> p : allSimplePaths.keySet()) {
			if (allSimplePaths.get(p) < min) {
				min = allSimplePaths.get(p);
				minPath = p;
			}
		}
		
		// create the TrainRoute Object
		int trainSpeed = blockData.get(train.currentBlock).speedLimit;
		if (train.maxSpeed < trainSpeed) {
			
		}
		TrainRoute r = new TrainRoute(train.currentBlock, minPath, )
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	private void findSimplePaths(Integer currentVertex, Integer destinationVertex, double totalWeight, LinkedList<Integer> path, Set<Integer> allVertices, Set<Integer> visitedNodes, HashMap<LinkedList<Integer>, Double> allSimplePaths) {
		// get all the edges it connects to
		Set<DefaultEdge> connectedEdges = layout.edgesOf(currentVertex);
		// remove all of the edges that have been visited
		for (DefaultEdge e : connectedEdges) {
			Integer v = layout.getEdgeSource(e);
			if (visitedNodes.contains(v)){
				continue; //already visited the edge, so skip it
			}
			if (destinationVertex.equals(currentVertex)){
				// Completed the loop
				allSimplePaths.put(path, totalWeight);
				return;
			}
			DefaultBlock b = blockData.get((int)currentVertex);
			// if it is a switch make sure the next block is a valid move
			if (b.getClass().equals(TrackSwitch.class)){
				int [] possibleNextBlocks = ((SwitchBlock) b).getPossibleNextBlocks();
				for (Integer i : allVertices) {
					if (i.equals(possibleNextBlocks[0])) {
						return;
					}
					if (i.equals(possibleNextBlocks[1])) {
						return;
					}
				}
			}
			// If the block is closed, it is not a path
			if (b.broken) {
				return;
			}
			// it is part of a path
			totalWeight += b.blockLength;
			LinkedList<Integer> pathCopy = (LinkedList<Integer>)path.clone();
			Set<Integer> visitedNodesCopy = new HashSet<Integer>(visitedNodes.size());
			visitedNodesCopy.addAll(visitedNodes);
			visitedNodesCopy.add(v);
			pathCopy.add(v);
			findSimplePaths(v, destinationVertex, totalWeight, pathCopy, allVertices, visitedNodesCopy, allSimplePaths);
		}
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
