package mbo;

import java.util.ArrayList;
import java.util.List;

public class SRSTrainStopDist {

	private List<AuthorityCalculator> calculatorList;
	
	public SRSTrainStopDist() {		
		calculatorList = new ArrayList<AuthorityCalculator>();
		Calculator1 calc1 = new Calculator1();
		Calculator2 calc2 = new Calculator2();
		calculatorList.add(0,calc1);
		calculatorList.add(1,calc2);	
		//********Calculator2 will be added later
	}
	
	public Double calcStopDist(double speed, double weight, double grade){
		double stopDistance1;
		double stopDistance2;
		stopDistance1 = calculatorList.get(0).calculateSafeStoppingDistance(speed, weight, grade);
		stopDistance2 = calculatorList.get(1).calculateSafeStoppingDistance(speed, weight, grade);

		if(compare(stopDistance1,stopDistance2)){			
			return stopDistance1; 
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
