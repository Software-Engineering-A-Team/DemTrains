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
  public static SimClock simClock = new SimClock(10);
  public static ArrayList<TrainModel> trainModels = new ArrayList<TrainModel>();
  public static TrackModel trackMod = new TrackModel();
  public static WaysideSystem waysideSys = new WaysideSystem(trackMod);
  public static MovingBlockOverlay mbo = new MovingBlockOverlay();
  public static CTCDriver ctcOffice = new CTCDriver();
  public static JFrame trackControllerGui = new TrackControllerGUI(waysideSys);
  public static JFrame trainControllerGui = new TrainControllerGui();
}
