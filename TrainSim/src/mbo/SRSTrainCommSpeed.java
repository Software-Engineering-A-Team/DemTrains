package mbo;

import java.util.ArrayList;
import java.util.List;

public class SRSTrainCommSpeed {

private List<AuthorityCalculator> calculatorList;
	
	public SRSTrainCommSpeed() {		
		calculatorList = new ArrayList<AuthorityCalculator>();
		Calculator1 calc1 = new Calculator1();
		Calculator2 calc2 = new Calculator2();
		calculatorList.add(0,calc1);
		calculatorList.add(1,calc2);
		//********Calculator2 will be added later
	}
	
	public Double calcCommSpeed(double distance){
		double commSpeed1;
		double commSpeed2;
		//distance already in miles no need for conversion
		commSpeed1 = calculatorList.get(0).calculateCommandedSpeed(distance);
		commSpeed2 = calculatorList.get(1).calculateCommandedSpeed(distance);

		if(compare(commSpeed1,commSpeed2)){			
			return commSpeed1; 
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
