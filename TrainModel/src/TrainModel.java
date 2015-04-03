import java.util.ArrayList;


public class TrainModel {
	public int trainID;
	
	// TODO: Train Controller here
	// TODO: Track blocks?
	// TODO: TrainModel GUI
	
	static final double LENGTH = 32.2;	// m
	static final double HEIGHT = 3.42;	// m
	static final double WIDTH = 2.65;	// m
	
	double mass = 37103.9; // kg (init as empty)
	double UNLADEN_MASS = 37103.9;
	
	private static final int MAX_PASSENGERS = 222;	// Seated + standing
	
	public int crewCount = 0;
	public int passengerCount = 0;
	
	public double position = 0;	// m
	
	String beaconMsg = "No beacon";
	
	boolean leftDoorStatus = false;		// false = closed, true = open
	boolean rightDoorStatus = false;	// false = closed, true = open
	
	boolean lightStatus = false;	// false = on, true = off
	
	boolean engineFailure = false;	// true if failed
	boolean brakeFailure = false;	// true if failed
	boolean sigPickupFailure = false;	// true if failed
	
	private static final double MAX_POWER = 120;	// kW
	private static final double MAX_SPEED = 70;	// km/h
	private static final int MAX_GRADIENT = 60;	// %
	
	// med. accel. (2/3 load) from 0 to 70 km/h
	private static final double LOAD_ACCEL = 0.5; 		// m/s^2
	private static final double SBRAKE_ACCEL = -1.2;		// m/s^2
	private static final double EBRAKE_ACCEL = -2.73;	// m/s^2
	
	boolean sBrake = false;	// false = off, true = on
	boolean eBrake = false;	// false = off, true = on
	
	double accel = 0;	// m/s^2
	double velocity = 0;	// m/s
	double force = 0;	// N
	
	public double commandedSpeed = 0;	// km/h
	public double commandedAuthority = 0;	// ???
	
	// Constructor used when a train is spawned
	TrainModel (int trainNum) {
		this.trainID = trainNum;
		// TODO: Instantiate GUI (system mode)
		// TODO: this.trainController = new TrainController();
	}
	
	// Constructor used if TrainModel is started on its own
	TrainModel () {
		this.trainID = 1;
		
		// TODO: this.trainController = null;
		
		ArrayList<TrainModel> trainList = new ArrayList<TrainModel>(1);
		trainList.add(this);
		// TODO: Instantiate GUI (independent mode)
	}
	
	// public getter / setter methods
	public double getMass() {
		return this.mass;
	}
	public double getPosition() {
		return this.position;
	}
	
	public void setEmergencyBrake(boolean state) {
		this.eBrake = state;
	}
	
	public void setServiceBrake(boolean state) {
		this.sBrake = state;
	}
	
	public boolean readBeacon(String beacon) {
		// Check to ensure beacon message is proper length
		if (beacon.length() > 20) {
			return false;
		} else {
			this.beaconMsg = beacon;
			return true;
		}	
	}
	
	public void simulateFailure(String fail) {
		if (fail.equals("engine"))
			this.engineFailure = true;
		else if (fail.equals("brake"))
			this.brakeFailure = true;
		else if (fail.equals("signal pickup"))
			this.sigPickupFailure = true;
	}
	
	public void restoreFailure(String fix) {
		if (fix.equals("engine"))
			this.engineFailure = false;
		else if (fix.equals("brake"))
			this.brakeFailure = false;
		else if (fix.equals("signal pickup"))
			this.sigPickupFailure = false;
	}
	
	// returns passengers exiting
	public int passExitTrain() {
		// generate random number <= passengers on train
		// subtract this number from passengers currently on train
		// update passenger count, return number generated
		return 5;
	}
	
	// take in amount of passengers station wishes to give train, return
	// actual passengers accepted
	public int passEnterTrain(int passCount) {
		// if passCount + currPass <= maxPass, take all
		// else only take some
		return 6;
	}
	
	public boolean setCommAuth(double authority) {
		if (this.sigPickupFailure == true) {
			return false;
		} else {
			this.commandedAuthority = authority;
			return true;
		}
	}
	
	public boolean setCommSpeed(double speed) {
		if (this.sigPickupFailure == true) {
			return false;
		} else {
			this.commandedSpeed = speed;
			return true;
		}
	}
	
	// internal methods for calculations *********************
	private void calcMass() {
		this.mass = ((crewCount + passengerCount) * 80.7) + UNLADEN_MASS;
	}
	
	private void calcForce() {
		
	}
	
	private void calcAccel() {
		
	}
	
	private void calcVelocity() {
		
	}
	private void calcPosition() {
		
	}
	
	
	
}
