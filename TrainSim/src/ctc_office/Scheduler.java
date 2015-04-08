package ctc_office;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Scheduler {
	private boolean fixedBlockModeEnabled = true;
	
	private Schedule scheduleFixedBlock;
	private HashMap<String, Schedule> scheduleMBO;
	private int throughput;
	private List<String> dispatchedTrains;
	private List<String> availableTrains;
	
	public boolean enableFixedBlockMode() {
		if (scheduleFixedBlock == null) {
			return false;
		}
		fixedBlockModeEnabled = true;
		return true;
	}

	public boolean enableMBOMode() {
		if (scheduleFixedBlock == null) {
			return false;
		}
		fixedBlockModeEnabled = false;
		return true;
	}

	/*
	 * gets the data for the next stop using the correct schedule
	 * if the train doesn't have a next stop, it will be removed from the list of
	 * active trains and added to the list of free trains.
	 * returns the StopData for the train
	 */
	public StopData getTrainNextStop(String trainId){
		Schedule currSchedule;
		StopData nextStop;
		
		if (fixedBlockModeEnabled) {
			currSchedule = scheduleFixedBlock;
		}
		else {
			currSchedule = scheduleMBO.get(trainId);
		}
		nextStop = currSchedule.getAndSetNextStopForTrain(trainId);
		if (nextStop == null) {
			dispatchedTrains.remove(trainId);
			availableTrains.add(trainId);
		}
		return nextStop;
	}

	/*
	 * returns a Map of trains that should be dispatched at the current time and
	 * a corresponding StopData object
	 */
	public HashMap<String, StopData> getTrainsToDispatch() {
		int numTrainsToDispatch = 0;
		String trainId;
		HashMap<String, StopData> trainsToDispatch = new HashMap<String, StopData>();
		// TODO: calculate how many trains need to be dispatched to satisfy the throughput requirement
		for (int i=0; i<numTrainsToDispatch; i++) {
			if (availableTrains.size() > 0) {
				trainId = availableTrains.remove(0);
			}
			else {
				trainId = "train" + dispatchedTrains.size();
			}
			trainsToDispatch.put(trainId, getTrainNextStop(trainId));
		}
		if (trainsToDispatch.size() == 0) {
			return null;
		}
		return trainsToDispatch;
	}
	
	/*
	 * Sets the throughput that will be used for routing the train when it is in fixed block mode.
	 */
	public void setThroughput(int t){
		throughput = t;
	}
	
	/*
	 * Takes in the filename of a csv file with lines in the
	 * format <Station Name>, <Time until next stop including dwell time>
	 * Creates a schedule from the data that will be used for routing trains
	 * when in fixed block mode.
	 */
	public void setFixedBlockSchedule(String filename) throws IOException{
		String line;
		String previousStation = "Yard";
		scheduleFixedBlock = new Schedule();
		BufferedReader br = new BufferedReader(new FileReader(filename));
		
		while((line = br.readLine()) != null) { // line[0] = station name, line[1] = travel time
			String [] stopData = line.split(",");
			scheduleFixedBlock.addNewStop(previousStation, stopData[0], Double.parseDouble(stopData[1]));
			previousStation = stopData[1];
		}
		scheduleFixedBlock.addNewStop(previousStation, "Yard", 0.0);
		br.close();
	}
	
	/*
	 * Sets a Schedule object created by the MBO as the schedule that will be used by a train in MBO mode
	 */
	public void setMBOSchedule(List<String> commaSeparatedSchedule){
		// TODO
		// parse the csv
		// the first line is the train name and the departure time
		// for all remaining lines parse the line, create a new Schedule object
	}

}
