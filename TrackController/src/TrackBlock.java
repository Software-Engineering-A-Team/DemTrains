import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrackBlock {
	private final String line;
	private final char section;
	private final int blockNumber;
	private final int speed;
	private final List<String> infrastructure; // null is nothing
	private final Integer switchId;
	private final String[] arrowDirection;
	private List<TrackBlock> connectedBlocks = new ArrayList<TrackBlock>();
	private boolean open = true;
	private boolean trackSig = false;
	private int lights = 0; 	/*0 is green, 1 is red may be changed to include yellow light*/
	private boolean heater = false;
	private boolean broken = false;
	
	/*
	 * Java requires a default constructor for child classes, but
	 * this class needs an argument to be valid. It will raise an exception
	 * if the default constructor is called.
	 */
	public TrackBlock(){
		throw new UnsupportedOperationException("You can't create a block class or subclass with a default constructor");
	}
	
	public TrackBlock(String[] blockDescriptor){
		line = blockDescriptor[0];
		section = blockDescriptor[1].charAt(0);
		blockNumber = Integer.parseInt(blockDescriptor[2]);
		speed = Integer.parseInt(blockDescriptor[3]);
		infrastructure = Arrays.asList(blockDescriptor[4].split(";"));
		if (!blockDescriptor[5].equals("")){
			switchId = Integer.parseInt(blockDescriptor[5].substring(7));
		}
		else{
			switchId = null;
		}
		arrowDirection = blockDescriptor[6].split(";");
	}
	
	public String getLine(){
		return line;
	}
	
	public int getBlockNumber(){
		return blockNumber;
	}
	
	public char getSection(){
		return section;
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
	
	public boolean containsCrossing(){
		return infrastructure.contains("RAILWAY CROSSING");
	}
	
	public List<String> getInfrastructure(){
		return infrastructure;
	}
	
	public Integer getSwitchId(){
		return switchId;
	}
	
	public boolean trackSigHigh(){
		return trackSig;
	}
	
	public boolean isOpen(){
		return open;
	}
	
	public int getLights(){
		return lights;
	}
	
	public boolean setLights(int n){
		if(n==0 || n==1){
			lights = n;
			return true;
		}
		else return false;
	}

	public boolean getHeater(){
		return heater;
	}
	
	public boolean isBroken(){
		return broken;
	}
	
	public String[] getArrowDirection(){
		return arrowDirection;
	}
	
	@SuppressWarnings("unchecked")
	public List<TrackBlock> getConnectedBlocks(){
		return (List<TrackBlock>)((ArrayList<TrackBlock>)connectedBlocks).clone();
	}
	
	public void setConnectedBlocks(List<TrackBlock> connected){
		if (!connectedBlocks.contains(connected)){
			connectedBlocks = connected;			
		}
	}
	
	public void addConnectedBlock(TrackBlock connected){
		connectedBlocks.add(connected);
		return;
	}
}
