import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Timer;
import java.util.TimerTask;


public class TrainModelGUI extends JFrame {
	private JTextField textField;
	
	private static TrainModel train;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrainModelGUI frame = new TrainModelGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	
	

	/**
	 * Create the frame.
	 */
	public TrainModelGUI() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 650);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmQuit = new JMenuItem("Quit");
		mntmQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnFile.add(mntmQuit);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Train Model UI Prototype v 1.0\nThe A-Team\nBen Guise");
			}
		});
		mnHelp.add(mntmAbout);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblForce = new JLabel("Force:");
		GridBagConstraints gbc_lblForce = new GridBagConstraints();
		gbc_lblForce.insets = new Insets(0, 0, 5, 5);
		gbc_lblForce.gridx = 1;
		gbc_lblForce.gridy = 1;
		getContentPane().add(lblForce, gbc_lblForce);
		
		JLabel forceLabel = new JLabel("0");
		GridBagConstraints gbc_forceLabel = new GridBagConstraints();
		gbc_forceLabel.insets = new Insets(0, 0, 5, 5);
		gbc_forceLabel.gridx = 2;
		gbc_forceLabel.gridy = 1;
		getContentPane().add(forceLabel, gbc_forceLabel);
		
		JLabel lblN_1 = new JLabel("N");
		GridBagConstraints gbc_lblN_1 = new GridBagConstraints();
		gbc_lblN_1.anchor = GridBagConstraints.WEST;
		gbc_lblN_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblN_1.gridx = 3;
		gbc_lblN_1.gridy = 1;
		getContentPane().add(lblN_1, gbc_lblN_1);
		
		JLabel lblSimulate = new JLabel("Simulate:");
		GridBagConstraints gbc_lblSimulate = new GridBagConstraints();
		gbc_lblSimulate.insets = new Insets(0, 0, 5, 5);
		gbc_lblSimulate.gridx = 7;
		gbc_lblSimulate.gridy = 1;
		getContentPane().add(lblSimulate, gbc_lblSimulate);
		
		JLabel lblAcceleration = new JLabel("Acceleration:");
		GridBagConstraints gbc_lblAcceleration = new GridBagConstraints();
		gbc_lblAcceleration.insets = new Insets(0, 0, 5, 5);
		gbc_lblAcceleration.gridx = 1;
		gbc_lblAcceleration.gridy = 2;
		getContentPane().add(lblAcceleration, gbc_lblAcceleration);
		
		JLabel accelLabel = new JLabel("0");
		GridBagConstraints gbc_accelLabel = new GridBagConstraints();
		gbc_accelLabel.insets = new Insets(0, 0, 5, 5);
		gbc_accelLabel.gridx = 2;
		gbc_accelLabel.gridy = 2;
		getContentPane().add(accelLabel, gbc_accelLabel);
		
		JLabel lblMs = new JLabel("m/s2");
		GridBagConstraints gbc_lblMs = new GridBagConstraints();
		gbc_lblMs.anchor = GridBagConstraints.WEST;
		gbc_lblMs.insets = new Insets(0, 0, 5, 5);
		gbc_lblMs.gridx = 3;
		gbc_lblMs.gridy = 2;
		getContentPane().add(lblMs, gbc_lblMs);
		
		JButton btnEngineFailure = new JButton("Engine Failure");
		GridBagConstraints gbc_btnEngineFailure = new GridBagConstraints();
		gbc_btnEngineFailure.insets = new Insets(0, 0, 5, 5);
		gbc_btnEngineFailure.gridx = 7;
		gbc_btnEngineFailure.gridy = 2;
		getContentPane().add(btnEngineFailure, gbc_btnEngineFailure);
		
		JLabel lblVelocity = new JLabel("Velocity:");
		GridBagConstraints gbc_lblVelocity = new GridBagConstraints();
		gbc_lblVelocity.insets = new Insets(0, 0, 5, 5);
		gbc_lblVelocity.gridx = 1;
		gbc_lblVelocity.gridy = 3;
		getContentPane().add(lblVelocity, gbc_lblVelocity);
		
		JLabel velLabel = new JLabel("0");
		GridBagConstraints gbc_velLabel = new GridBagConstraints();
		gbc_velLabel.insets = new Insets(0, 0, 5, 5);
		gbc_velLabel.gridx = 2;
		gbc_velLabel.gridy = 3;
		getContentPane().add(velLabel, gbc_velLabel);
		
		JLabel lblMs_1 = new JLabel("m/s");
		GridBagConstraints gbc_lblMs_1 = new GridBagConstraints();
		gbc_lblMs_1.anchor = GridBagConstraints.WEST;
		gbc_lblMs_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblMs_1.gridx = 3;
		gbc_lblMs_1.gridy = 3;
		getContentPane().add(lblMs_1, gbc_lblMs_1);
		
		JButton btnBrakeFailure = new JButton("Brake Failure");
		GridBagConstraints gbc_btnBrakeFailure = new GridBagConstraints();
		gbc_btnBrakeFailure.insets = new Insets(0, 0, 5, 5);
		gbc_btnBrakeFailure.gridx = 7;
		gbc_btnBrakeFailure.gridy = 3;
		getContentPane().add(btnBrakeFailure, gbc_btnBrakeFailure);
		
		JLabel lblPosition = new JLabel("Position:");
		GridBagConstraints gbc_lblPosition = new GridBagConstraints();
		gbc_lblPosition.insets = new Insets(0, 0, 5, 5);
		gbc_lblPosition.gridx = 1;
		gbc_lblPosition.gridy = 4;
		getContentPane().add(lblPosition, gbc_lblPosition);
		
		JLabel lblFt = new JLabel("0");
		GridBagConstraints gbc_lblFt = new GridBagConstraints();
		gbc_lblFt.insets = new Insets(0, 0, 5, 5);
		gbc_lblFt.gridx = 2;
		gbc_lblFt.gridy = 4;
		getContentPane().add(lblFt, gbc_lblFt);
		
		JLabel lblM = new JLabel("m");
		GridBagConstraints gbc_lblM = new GridBagConstraints();
		gbc_lblM.anchor = GridBagConstraints.WEST;
		gbc_lblM.insets = new Insets(0, 0, 5, 5);
		gbc_lblM.gridx = 3;
		gbc_lblM.gridy = 4;
		getContentPane().add(lblM, gbc_lblM);
		
		JButton btnRestoreEngine = new JButton("Restore Engine");
		GridBagConstraints gbc_btnRestoreEngine = new GridBagConstraints();
		gbc_btnRestoreEngine.insets = new Insets(0, 0, 5, 5);
		gbc_btnRestoreEngine.gridx = 7;
		gbc_btnRestoreEngine.gridy = 4;
		getContentPane().add(btnRestoreEngine, gbc_btnRestoreEngine);
		
		JLabel lblPassengerCount = new JLabel("Passenger Count:");
		GridBagConstraints gbc_lblPassengerCount = new GridBagConstraints();
		gbc_lblPassengerCount.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassengerCount.gridx = 1;
		gbc_lblPassengerCount.gridy = 5;
		getContentPane().add(lblPassengerCount, gbc_lblPassengerCount);
		
		JLabel label = new JLabel("0");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 2;
		gbc_label.gridy = 5;
		getContentPane().add(label, gbc_label);
		
		JButton btnRestoreBrakes = new JButton("Restore Brakes");
		GridBagConstraints gbc_btnRestoreBrakes = new GridBagConstraints();
		gbc_btnRestoreBrakes.insets = new Insets(0, 0, 5, 5);
		gbc_btnRestoreBrakes.gridx = 7;
		gbc_btnRestoreBrakes.gridy = 5;
		getContentPane().add(btnRestoreBrakes, gbc_btnRestoreBrakes);
		
		JLabel lblCrewCount = new JLabel("Crew Count:");
		GridBagConstraints gbc_lblCrewCount = new GridBagConstraints();
		gbc_lblCrewCount.insets = new Insets(0, 0, 5, 5);
		gbc_lblCrewCount.gridx = 1;
		gbc_lblCrewCount.gridy = 6;
		getContentPane().add(lblCrewCount, gbc_lblCrewCount);
		
		JLabel label_1 = new JLabel("0");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 2;
		gbc_label_1.gridy = 6;
		getContentPane().add(label_1, gbc_label_1);
		
		JButton btnOpenDoors = new JButton("Open Doors");
		GridBagConstraints gbc_btnOpenDoors = new GridBagConstraints();
		gbc_btnOpenDoors.insets = new Insets(0, 0, 5, 5);
		gbc_btnOpenDoors.gridx = 6;
		gbc_btnOpenDoors.gridy = 6;
		getContentPane().add(btnOpenDoors, gbc_btnOpenDoors);
		
		JRadioButton rdbtnLeft = new JRadioButton("Left");
		GridBagConstraints gbc_rdbtnLeft = new GridBagConstraints();
		gbc_rdbtnLeft.anchor = GridBagConstraints.WEST;
		gbc_rdbtnLeft.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnLeft.gridx = 7;
		gbc_rdbtnLeft.gridy = 6;
		getContentPane().add(rdbtnLeft, gbc_rdbtnLeft);
		
		JLabel lblLeftDoors = new JLabel("Left Doors:");
		GridBagConstraints gbc_lblLeftDoors = new GridBagConstraints();
		gbc_lblLeftDoors.insets = new Insets(0, 0, 5, 5);
		gbc_lblLeftDoors.gridx = 1;
		gbc_lblLeftDoors.gridy = 7;
		getContentPane().add(lblLeftDoors, gbc_lblLeftDoors);
		
		JLabel lblClosed = new JLabel("Closed");
		GridBagConstraints gbc_lblClosed = new GridBagConstraints();
		gbc_lblClosed.insets = new Insets(0, 0, 5, 5);
		gbc_lblClosed.gridx = 2;
		gbc_lblClosed.gridy = 7;
		getContentPane().add(lblClosed, gbc_lblClosed);
		
		JRadioButton rdbtnRight = new JRadioButton("Right");
		GridBagConstraints gbc_rdbtnRight = new GridBagConstraints();
		gbc_rdbtnRight.anchor = GridBagConstraints.WEST;
		gbc_rdbtnRight.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnRight.gridx = 7;
		gbc_rdbtnRight.gridy = 7;
		getContentPane().add(rdbtnRight, gbc_rdbtnRight);
		
		JLabel lblRightDoors = new JLabel("Right Doors:");
		GridBagConstraints gbc_lblRightDoors = new GridBagConstraints();
		gbc_lblRightDoors.insets = new Insets(0, 0, 5, 5);
		gbc_lblRightDoors.gridx = 1;
		gbc_lblRightDoors.gridy = 8;
		getContentPane().add(lblRightDoors, gbc_lblRightDoors);
		
		JLabel lblClosed_1 = new JLabel("Closed");
		GridBagConstraints gbc_lblClosed_1 = new GridBagConstraints();
		gbc_lblClosed_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblClosed_1.gridx = 2;
		gbc_lblClosed_1.gridy = 8;
		getContentPane().add(lblClosed_1, gbc_lblClosed_1);
		
		JButton btnCloseDoors = new JButton("Close Doors");
		GridBagConstraints gbc_btnCloseDoors = new GridBagConstraints();
		gbc_btnCloseDoors.insets = new Insets(0, 0, 5, 5);
		gbc_btnCloseDoors.gridx = 6;
		gbc_btnCloseDoors.gridy = 8;
		getContentPane().add(btnCloseDoors, gbc_btnCloseDoors);
		
		JRadioButton rdbtnLeft_1 = new JRadioButton("Left");
		GridBagConstraints gbc_rdbtnLeft_1 = new GridBagConstraints();
		gbc_rdbtnLeft_1.anchor = GridBagConstraints.WEST;
		gbc_rdbtnLeft_1.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnLeft_1.gridx = 7;
		gbc_rdbtnLeft_1.gridy = 8;
		getContentPane().add(rdbtnLeft_1, gbc_rdbtnLeft_1);
		
		JLabel lblLights = new JLabel("Lights:");
		GridBagConstraints gbc_lblLights = new GridBagConstraints();
		gbc_lblLights.insets = new Insets(0, 0, 5, 5);
		gbc_lblLights.gridx = 1;
		gbc_lblLights.gridy = 9;
		getContentPane().add(lblLights, gbc_lblLights);
		
		JLabel lblOff = new JLabel("Off");
		GridBagConstraints gbc_lblOff = new GridBagConstraints();
		gbc_lblOff.insets = new Insets(0, 0, 5, 5);
		gbc_lblOff.gridx = 2;
		gbc_lblOff.gridy = 9;
		getContentPane().add(lblOff, gbc_lblOff);
		
		JRadioButton rdbtnRight_1 = new JRadioButton("Right");
		GridBagConstraints gbc_rdbtnRight_1 = new GridBagConstraints();
		gbc_rdbtnRight_1.anchor = GridBagConstraints.WEST;
		gbc_rdbtnRight_1.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnRight_1.gridx = 7;
		gbc_rdbtnRight_1.gridy = 9;
		getContentPane().add(rdbtnRight_1, gbc_rdbtnRight_1);
		
		JLabel lblPlacehold = new JLabel("Beacon:");
		GridBagConstraints gbc_lblPlacehold = new GridBagConstraints();
		gbc_lblPlacehold.insets = new Insets(0, 0, 5, 5);
		gbc_lblPlacehold.gridx = 1;
		gbc_lblPlacehold.gridy = 10;
		getContentPane().add(lblPlacehold, gbc_lblPlacehold);
		
		JLabel lblNone = new JLabel("None");
		GridBagConstraints gbc_lblNone = new GridBagConstraints();
		gbc_lblNone.insets = new Insets(0, 0, 5, 5);
		gbc_lblNone.gridx = 2;
		gbc_lblNone.gridy = 10;
		getContentPane().add(lblNone, gbc_lblNone);
		
		JLabel lblEngineStatus = new JLabel("Engine Status:");
		GridBagConstraints gbc_lblEngineStatus = new GridBagConstraints();
		gbc_lblEngineStatus.insets = new Insets(0, 0, 5, 5);
		gbc_lblEngineStatus.gridx = 4;
		gbc_lblEngineStatus.gridy = 11;
		getContentPane().add(lblEngineStatus, gbc_lblEngineStatus);
		
		JLabel lblOk = new JLabel("OK");
		GridBagConstraints gbc_lblOk = new GridBagConstraints();
		gbc_lblOk.insets = new Insets(0, 0, 5, 5);
		gbc_lblOk.gridx = 5;
		gbc_lblOk.gridy = 11;
		getContentPane().add(lblOk, gbc_lblOk);
		
		JLabel lblEnterPower = new JLabel("Enter Power:");
		GridBagConstraints gbc_lblEnterPower = new GridBagConstraints();
		gbc_lblEnterPower.anchor = GridBagConstraints.EAST;
		gbc_lblEnterPower.insets = new Insets(0, 0, 5, 5);
		gbc_lblEnterPower.gridx = 6;
		gbc_lblEnterPower.gridy = 11;
		getContentPane().add(lblEnterPower, gbc_lblEnterPower);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 7;
		gbc_textField.gridy = 11;
		getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel lblCurrentPower = new JLabel("Current Power:");
		GridBagConstraints gbc_lblCurrentPower = new GridBagConstraints();
		gbc_lblCurrentPower.insets = new Insets(0, 0, 5, 5);
		gbc_lblCurrentPower.gridx = 1;
		gbc_lblCurrentPower.gridy = 12;
		getContentPane().add(lblCurrentPower, gbc_lblCurrentPower);
		
		JLabel powLabel = new JLabel("0");
		GridBagConstraints gbc_powLabel = new GridBagConstraints();
		gbc_powLabel.insets = new Insets(0, 0, 5, 5);
		gbc_powLabel.gridx = 2;
		gbc_powLabel.gridy = 12;
		getContentPane().add(powLabel, gbc_powLabel);
		
		JLabel lblKw_1 = new JLabel("kW");
		GridBagConstraints gbc_lblKw_1 = new GridBagConstraints();
		gbc_lblKw_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblKw_1.gridx = 3;
		gbc_lblKw_1.gridy = 12;
		getContentPane().add(lblKw_1, gbc_lblKw_1);
		
		JLabel lblBrakeStatus = new JLabel("Brake Status:");
		GridBagConstraints gbc_lblBrakeStatus = new GridBagConstraints();
		gbc_lblBrakeStatus.insets = new Insets(0, 0, 5, 5);
		gbc_lblBrakeStatus.gridx = 4;
		gbc_lblBrakeStatus.gridy = 12;
		getContentPane().add(lblBrakeStatus, gbc_lblBrakeStatus);
		
		JLabel lblOk_1 = new JLabel("OK");
		GridBagConstraints gbc_lblOk_1 = new GridBagConstraints();
		gbc_lblOk_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblOk_1.gridx = 5;
		gbc_lblOk_1.gridy = 12;
		getContentPane().add(lblOk_1, gbc_lblOk_1);
		
		JButton btnSetPower = new JButton("Set Power");
		btnSetPower.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double newPow = Double.parseDouble(textField.getText());
				train.setPower(newPow);
			}
		});
		GridBagConstraints gbc_btnSetPower = new GridBagConstraints();
		gbc_btnSetPower.insets = new Insets(0, 0, 5, 5);
		gbc_btnSetPower.gridx = 7;
		gbc_btnSetPower.gridy = 12;
		getContentPane().add(btnSetPower, gbc_btnSetPower);
		
		JLabel lblEmergencyBrake = new JLabel("Emergency Brake:");
		GridBagConstraints gbc_lblEmergencyBrake = new GridBagConstraints();
		gbc_lblEmergencyBrake.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmergencyBrake.gridx = 1;
		gbc_lblEmergencyBrake.gridy = 13;
		getContentPane().add(lblEmergencyBrake, gbc_lblEmergencyBrake);
		
		JButton eBrakeEnBtn = new JButton("Engage");
		eBrakeEnBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				train.setEBrake(true);
			}
		});
		GridBagConstraints gbc_eBrakeEnBtn = new GridBagConstraints();
		gbc_eBrakeEnBtn.insets = new Insets(0, 0, 5, 5);
		gbc_eBrakeEnBtn.gridx = 2;
		gbc_eBrakeEnBtn.gridy = 13;
		getContentPane().add(eBrakeEnBtn, gbc_eBrakeEnBtn);
		
		JButton eBrakeReBtn = new JButton("Release");
		eBrakeReBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				train.setEBrake(false);
			}
		});
		GridBagConstraints gbc_eBrakeReBtn = new GridBagConstraints();
		gbc_eBrakeReBtn.insets = new Insets(0, 0, 5, 5);
		gbc_eBrakeReBtn.gridx = 3;
		gbc_eBrakeReBtn.gridy = 13;
		getContentPane().add(eBrakeReBtn, gbc_eBrakeReBtn);
		
		JLabel eBrakeStatusLabel = new JLabel("Off");
		GridBagConstraints gbc_eBrakeStatusLabel = new GridBagConstraints();
		gbc_eBrakeStatusLabel.anchor = GridBagConstraints.EAST;
		gbc_eBrakeStatusLabel.insets = new Insets(0, 0, 5, 5);
		gbc_eBrakeStatusLabel.gridx = 1;
		gbc_eBrakeStatusLabel.gridy = 14;
		getContentPane().add(eBrakeStatusLabel, gbc_eBrakeStatusLabel);
		
		JLabel lblServiceBrake = new JLabel("Service Brake:");
		GridBagConstraints gbc_lblServiceBrake = new GridBagConstraints();
		gbc_lblServiceBrake.insets = new Insets(0, 0, 5, 5);
		gbc_lblServiceBrake.gridx = 1;
		gbc_lblServiceBrake.gridy = 15;
		getContentPane().add(lblServiceBrake, gbc_lblServiceBrake);
		
		JButton sBrakeEnBtn = new JButton("Engage");
		sBrakeEnBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				train.setServBrake(true);
			}
		});
		GridBagConstraints gbc_sBrakeEnBtn = new GridBagConstraints();
		gbc_sBrakeEnBtn.insets = new Insets(0, 0, 5, 5);
		gbc_sBrakeEnBtn.gridx = 2;
		gbc_sBrakeEnBtn.gridy = 15;
		getContentPane().add(sBrakeEnBtn, gbc_sBrakeEnBtn);
		
		JButton sBrakeReBtn = new JButton("Release");
		sBrakeReBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				train.setServBrake(false);
			}
		});
		GridBagConstraints gbc_sBrakeReBtn = new GridBagConstraints();
		gbc_sBrakeReBtn.insets = new Insets(0, 0, 5, 5);
		gbc_sBrakeReBtn.gridx = 3;
		gbc_sBrakeReBtn.gridy = 15;
		getContentPane().add(sBrakeReBtn, gbc_sBrakeReBtn);
		
		JLabel sBrakeStatusLabel = new JLabel("Off");
		GridBagConstraints gbc_sBrakeStatusLabel = new GridBagConstraints();
		gbc_sBrakeStatusLabel.anchor = GridBagConstraints.EAST;
		gbc_sBrakeStatusLabel.insets = new Insets(0, 0, 5, 5);
		gbc_sBrakeStatusLabel.gridx = 1;
		gbc_sBrakeStatusLabel.gridy = 16;
		getContentPane().add(sBrakeStatusLabel, gbc_sBrakeStatusLabel);
		
		
		Timer timer = new Timer();
		train = new TrainModel();
					
		timer.scheduleAtFixedRate(new TimerTask() {
		@Override
		public void run() {
			/* Run code to make the train run, runs every second */
			double delta = 1;
			train.updateTime(delta);
			train.calcAccel();
			train.calcVelocity();
			powLabel.setText(String.format("%.4g%n", train.getPower()));
			velLabel.setText(String.format("%.4g%n", train.getVelocity()));
			accelLabel.setText(String.format("%.4g%n", train.getAccel()));
			forceLabel.setText(String.format("%.4g%n", train.getForce()));
			if (train.getServBrake())
				sBrakeStatusLabel.setText("On");
			else 
				sBrakeStatusLabel.setText("Off");
			if (train.getEBrake())
				eBrakeStatusLabel.setText("On");
			else
				eBrakeStatusLabel.setText("Off");
			}
		}, 0, 1000); // Set delay to 0, 1000ms (or 1s)

	}

}
