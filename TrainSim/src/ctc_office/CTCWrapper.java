package ctc_office;

import java.util.ArrayList;

import javax.swing.JFrame;

import system_wrapper.SimClock;

public class CTCWrapper {
	  public static double perceivedTimeMultiplier = 1;
	  public static SimClock simClock = new SimClock(10);
	  public static CTCDriver ctcOffice = new CTCDriver();
	  public static ArrayList<Train> trainModels = new ArrayList<Train>();
	  public static CTCTrackControllerSim trackController = new CTCTrackControllerSim();
	  public static JFrame ctcGUI = new CTCGUI();
}
