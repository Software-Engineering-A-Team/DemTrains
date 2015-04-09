package train_model;


import java.util.ArrayList;

import system_wrapper.SimClock;
import system_wrapper.SystemWrapper;
import track_model.TrackBlock;
import train_controller.TrainController;


public class TrainModel {
	public short trainID;
	public String trainName;
	
	// True if run by its own GUI, false if spawned in system mode
	private boolean standAlone = true;
	
	// TODO: Train Controller here
	TrainController controller = null;
	
	// TODO: Track blocks?
	TrackBlock currBlock = null;
	
	// TODO: Connect to TrainModel GUI
	
	static final double LENGTH = 105.643;	// ft
	static final double HEIGHT = 11.2205;	// ft
	static final double WIDTH = 8.694226;	// ft
	
	double UNLADEN_WEIGHT = 90169.1;
	public double weight = UNLADEN_WEIGHT; // lbs (init as empty)
	
	private static final int MAX_PASSENGERS = 222;	// Seated + standing
	
	public int crewCount = 0;
	public int passengerCount = 0;
	
	public double position = 0;	// ft
	
	String beaconMsg = "No beacon";
	
	boolean leftDoorStatus = false;		// false = closed, true = open
	boolean rightDoorStatus = false;	// false = closed, true = open
	
	boolean lightStatus = false;	// false = on, true = off
	
	boolean engineFailure = false;	// true if failed
	boolean brakeFailure = false;	// true if failed
	boolean sigPickupFailure = false;	// true if failed
	
	// TODO: how is power conveyed from train controller?
	private static final double MAX_POWER = 120;	// kW
	private static final double MAX_SPEED = 43.496;	// mph
	private static final int MAX_GRADIENT = 60;	// %
	
	// med. accel. (2/3 load) from 0 to 43.496 mph
	private static final double LOAD_ACCEL = 1.6404199475066; 		// ft/s^2
	private static final double SBRAKE_ACCEL = -3.9370078740157;		// ft/s^2
	private static final double EBRAKE_ACCEL = -8.9566929133858;	// ft/s^2
	
	boolean sBrake = false;	// false = off, true = on
	boolean eBrake = false;	// false = off, true = on
	
	boolean airConditioning = false; // false = off, true = on
	
	double powCommand = 0;	// kW
	double accel = 0;	// ft/s^2
	double velocity = 0;	// ft/s
	double force = 0;	// N
	double speed = 0;
	
	public double commandedSpeed = 0;	// km/h
	public double commandedAuthority = 0;	// yards
	
	
	// Constructor used when a train is spawned
	public TrainModel (String name, short number) {
		this.trainID = number;
		this.trainName = name;
		
		// If train is NOT spawned by standalone TrainModel GUI, 
		// create a train controller and add to system list
		if (!this.trainName.equals("SingleTrain")){
			this.controller = new TrainController();
			SystemWrapper.trainControllers.add(this.controller);
			this.standAlone = false;
		}
	}
	
	// public getter & setter methods ********************************************
	
	/*
	 * Returns the current weight of the train
	 */
	public double getWeight() {
		return this.weight;
	}
	
	/*
	 * Returns the current position of the train in yards
	 */
	public double getPosition() {
		// Convert current position (in ft) to yards and then send it
		return this.position / 3.0;
	}
	
	/*
	 * Sets the Emergency brake status of the train
	 * True = on
	 * False = off
	 */
	public void setEmergencyBrake(boolean state) {
		this.eBrake = state;
	}
	
	/*
     * Sets the Service brake status of the train
     * True = on
     * False = off
     */
	public void setServiceBrake(boolean state) {
		this.sBrake = state;
	}
	
	/*
	 * Sets the lights on the train
	 * False = off
	 * True = on
	 */
	public void setLightStatus(boolean state) {
		this.lightStatus = state;
	}
	
	/*
	 * Opens / closes the left doors
	 * False = Closed
	 * True = Open
	 */
	public void setLeftDoors(boolean state) {
		this.leftDoorStatus = state;
	}
	
	/*
	 * Opens / closes the right doors
	 * False = Closed
	 * True = Open
	 */
	public void setRightDoors(boolean state) {
		this.rightDoorStatus = state;
	}
	
	/*
	 * Reads the beacon of the track block
	 */
	public boolean readBeacon(String beacon) {
		// Check to ensure beacon message is proper length
		if (beacon.length() > 20) {
			return false;
		} else {
			this.beaconMsg = beacon;
			return true;
		}	
	}
	
	/*
	 * Used to simulate failure of a train
	 */
	public void simulateFailure(String fail) {
		if (fail.equals("engine"))
			this.engineFailure = true;
		else if (fail.equals("brake"))
			this.brakeFailure = true;
		else if (fail.equals("signal pickup"))
			this.sigPickupFailure = true;
	}
	
