package ctc_office;

import java.util.LinkedList;
import java.util.Queue;

public class YardBlock extends DefaultBlock {
	private Queue<String> dispatchQueue = new LinkedList<String>();

	public YardBlock() {
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
		if (dispatchQueue.size() > 0) {
			return dispatchQueue.remove();
		}
		return null;
	}

}
