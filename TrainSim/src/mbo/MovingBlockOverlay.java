package mbo;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;





import javax.swing.table.DefaultTableModel;



//import ctc_office.CTCDriver;
import system_wrapper.*;
//import train_model.TrainModel;

public class MovingBlockOverlay {

	HashMap<String,Double> trainLocationTrackRMap;
	HashMap<String,Double> trainLocationTrackGMap;
	DefaultTableModel trainScheduleTable;
	static DefaultTableModel crewScheduleTable;
	DefaultTableModel trainSafeAuthorityRTable;
	DefaultTableModel trainSafeAuthorityGTable;
	SRSTrainCurrSpeed trainCurrSpeed;
	SRSTrainStopDist trainStopDist;
	SRSDistFromNextTrain trainAuth;
	SRSTrainCommSpeed trainCommSpeed;
	track_model.TrackModel trackModel;
	Scheduler scheduler;
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
		trainScheduleTable = new DefaultTableModel();
			trainScheduleTable.addColumn("Train ID");
			trainScheduleTable.addColumn("Weekday");
			trainScheduleTable.addColumn("Station");
			trainScheduleTable.addColumn("Departure Time");
		crewScheduleTable = new DefaultTableModel();
			crewScheduleTable.addColumn("Crew Member");
			crewScheduleTable.addColumn("Train ID");
			crewScheduleTable.addColumn("Weekday");
			crewScheduleTable.addColumn("Start Time");
			crewScheduleTable.addColumn("Break Time");
			crewScheduleTable.addColumn("End Time");
		trainSafeAuthorityRTable = new DefaultTableModel();
			trainSafeAuthorityRTable.addColumn("Train ID");
			trainSafeAuthorityRTable.addColumn("Safe Authority");
			trainSafeAuthorityRTable.addColumn("Passengers");
		trainSafeAuthorityGTable = new DefaultTableModel();
			trainSafeAuthorityGTable.addColumn("Train ID");
			trainSafeAuthorityGTable.addColumn("Safe Authority");
			trainSafeAuthorityGTable.addColumn("Passengers");
		String[] stuff = {"r0","200"};
		String[] stuff2 = {"r1","300"};
		String[] stuff3 = {"r0","400"};
		trainSafeAuthorityRTable.insertRow(0,stuff);
		trainSafeAuthorityRTable.insertRow(1,stuff2);
		trainSafeAuthorityRTable.removeRow(0);
		trainSafeAuthorityRTable.insertRow(0, stuff3);  //it will overwrite this location
		
		trainCurrSpeed = new SRSTrainCurrSpeed();
		trainStopDist = new SRSTrainStopDist();	
		trainAuth = new SRSDistFromNextTrain();
		trackModel = new track_model.TrackModel();
		trainCommSpeed = new SRSTrainCommSpeed();
		scheduler = new Scheduler();
		//sysWrapper = new SystemWrapper();
		//gui = new MBOGUI();
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
	
	public void getTrainSchedule(String startTime, int[] throughputArray) throws ParseException {
		//need track model
		//initialize the trackMaps with the starting locations all being zero
		scheduler.createRTrainSchedule(startTime, throughputArray);
		scheduler.createGTrainSchedule(startTime, throughputArray);
		//SystemWrapper.ctcOffice.setMBOSchedule("red",scheduler.createRTrainSchedule(startTime, throughputArray));
		//SystemWrapper.ctcOffice.setMBOSchedule("green",scheduler.createGTrainSchedule(startTime, throughputArray));
		
	}
	
	public void getCrewSchedule(String startTime, int[] throughputArray){
		//need train schedule
		scheduler.createCrewSchedule();
	}
	
	public void getSafeMovingBlock(short trainID, double currLocation, double weight){
		//location is in yards
		double prevLocation = 0;
		//might want to change this to a function later
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
		Double nextTrainDist;
		Double commSpeed;
		double nextTrainLocation;
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
		//***This is temporary...need to get the train thats actually in front of curr train
		if(SystemWrapper.trainModels.get(trainID+1)==null) {
			nextTrainLocation = stopDist*2;
		}
		else {
			nextTrainLocation = SystemWrapper.trainModels.get(trainID+1).position;
		}
		nextTrainDist = trainAuth.calcSafeAuth(currLocation, nextTrainLocation);
		//if the distance to the next train cannot be found we must stop all trains
		if(nextTrainDist == null){
			for(int i=0; i<SystemWrapper.trainModels.size(); i++){
				if(SystemWrapper.trainModels.get(i) != null) {
					SystemWrapper.trainModels.get(i).setCommSpeed(0);
				}
			}
		}
		
		if(nextTrainDist > stopDist){
			//keep current speed
			SystemWrapper.trainModels.get(trainID).setCommSpeed(speed);
			SystemWrapper.trainModels.get(trainID).setCommAuth(stopDist);
			if(SystemWrapper.trainModels.get(trainID).trainName.charAt(0) == 'r'){
				String[] info = { SystemWrapper.trainModels.get(trainID).trainName,stopDist.toString()};
				trainSafeAuthorityRTable.addRow(info);
			}
			else{  //trainID.charAt(0) == 'g'
				String[] info = { SystemWrapper.trainModels.get(trainID).trainName,stopDist.toString()};
				trainSafeAuthorityGTable.addRow(info);
			}
		}
		else {
			//stopping distance is less than next train so we need to change its speed
			commSpeed = trainCommSpeed.calcCommSpeed(nextTrainDist);
			//if commanded speed cannot be found we must stop all the trains
			if(commSpeed == null){
				for(int i=0; i<SystemWrapper.trainModels.size(); i++){
					if(SystemWrapper.trainModels.get(i) != null) {
						SystemWrapper.trainModels.get(i).setCommSpeed(0);
					}
				}
			}
			SystemWrapper.trainModels.get(trainID).setCommSpeed(commSpeed);
			SystemWrapper.trainModels.get(trainID).setCommAuth(nextTrainDist);
			if(SystemWrapper.trainModels.get(trainID).trainName.charAt(0) == 'r'){
				String[] info = { SystemWrapper.trainModels.get(trainID).trainName,nextTrainDist.toString()};
				trainSafeAuthorityRTable.addRow(info);
			}
			else{  //trainID.charAt(0) == 'g'
				String[] info = { SystemWrapper.trainModels.get(trainID).trainName,nextTrainDist.toString()};
				trainSafeAuthorityGTable.addRow(info);
			}
		}
		
		// need to calculate distance between train and next train
		// calculate speed of train based on prev position and curr position and time chance
		//send speed and weight to each calculator of the SRS and then compare them
		//as well as the commanded speed				
		
	}	
			
}
