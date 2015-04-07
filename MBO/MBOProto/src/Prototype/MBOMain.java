package Prototype;
//Nathan Spangler


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;



public class MBOMain {

	public static void main(String[] args) throws ParseException{

		int safeStoppingDistance;
		int position = 1;
		int velocity = 1;
		int mass = 1;
		//int[] schedule;

		safeStoppingDistance = calculateSafeStoppingDistance(position, velocity, mass);
		//schedule = generateCrewSchedule(8);
		generateCrewSchedule("8:00", "30");
		
	}
	
	private static void generateCrewSchedule(String startTime,String sFrequency) throws ParseException{
		//int[] schedule = {0,0,0};
		int frequency;
		frequency = Integer.parseInt(sFrequency);
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
		Calendar departureTime = Calendar.getInstance();
		Calendar breakTime = Calendar.getInstance();
		Calendar shiftEnd = Calendar.getInstance();
		Date time = dateFormat.parse(startTime);
		departureTime.setTime(time);
		breakTime.setTime(time);
		breakTime.add(Calendar.HOUR,4);
		shiftEnd.setTime(time);
		shiftEnd.add(Calendar.HOUR, 8);
		shiftEnd.add(Calendar.MINUTE, 30);
		String[] weekDays = {"Monday:", "Tuesday:", "Wednesday:", "Thursday:", "Friday:", "Saturday:", "Sunday:"};
		
		
		//mon-fri trains depart in 30 min intervals
		//sat sun trains depart in 15 min intervals
		
		for(int i=0; i<7; i++){
			System.out.println(weekDays[i]);
			
			for(int j=0; j<((60/frequency)*24); j++){
				
				
				System.out.println("DepartureTime: "+(dateFormat.format(departureTime.getTime()))+
						"\tBreakTime: "+(dateFormat.format(breakTime.getTime()))+
						"\tShiftEnd: "+(dateFormat.format(shiftEnd.getTime())));
				
				departureTime.add(Calendar.MINUTE, frequency);
				breakTime.add(Calendar.MINUTE, frequency);
				shiftEnd.add(Calendar.MINUTE, frequency);
				
			}
		}
		
		
		
	}


	private static int calculateSafeStoppingDistance(int Position, int Velocity,int Mass){
		int safeStoppingDistance = 0;

		return safeStoppingDistance;
	}
	
	/*//converts the entered time string HH:MM to a usable int for hour 
	//military time
	private static int toHours(String s) {
	    String[] hourMin = s.split(":");
	    int hour = Integer.parseInt(hourMin[0]);	    
	    return hour;
	}
	//converts the entered time string HH:MM to a usable int for minutes
	//military time
	private static int toMins(String s) {
	    String[] hourMin = s.split(":");
	    int mins = Integer.parseInt(hourMin[1]);	    
	    return mins;
	}*/
	
}