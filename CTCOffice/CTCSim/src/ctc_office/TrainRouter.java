package ctc_office;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedMultigraph;

public class TrainRouter {
	private final ArrayList<BlockInterface> blockData;
	private final HashMap<String, StationBlock> allStations;
	private final DirectedMultigraph<Integer, DefaultEdge> layout;
	private final String lineName;
	private HashMap<Short, TrainRoute> trainRoutes = new HashMap<Short, TrainRoute>();
	private ArrayList<Train> trains = new ArrayList<Train>();

	public TrainRouter(DirectedMultigraph<Integer, DefaultEdge> l, ArrayList<BlockInterface> bData, HashMap<String, StationBlock> stations, String lName) {
		layout = l;
		blockData = bData;
		allStations = stations;
		lineName = lName;
	}

	/**
	 * Calculates the shortest route that will not interfere with another train.
	 */
	public LinkedList<TrainRoute> calculateShortestRoute(Train train) {
		Set<Integer> allVertices = layout.vertexSet();
		HashSet<Integer> visitedNodes = new HashSet<Integer>(layout.vertexSet().size());
		double totalWeight = ((DefaultBlock)blockData.get(train.currentBlock)).blockLength - train.distanceTraveledOnBlock;
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
		// Create a route object for all of the paths
		LinkedList<TrainRoute> allTrainRoutes = new LinkedList<TrainRoute>();
		for (LinkedList<Integer> p : allSimplePaths.keySet()) {
			Double pathDistance = allSimplePaths.get(p);
			p.add(0, currentBlock);
			pathDistance += ((DefaultBlock)blockData.get(currentBlock)).blockLength;
			pathDistance += ((DefaultBlock)blockData.get(p.get(p.size()-1))).blockLength/2;
			// create the TrainRoute Object
			double trainSpeed = ((DefaultBlock)blockData.get(train.currentBlock)).speedLimit;
			if (train.maxSpeed < trainSpeed) {
				trainSpeed = train.maxSpeed;
			}
			double authority = train.authority;
			if (authority > pathDistance) {
				authority = pathDistance;
			}

			allTrainRoutes.add(new TrainRoute(lineName, train.currentBlock, p, trainSpeed, authority, pathDistance));
		}
		Collections.sort(allTrainRoutes);
		return allTrainRoutes;
	}
	
	
	@SuppressWarnings("unchecked")
	private void findSimplePaths(Integer currentVertex, Integer destinationVertex, double totalWeight, LinkedList<Integer> path, Set<Integer> allVertices, Set<Integer> visitedNodes, HashMap<LinkedList<Integer>, Double> allSimplePaths) {
		// get all the edges it connects to
		Set<DefaultEdge> connectedEdges = layout.edgesOf(currentVertex);
		// remove all of the edges that have been visited
		if (destinationVertex.equals(currentVertex)){
			// Completed the loop
			allSimplePaths.put(path, totalWeight);
			return;
		}
		for (DefaultEdge e : connectedEdges) {
			Integer v = layout.getEdgeTarget(e);
			if (!currentVertex.equals(layout.getEdgeSource(e))) {
				continue;
			}
			if (visitedNodes.contains(v)){
				continue; //already visited the edge, so skip it
			}
			DefaultBlock b = (DefaultBlock)blockData.get((int)currentVertex);          
			// if it is a switch make sure the next block is a valid move
			/*
			if (b.getClass().equals(SwitchBlock.class)){
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
			*/
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
			pathCopy.add(pathCopy.size(), v);
			findSimplePaths(v, destinationVertex, totalWeight, pathCopy, allVertices, visitedNodesCopy, allSimplePaths);
		}
	}
	
