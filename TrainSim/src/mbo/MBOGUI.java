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
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;

import system_wrapper.SystemWrapper;

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
	private JTable table;
	
	int[] throughputArray;
	int startTime;
	
	/*//if button presses mbo.getTrainSchedule
	public void setTrainSchedule(){
		//display to gui
	}
	public void setCrewSchedule(){
		//display to gui
	}*/
	
	public MBOGUI(){
		throughputArray = new int[14];
		
		
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		JPanel createSchedPanel = new JPanel();
		springLayout.putConstraint(SpringLayout.WEST, createSchedPanel, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, createSchedPanel, 484, SpringLayout.NORTH, getContentPane());
		createSchedPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		springLayout.putConstraint(SpringLayout.NORTH, createSchedPanel, 10, SpringLayout.NORTH, getContentPane());
		getContentPane().add(createSchedPanel);
		
		JPanel displaySchedPanel = new JPanel();
		springLayout.putConstraint(SpringLayout.WEST, displaySchedPanel, 330, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, displaySchedPanel, -323, SpringLayout.EAST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, createSchedPanel, -6, SpringLayout.WEST, displaySchedPanel);
		springLayout.putConstraint(SpringLayout.NORTH, displaySchedPanel, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, displaySchedPanel, -10, SpringLayout.SOUTH, getContentPane());
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
		
		JLabel lblMonday = new JLabel("              Red Track");
		lblMonday.setBounds(31, 140, 141, 20);
		lblMonday.setForeground(Color.RED);
		lblMonday.setHorizontalAlignment(SwingConstants.LEFT);
		createSchedPanel.add(lblMonday);
		
		JLabel lblGreenTrack = new JLabel("    Green Track");
		lblGreenTrack.setBounds(177, 140, 106, 20);
		lblGreenTrack.setForeground(new Color(0, 128, 0));
		createSchedPanel.add(lblGreenTrack);
		
		JLabel lblMonday_1 = new JLabel("Monday:  ");
		lblMonday_1.setBounds(17, 168, 71, 20);
		createSchedPanel.add(lblMonday_1);
		
		MonRtextField = new JTextField();
		MonRtextField.setBounds(110, 165, 62, 26);
		createSchedPanel.add(MonRtextField);
		MonRtextField.setColumns(4);
		
		JLabel label_2 = new JLabel("      ");
		label_2.setBounds(180, 168, 30, 20);
		createSchedPanel.add(label_2);
		
		MonGtextField = new JTextField();
		MonGtextField.setBounds(215, 165, 62, 26);
		createSchedPanel.add(MonGtextField);
		MonGtextField.setColumns(4);
		
		JLabel lblTuesday = new JLabel("Tuesday: ");
		lblTuesday.setBounds(17, 199, 70, 20);
		createSchedPanel.add(lblTuesday);
		
		TueRtextField = new JTextField();
		TueRtextField.setBounds(110, 196, 62, 26);
		TueRtextField.setColumns(4);
		createSchedPanel.add(TueRtextField);
		
		JLabel label_3 = new JLabel("      ");
		label_3.setBounds(179, 199, 30, 20);
		createSchedPanel.add(label_3);
		
		TueGtextField = new JTextField();
		TueGtextField.setBounds(215, 196, 62, 26);
		TueGtextField.setColumns(4);
		createSchedPanel.add(TueGtextField);
		
		JLabel lblWednesday = new JLabel("Wednesday:");
		lblWednesday.setBounds(17, 230, 86, 20);
		createSchedPanel.add(lblWednesday);
		
		WedRtextField = new JTextField();
		WedRtextField.setBounds(110, 227, 62, 26);
		WedRtextField.setColumns(4);
		createSchedPanel.add(WedRtextField);
		
		JLabel label_4 = new JLabel("   ");
		label_4.setBounds(195, 230, 15, 20);
		createSchedPanel.add(label_4);
		
		WedGtextField = new JTextField();
		WedGtextField.setBounds(215, 227, 62, 26);
		WedGtextField.setColumns(4);
		createSchedPanel.add(WedGtextField);
		
		JLabel lblThursday = new JLabel("Thursday: ");
		lblThursday.setBounds(17, 261, 77, 20);
		createSchedPanel.add(lblThursday);
		
		ThurRtextField = new JTextField();
		ThurRtextField.setBounds(110, 258, 62, 26);
		ThurRtextField.setColumns(4);
		createSchedPanel.add(ThurRtextField);
		
		JLabel label_5 = new JLabel("      ");
		label_5.setBounds(183, 261, 30, 20);
		createSchedPanel.add(label_5);
		
		ThurGtextField = new JTextField();
		ThurGtextField.setBounds(215, 258, 62, 26);
		ThurGtextField.setColumns(4);
		createSchedPanel.add(ThurGtextField);
		
		JLabel lblFriday = new JLabel("Friday:    ");
		lblFriday.setBounds(17, 292, 69, 20);
		createSchedPanel.add(lblFriday);
		
		FriRtextField = new JTextField();
		FriRtextField.setBounds(110, 289, 62, 26);
		FriRtextField.setColumns(4);
		createSchedPanel.add(FriRtextField);
		
		JLabel label_6 = new JLabel("      ");
		label_6.setBounds(179, 292, 30, 20);
		createSchedPanel.add(label_6);
		
		FriGtextField = new JTextField();
		FriGtextField.setBounds(215, 289, 62, 26);
		FriGtextField.setColumns(4);
		createSchedPanel.add(FriGtextField);
		
		JLabel lblSaturday = new JLabel("Saturday:  ");
		lblSaturday.setBounds(17, 323, 78, 20);
		createSchedPanel.add(lblSaturday);
		
		SatRtextField = new JTextField();
		SatRtextField.setBounds(110, 320, 62, 26);
		SatRtextField.setColumns(4);
		createSchedPanel.add(SatRtextField);
		
		JLabel label_7 = new JLabel("      ");
		label_7.setBounds(183, 323, 30, 20);
		createSchedPanel.add(label_7);
		
		SatGtextField = new JTextField();
		SatGtextField.setBounds(215, 320, 62, 26);
		SatGtextField.setColumns(4);
		createSchedPanel.add(SatGtextField);
		
		JLabel lblSunday = new JLabel("Sunday:  ");
		lblSunday.setBounds(17, 354, 68, 20);
		createSchedPanel.add(lblSunday);
		
		SunRtextField = new JTextField();
		SunRtextField.setBounds(110, 351, 62, 26);
		SunRtextField.setColumns(4);
		createSchedPanel.add(SunRtextField);
		
		JLabel label_9 = new JLabel("      ");
		label_9.setBounds(178, 354, 30, 20);
		createSchedPanel.add(label_9);
		
		SunGtextField = new JTextField();
		SunGtextField.setBounds(215, 351, 62, 26);
		SunGtextField.setColumns(4);
		createSchedPanel.add(SunGtextField);
		
		JButton btnCreateSchedule = new JButton("CREATE SCHEDULE");
		btnCreateSchedule.setBackground(Color.LIGHT_GRAY);
		btnCreateSchedule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//create throughput array
				throughputArray[0]=Integer.parseInt(MonRtextField.getText());
				throughputArray[1]=Integer.parseInt(MonGtextField.getText());
				throughputArray[2]=Integer.parseInt(TueRtextField.getText());
				throughputArray[3]=Integer.parseInt(TueGtextField.getText());
				throughputArray[4]=Integer.parseInt(WedRtextField.getText());
				throughputArray[5]=Integer.parseInt(WedGtextField.getText());
				throughputArray[6]=Integer.parseInt(ThurRtextField.getText());
				throughputArray[7]=Integer.parseInt(ThurGtextField.getText());
				throughputArray[8]=Integer.parseInt(FriRtextField.getText());
				throughputArray[9]=Integer.parseInt(FriGtextField.getText());
				throughputArray[10]=Integer.parseInt(SatRtextField.getText());
				throughputArray[11]=Integer.parseInt(SatGtextField.getText());
				throughputArray[12]=Integer.parseInt(SunRtextField.getText());
				throughputArray[13]=Integer.parseInt(SunGtextField.getText());
				
				startTime=Integer.parseInt(startTimeTextField.getText());
				
				SystemWrapper.mbo.getTrainSchedule(startTime, throughputArray);
				SystemWrapper.mbo.getCrewSchedule(startTime, throughputArray);
			}
		});
		btnCreateSchedule.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnCreateSchedule.setBounds(50, 411, 208, 31);
		createSchedPanel.add(btnCreateSchedule);
		getContentPane().add(displaySchedPanel);
		
		JPanel displaySafeAuthPanel = new JPanel();
		springLayout.putConstraint(SpringLayout.WEST, displaySafeAuthPanel, 6, SpringLayout.EAST, displaySchedPanel);
		
		JLabel lblSchedule = new JLabel("Schedule:");
		displaySchedPanel.add(lblSchedule);
		
		table = new JTable();
		displaySchedPanel.add(table);
		springLayout.putConstraint(SpringLayout.NORTH, displaySafeAuthPanel, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, displaySafeAuthPanel, -10, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, displaySafeAuthPanel, -22, SpringLayout.EAST, getContentPane());
		getContentPane().add(displaySafeAuthPanel);
		displaySafeAuthPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblTrain = new JLabel("Train: ");
		displaySafeAuthPanel.add(lblTrain);
		
		
		
		
		
	}
}
