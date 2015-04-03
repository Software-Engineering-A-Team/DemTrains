package ctc_office;

import java.util.List;

public class TrainRoute {
	public int authority;
	public int speed;
	public int startingBlock;
	public List<Integer> route;
	public TrainRoute(int sBlock, List<Integer> routePath, int trainSpeed, int trainAuthority){
		authority = trainAuthority;
		speed = trainSpeed;
		startingBlock = sBlock;
		route = routePath;		
	}
}
