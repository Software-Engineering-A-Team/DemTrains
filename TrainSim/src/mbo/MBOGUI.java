package mbo;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.SpringLayout;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;

import system_wrapper.SystemWrapper;
import track_model.TrackBlock;

import javax.swing.JTabbedPane;

import net.miginfocom.swing.MigLayout;

import java.awt.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.Box;
import javax.swing.JScrollPane;

public class MBOGUI extends JFrame{
	private JTextField startTimeTextField;	
	private JTextField MonRtextField;
	private JTextField MonGtextField;
	private JTextField TueRtextField;
	private JTextField TueGtextField;
	private JTextField WedRtextField;
	private JTextField WedGtextField;
	private JTextField ThurRtextField;
	private JTextField ThurGtextField;
	private JTextField FriRtextField;
	private JTextField FriGtextField;
	private JTextField SatRtextField;
	private JTextField SatGtextField;
	private JTextField SunRtextField;
	private JTextField SunGtextField;
	
	int[] throughputArray;
	String startTime;
	//int[] startTime;
	//String enteredTime;
	//Date startTime;
	private List<JTextField> textFieldList;
	private JTable SafeAuthTableR;
	private JTable SafeAuthTableG;
	private JTable table;
	private JTable table_1;
	
	/*//if button presses mbo.getTrainSchedule
	public void setTrainSchedule(){
		//display to gui
	}
	public void setCrewSchedule(){
		//display to gui
	}*/
	
