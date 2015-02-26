/* *
  * The PLCInterface interface provides functions
 * necessary for the Wayside Engineer to process signals
 * sent to the Wayside Controller. The functions must be
 * implemented in a separate PLC class at the Wayside Engineer's
 * discretion.
 * 
 * @version		1.0 21 Feb 2015
 * @author		Jessica Egler
 * */
import java.util.List;

public interface PLCInterface {
	
	 public int ctrlLights(int lights, boolean trackSig, boolean open, boolean broken);
	
	 public TrackBlock changeSwitch(Switch s, List<TrackBlock> route, boolean trackSig);
}
