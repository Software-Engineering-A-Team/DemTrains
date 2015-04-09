package mbo;
import java.lang.Math;

public class Calculator1 extends AuthorityCalculator {	
	
	public double calculateAuthorityDistance(double currLocation, double nextTrainLocation) {		
		return nextTrainLocation - currLocation;  
	}
	public double calculateSpeed(double prevLocation, double currLocation, double time) {
		return (currLocation-prevLocation)/time; //return speed
	}
	public double calculateSafeStoppingDistance(double speed, double weight, double grade) {
		//need the grade of the current Block the train is on
		//use the mass of the train times gravity also including the grade (normal force)
		//or use weight assuming its mass*gravity
		final double mu = 0.65;
		final double g = 78919.1124; //gravity in miles/hour/hour LOL
		final double decel = 9663.56478; //service brake decel 1.2m/s/s in miles/hr/hr
		
		//d=v^2/2g(mu - grade); 
		//return (Math.pow(speed,2))/(2*g*(mu - grade));
		return (Math.pow(speed,2))/(2*decel);
	}
	public double calculateCommandedSpeed(double x) {
		return x;
	}
}
