package ctc_office;

import java.util.LinkedList;
import java.util.Queue;

public class Yard extends DefaultBlock {
	private Queue<String> dispatchQueue = new LinkedList<String>();

	public Yard() {
		super(0, 0, Integer.MAX_VALUE, false, false);
	}
	
	/*
	 * Add a train to the dispatch queue
	 */
	public void addTrainToQueue(String trainId) {
		dispatchQueue.add(trainId);
	}
	
	/*
	 * Get the next train that needs to be dispatched.
	 */
	public String getNextTrain() {
		return dispatchQueue.remove();
	}

}
