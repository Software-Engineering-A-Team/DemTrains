package mbo;

public abstract class AuthorityCalculator {

	//protected double trainSpeed = 0;	
	abstract public double calculateAuthorityDistance(double currLocation, double nextTrainLocation);
	abstract public double calculateSpeed(double prevLocation, double currLocation, double time);
	abstract public double calculateSafeStoppingDistance(double speed, double weight, double grade);  //safeMovingBlockAuthority
	abstract public double calculateCommandedSpeed(double speed);
}
