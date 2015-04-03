package mbo;
import java.util.ArrayList;
import java.util.List;


public class SafetyRedundancySystem {

	private List<AuthorityCalculator> calculatorList;
	
	public void SRS() {
		
		calculatorList = new ArrayList<AuthorityCalculator>();
		Calculator1 calc1 = new Calculator1();
		calculatorList.add(0,calc1);
		double abc = calculatorList.get(0).calculateAuthorityDistance(3); ///ohhhhhhh yeahh
		double authority = compare(calc1, calc2, calc3);
		
		
		
		
		
	}
	
	private double compare(double x,double y,double z) {		
		return x;
	}
	
}
