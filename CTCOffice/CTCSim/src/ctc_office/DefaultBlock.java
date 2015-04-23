package ctc_office;

public class DefaultBlock implements BlockInterface{
	public boolean broken;
	public boolean occupied;
	public final double blockLength;
	public final int blockNumber;
	public final double speedLimit;
	
	public DefaultBlock(int num, double length, double sLimit, boolean occupiedStatus, boolean brokenStatus) {
		broken = brokenStatus;
		occupied = occupiedStatus;
		blockLength = length;
		blockNumber = num;
		speedLimit = sLimit;
	}
	
	public String toString() {
		String s = "Block type: Default Block";
		return s;
	}
}
