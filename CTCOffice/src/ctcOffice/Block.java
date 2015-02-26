package ctcOffice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Block implements Infrastructure{
	private final int shape; //0 is a square, 1 is a triangle, 2 is a Circle, and 3 is a 'y' like switch object with no arrows, 4 is a 'y' like switch object with arrows
	private final char section;
	private final int blockNumber;
	private final double blockLength;
	private final double grade;
	private final int speedLimit;
	private final List<String> infrastructure; // null is nothing
	private final double elevation;
	private final double cumulativeElevation;
	private final String[] arrowDirection;
	private final Integer switchId;
	private List<Block> connectedBlocks = new ArrayList<Block>();
	private boolean open = true;
	private final String line;
	
	/*
	 * Java requires a default constructor for child classes, but
	 * this class needs an argument to be valid. It will raise an exception
	 * if the default constructor is called.
	 */
	public Block(){
		throw new UnsupportedOperationException("You can't create a block class or subclass with a default constructor");
	}
	public Block(String[] blockDescriptor){
		line = blockDescriptor[0];
		section = blockDescriptor[1].charAt(0);
		blockNumber = Integer.parseInt(blockDescriptor[2]);
		blockLength = Double.parseDouble(blockDescriptor[3]);
		grade = Double.parseDouble(blockDescriptor[4]);
		speedLimit = Integer.parseInt(blockDescriptor[5]);
		infrastructure = Arrays.asList(blockDescriptor[6].split(";"));
		elevation = Double.parseDouble(blockDescriptor[7]);
		cumulativeElevation = Double.parseDouble(blockDescriptor[8]);
		if (!blockDescriptor[9].equals("")){
			switchId = Integer.parseInt(blockDescriptor[9].substring(7));
		}
		else{
			switchId = null;
		}
		arrowDirection = blockDescriptor[10].split(";");
		boolean arrows = false;
		if (arrowDirection != null){
			arrows = true;
		}
		if (infrastructure.contains("STATION")){
			shape = 2;
		}
		else if (infrastructure.contains("SWITCH")){
			if (arrows){
				shape = 4;
			}
			else{
				shape = 3;
			}
		}
		else if (arrows){
			shape = 1;
		}
		else{
			shape = 0;
		}
		
	}
	
	public int getShape(){
		return shape;
	}
	
	public char getSection(){
		return section;
	}
	
	public int getBlockNumber(){
		return blockNumber;
	}
	
	public double getBlockLength(){
		return blockLength;
	}
	
	public double getGrade(){
		return grade;
	}
	
	public int getSpeed(){
		return speedLimit;
	}
	
	public boolean containsSwitch(){
		return infrastructure.contains("SWITCH");
	}
	
	public boolean containsStation(){
		return infrastructure.contains("STATION");
	}
	
	public double getElevation(){
		return elevation;
	}
	
	public double getCumulativeElevation(){
		return cumulativeElevation;
	}
	
	public Integer getSwitchId(){
		return switchId;
	}
	
	public String[] getArrowDirection(){
		return arrowDirection;
	}

	@SuppressWarnings("unchecked")
	public List<Block> getConnectedBlocks(){
		return (List<Block>)((ArrayList<Block>)connectedBlocks).clone();
	}
	
	public void setConnectedBlocks(List<Block> connected){
		if (!connectedBlocks.contains(connected)){
			connectedBlocks = connected;			
		}
	}
	
	public void addConnectedBlock(Block connected){
		connectedBlocks.add(connected);
		return;
	}
	public void openBlock(){
		open = true;
	}
	public void closeBlock(){
		open = false;
	}
	public boolean isOpen(){
		return open;
	}
	
	public String toString(){
		String displayString = "<html><body>";
		displayString += "Section:\t" + section + "<br>";
		displayString += "Block Number:\t" + blockNumber + "<br>";
		displayString += "BlockLength:\t" + blockLength + "<br>";
		displayString += "Block Grade(%):\t" + grade + "<br>";
		displayString += "Speed Limit (KM/Hr):\t" + speedLimit + "<br>";
		displayString += "Elevation (M):\t" + elevation + "<br>";
		displayString += "Cumalitive Elevation (M):\t" + cumulativeElevation + "<br>";
		
		return displayString + "</body></html>";
	}
	
}
