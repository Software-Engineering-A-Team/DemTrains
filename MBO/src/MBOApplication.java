//package ctcOfficeGUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JSplitPane;
import javax.swing.JScrollPane;

public class MBOApplication {

	private JFrame frame;
	//String schedule;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					int safeStoppingDistance;
					int position = 1;
					int velocity = 1;
					int mass = 1;
					//int[] schedule;
					
					
					
					safeStoppingDistance = calculateSafeStoppingDistance(position, velocity, mass);
					//schedule = generateCrewSchedule(8);
					String schedule=generateCrewSchedule("8:00", "30");
					//System.out.println(schedule);
					MBOApplication window = new MBOApplication();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MBOApplication() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new MBOGUIImplementation();
		frame.setResizable(false);
		frame.setBounds(100, 100, 836, 530);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		
		
		
	}

	private static String generateCrewSchedule(String startTime,String sFrequency) throws ParseException{
		//int[] schedule = {0,0,0};
		int frequency;
		int count;
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
		String[] weekDays = {"Monday:<br>", "<br><br>Tuesday:<br>", "<br><br>Wednesday:<br>", "<br><br>Thursday:<br>", 
				"<br><br>Friday:<br>", "<br><br>Saturday:<br>", "<br><br>Sunday:<br>"};
		String schedule = "<html><body>";
		
		//mon-fri trains depart in 30 min intervals
		//sat sun trains depart in 15 min intervals
		
		for(int i=0; i<7; i++){
			schedule += weekDays[i];
			//System.out.println(weekDays[i]);
			count = 1;
			for(int j=0; j<((60/frequency)*24); j++){
				
				schedule += " <br>Train "+String.format("%02d", count);
				schedule += "<&emsp DepartureTime: "+(dateFormat.format(departureTime.getTime()))+
								"<&emsp BreakTime: "+(dateFormat.format(breakTime.getTime()))+
								"<&emsp ShiftEnd: "+(dateFormat.format(shiftEnd.getTime()));
				
				//System.out.println("DepartureTime: "+(dateFormat.format(departureTime.getTime()))+
				//		"\tBreakTime: "+(dateFormat.format(breakTime.getTime()))+
				//		"\tShiftEnd: "+(dateFormat.format(shiftEnd.getTime())));
				
				
				departureTime.add(Calendar.MINUTE, frequency);
				breakTime.add(Calendar.MINUTE, frequency);
				shiftEnd.add(Calendar.MINUTE, frequency);
				count++;
			}
		}
		
		schedule += "<body><html>";
		return schedule;
	}


	private static int calculateSafeStoppingDistance(int Position, int Velocity,int Mass){
		int safeStoppingDistance = 0;

		return safeStoppingDistance;
	}
	
}
