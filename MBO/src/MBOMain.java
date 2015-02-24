//Nathan Spangler

import java.util.*;
import java.io.*;


public class MBOMain {

	public static void main(String[] args){

		int safeStoppingDistance;
		int position = 1;
		int velocity = 1;
		int mass = 1;
		//int[] schedule;

		safeStoppingDistance = calculateSafeStoppingDistance(position, velocity, mass);
		//schedule = generateCrewSchedule(8);
		generateCrewSchedule(800);
	}
	
	private static void generateCrewSchedule(int startTime){
		int[] schedule = {0,0,0};
		int time;
		startTime = startTime;
		MBOGUIImplementation gui = new MBOGUIImplementation();
		
		//mon-fri trains depart in 30 min intervals
		//sat sun trains depart in 15 min intervals
		
		for(int i=1; i<8; i++){
			time=startTime;
			for(int j=0; j<2400-time; j=j+30){
				System.out.println("DepartureTime: "+(String.format("%04d", ((j+time)%2400)))+"\tBreakTime: "+(String.format("%04d", ((j+400+time)%2400)))+"\tShiftEnd: "+(String.format("%04d", ((j+830+time)%2400))));
			}
		}
		
		
		//return schedule;
	}


	private static int calculateSafeStoppingDistance(int Position, int Velocity,int Mass){
		int safeStoppingDistance = 0;

		return safeStoppingDistance;
	}

}