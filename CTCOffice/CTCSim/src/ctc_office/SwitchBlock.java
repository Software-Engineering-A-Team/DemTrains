package ctc_office;

public class SwitchBlock extends DefaultBlock {
	private int pointingToBlock;
	private int[] possibleNextBlocks;

	public SwitchBlock(int num, double length, double sLimit, boolean occupiedStatus, boolean brokenStatus,int[] nextBlocks) {
		super(num, length, sLimit, occupiedStatus, brokenStatus);
		possibleNextBlocks = nextBlocks;
	    pointingToBlock = 0;
		if (nextBlocks[1] < nextBlocks[0]) {
		    pointingToBlock = 1;
		}
	}

	/*
	 * Changes the switch position
	 */
	public void toggleSwitch() {
		pointingToBlock = pointingToBlock + 1 % 2;
	}
	
	/*
	 * Returns the block number of the block the switch is pointing to
	 */
	public int getNextBlock() {
		return possibleNextBlocks[pointingToBlock];
	}
	
	public int[] getPossibleNextBlocks() {
		return possibleNextBlocks;
	}

}
