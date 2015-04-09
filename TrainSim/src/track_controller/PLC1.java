package track_controller;
import track_model.TrackBlock;
import java.util.ArrayList;
import java.util.Collection;

public class PLC1 implements PLCInterface {
	ArrayList<TrackBlock> controlledBlocks;
	
	
	public PLC1(ArrayList<TrackBlock> blockList){
		controlledBlocks = blockList;
	}	
	/*
	 * Determines safe state of the railway crossing and returns the state
	 * true for active, false for inactive
	 */
	public boolean ctrlCrossing() {
		return true;
	}
	/*
	 * Determines safe state of the switch and returns the state
	 * true for second block in attach array , false for first block in attach array
	 */
	public boolean ctrlSwitch() {
		return false;
	}
	/*
	 * Determines safe state of the track heater and returns the state
	 * true on, false off
	 */
	public boolean ctrlHeater(TrackBlock b) {
		if(b.hasAdverseWeather()) return true;
		else return false;		
	}
	/*
	 * Determines safe state of the lights and returns the state
	 * true green, false red
	 */
	public boolean ctrlLights(TrackBlock b) {
		if(b.occupancy) return true;
		else return false;
	}
	/*
	 * Determines safe speed and authority and returns 
	 * 
	 */
	public boolean ctrlSpeedAuthority(TrackBlock b, double speed, double authority) {
		if (speed > b.speedLimit) return false;
		else return true;
	}
	/*
	 * Determines safe closing of block and returns block state
	 * true for open, false for closed
	 */
	public boolean ctrlBlockClosed(TrackBlock b){
		return false;
	}
	
	public void run(){
		System.out.println("Running PLC1");
		for (TrackBlock b : controlledBlocks) {
			b.heater = ctrlHeater(b);
			b.lights = ctrlLights(b);
		}
		
	}
}
