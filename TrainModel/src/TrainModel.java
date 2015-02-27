import java.util.Timer;
import java.util.TimerTask;


public class TrainModel {
	
	private static final double maxPower = 120; //kW
	private static final double maxVelocity = 70; //km/h
	private static final double maxPassenger = 222; // 74 Seated + 148 Standing
	private static final double emptyMass = 40900; // 40.9 t
	private static final double length = 32.2; // m
	private static final double width = 3.42; // m
	private static final double height = 2.65; // m
	
	private static final double servBrakeAccel = -1.2; // m/s2
	private static final double eBrakeAccel = -2.73; // m/s2
	private boolean servBrakeState = false; // true is on, false is off 
	private boolean eBrakeState = false; // true is on, false is off
	
	private int crewCount = 0;
	private int passengerCount = 0;
	private double force = 0;
	private double velocity = 0;
	private double acceleration = 0;
	private double mass = emptyMass;
	private double power = 0;
	
	private double dt = 1; // delta t for use in calculations
	
	public void setPower(double pow) {
		if (pow > maxPower)
			this.power = maxPower;
		else
			this.power = pow;
	}
	
	public double getPower() {
		return this.power;
	}
	
	public void calcAccel() {
		// F = m * a
		// P = F * v
		// F = P / v
		// a = (P / v) / m
		/* if we're starting from standstill--apply max power */
		if (velocity == 0) {
			force = power / 0.001;
		} else if (velocity != 0) {
			force = power / velocity;
		}
		if (servBrakeState == true)
			acceleration = (force / mass) + servBrakeAccel;
		else if (eBrakeState == true)
			acceleration = (force / mass) + eBrakeAccel;
		else
			acceleration = force / mass;
		if (velocity == 0 && acceleration < 0)
			acceleration = 0;
	}
	
	public double getForce() {
		return this.force;
	}
	
	public double getAccel() {
		return this.acceleration;
	}
	
	public void calcVelocity() {
		// v = (accel. * dt) + v
		this.velocity += (acceleration * dt);
		if (this.velocity < 0)
			this.velocity = 0;
	}
	
	public double getVelocity() {
		return this.velocity;
	}
	
	public void setServBrake(boolean newState) {
		this.servBrakeState = newState;
	}
	
	public void setEBrake(boolean newState) {
		this.eBrakeState = newState;
	}
	
	public boolean getServBrake() {
		return this.servBrakeState;
	}
	
	public boolean getEBrake() {
		return this.eBrakeState;
	}
	
	public void updateTime (double delta) {
		this.dt = delta;
		calcAccel();
		calcVelocity();
	}
	
	
	
	
	
	public static void main(String[] args) {
		//System.out.println("Welcome to TrainModel subsystem prototype!");
		
	}
}
