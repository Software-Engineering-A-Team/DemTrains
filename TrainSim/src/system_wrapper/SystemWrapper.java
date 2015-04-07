package system_wrapper;
import ctc_office.*;
import mbo.*;
import track_controller.*;
import track_model.*;
import train_controller.*;
import train_model.*;

public class SystemWrapper {
  public static SimClock simClock = new SimClock(10);
  
  
  
  TrackModel trackMod = new TrackModel();
  WaysideSystem waysideSys = new WaysideSystem(trackMod);
  TrackControllerGUI tcGUI = new TrackControllerGUI(waysideSys);
  
}
