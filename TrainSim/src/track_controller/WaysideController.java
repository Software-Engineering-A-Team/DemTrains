package track_controller;
import javax.tools.*;
//import track_model.TrackBlock;
import track_model.*;
import ctc_office.TrainRoute;
import java.util.ArrayList;
import java.util.HashMap;

public class WaysideController {
	ArrayList<TrackBlock> affectedBlocks = new ArrayList<TrackBlock>();
	HashMap<Integer, TrackBlock> blockMap = new HashMap<Integer, TrackBlock>();
	TrainRoute route;
	PLCInterface plc;
	int span = 0;
	
	public boolean containsSwitch = false;
	public boolean containsCrossing = false;
	
			
	/*
	 * Updates the PLC program for the controller
	 * based on a user input file.
	 */
	public boolean updatePLC(String filename) {
		System.setProperty("java.home", "C:\\Program Files\\Java\\jdk1.7.0_40");
		JavaCompiler comp = ToolProvider.getSystemJavaCompiler();
		int compRes = comp.run(null, null, null, filename);
		if (compRes == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/*
	 * Runs user specified PLC program twice and compares
	 * outputs to ensure safe operation of the system.
	 */
	public void runPLC() {
		plc.run();
	}
	
	/*
	 * Allows user to manually add a block
	 * to the a certain controller.
	 */
	public boolean addBlocks(ArrayList<TrackBlock> b) {
		for (TrackBlock block: b){
			affectedBlocks.add(block);
			blockMap.put(block.number, block);
			span++;
		}
		return true;
	}		
}
