import track_model.*;
import ctc_office.*;
package track_controller;

public class WaysideSystem {
	List<TrackLayout> tracks;
	String failure;
	
	
	public boolean createControllers(TrackLayout t) {}
	
	public boolean updatePLC(int blockNum) {}
	
	public boolean setTrackLayout(String line, DirectedMultigraph layout, List<TrackBlock> blockData) {}
	
	public boolean setBlockClosed(int blockNum) {}
	
	public boolean setBlockBroken(int blockNum) {}
	
	public boolean setOccupancy(int blockNum) {}
	
	public boolean setSwitchState(int blockNum, boolean) {}
	
	public boolean setCrossingState(int blockNum, boolean) {}
	
	public boolean setFailureMode (int blockNum, String) {}
	
	public TrainRoute addRoute(TrainRoute r, int startBlock) {}
	
	public boolean setBeacon(String beacon) {}
}
