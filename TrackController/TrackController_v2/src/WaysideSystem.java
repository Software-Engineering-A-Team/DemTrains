import java.util.List;
import java.util.HashMap;

public class WaysideSystem {
	HashMap<String, List<TrackSection>> tCntrlSystem;
	TrackModel tracks;
	String failure;
	TrainRoute route;
	
	public void createControllers(){
		//for each lines in track model
			//
	}
	public void setTrackLayout(String line, DirectedMultigraph layout, List<TrackBlock> blocks){
		//called by TrackModel to add track layout to track model
	}
	public void updatePLC(TrackSection c){
		//change the plc reference for a give section 
	}
	public void setFailureMode(String f, TrackBlock num){
		//set the failure mode of a trackblock 
	}
	public boolean setBlockClosed(TrackBlock num, boolean closed){
		//called by user or CTC to set block closed
		//consult WaysideController -> PLC
		//if safe, set closed
		return true;
	}
	public boolean setBlockBroken(TrackBlock num, boolean broken){
		//called by user or TrackModel to set block broken
		//just break the block
		num.broken= broken;
		return true;
	}
	public boolean setOccupancy(TrackBlock num, boolean occupied){
		//called by user or internally by WaysideSystem to occupy a block
		//consult WaysideController -> PLC
		//if safe, set occupied
		return true;
	}
	public boolean setSwitchState(TrackBlock num, boolean s){
		//called on every tick to update switch positions
		//consult WaysideController -> PLC
		//if safe, set switch state
		return true;
	}
	public boolean setCrossingState(TrackBlock num, boolean crossing){
		//called by user or updated by system on every tick
		//consult WaysideController -> PLC
		//if safe, set state
		return true;
	}
	public void setBeacon(String beacon, TrackBlock num){
		//set the beacon text given by the CTC
		//for the specified block
		num.beacon = beacon;
	}
	public void addRoute(TrainRoute r){
		//update the current route and place a train on the start block
		this.route = r;
		//pass updated occupancy to Track Model
	}
}
