package train_model;


import java.util.ArrayList;

import mbo.MovingBlockOverlay;


public class TrainModel {
	public short trainID;
	public String trainName;
	
	// TODO: Train Controller here
	// TODO: Track blocks?
	// TODO: TrainModel GUI
	
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
	
	double powCommand = 0;	// kW
	double accel = 0;	// ft/s^2
	double velocity = 0;	// ft/s
	double force = 0;	// N
	double speed = 0;
	
	public double commandedSpeed = 0;	// km/h
	public double commandedAuthority = 0;	// ???
	
	
	// Constructor used when a train is spawned
	public TrainModel (String name, short number) {
		this.trainID = number;
		this.trainName = name;
		// TODO: Instantiate GUI (system mode)
		// TODO: this.trainController = new TrainController();
	}
	
	// Constructor used if TrainModel is started on its own
	public TrainModel () {
		this.trainID = 0;
		this.trainName = "Default";
		
		// TODO: this.trainController = null;
		
		ArrayList<TrainModel> trainList = new ArrayList<TrainModel>(1);
		trainList.add(this);
		// TODO: Instantiate GUI (independent mode)
	}
	
	// public getter & setter methods
	
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
	 * returns passengers exiting
	 */
	public int passExitTrain() {
		// generate random number <= passengers on train
		// subtract this number from passengers currently on train
		// update passenger count, return number generated
		return 5;
	}

	/*
	 * Take in amount of passengers station wishes to board train, return actual passengers accepted
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
		// Get power command from Train Controller
		// Calculate the force
		// Calculate the acceleration
		// Calculate the current velocity
		// Update position
		// Check if we need a new Track Block
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
	private double calcVelocity() {
		double newVelocity = (powCommand / this.velocity) * (1 / this.weight) * (1 / sec);
		this.velocity = newVelocity;
		return newVelocity;
		// TODO: account for engine failures
		// TODO: determine what the time interval is for the calculation
	}
	
	/*
	 * Calculates the position of the train
	 */
	private double calcPosition() {
		double newPosition = (this.velocity / sec);
		this.position += newPosition;
		return newPosition;
		// TODO: figure out how to get time interval
	}
	
	
	
}
