package ctc_office;

public class RailwayCrossingBlock extends TrackBlock {
	public boolean crossingClosed;

	public RailwayCrossingBlock(int num, double length, int sLimit, boolean occupiedStatus, boolean brokenStatus, boolean crossingStatus) {
		super(num, length, sLimit, occupiedStatus, brokenStatus);
		crossingClosed = crossingStatus;
	}

	/*
	 * Changes the state of the crossing signals
	 */
	public void toggleRailwayCrossing() {
		crossingClosed = !crossingClosed;
	}
}
