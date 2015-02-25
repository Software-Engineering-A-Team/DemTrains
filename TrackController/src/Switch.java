
import java.util.Arrays;

public class Switch extends TrackBlock {
	private int pointingToBlock;
	private TrackBlock[] connectedBlocks;

	public Switch(String[] blockDescriptor){
		super(blockDescriptor);
	}
	
	
	public TrackBlock getNextblock(){
		return connectedBlocks[pointingToBlock];
	}
	
	public boolean setSwitchPosition(int id){
		if ((id >= 0) && id < connectedBlocks.length){
			pointingToBlock = id;
			return true;
		}
		return false;
	}
	
	public void setConnectedBlocks(TrackBlock[] connected){
		connectedBlocks = connected;
	}
	
	public TrackBlock[] getConnectedBlocks(){
		return Arrays.copyOf(connectedBlocks, connectedBlocks.length);
	}
	
}