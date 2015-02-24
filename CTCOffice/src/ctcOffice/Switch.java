package ctcOffice;

import java.util.Arrays;

public class Switch extends Block {
	private int pointingToBlock;
	private Block[] connectedBlocks;

	public Switch(String[] blockDescriptor){
		super(blockDescriptor);
	}
	
	
	public Block getNextblock(){
		return connectedBlocks[pointingToBlock];
	}
	
	public boolean setSwitchPosition(int id){
		if ((id >= 0) && id < connectedBlocks.length){
			pointingToBlock = id;
			return true;
		}
		return false;
	}
	
	public void setConnectedBlocks(Block[] connected){
		connectedBlocks = connected;
	}
	
	public Block[] getConnectedBlocks(){
		return Arrays.copyOf(connectedBlocks, connectedBlocks.length);
	}
	
}
