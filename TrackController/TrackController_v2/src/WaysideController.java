import java.util.List;

public class WaysideController {
	List<TrackBlock> coveredBlocks = new ArrayList<TrackBlock>;
	PLCInterface plc1;
	PLCInterface plc2;
	char section;
	boolean occupied = false;
	
	public boolean checkSwitch(){}
	
	public boolean checkRailway(){}
	
	public boolean checkLights(){}
	
	public boolean checkHeater(){}
	
	public boolean checkSpeedAuthority(double speed, double auth){}
	
	public boolean checkBlockStatus(){}
	
	public void updatePLC(String fileName){}
	
	public void updateBeacon(String beacon, int blockNum){}
	} 