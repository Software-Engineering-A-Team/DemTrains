package system_wrapper;
import java.util.ArrayList;

import javax.swing.JFrame;

import ctc_office.*;
import mbo.*;
import track_controller.*;
import track_model.*;
import train_controller.*;
import train_model.*;

public class SystemWrapper {
  public static double perceivedTimeMultiplier = 1;
  public static SimClock simClock = new SimClock(10);
  public static CTCDriver ctcOffice = new CTCDriver();
  public static ArrayList<TrainModel> trainModels = new ArrayList<TrainModel>();
  public static ArrayList<TrainController> trainControllers = new ArrayList<TrainController>();
  public static TrackModelGUI trackModelGUI = new TrackModelGUI();
  public static TrainModelGUI trainModelGUI = new TrainModelGUI(true);
  public static WaysideSystem waysideSys = new WaysideSystem(trackModelGUI.trackModel);
  public static MovingBlockOverlay mbo = new MovingBlockOverlay();
  public static JFrame trackControllerGui = new TrackControllerGUI(waysideSys, true);
  public static JFrame trainControllerGui = new TrainControllerGui(trainControllers);
  public static JFrame ctcGUI = new CTCGUI();
  public static JFrame mboGUI = new MBOGUI();
}
