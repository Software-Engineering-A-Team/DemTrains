import java.util.Arrays;

public class Switch extends TrackBlock {
	private int pointingToBlock;
	private TrackBlock[] switchBlocks;
	

	public Switch(String[] blockDescriptor){
		super(blockDescriptor);
	}	
	
	public TrackBlock getNextblock(){
		TrackBlock s = switchBlocks[pointingToBlock];
		return s;
	}
	
	public boolean setSwitchPosition(int id){
		if ((id >= 0) && id < switchBlocks.length){
			pointingToBlock = id;
			return true;
		}
		return false;
	}
	
	public void setSwitchBlocks(TrackBlock[] connected){
		switchBlocks = connected;
	}
	
	public TrackBlock[] getSwitchBlocks(){
		return Arrays.copyOf(switchBlocks, switchBlocks.length);
	}	
}