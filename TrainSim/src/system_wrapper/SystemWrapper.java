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
  public static TrackModel trackMod = new TrackModel();
  public static WaysideSystem waysideSys = new WaysideSystem(trackMod);
  public static JFrame trackControllerGui = new TrackControllerGUI(waysideSys);
  public static JFrame trainControllerGui = new TrainControllerGui();
  
  
}
