package system_wrapper;
import javax.swing.JFrame;

import ctc_office.*;
import mbo.*;
import track_controller.*;
import track_model.*;
import train_controller.*;
import train_model.*;

public class SystemWrapper {
  public static SimClock simClock = new SimClock(10);
  
  public static JFrame trainControllerGui = new TrainControllerGui();
  
  TrackModel trackMod = new TrackModel();
  WaysideSystem waysideSys = new WaysideSystem(trackMod);
  TrackControllerGUI tcGUI = new TrackControllerGUI(waysideSys);
  
  
}
