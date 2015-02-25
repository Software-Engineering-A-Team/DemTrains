import java.util.List;

public class TrackController{
	private final char section;
	private final int blockNumber;
	private final int speed;
	private final List<String> infrastructure; // null is nothing
	private final Integer switchId;
	private TrackBlock from;
	private TrackBlock nextBlock;
	private boolean open;
	private boolean trackSig;
	private int lights;
	private double sugSpd;
	private double sugAuth;
	private boolean heater;
	private List<String> weather; //null is clear weather
	private boolean broken;
	private String[] beaconInfo;
	private PLCProgram plc;
		
	/*
	 * Java requires a default constructor for child classes, but
	 * this class needs an argument to be valid. It will raise an exception
	 * if the default constructor is called.
	 */
	public TrackController(){
		throw new UnsupportedOperationException("You can't create a TrackController class or subclass with a default constructor");
	}
	
	public TrackController(TrackBlock b){
		section = b.getSection();
		blockNumber = b.getBlockNumber();
		speed = b.getSpeed();
		infrastructure = b.getInfrastructure();
		switchId = b.getSwitchId();
		from = b.getPreviousBlock();
		nextBlock = b.getNextBlock();
		open = b.isOpen();
		trackSig = b.trackSigHigh();
		lights = b.getLights();
		heater = b.getHeater();
		broken = b.isBroken();
		plc = new PLCProgram();
	}
	
	public void setTrackSig(boolean sig){
		trackSig = sig;
	}
	
	public void checkLights(){
		plc.ctrlLights(lights, trackSig, open);
	}
	
	public void setBroken(boolean b){
		broken = b;
	}
	
	public void checkHeater(){
		//plc.ctrlHeater(weather, open, broken);
	}
	
	public int getLights(){
		return lights;
	}
	
	public static void main (String [] args){
		String[] specs = new String[]{"A", "2", "50", "", "6"};
		TrackBlock b = new TrackBlock(specs);
		TrackController t = new TrackController(b);
	    
		t.setTrackSig(true);
		
		if(t.getLights()==1){
			System.out.println("The lights are red.");
		}
		else System.out.println("The lights are green.");
	}
}
