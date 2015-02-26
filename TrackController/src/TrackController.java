import java.util.List;

public class TrackController{
	private final char section;
	private final int blockNumber;
	private final int speed;
	private final List<String> infrastructure; // null is nothing
	private final Integer switchId;
	private TrackBlock block;
	private boolean open;
	private boolean trackSig;
	private int lights;
	private double sugSpd;
	private double sugAuth;
	private boolean heater;
	private List<String> weather; //null is clear weather
	private boolean broken;
	private String[] beaconInfo;
	private List<TrackBlock> route;
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
		block = b;
		section = b.getSection();
		blockNumber = b.getBlockNumber();
		speed = b.getSpeed();
		infrastructure = b.getInfrastructure();
		switchId = b.getSwitchId();
		open = b.isOpen();
		trackSig = b.trackSigHigh();
		lights = b.getLights();
		heater = b.getHeater();
		broken = b.isBroken();
		plc = new PLCProgram();
	}
	
	public TrackController(Switch s){
		section = s.getSection();
		blockNumber = s.getBlockNumber();
		speed = s.getSpeed();
		infrastructure = s.getInfrastructure();
		switchId = s.getSwitchId();
		open = s.isOpen();
		trackSig = s.trackSigHigh();
		lights = s.getLights();
		heater = s.getHeater();
		broken = s.isBroken();
		plc = new PLCProgram();
	}
	
	public void setTrackSig(boolean sig){
		trackSig = sig;
		//System.out.println(trackSig);
	}
	
	public int getSpeed(){
		return speed;
	}
	
	public boolean isOccupied(){
		return trackSig;
	}
	public void checkLights(){
		setLights(plc.ctrlLights(lights, trackSig, open, broken));
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
	
	public void setLights(int l){
		lights = l;
		
	}
	
	public void setOpen(boolean c){
		open=c;
	}
	
	public boolean isOpen(){
		return open;
	}
	
	public boolean isBroken(){
		return broken;
	}
	
	public TrackBlock chkSwitch(List<TrackBlock> route){
		if(switchId!=null){
		TrackBlock sp = plc.changeSwitch((Switch)block, route);
		return sp;
		}
		else return null;
	}
}
