package ctcOffice;

import java.util.Arrays;

public class Switch extends Block {
	private int pointingToBlock;
	private Block[] switchBlocks;
	

	public Switch(String[] blockDescriptor){
		super(blockDescriptor);
	}	
	
	public Block getNextblock(){
		return switchBlocks[pointingToBlock];
	}
	
	public boolean setSwitchPosition(int id){
		if ((id >= 0) && id < switchBlocks.length){
			pointingToBlock = id;
			return true;
		}
		return false;
	}
	
	public void setSwitchBlocks(Block[] connected){
		switchBlocks = connected;
	}
	
	public Block[] getSwitchBlocks(){
		return Arrays.copyOf(switchBlocks, switchBlocks.length);
	}
	
}
