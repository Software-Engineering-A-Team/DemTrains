package mbo;

public class Calculator2 extends AuthorityCalculator{

	public double calculateAuthorityDistance(double currLocation, double nextTrainLocation) {
		double x = 1;
		return x;  //just messing with stuff to get a better understanding
	}
	public double calculateSpeed(double prevLocation, double currLocation, double time) {
		double speed;
		speed = (currLocation-prevLocation)/time;
		return speed;
	}
	public double calculateSafeStoppingDistance(double x, double y, double z) {
		return x;
	}
	public double calculateCommandedSpeed(double x) {
		return x;
	}
}
