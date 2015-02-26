import java.util.List;

public class PLCProgram implements PLCInterface {
	
	public int ctrlLights(int lights, boolean trackSignal, boolean open, boolean broken) {
		if (!open) return 1;	//if block closed turn on red light
		//else if (broken) return 1;
		else if(lights==0 && trackSignal==true){	//if lights green and block occupied
			return 1; //lights set to red
		}
		else if(lights==1 && trackSignal==true) return 1;
		else if(lights==1 && trackSignal==false){	//if light red and block not occupied
			return 0; //lights set green
		}
		else return 0; //lights set green
	}

	public TrackBlock changeSwitch(Switch s, List<TrackBlock> route) {
		TrackBlock switchP = s.getNextblock();
		int currentPos = route.indexOf(s);						//get position in route
		TrackBlock next = route.get(currentPos+1);				//find next block to pass through
		List<TrackBlock> connected = s.getConnectedBlocks();	//get the list of connected blocks
		TrackBlock curCon = s.getNextblock();
		if(curCon.getBlockNumber() != next.getBlockNumber()){ 	//if next is not already connected to switch
			if(connected.contains(next)){						//does the connected blocks list contain next
				int i = connected.indexOf(next) - 1;
				if(s.setSwitchPosition(i)){
					switchP = s.getNextblock(); //set switch pos
					return switchP;
				}
				
			}
		}
		return switchP;
	}
}
