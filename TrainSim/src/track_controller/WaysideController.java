import javax.tools.*;
import track_model.TrackBlock;
package track_controller;

public class WaysideController {
	List<TrackBlock> affectedBlocks;
	PLCInterface plc;
	int span = 0;
	
	/*
	 * Constructs a wayside controller based on a 
	 * list of blocks it will control.
	 */
	WaysideController(TrackBlock[] b) {
		for (int i=0; i<b.length; i++){
			affectedBlocks.add(b[i]);
			b[i].waysidecontroller = this;
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
		else affectedBlocks.add(b);
	}
	
	
}