	public MBOGUI(){
		throughputArray = new int[14];
		//startTime = new Date();
		//startTime = new int[2];
		textFieldList = new ArrayList<JTextField>();
		getContentPane().setLayout(new MigLayout("", "[314px][578px][309px]", "[30px][6px][671px]"));
		
		JPanel createSchedPanel = new JPanel();
		createSchedPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		getContentPane().add(createSchedPanel, "cell 0 2,grow");
		createSchedPanel.setLayout(null);
		
		JLabel lblEnterScheduleStart = new JLabel("Enter Schedule Start Time:");
		lblEnterScheduleStart.setBounds(36, 5, 242, 22);
		lblEnterScheduleStart.setForeground(Color.BLUE);
		lblEnterScheduleStart.setFont(new Font("Tahoma", Font.BOLD, 18));
		createSchedPanel.add(lblEnterScheduleStart);
		
		JLabel lblHhmm = new JLabel("HH:MM");
		lblHhmm.setBounds(34, 35, 54, 20);
		createSchedPanel.add(lblHhmm);
		
		startTimeTextField = new JTextField();
		startTimeTextField.setText("00:00");
		startTimeTextField.setHorizontalAlignment(SwingConstants.CENTER);
		startTimeTextField.setBounds(96, 32, 104, 26);
		createSchedPanel.add(startTimeTextField);
		startTimeTextField.setColumns(7);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(215, 32, 54, 26);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"AM", "PM"}));
		createSchedPanel.add(comboBox);
		
		JLabel label = new JLabel("                                                        ");
		label.setBounds(17, 63, 280, 20);
		createSchedPanel.add(label);
		
		JLabel label_1 = new JLabel("                                                        ");
		label_1.setBounds(17, 88, 280, 20);
		createSchedPanel.add(label_1);
		
		JLabel lblEnterThroughput = new JLabel("Enter Throughputs:");
		lblEnterThroughput.setBounds(68, 113, 177, 22);
		lblEnterThroughput.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblEnterThroughput.setForeground(Color.BLUE);
		createSchedPanel.add(lblEnterThroughput);
		
		JLabel lblMonday = new JLabel("              Red Line");
		lblMonday.setBounds(40, 140, 141, 20);
		lblMonday.setForeground(Color.RED);
		lblMonday.setHorizontalAlignment(SwingConstants.LEFT);
		createSchedPanel.add(lblMonday);
		
		JLabel lblGreenTrack = new JLabel("    Green Line");
		lblGreenTrack.setBounds(185, 140, 106, 20);
		lblGreenTrack.setForeground(new Color(0, 128, 0));
		createSchedPanel.add(lblGreenTrack);
		
		JLabel lblMonday_1 = new JLabel("Monday:  ");
		lblMonday_1.setBounds(17, 168, 71, 20);
		createSchedPanel.add(lblMonday_1);
		
		MonRtextField = new JTextField();		
		MonRtextField.setHorizontalAlignment(SwingConstants.CENTER);
		MonRtextField.setBounds(110, 165, 62, 26);
		createSchedPanel.add(MonRtextField);
		MonRtextField.setColumns(4);
		textFieldList.add(MonRtextField);
		
		JLabel label_2 = new JLabel("      ");
		label_2.setBounds(180, 168, 30, 20);
		createSchedPanel.add(label_2);
		
		MonGtextField = new JTextField();
		MonGtextField.setHorizontalAlignment(SwingConstants.CENTER);
		MonGtextField.setBounds(215, 165, 62, 26);
		createSchedPanel.add(MonGtextField);
		MonGtextField.setColumns(4);
		textFieldList.add(MonGtextField);
		
		JLabel lblTuesday = new JLabel("Tuesday: ");
		lblTuesday.setBounds(17, 199, 70, 20);
		createSchedPanel.add(lblTuesday);
		
		TueRtextField = new JTextField();
		TueRtextField.setHorizontalAlignment(SwingConstants.CENTER);
		TueRtextField.setBounds(110, 196, 62, 26);
		TueRtextField.setColumns(4);
		createSchedPanel.add(TueRtextField);
		textFieldList.add(TueRtextField);
		
		JLabel label_3 = new JLabel("      ");
		label_3.setBounds(179, 199, 30, 20);
		createSchedPanel.add(label_3);
		
		TueGtextField = new JTextField();
		TueGtextField.setHorizontalAlignment(SwingConstants.CENTER);
		TueGtextField.setBounds(215, 196, 62, 26);
		TueGtextField.setColumns(4);
		createSchedPanel.add(TueGtextField);
		textFieldList.add(TueGtextField);
		
		JLabel lblWednesday = new JLabel("Wednesday:");
		lblWednesday.setBounds(17, 230, 86, 20);
		createSchedPanel.add(lblWednesday);
		
		WedRtextField = new JTextField();
		WedRtextField.setHorizontalAlignment(SwingConstants.CENTER);
		WedRtextField.setBounds(110, 227, 62, 26);
		WedRtextField.setColumns(4);
		createSchedPanel.add(WedRtextField);
		textFieldList.add(WedRtextField);
		
		JLabel label_4 = new JLabel("   ");
		label_4.setBounds(195, 230, 15, 20);
		createSchedPanel.add(label_4);
		
		WedGtextField = new JTextField();
		WedGtextField.setHorizontalAlignment(SwingConstants.CENTER);
		WedGtextField.setBounds(215, 227, 62, 26);
		WedGtextField.setColumns(4);
		createSchedPanel.add(WedGtextField);
		textFieldList.add(WedGtextField);
		
		
		JLabel lblThursday = new JLabel("Thursday: ");
		lblThursday.setBounds(17, 261, 77, 20);
		createSchedPanel.add(lblThursday);
		
		ThurRtextField = new JTextField();
		ThurRtextField.setHorizontalAlignment(SwingConstants.CENTER);
		ThurRtextField.setBounds(110, 258, 62, 26);
		ThurRtextField.setColumns(4);
		createSchedPanel.add(ThurRtextField);
		textFieldList.add(ThurRtextField);
		
		JLabel label_5 = new JLabel("      ");
		label_5.setBounds(183, 261, 30, 20);
		createSchedPanel.add(label_5);
		
		ThurGtextField = new JTextField();
		ThurGtextField.setHorizontalAlignment(SwingConstants.CENTER);
		ThurGtextField.setBounds(215, 258, 62, 26);
		ThurGtextField.setColumns(4);
		createSchedPanel.add(ThurGtextField);
		textFieldList.add(ThurGtextField);
		
		JLabel lblFriday = new JLabel("Friday:    ");
		lblFriday.setBounds(17, 292, 69, 20);
		createSchedPanel.add(lblFriday);
		
		FriRtextField = new JTextField();
		FriRtextField.setHorizontalAlignment(SwingConstants.CENTER);
		FriRtextField.setBounds(110, 289, 62, 26);
		FriRtextField.setColumns(4);
		createSchedPanel.add(FriRtextField);
		textFieldList.add(FriRtextField);
		
		JLabel label_6 = new JLabel("      ");
		label_6.setBounds(179, 292, 30, 20);
		createSchedPanel.add(label_6);
		
		FriGtextField = new JTextField();
		FriGtextField.setHorizontalAlignment(SwingConstants.CENTER);
		FriGtextField.setBounds(215, 289, 62, 26);
		FriGtextField.setColumns(4);
		createSchedPanel.add(FriGtextField);
		textFieldList.add(FriGtextField);
		
		JLabel lblSaturday = new JLabel("Saturday:  ");
		lblSaturday.setBounds(17, 323, 78, 20);
		createSchedPanel.add(lblSaturday);
		
		SatRtextField = new JTextField();
		SatRtextField.setHorizontalAlignment(SwingConstants.CENTER);
		SatRtextField.setBounds(110, 320, 62, 26);
		SatRtextField.setColumns(4);
		createSchedPanel.add(SatRtextField);
		textFieldList.add(SatRtextField);
		
		JLabel label_7 = new JLabel("      ");
		label_7.setBounds(183, 323, 30, 20);
		createSchedPanel.add(label_7);
		
		SatGtextField = new JTextField();
		SatGtextField.setHorizontalAlignment(SwingConstants.CENTER);
		SatGtextField.setBounds(215, 320, 62, 26);
		SatGtextField.setColumns(4);
		createSchedPanel.add(SatGtextField);
		textFieldList.add(SatGtextField);
		
		JLabel lblSunday = new JLabel("Sunday:  ");
		lblSunday.setBounds(17, 354, 68, 20);
		createSchedPanel.add(lblSunday);
		
		SunRtextField = new JTextField();
		SunRtextField.setHorizontalAlignment(SwingConstants.CENTER);
		SunRtextField.setBounds(110, 351, 62, 26);
		SunRtextField.setColumns(4);
		createSchedPanel.add(SunRtextField);
		textFieldList.add(SunRtextField);
		
		JLabel label_9 = new JLabel("      ");
		label_9.setBounds(178, 354, 30, 20);
		createSchedPanel.add(label_9);
		
		SunGtextField = new JTextField();
		SunGtextField.setHorizontalAlignment(SwingConstants.CENTER);
		SunGtextField.setBounds(215, 351, 62, 26);
		SunGtextField.setColumns(4);
		createSchedPanel.add(SunGtextField);
		textFieldList.add(SunGtextField);
		
		JButton btnCreateSchedule = new JButton("CREATE SCHEDULE");
		btnCreateSchedule.setBackground(Color.LIGHT_GRAY);
		btnCreateSchedule.addActionListener(new ActionListener() {
			//Create Schedule button being pressed
			public void actionPerformed(ActionEvent arg0) {				
				//check to make sure all throughput text boxes have input			
				for(int i=0; i<14; i++){
					if(textFieldList.get(i).getText().equals("")) {
						throughputArray[i] = 0;
					}
					else {
						throughputArray[i]=(int)Double.parseDouble(textFieldList.get(i).getText());
					}
				}
				//ensure text was entered in the startTimeTextField
			/*	if(startTimeTextField.getText().equals("")) {
					startTime[0] = 0;
					startTime[1] = 0;
				}
				else {
					String[] temp = startTimeTextField.getText().split(":",2);
					startTime[0] = Integer.parseInt(temp[0]);
					startTime[1] = Integer.parseInt(temp[1]);
				}*/	
				
				startTime = startTimeTextField.getText();				
				
				//send data to function to create the trian and crew schedule
				
				try {
					SystemWrapper.mbo.getTrainSchedule(startTime, throughputArray);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				SystemWrapper.mbo.getCrewSchedule(startTime, throughputArray);
			}
		});
		btnCreateSchedule.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnCreateSchedule.setBounds(50, 411, 208, 31);
		createSchedPanel.add(btnCreateSchedule);
		
		JTabbedPane SchedulePane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(SchedulePane, "cell 1 0 1 3,grow");
		
		JPanel trainSchedulePanel = new JPanel();
		trainSchedulePanel.setToolTipText("");
		SchedulePane.addTab("Train Schedule", null, trainSchedulePanel, null);
		trainSchedulePanel.setLayout(new MigLayout("", "[79px][8px][107px][15px][95px][8px][111px][14px][43px][8px][71px]", "[27px][627px]"));
		
		JLabel lblTrackLine = new JLabel("Track Line:");
		trainSchedulePanel.add(lblTrackLine, "cell 0 0,alignx left,aligny center");
		
		JComboBox trackLineComboBox = new JComboBox();
		trackLineComboBox.setModel(new DefaultComboBoxModel(new String[] {"Red Line", "Green Line"}));
		trainSchedulePanel.add(trackLineComboBox, "cell 2 0,alignx left,aligny top");
		
		JLabel lblNewLabel = new JLabel("Day of Week:");
		trainSchedulePanel.add(lblNewLabel, "cell 4 0,alignx left,aligny center");
		
		JComboBox dayOfWeekComboBox = new JComboBox();
		dayOfWeekComboBox.setModel(new DefaultComboBoxModel(new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"}));
		trainSchedulePanel.add(dayOfWeekComboBox, "cell 6 0,alignx left,aligny bottom");
		
		JLabel lblTrain = new JLabel("Train:");
		trainSchedulePanel.add(lblTrain, "cell 8 0,alignx left,aligny center");
		
		JComboBox trainComboBox = new JComboBox();
		trainComboBox.setModel(new DefaultComboBoxModel(new String[] {"r0", "r1", "r2", "g30"}));
		trainSchedulePanel.add(trainComboBox, "cell 10 0,alignx left,aligny bottom");
		
		JScrollPane scrollPane = new JScrollPane();
		trainSchedulePanel.add(scrollPane, "cell 0 1 11 1,grow");
		
		table = new JTable(SystemWrapper.mbo.trainScheduleTable);
		scrollPane.setViewportView(table);
		
		JPanel crewSchedulePanel = new JPanel();
		SchedulePane.addTab("Crew Schedule", null, crewSchedulePanel, null);
		crewSchedulePanel.setLayout(new MigLayout("", "[125px][15px][433px]", "[26px][624px]"));
		
		JLabel lblNewLabel_3 = new JLabel("Day of Week:");
		crewSchedulePanel.add(lblNewLabel_3, "cell 0 0,alignx right,aligny center");
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"}));
		crewSchedulePanel.add(comboBox_1, "cell 2 0,alignx left,aligny top");
		
		JScrollPane scrollPane_1 = new JScrollPane();
		crewSchedulePanel.add(scrollPane_1, "cell 0 1 3 1,grow");
		
		table_1 = new JTable(SystemWrapper.mbo.crewScheduleTable);
		scrollPane_1.setViewportView(table_1);
		
		JTabbedPane SafeMBOAuthoPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(SafeMBOAuthoPane, "cell 2 2,grow");
		
		JPanel RedLinePanel = new JPanel();
		SafeMBOAuthoPane.addTab("Red Line", null, RedLinePanel, null);
		RedLinePanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JScrollPane RedLinescrollPane = new JScrollPane();
		RedLinePanel.add(RedLinescrollPane, "cell 0 0,grow");
		
		SafeAuthTableR = new JTable(SystemWrapper.mbo.trainSafeAuthorityRTable);
		RedLinescrollPane.setViewportView(SafeAuthTableR);
		
		JPanel GreenLinePanel = new JPanel();
		SafeMBOAuthoPane.addTab("Green Line", null, GreenLinePanel, null);
		GreenLinePanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JScrollPane GreenLinescrollPane = new JScrollPane();
		GreenLinePanel.add(GreenLinescrollPane, "cell 0 0,grow");
		
		SafeAuthTableG = new JTable(SystemWrapper.mbo.trainSafeAuthorityGTable);
		GreenLinescrollPane.setViewportView(SafeAuthTableG);
		
		JLabel label_8 = new JLabel("Safe Moving Block Authority:");
		label_8.setFont(new Font("Tahoma", Font.BOLD, 18));
		getContentPane().add(label_8, "cell 2 0,alignx center,aligny bottom");
		
				
	}
}