	/*
	 * Used to restore a failure that has been set
	 */
	public void restoreFailure(String fix) {
		if (fix.equals("engine"))
			this.engineFailure = false;
		else if (fix.equals("brake"))
			this.brakeFailure = false;
		else if (fix.equals("signal pickup"))
			this.sigPickupFailure = false;
	}
	
	/* 
	 * Called by a station block to receive passengers from train
	 * @returns number of passengers exiting train
	 */
	public int passExitTrain() {
		// generate random number <= passengers on train
		// subtract this number from passengers currently on train
		// update passenger count, return number generated
		return 5;
	}

	/*
	 * Called by a station block to give passengers to the train
	 * @param passCount: passengers station wishes to board train
	 * @returns number of passengers accepted by train
	 */
	public int passEnterTrain(int passCount) {
		// if passCount + currPass <= maxPass, take all
		// else only take some
		return 6;
	}
	
	/*
	 * Sets the commanded authority for the train 
	 */
	public boolean setCommAuth(double authority) {
		if (this.sigPickupFailure == true) {
			return false;
		} else {
			this.commandedAuthority = authority;
			return true;
		}
	}
	
	/*
	 * Sets the commanded speed for the train
	 */
	public boolean setCommSpeed(double speed) {
		if (this.sigPickupFailure == true) {
			return false;
		} else {
			this.commandedSpeed = speed;
			return true;
		}
	}
	
	/*
	 * Called on a clock tick to update the train's information
	 */
	public void run() {
		
		// Handle comms with trainController (if there is one)
		if (this.standAlone == false) {
			this.controller.setCurrentSpeed(this.velocity);
			
			this.powCommand = this.controller.calcPower();
			this.sBrake = this.controller.isServiceBrakeOn();
			this.eBrake = this.controller.isEmergencyBrakeOn();
			this.leftDoorStatus = this.controller.isLeftDoorOpen();
			this.rightDoorStatus = this.controller.isRightDoorOpen();
			this.lightStatus = this.controller.isLightOn();
			this.airConditioning = this.controller.isAirConditioningOn();
		}
		
		
		
		
		// Get time difference needed for calculations
		// divide by 1000 to get value in seconds
		double delta = (double)SimClock.getDeltaMs() / 1000;	

		// Calculate the current velocity
		this.calcVelocity(delta);
		
		
		// Update weight of the train
		this.calcWeight();
		
		// Update position
		this.calcPosition(delta);
		
		// Check if we need a new Track Block
		TrackBlock oldBlock = this.currBlock;
		this.currBlock = SystemWrapper.trackModelGUI.trackModel.getCurrentBlock(this.trainName, this.position / 3, oldBlock);
	}
	
	// internal methods for calculations *********************
	
	/*
	 * Calculates the weight of the train based on train's weight and the weight of passengers and crew
	 */
	private void calcWeight() {
		// Average weight of a person is 185 lbs
		this.weight = ((crewCount + passengerCount) * 185) + UNLADEN_WEIGHT;
	}
	
	/*
	 * Calculates the current force of the train produced by the engines
	 */
	private double calcForce() {
		return 5.0;
	}
	
	/*
	 * Calculates the acceleration of the train
	 */
	private double calcAccel() {
		return 5.0;
	}
	
	/*
	 * Calculates the velocity of the train using the equation
	 * v = (P/v) * (1/m) * (1/s)
	 */
	private void calcVelocity(double sec) {
		double newForce = 0, newAccel = 0, newVelocity = 0;
		
		// Calculate the force
		if (this.velocity == 0) {	// if velocity == 0, use max power
			newForce = powCommand / 0.001;
		} else if (velocity != 0) {
			newForce = (powCommand / this.velocity);
		}
		if (this.engineFailure == true) {
			newForce = 0;
		}
		
		// Calculate the acceleration
		if (this.sBrake == false && this.eBrake == false) {
			newAccel = newForce / this.weight;
		} else if (this.sBrake == true && this.brakeFailure == false) {
			newAccel = (newForce / this.weight) + SBRAKE_ACCEL;
		} else if (this.eBrake == true) {
			newAccel = (newForce / this.weight) + EBRAKE_ACCEL;
		}
		if (this.velocity == 0 && this.accel < 0)
			newAccel = 0;
		
		newVelocity += newAccel * (1 / sec);
		if (newVelocity < 0)
			newVelocity = 0;
		
		this.force = newForce;
		this.accel = newAccel;
		this.velocity = newVelocity;
		// TODO: account for track grade variations
		// TODO: account for friction..?
	}
	
	/*
	 * Calculates the position of the train
	 */
	private void calcPosition(double sec) {
		double newPosition = (this.velocity / sec);
		this.position += newPosition;
	}
	
	
	
}
