package ctc_office;

import java.util.List;

public class TrainRoute {
	public String lineName;
	public double authority;
	public double speed;
	public int startingBlock;
	public List<Integer> route;
	public TrainRoute(int sBlock, List<Integer> routePath, double trainSpeed, double trainAuthority){
		authority = trainAuthority;
		speed = trainSpeed;
		startingBlock = sBlock;
		route = routePath;
	}
}
