package ctc_office;

import java.util.List;

public class TrainRoute implements Comparable<TrainRoute>{
	public String lineName;
	public double authority;
	public double speed;
	public int startingBlock;
	public Double weight;
	public List<Integer> route;
	public TrainRoute(String lName, int sBlock, List<Integer> routePath, double trainSpeed, double trainAuthority, double w){
		lineName = lName;
		authority = trainAuthority;
		speed = trainSpeed;
		startingBlock = sBlock;
		route = routePath;
		weight = w;
	}
	@Override
	public int compareTo(TrainRoute t) {
		// TODO Auto-generated method stub
		return this.weight.compareTo(t.weight);
	}
	public TrainRoute(String lName, int sBlock, List<Integer> routePath, double trainSpeed, double trainAuthority){
		lineName = lName;
		authority = trainAuthority;
		speed = trainSpeed;
		startingBlock = sBlock;
		route = routePath;
	}
}
