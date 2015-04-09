package mbo;

import java.util.HashMap;
import java.util.Map;

//import ctc_office.CTCDriver;
import system_wrapper.*;
//import train_model.TrainModel;

public class MovingBlockOverlay {

	HashMap<String,Double> trainLocationTrackRMap;
	HashMap<String,Double> trainLocationTrackGMap;
	SRSTrainCurrSpeed trainCurrSpeed;
	SRSTrainStopDist trainStopDist;
	//SystemWrapper sysWrapper;
	SimClock systemClock;
	//CTCDriver ctcDriver;   //this still need to be here?
	//TrainModel trainModel; //this still need to be here?
	
	MBOGUI gui;
	// need to have the track Layout Map, and then keep track of every train on the map
	//then use this map to see if there is a train within another trains stopping distance
	
	public MovingBlockOverlay(){
		trainLocationTrackRMap = new HashMap<String,Double>();
		trainLocationTrackGMap = new HashMap<String,Double>();
		trainCurrSpeed = new SRSTrainCurrSpeed();
		trainStopDist = new SRSTrainStopDist();	
		//sysWrapper = new SystemWrapper();
		gui = new MBOGUI();
	}
	/*public void setCTC(CTCDriver ctc){
		ctcDriver = ctc;
	}
	public void setTrainModel(TrainModel train){
		trainModel = train;
	}*/
	public void run(){	
		SystemWrapper.ctcOffice.setActualTrainLocations("trackA", trainLocationTrackRMap);
		SystemWrapper.ctcOffice.setActualTrainLocations("trackB", trainLocationTrackGMap);
	}
	
	public void getTrainSchedule(){
		//need track model
		//initialize the trackMaps with the starting locations all being zero
		//setMBOTrainSchedule() for CTC
	}
	
	public void getCrewSchedule(){
		//need train schedule
		gui.setCrewSchedule();  //needs to be added to the Train Model
	}
	
	public void getSafeMovingBlock(short trainID, double currLocation, double weight){		
		double prevLocation = 0;
		if(SystemWrapper.trainModels.get(trainID).trainName.charAt(0) == 'r'){
			prevLocation = trainLocationTrackRMap.get(trainID);
			trainLocationTrackRMap.put(SystemWrapper.trainModels.get(trainID).trainName, currLocation);
		}
		else{  //trainID.charAt(0) == 'g'
			prevLocation = trainLocationTrackGMap.get(trainID);
			trainLocationTrackGMap.put(SystemWrapper.trainModels.get(trainID).trainName, currLocation);
		}
		
		Double speed;
		Double stopDist;
		speed = trainCurrSpeed.calcTrainSpeed(prevLocation, currLocation, SystemWrapper.simClock.getDeltaMs());
		//if the speed is null we stop all trains
		if(speed == null){
			for(int i=0; i<SystemWrapper.trainModels.size(); i++){
				if(SystemWrapper.trainModels.get(i) != null) {
					SystemWrapper.trainModels.get(i).setCommSpeed(0);
				}
			}
		}
		double grade = 0.0; //going to need to figure out what block its on and get that blocks grade
		stopDist = trainStopDist.calcStopDist(speed, weight, grade);
		//if the stopDistance is null we stop all trains
		if(stopDist == null){
			for(int i=0; i<SystemWrapper.trainModels.size(); i++){
				if(SystemWrapper.trainModels.get(i) != null) {
					SystemWrapper.trainModels.get(i).setCommSpeed(0);
				}
			}
		}
		
		// need to calculate distance between train and next train
		// calculate speed of train based on prev position and curr position and time chance
		//send speed and weight to each calculator of the SRS and then compare them
		//as well as the commanded speed				
		
	}	
			
}
