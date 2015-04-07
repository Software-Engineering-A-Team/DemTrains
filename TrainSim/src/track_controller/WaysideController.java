package track_controller;
import javax.tools.*;
//import track_model.TrackBlock;
import ctc_office.*;
import java.util.ArrayList;

public class WaysideController {
	ArrayList<TrackBlock> affectedBlocks = new ArrayList<TrackBlock>();
	PLCInterface plc;
	int span = 0;
	
	/*
	 * Constructs a wayside controller based on a 
	 * list of blocks it will control.
	 */
	WaysideController(TrackBlock[] b) {
		for (int i=0; i<b.length; i++){
			affectedBlocks.add(b[i]);
			//b[i].waysideController = this;
			span++;
		}	
	}
		
	/*
	 * Updates the PLC program for the controller
	 * based on a user input file.
	 */
	public boolean updatePLC(String filename) {
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
		
	}
	
	/*
	 * Allows user to manually add a block
	 * to the a certain controller.
	 */
	public boolean addBlock(TrackBlock b) {
		if(affectedBlocks.contains(b)) return false;
		else {
			affectedBlocks.add(b);
			return true;
		}
	}
	
	public static void main (String[] args){
		TrackBlock block = new TrackBlock();
		TrackBlock[] b = {block};
		WaysideController w = new WaysideController(b);
		System.out.println(w.updatePLC("/TrainSim/src/track_controller/PLCCrossing.java"));
	}
	
}
