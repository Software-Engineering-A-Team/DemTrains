package MBO;
import java.util.ArrayList;
import java.util.List;


public class SafetyRedundancySystem {

	private List<AuthorityCalculator> ac;
	
	public void SRS() {
		
		ac = new ArrayList<AuthorityCalculator>();
		double[] calc1 = ac[0].getAuthority();
		double[] authority = compare(calc1, calc2, calc3);
		
		
		
		
		
	}
	
	private double[] compare(double[] x,double[] y,double[] z){
		double[] result = {0,1} ;  //going to contain the safe stopping distance and commanded speed		
		return result;
	}
	
}
