package ctc_office;

public class RailwayCrossingBlock extends DefaultBlock {
	public boolean crossingClosed;

	public RailwayCrossingBlock(int num, double length, double sLimit, boolean occupiedStatus, boolean brokenStatus, boolean crossingStatus) {
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
