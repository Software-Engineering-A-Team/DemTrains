package mbo;

import java.util.ArrayList;
import java.util.List;

public class SRSDistFromNextTrain {

private List<AuthorityCalculator> calculatorList;
	
	public SRSDistFromNextTrain() {		
		calculatorList = new ArrayList<AuthorityCalculator>();
		Calculator1 calc1 = new Calculator1();
		Calculator2 calc2 = new Calculator2();
		calculatorList.add(0,calc1);
		calculatorList.add(1,calc2);
		//********Calculator2 will be added later
	}
	
	public Double calcSafeAuth(double currLocation, double nextTrainLocation){
		double safeAuth1;
		double safeAuth2;
		currLocation = currLocation * 0.000568182; //converts yards to miles
		nextTrainLocation = nextTrainLocation * 0.000568182; //converts yards to miles
		safeAuth1 = calculatorList.get(0).calculateAuthorityDistance(currLocation, nextTrainLocation);
		safeAuth2 = calculatorList.get(1).calculateAuthorityDistance(currLocation, nextTrainLocation);

		if(compare(safeAuth1,safeAuth2)){			
			return safeAuth1; 
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
