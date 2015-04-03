package mbo;

public abstract class AuthorityCalculator {

	protected double trainSpeed = 0;
	
	abstract public double[] getAuthority(double trainPosition);
	
}
