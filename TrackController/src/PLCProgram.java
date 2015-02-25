import java.util.List;
import java.util.Arrays;

public class PLCProgram implements PLCInterface {
	
	public int ctrlLights(int lights, boolean trackSignal, boolean open) {
		if (!open) return 1;							//if block closed turn on red light
		else if(lights==0 && trackSignal==true){	//if lights green and block occupied
			return 1; //lights set to red
		}
		else if(lights==1 && trackSignal==false){	//if light red and block not occupied
			return 1; //lights set red
		}
		else return 0; //lights set green
	}

	public Switch changeSwitch(Switch s, List<TrackBlock> route) {
		int currentPos = route.indexOf(s);					//get position in route
		TrackBlock next = route.get(currentPos+1);			//find next block to pass through
		TrackBlock[] connected = s.getConnectedBlocks();	//get the array of connected blocks
		
		if(s.getNextBlock() != next){						//if next is not already connected to switch
			List<TrackBlock> conList = Arrays.asList(connected);	
			if(conList.contains(next)){						//does the connected blocks list contain next
				int i = conList.indexOf(next);				
				s.setSwitchPosition(i);						//set the switch id of next to switch pos
				return s;
			}
		}
		return s;
	}
}
