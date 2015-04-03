package mbo;

public abstract class AuthorityCalculator {

	protected double trainSpeed = 0;
	
	//not sure what attributes yet!!!!!!!!!!!**********
	
	abstract public double calculateAuthorityDistance(double trainPosition);
	abstract public double calculateSpeed(double distanceTraveled, double weight);
	abstract public double calculateSafeStoppingDistance(double trainPosition);  //safeMovingBlockAuthority
	abstract public double calculateCommandedSpeed(double speed);
}
