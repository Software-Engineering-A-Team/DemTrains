package mbo;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.table.DefaultTableModel;


public class Scheduler {
		
	public Scheduler(){
		
	
	}
	
	//create RedLine Track first
	//each train can produce a throughput of 30 per hour
	public List<String> createRTrainSchedule(String startTime, int[] throughputArray) throws ParseException {
		List<String> schedule = new ArrayList<String>();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
		Calendar departureTime = Calendar.getInstance();
		Calendar tempTime = Calendar.getInstance();
		Date time = dateFormat.parse(startTime);
		departureTime.setTime(time);
		tempTime.setTime(time);
		int count=0;
		int throughput;
		int totalTrains;
		String[] crewSched = new String[6];
		String [] weekDays = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
		
		//clear display table for both tracks
		if (MovingBlockOverlay.crewScheduleTable.getRowCount() > 0) {
		    for (int i = MovingBlockOverlay.crewScheduleTable.getRowCount() - 1; i > -1; i--) {
		    	MovingBlockOverlay.crewScheduleTable.removeRow(i);
		    }
		}		
				
		for(int rDay=0; rDay<throughputArray.length; rDay+=2) {
			String trainRoute = generateRedTrainRoute();
			String trainSched="";
			throughput = throughputArray[rDay];
			if(throughput == 0){
				totalTrains=0;
			}
			else if(throughput%30 == 0) {
				totalTrains = throughput/30 + 1;
			}
			else {
				totalTrains = (throughput/30) + 2;
			}
			for(int t=0; t<totalTrains; t++) {
				trainSched = "r" + t + "," + weekDays[rDay/2] + "," + (dateFormat.format(departureTime.getTime())) + "\n";
				trainSched += trainRoute;
				schedule.add(trainSched);
				
				//generate Crew Schedule				 
				crewSched[0]=Integer.toString(count);
				crewSched[1]="r"+t;
				crewSched[2]=weekDays[rDay/2];
				crewSched[3]=dateFormat.format(tempTime.getTime());
				tempTime.add(Calendar.MINUTE, 240);
				crewSched[4]=dateFormat.format(tempTime.getTime());
				tempTime.add(Calendar.MINUTE, 270);
				crewSched[5]=dateFormat.format(tempTime.getTime());
				MovingBlockOverlay.crewScheduleTable.addRow(crewSched);
				
				tempTime.add(Calendar.MINUTE,(930/totalTrains)-510);
				departureTime.add(Calendar.MINUTE, 930/totalTrains);				
				System.out.println(trainSched);
				trainSched="";
				count++;
			}
			departureTime.setTime(time);
			tempTime.setTime(time);		
		}
		//displayTrainSchedule(schedule);
		return schedule;
	}
	
	//create GreenLine Track second
	//each train can produce a throughput of 21 per hour
	public List<String> createGTrainSchedule(String startTime, int[] throughputArray) throws ParseException {
		List<String> schedule = new ArrayList<String>();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
		Calendar departureTime = Calendar.getInstance();
		Calendar tempTime = Calendar.getInstance();
		Date time = dateFormat.parse(startTime);
		departureTime.setTime(time);
		tempTime.setTime(time);
		int count=0;		
		String [] weekDays = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
		int throughput;
		int totalTrains;
		String[] crewSched = new String[6];				
		
		for(int gDay=1; gDay<throughputArray.length; gDay+=2) {
			String trainRoute = generateGreenTrainRoute();
			String trainSched="";
			throughput = throughputArray[gDay];
			if(throughput == 0){
				totalTrains=0;
			}
			else if(throughput%21 == 0) {
				totalTrains = (throughput/21)+1;
			}
			else {
				totalTrains = (throughput/21) + 2;				
			}
			for(int t=0; t<totalTrains; t++) {
				trainSched = "g" + t + "," + weekDays[gDay/2] + "," + (dateFormat.format(departureTime.getTime())) + "\n";
				trainSched += trainRoute;
				schedule.add(trainSched);
				
				//generate Crew Schedule				 
				crewSched[0]=Integer.toString(count);
				crewSched[1]="g"+t;
				crewSched[2]=weekDays[gDay/2];
				crewSched[3]=dateFormat.format(tempTime.getTime());
				tempTime.add(Calendar.MINUTE, 240);
				crewSched[4]=dateFormat.format(tempTime.getTime());
				tempTime.add(Calendar.MINUTE, 270);
				crewSched[5]=dateFormat.format(tempTime.getTime());
				MovingBlockOverlay.crewScheduleTable.addRow(crewSched);
				
				tempTime.add(Calendar.MINUTE,(930/totalTrains)-510);				
				departureTime.add(Calendar.MINUTE, 930/totalTrains);				
				System.out.println(trainSched);
				trainSched="";
				count++;
			}
			departureTime.setTime(time);
			tempTime.setTime(time);
		}
		//displayTrainSchedule(schedule);		
		
		return schedule;
	}
	
