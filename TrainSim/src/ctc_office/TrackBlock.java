package ctc_office;

public class TrackBlock {
	public boolean broken;
	public boolean occupied;
	public final double blockLength;
	public final int blockNumber;
	public final int speedLimit;
	
	public TrackBlock(int num, double length, int sLimit, boolean occupiedStatus, boolean brokenStatus) {
		broken = brokenStatus;
		occupied = occupiedStatus;
		blockLength = length;
		blockNumber = num;
		speedLimit = sLimit;
	}
	
	public String toString() {
		String s = "";
		return s;
	}
}
