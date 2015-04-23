package mbo;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;


public class SRSTrainCurrSpeed {

	private List<AuthorityCalculator> calculatorList;
	
	public SRSTrainCurrSpeed() {		
		calculatorList = new ArrayList<AuthorityCalculator>();
		Calculator1 calc1 = new Calculator1();
		Calculator2 calc2 = new Calculator2();
		calculatorList.add(0,calc1);
		calculatorList.add(1,calc2);		
	}
	
	public Double calcTrainSpeed(double prevLocation, double currLocation, double time){
		double speed1;
		double speed2;
		time = (time * 2.7778 * Math.pow(10,-7)); //converts miliseconds to hours
		prevLocation = prevLocation * 0.000568182; //converts yards to miles
		currLocation = currLocation * 0.000568182; //converts yards to miles
		speed1 = calculatorList.get(0).calculateSpeed(prevLocation, currLocation, time);
		speed2 = calculatorList.get(1).calculateSpeed(prevLocation, currLocation, time);//will need to change to second calculator

		if(compare(speed1,speed2)){			
			return speed1; 
		}
		else{			
			return null;
		}
	}
	
	private boolean compare(double x,double y) {
		if(x==y){
			return true;
		}
		else{
			return false;
		}
		
	}
	
}
