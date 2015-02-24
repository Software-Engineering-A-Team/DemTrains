package ctcOffice;

import java.util.Arrays;
import java.util.List;

public class Block {
	private final int shape; //0 is a square, 1 is a triangle, 2 is a Circle, and 3 is a 'y' like switch object with no arrows, 4 is a 'y' like switch object with arrows
	private final char section;
	private final int blockNumber;
	private final int blockLength;
	private final double grade;
	private final int speed;
	private final List<String> infrastructure; // null is nothing
	private final double elevation;
	private final double cumulativeElevation;
	private final String[] arrowDirection;
	private final Integer switchId;
	private Block from;
	private Block nextBlock;
	private boolean open = true;
	
	/*
	 * Java requires a default constructor for child classes, but
	 * this class needs an argument to be valid. It will raise an exception
	 * if the default constructor is called.
	 */
	public Block(){
		throw new UnsupportedOperationException("You can't create a block class or subclass with a default constructor");
	}
	public Block(String[] blockDescriptor){
		section = blockDescriptor[1].charAt(1);
		blockNumber = Integer.parseInt(blockDescriptor[2]);
		blockLength = Integer.parseInt(blockDescriptor[3]);
		grade = Double.parseDouble(blockDescriptor[4]);
		speed = Integer.parseInt(blockDescriptor[5]);
		infrastructure = Arrays.asList(blockDescriptor[6].split(";"));
		elevation = Double.parseDouble(blockDescriptor[7]);
		cumulativeElevation = Double.parseDouble(blockDescriptor[8]);
		switchId = Integer.parseInt(blockDescriptor[9]);
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
	
	public int getBlockLength(){
		return blockLength;
	}
	
	public double getGrade(){
		return grade;
	}
	
	public int getSpeed(){
		return speed;
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

	public Block getPreviousBlock(){
		return from;
	}
	
	public Block getNextBlock(){
		return nextBlock;		
	}
	
	public Block setNextBlock(Block next){
		return nextBlock;		
	}
	
}
