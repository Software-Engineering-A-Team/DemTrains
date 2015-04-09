package mbo;

public abstract class AuthorityCalculator {

	abstract public double calculateSpeed(double prevLocation, double currLocation, double time);
	abstract public double calculateSafeStoppingDistance(double speed, double weight, double grade);  
	abstract public double calculateAuthorityDistance(double currLocation, double nextTrainLocation);
	abstract public double calculateCommandedSpeed(double speed);
}