	private String generateRedTrainRoute() {
		String[] stationsTrackR = {"SHADYSIDE","HERRON AVE","SWISSVILLE","PENN STATION"
				,"STEEL PLAZA","FIRST AVE","STATION SQUARE","SOUTH HILLS JUNCTION"
				,"STATION SQUARE","FIRST AVE","STEEL PLAZA","PENN STATION","SWISSVILLE"
				,"HERRON AVE","SHADYSIDE"};
		double[] timeToStationTrackR = {36, 31, 23, 26, 29, 29, 25, 31, 31, 25, 29, 29, 26, 23, 31};//if 4 hours use [0] if not use [1] 
		String trainSched="";
		double totalTime=0;
		totalTime = totalTime + timeToStationTrackR[0];
		trainSched += stationsTrackR[0] + "," + totalTime/10 + "\n";
		
		//add while loop for first 4 hours
		while ((totalTime + 328) < 2400){ //253
			for(int station=1; station<stationsTrackR.length; station++){
				totalTime = totalTime + timeToStationTrackR[station];
				trainSched += stationsTrackR[station] + "," + totalTime/10 + "\n";
			}
		}
		//add yard to schedule
		totalTime = totalTime + timeToStationTrackR[0];
		trainSched += "YARD," + totalTime/10 + "\n";
		//add time for 30 min break at yard
		totalTime += 300;
		totalTime = totalTime + timeToStationTrackR[0];
		trainSched += stationsTrackR[0] + "," + totalTime/10 + "\n";
		//add while loop for first 4 hours
		while ((totalTime + 328) < 5100){
			for(int station=1; station<stationsTrackR.length; station++){
				totalTime = totalTime + timeToStationTrackR[station];
				trainSched += stationsTrackR[station] + "," + totalTime/10 + "\n";
			//System.out.println(trainSched);
			}
		}
		//add yard to schedule
		totalTime = totalTime + timeToStationTrackR[0];
		trainSched += "YARD," + totalTime/10 + "\n";
		
		//do next four hours then add to list
		//now onto next train
		//schedule.add(trainSched);
		//System.out.println(trainSched);
		
		return trainSched;
	}
	
	private String generateGreenTrainRoute(){
		String[] stationsTrackG = {"GLENBURY","DORMONT","MT LEBANON","POPLAR","CASTLE SHANNON"
				,"MT LEBANON","DORMONT","GLENBURY","OVERBROOK","INGLEWOOD","CENTRAL","WHITED"
				,"JOEPRO","EDGEBROOK","PIONEER","JOEPRO","WHITED","SOUTH BANK","CENTRAL"
				,"INGLEWOOD","OVERBROOK"};
		//double[] timeToStationTrackG = {40, 26, 23, 44, 23, 47, 26, 25, 22, 21, 21, 33, 28, 25, 24, 31, 28, 27, 20, 21, 21}; 
		double[] timeToStationTrackG = {40, 26, 24, 45, 24, 48, 27, 26, 23, 22, 22, 33, 28, 26, 25, 31, 28, 27, 21, 22, 22}; 
		String trainSched="";
		double totalTime=0;
		//totalTime = totalTime + timeToStationTrackG[0];
		//trainSched += stationsTrackG[0] + "," + totalTime/10 + "\n";
		
		//add while loop for first 4 hours
		while ((totalTime + 558) < 2400){ //253
			for(int station=0; station<stationsTrackG.length; station++){
				totalTime = totalTime + timeToStationTrackG[station];
				trainSched += stationsTrackG[station] + "," + totalTime/10 + "\n";
			//System.out.println(trainSched);
			}
		}
		//add yard to schedule
		totalTime = totalTime + timeToStationTrackG[0];
		trainSched += "YARD," + totalTime/10 + "\n";
		//add time for 30 min break at yard
		totalTime += 300;
		//add while loop for first 4 hours
		while ((totalTime + 558) < 5100){
			for(int station=0; station<stationsTrackG.length; station++){
				totalTime = totalTime + timeToStationTrackG[station];
				trainSched += stationsTrackG[station] + "," + totalTime/10 + "\n";
			//System.out.println(trainSched);
			}
		}
		//add yard to schedule
		totalTime = totalTime + timeToStationTrackG[0];
		trainSched += "YARD," + totalTime/10 + "\n";	
		
		
		return trainSched;
	}
	
	public List<String> createCrewSchedule(){
		
		List<String> schedule = new ArrayList<String>();
		
		return schedule;		
	}
	
	/*private void displayTrainSchedule(List<String> schedule) throws ParseException {
		String[] line = null;
		String trainID, weekDay;
		String[] input = new String[4];
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
		Calendar departureTime = Calendar.getInstance();
		
		
		for(int i=0; i<schedule.size(); i++) {
			line = schedule.get(i).split("[,\\n]+");
			trainID = line[0];
			weekDay = line[1];			
			Date time = dateFormat.parse(line[2]);
			departureTime.setTime(time);
			int temp;
			for(int j=3; j<line.length; j+=2) {
				temp = (int)Double.parseDouble(line[j+1]);
				departureTime.add(Calendar.MINUTE,temp );
				input[0] = trainID;
				input[1] = weekDay;
				input[2] = line[j];
				input[3] = (dateFormat.format(departureTime.getTime()));
				//System.out.println(MovingBlockOverlay.trainScheduleTable.getRowCount());
				//MovingBlockOverlay.trainScheduleTable.addRow(input);
				departureTime.setTime(time);
			}
		}
	}*/
}