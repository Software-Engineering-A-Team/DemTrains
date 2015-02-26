package ctcOffice;

import java.util.List;

public class Train {
	private List<Infrastructure> route;
	private Infrastructure currentBlock;
	private Infrastructure previousBlock = null;
	private double speed = 40;
	public Train(Infrastructure startBlock){
		currentBlock = startBlock;
		startBlock.closeBlock();
	}

	public List<Infrastructure> getRoute(){
		return route;
	}
	public void setRoute(List<Infrastructure> newRoute){
		route = newRoute;
	}
	
	public void setCurrentBlock(Infrastructure currBlock){
		previousBlock = currentBlock;
		currentBlock = currBlock;
		previousBlock.openBlock();
		currentBlock.closeBlock();
	}
	
	public Infrastructure getCurrentBlock(){
		return currentBlock;
	}
	
	public Infrastructure getPreviousBlock(){
		return previousBlock;
	}
	
	public double getSpeed(){
		return speed;
	}
}
