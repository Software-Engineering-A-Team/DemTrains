package ctcOffice;

import java.util.List;

public interface Infrastructure {
	public int getShape();
	
	public char getSection();
	
	public int getBlockNumber();
	
	public double getBlockLength();
	
	public double getGrade();
	
	public int getSpeed();
	
	public boolean containsSwitch();
	
	public boolean containsStation();
	
	public double getElevation();
	
	public double getCumulativeElevation();
	
	public Integer getSwitchId();
	
	public String[] getArrowDirection();

	public List<Block> getConnectedBlocks();
	
	public void setConnectedBlocks(List<Block> connected);
	
	public void addConnectedBlock(Block connected);
}
