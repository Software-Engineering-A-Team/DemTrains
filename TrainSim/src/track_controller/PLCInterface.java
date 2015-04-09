package track_controller;
import track_model.TrackBlock;
import ctc_office.TrainRoute;

public interface PLCInterface {
	/*
	 * Determines safe state of the railway crossing and returns the state
	 * true for active, false for inactive
	 */
	public boolean ctrlCrossing();
	/*
	 * Determines safe state of the switch and returns the state
	 * true for second block in attach array , false for first block in attach array
	 */
	public boolean ctrlSwitch();
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
	public boolean ctrlSpeedAuthority(TrackBlock b, double speed, double authority);
	/*
	 * Determines safe closing of block and returns block state
	 * true for open, false for closed
	 */
	public boolean ctrlBlockClosed(TrackBlock b);
	/*
	 * Runs all necessary plc to determine safe function for the
	 * entire area covered by the controller.
	 */
	public void run();
	/*
	 *Checks that train route passed by CTC is safe.
	 */
	public boolean checkRoute();
	
	public void changeRoute(TrainRoute r);
	
	public boolean switchCtrl();
	
}