	/**
	 * Gets all of the trains that have reached their destinations and finished dwelling.
	 */
	public HashSet<String> getTrainsWithFinishedRoutes() {
		HashSet<String> finishedTrains = new HashSet<String>();
		for (Train t : trains) {
			if (t.currentBlock == t.destination) {
				if (t.distanceTraveledOnBlock == ((DefaultBlock)blockData.get(t.currentBlock)).blockLength/2) { // it is at the station
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
	 * Routes all of the trains
	 */
	public void routeAllTrains() {
		HashMap<Short, LinkedList<TrainRoute>> allRoutesAllTrains = new HashMap<Short, LinkedList<TrainRoute>>();
		// get all possible routes for all of the trains. The routes will be sorted with the shortest at the front of the list
		for (Train t : trains) {
			allRoutesAllTrains.put(t.trainId, calculateShortestRoute(t));
		}
		// Check for collisions
		for (Train t : trains) {
			TrainRoute r = allRoutesAllTrains.get(t.trainId).get(0);
			trainRoutes.put(t.trainId, r);
			CTCWrapper.trackController.addRoute(r);
			if (t.authority > r.weight) {
				t.authority = r.weight;
			}
		}
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
	 * sets an alternate route for the train used when the TrackController doesn't approve a route
	 */
	public void setAlternateRoute(short trainId, TrainRoute r) {
		trainRoutes.put(trainId, r);
	}

	/**
	 * Sets the estimated train locations calculated using the current speed
	 */
	public void setEstimatedTrainLocations() {
		// TODO: for each train
		for (Train t : trains) {
			// calculate the distance traveled since the last tick
			double speed = t.currSpeed * 0.488888889; // in yards/second
			double elapsedTime = SimClock.getDeltaMs() * 0.001; //in seconds
			double distanceTraveled = speed*elapsedTime; //yards
			DefaultBlock block = ((DefaultBlock)blockData.get(t.currentBlock));
			if (distanceTraveled > t.authority) {
				distanceTraveled = t.authority;
				t.currSpeed = 0;
			}
			else if (trainRoutes.get(t.trainId).route.size() == 1) {
				if (t.distanceTraveledOnBlock + distanceTraveled > block.blockLength/2) {
					distanceTraveled = block.blockLength/2 - t.distanceTraveledOnBlock;
					t.distanceTraveledOnBlock = block.blockLength/2;
					t.currSpeed = 0;
				}
			}
			// check if the train has entered a new block
			if (block.occupied == false) {
				// it has.
				t.currentBlock = trainRoutes.get(t.trainId).route.get(1);
				t.distanceTraveledOnBlock = block.blockLength - t.distanceTraveledOnBlock;
				t.currSpeed = ((DefaultBlock) blockData.get(t.currentBlock)).speedLimit;
				if (t.currSpeed > t.maxSpeed) {
					t.currSpeed = t.maxSpeed;
				}
			}
			// calculate the distance traveled on block
			else if ((t.distanceTraveledOnBlock + distanceTraveled) > block.blockLength) {
				t.distanceTraveledOnBlock = block.blockLength;
			}
			t.authority -= distanceTraveled;
			t.currSpeed = trainRoutes.get(t.trainId).speed;
			t.distanceTraveledOnBlock += distanceTraveled;
			
		}
	}

	/**
	 * Spawns a new train in the yard with no destination and a speed/authority of 0
	 */
	public Train spawnNewTrain(String trainName, short trainId) {
		// check that the trainId and Name are unique
		Train newTrain = new Train(trainId, trainName, lineName);
		trains.add(newTrain);
		return newTrain;
	}

	/**
	 * Updates a trains destination block and travel time
	 */
	public boolean updateTrainDestination(String trainName, int destBlock, double travelTime) {
		for (Train t : trains) {
			if (t.trainName.equals(trainName)) {
				t.destination = destBlock;
				t.remainingTravelTime = (int)Math.ceil(travelTime * 60000);
				t.currSpeed = ((DefaultBlock)blockData.get(t.currentBlock)).speedLimit;
				if (t.currSpeed > t.maxSpeed) {
					t.currSpeed = t.maxSpeed;
				}
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
				t.currSpeed = ((DefaultBlock)blockData.get(t.currentBlock)).speedLimit;
				if (t.currSpeed > t.maxSpeed) {
					t.currSpeed = t.maxSpeed;
				}
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
		for (Train t : trains) {
			if (t.currentBlock != 0) {
				dispatchedTrains.add(t);
			}
		}
		if (dispatchedTrains.size() > 0) {
			return dispatchedTrains;
		}
		return null;
	}
	
	public Train getTainApprochingStation(int blockNumber) {
		//TODO
		double shortestDistance = Double.MAX_VALUE;
		Train shortestDistanceTrain = null;
		for (Short trainId : trainRoutes.keySet()) {
			Train t = trains.get(trainId);
			double distanceToStation = Double.MAX_VALUE;
			boolean found = false;
			for (Integer blockNum : trainRoutes.get(trainId).route) {
				if (distanceToStation < shortestDistance) {
					distanceToStation += ((DefaultBlock) blockData.get((int)blockNum)).blockLength;
					if (blockNum.equals(blockNumber)) {
						found = true;
						break;
					}
				}
			}
			if (found) {
				shortestDistanceTrain = t;
				shortestDistance = distanceToStation;
			}
		}
		return shortestDistanceTrain;
	}


}
