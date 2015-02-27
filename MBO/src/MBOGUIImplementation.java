//package ctcOfficeGUI;

import javax.management.timer.Timer;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import java.awt.Canvas;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import java.awt.GridLayout;

import javax.swing.border.LineBorder;
import javax.swing.JSlider;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MBOGUIImplementation extends JFrame {
	String schedule;
	JLabel scheduleLabel = new JLabel();

	/**
	 * Create the panel.
	 */
	public MBOGUIImplementation() {


		//String schedule = "";
		setResizable(false);
		getContentPane().setBackground(Color.WHITE);
		setBackground(Color.WHITE);

		JPanel toolbar = new JPanel();
		toolbar.setBorder(new LineBorder(new Color(0, 0, 0)));
		toolbar.setBackground(Color.WHITE);

		JPanel topSection = new JPanel();
		topSection.setBounds(24, 2, 560, 29);
		topSection.setBackground(Color.WHITE);
		topSection.setLayout(null);

		JLabel enterTimeLabel = new JLabel("Enter Start Time:");
		enterTimeLabel.setBounds(10, 8, 100, 14);
		topSection.add(enterTimeLabel);

		final JTextField enterTimeField = new JTextField(20);
		enterTimeField.setBounds(10, 15, 20, 14);
		enterTimeField.setSize(40,15);
		topSection.add(enterTimeField);
		enterTimeField.move(120, 10);

		JLabel frequencyLabel = new JLabel("Enter Frequency of Departure (Min):");
		frequencyLabel.setBounds(10, 8, 200, 14);
		topSection.add(frequencyLabel);
		frequencyLabel.move(170, 10);

		final JTextField enterFrequencyField = new JTextField(20);
		enterFrequencyField.setBounds(10, 15, 20, 14);
		enterFrequencyField.setSize(40,15);
		topSection.add(enterFrequencyField);
		enterFrequencyField.move(380, 10);

		JButton enterTimeButton = new JButton("Create Schedule");
		enterTimeButton.setBounds(10, 15, 20, 14);
		enterTimeButton.setSize(130,15);
		topSection.add(enterTimeButton);
		enterTimeButton.move(430, 10);


		enterTimeButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent event){    
				String startTime = enterTimeField.getText().toString();
				String frequency = enterFrequencyField.getText().toString();
				try {
					schedule = generateCrewSchedule(startTime,frequency);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//System.out.println(startTime);
				//System.out.println(schedule);
				scheduleLabel.setText(schedule);
			}
		});


		//JSlider slider = new JSlider();
		//slider.setForeground(Color.BLACK);
		//slider.setBounds(101, 9, 50, 10);
		//simulationSpeed.add(slider);
		toolbar.setLayout(null);
		toolbar.add(topSection);

		JScrollPane trackLayoutScrollPane = new JScrollPane();

		JPanel trackLayoutPanel = new JPanel();
		trackLayoutPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		trackLayoutPanel.setBackground(Color.WHITE);
		trackLayoutScrollPane.setViewportView(trackLayoutPanel);

		//JLabel scheduleLabel = new JLabel(schedule);
		//scheduleLabel.setText("<html><body>with<br>linebreak</body></html>");
		GroupLayout gl_trackLayoutPanel = new GroupLayout(trackLayoutPanel);
		gl_trackLayoutPanel.setHorizontalGroup(
				gl_trackLayoutPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_trackLayoutPanel.createSequentialGroup()
						.addContainerGap()
						.addComponent(scheduleLabel)
						.addContainerGap(582, Short.MAX_VALUE))
				);
		gl_trackLayoutPanel.setVerticalGroup(
				gl_trackLayoutPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_trackLayoutPanel.createSequentialGroup()
						.addGap(32)
						.addComponent(scheduleLabel)
						.addContainerGap(447, Short.MAX_VALUE))
				);
		trackLayoutPanel.setLayout(gl_trackLayoutPanel);

		JScrollPane trackInfoScrollPane = new JScrollPane();
		trackInfoScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

	    JLabel value1 = new JLabel("1 mile");
		JLabel value2 = new JLabel("2 miles");
		
		JPanel trackInfoPanel = new MBODistancePanel(value1,value2);
		//JPanel trackInfoPanel = new JPanel();
		
		trackInfoPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		trackInfoPanel.setBackground(Color.WHITE);
		trackInfoScrollPane.setViewportView(trackInfoPanel);
		//trackInfoPanel.setLayout(new GridLayout(1, 0, 0, 0));
		trackInfoPanel.setLayout(null);
		trackInfoPanel.setBounds(2000, 1000,2000, 1000);

		JLabel safeDistanceLabel = new JLabel("Safe Stopping Distance:");
		safeDistanceLabel.setBounds(100, 20, 1000, 20);        
		trackInfoPanel.add(safeDistanceLabel);        
		safeDistanceLabel.setVerticalAlignment(1);
		safeDistanceLabel.move(5, 0);

		JLabel safeDistanceTrainLabel = new JLabel("Train 1: ");
		safeDistanceTrainLabel.setBounds(100, 20, 45, 20);
		trackInfoPanel.add(safeDistanceTrainLabel);
		safeDistanceTrainLabel.move(5,20);

		//JLabel value1 = new JLabel("1 mile");
		value1.setBounds(10,10,50,10);
		trackInfoPanel.add(value1);
		value1.move(50, 25);

		JLabel safeDistanceTrainLabel2 = new JLabel("Train 2: ");
		safeDistanceTrainLabel2.setBounds(100, 20, 45, 20);
		trackInfoPanel.add(safeDistanceTrainLabel2);
		safeDistanceTrainLabel2.move(5,40);

		//JLabel value2 = new JLabel("2 miles");
		value2.setBounds(10,10,50,10);
		trackInfoPanel.add(value2);
		value2.move(50, 45);

		//MBODistancePanel(value1,value2);
		
		JPanel CTCOfficeLabel = new JPanel();
		CTCOfficeLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
		CTCOfficeLabel.setBackground(Color.WHITE);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(toolbar, GroupLayout.PREFERRED_SIZE, 654, GroupLayout.PREFERRED_SIZE)
				.addComponent(trackLayoutScrollPane, GroupLayout.PREFERRED_SIZE, 654, GroupLayout.PREFERRED_SIZE)
				.addGroup(groupLayout.createSequentialGroup()
						.addGap(653)
						.addComponent(trackInfoScrollPane, GroupLayout.PREFERRED_SIZE, 197, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
								.addGap(653)
								.addComponent(CTCOfficeLabel, GroupLayout.PREFERRED_SIZE, 197, GroupLayout.PREFERRED_SIZE))
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(toolbar, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
				.addGroup(groupLayout.createSequentialGroup()
						.addGap(33)
						.addComponent(trackLayoutScrollPane, GroupLayout.PREFERRED_SIZE, 499, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
								.addGap(33)
								.addComponent(trackInfoScrollPane, GroupLayout.PREFERRED_SIZE, 499, GroupLayout.PREFERRED_SIZE))
								.addComponent(CTCOfficeLabel, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
				);

		JLabel lblCtcOffice = new JLabel("MBO");
		lblCtcOffice.setFont(new Font("Tahoma", Font.BOLD, 14));
		CTCOfficeLabel.add(lblCtcOffice);
		getContentPane().setLayout(groupLayout);


	}

	private  String generateCrewSchedule(String startTime,String sFrequency) throws ParseException{
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
		String[] weekDays = {"Monday:", "<br><br>Tuesday:", "<br><br>Wednesday:", "<br><br>Thursday:", 
				"<br><br>Friday:", "<br><br>Saturday:", "<br><br>Sunday:"};
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


}

