import java.util.ArrayList;


public class TrainModelGUI {
	
	ArrayList<TrainModel> trainList = null;
	String sysMode = null;
	
	// Constructor
	TrainModelGUI (ArrayList<TrainModel> trains, boolean mode) {
		this.trainList = trains;
		if (mode == true) {
			// TODO: Set a label indicating TrainModelGUI is in system mode
			this.sysMode = "System mode (multiple trains)";
		} else {
			// TODO: Set a label indicating TrainModelGUI is in standalone mode
			this.sysMode = "Standalone mode (single train)";
		}
		
		
	}
	
	
	
}
