package ctc_office;

import java.util.HashMap;

public class Schedule {
	private HashMap<String, StopData> trainStops;
	private HashMap<String, StopData> currentTrainStops;
	public double minutesForOneTrip;

	public Schedule() {
		trainStops = new HashMap<String, StopData>();
		currentTrainStops = new HashMap<String, StopData>();
	}

	/*
	 * gets the StopData for the stop the train should go to next
	 */
	private StopData getNextStopForTrain(String trainId){
		String nextStationName = "Yard";
		if (currentTrainStops.containsKey(trainId)) {
			nextStationName = currentTrainStops.get(trainId).destinationStation;
		}
		return trainStops.get(nextStationName);
	}
	
	/*
	 * Adds a stop to the map of possible stops trains can make
	 */
	public void addNewStop(String startStation, String destination, double minutes){
		trainStops.put(startStation, new StopData(startStation, destination, minutes));
		minutesForOneTrip += minutes;
	}
	
	/*
	 * Adds a stop to the map of possible stops trains can make
	 */
	public void addNewStop(StopData newStopData){
		trainStops.put(newStopData.startStation, newStopData);
		minutesForOneTrip += newStopData.travelTime;
	}
	
	/*
	 * Gets the next stop the train should make and sets the train's StopData to the next stop
	 */
	public StopData getAndSetNextStopForTrain(String trainId){
		StopData nextStop = getNextStopForTrain(trainId);
		currentTrainStops.put(trainId, nextStop);
		return nextStop;
	}

	 /**
	  * Gets the number of stations the train will stop at
	  */
	public int getNumberOfStations() {
		return trainStops.size();
	}
}
