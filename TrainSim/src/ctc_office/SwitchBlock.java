package ctc_office;

public class SwitchBlock extends TrackBlock {
	private int pointingToBlock;
	private int[] possibleNextBlocks;

	public SwitchBlock(int num, double length, int sLimit, boolean occupiedStatus, boolean brokenStatus, int pointing, int[] nextBlocks) {
		super(num, length, sLimit, occupiedStatus, brokenStatus);
		pointingToBlock = pointing;
		possibleNextBlocks = nextBlocks;
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

}
