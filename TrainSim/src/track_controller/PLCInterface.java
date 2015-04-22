package track_controller;
import java.util.PriorityQueue;

import track_model.TrackBlock;
import ctc_office.TrainRoute;

public interface PLCInterface {
	/*
	 * Determines safe state of the railway crossing and returns the state
	 * true for active, false for inactive
	 */
	public boolean ctrlCrossing(TrainRoute r);
	/*
	 * Determines safe state of the switch and returns the state
	 * true for second block in attach array , false for first block in attach array
	 */
	public boolean ctrlSwitch(TrainRoute r);
	/*
	 * Determines safe state of the track heater and returns the state
	 * true on, false off
	 */
	public boolean ctrlHeater(TrackBlock b);
	/*
	 * Determines safe state of the lights and returns the state
	 * true green, false red
	 */
	public boolean ctrlLights(TrackBlock b);
	/*
	 * Determines safe speed and authority and returns 
	 * 
	 */
	public boolean ctrlSpeedAuthority(TrackBlock b, double sugSpeed, double sugAuthority);
	/*
	 * Determines safe closing of block and returns block state
	 * true for open, false for closed
	 */
	public boolean ctrlBlockClosed(TrackBlock b);
	/*
	 *Checks that train route passed by CTC is safe.
	 */
	public boolean checkRoute(TrainRoute r);	
}
