package train_model;


import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.util.ArrayList;
import java.awt.GridBagLayout;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextField;


public class TrainModelGUI extends JFrame {
	ArrayList<TrainModel> trainList = null;
	String sysMode = null;
	
	private JPanel contentPane;
	private JTextField txtEnterPowerHere;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrainModelGUI frame = new TrainModelGUI(false);
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
	public TrainModelGUI(boolean mode) {
		//this.trainList = trains;
		if (mode == true) {
			// TODO: Set a label indicating TrainModelGUI is in system mode
			this.sysMode = "System mode (multiple trains)";
			// TODO: Need to grab the system's collection of trains
		} else {
			// TODO: Set a label indicating TrainModelGUI is in standalone mode
			this.sysMode = "Standalone mode (single train)";
		}
		
	//public TrainModelGUI() {	
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
				JOptionPane.showMessageDialog(null, "Created by Ben Guise \nfor Software Engineering\nThe A-Team\nSpring 2015");
			}
		});
		mnHelp.add(mntmAbout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblTrainModelInterface = new JLabel("Train Model Interface");
		Font labelFont = lblTrainModelInterface.getFont();
		Font boldFont = new Font(labelFont.getFontName(), Font.BOLD, labelFont.getSize());
		lblTrainModelInterface.setFont(boldFont);
		GridBagConstraints gbc_lblTrainModelInterface = new GridBagConstraints();
		gbc_lblTrainModelInterface.insets = new Insets(0, 0, 5, 5);
		gbc_lblTrainModelInterface.gridx = 0;
		gbc_lblTrainModelInterface.gridy = 0;
		contentPane.add(lblTrainModelInterface, gbc_lblTrainModelInterface);
		
		JLabel lblMode = new JLabel(sysMode);
		GridBagConstraints gbc_lblMode = new GridBagConstraints();
		gbc_lblMode.insets = new Insets(0, 0, 5, 5);
		gbc_lblMode.gridx = 0;
		gbc_lblMode.gridy = 1;
		contentPane.add(lblMode, gbc_lblMode);
		
		JLabel lblTrainModelSelector = new JLabel("Train Model Selector");
		GridBagConstraints gbc_lblTrainModelSelector = new GridBagConstraints();
		gbc_lblTrainModelSelector.insets = new Insets(0, 0, 5, 0);
		gbc_lblTrainModelSelector.gridx = 4;
		gbc_lblTrainModelSelector.gridy = 1;
		contentPane.add(lblTrainModelSelector, gbc_lblTrainModelSelector);
		
		JComboBox comboBox = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 4;
		gbc_comboBox.gridy = 2;
		contentPane.add(comboBox, gbc_comboBox);
		
		JLabel lblCurrentPower = new JLabel("Current Power:");
		GridBagConstraints gbc_lblCurrentPower = new GridBagConstraints();
		gbc_lblCurrentPower.insets = new Insets(0, 0, 5, 5);
		gbc_lblCurrentPower.gridx = 0;
		gbc_lblCurrentPower.gridy = 3;
		contentPane.add(lblCurrentPower, gbc_lblCurrentPower);
		
		JLabel label = new JLabel("0000");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 1;
		gbc_label.gridy = 3;
		contentPane.add(label, gbc_label);
		
		JLabel lblForce = new JLabel("Force:");
		GridBagConstraints gbc_lblForce = new GridBagConstraints();
		gbc_lblForce.insets = new Insets(0, 0, 5, 5);
		gbc_lblForce.gridx = 0;
		gbc_lblForce.gridy = 4;
		contentPane.add(lblForce, gbc_lblForce);
		
		JLabel label_1 = new JLabel("0000");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 1;
		gbc_label_1.gridy = 4;
		contentPane.add(label_1, gbc_label_1);
		
		JLabel lblSimulationControls = new JLabel("Simulation Controls");
		GridBagConstraints gbc_lblSimulationControls = new GridBagConstraints();
		gbc_lblSimulationControls.insets = new Insets(0, 0, 5, 0);
		gbc_lblSimulationControls.gridx = 4;
		gbc_lblSimulationControls.gridy = 4;
		contentPane.add(lblSimulationControls, gbc_lblSimulationControls);
		
		JLabel lblAcceleration = new JLabel("Acceleration:");
		GridBagConstraints gbc_lblAcceleration = new GridBagConstraints();
		gbc_lblAcceleration.insets = new Insets(0, 0, 5, 5);
		gbc_lblAcceleration.gridx = 0;
		gbc_lblAcceleration.gridy = 5;
		contentPane.add(lblAcceleration, gbc_lblAcceleration);
		
		JLabel label_2 = new JLabel("0000");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 1;
		gbc_label_2.gridy = 5;
		contentPane.add(label_2, gbc_label_2);
		
		JLabel lblVelocity = new JLabel("Velocity:");
		GridBagConstraints gbc_lblVelocity = new GridBagConstraints();
		gbc_lblVelocity.insets = new Insets(0, 0, 5, 5);
		gbc_lblVelocity.gridx = 0;
		gbc_lblVelocity.gridy = 6;
		contentPane.add(lblVelocity, gbc_lblVelocity);
		
		JLabel label_3 = new JLabel("0000");
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridx = 1;
		gbc_label_3.gridy = 6;
		contentPane.add(label_3, gbc_label_3);
		
		JButton btnEngineFailure = new JButton("Engine Failure");
		GridBagConstraints gbc_btnEngineFailure = new GridBagConstraints();
		gbc_btnEngineFailure.insets = new Insets(0, 0, 5, 0);
		gbc_btnEngineFailure.gridx = 4;
		gbc_btnEngineFailure.gridy = 6;
		contentPane.add(btnEngineFailure, gbc_btnEngineFailure);
		
		JLabel lblPosition = new JLabel("Position:");
		GridBagConstraints gbc_lblPosition = new GridBagConstraints();
		gbc_lblPosition.insets = new Insets(0, 0, 5, 5);
		gbc_lblPosition.gridx = 0;
		gbc_lblPosition.gridy = 7;
		contentPane.add(lblPosition, gbc_lblPosition);
		
		JLabel label_4 = new JLabel("0000");
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.insets = new Insets(0, 0, 5, 5);
		gbc_label_4.gridx = 1;
		gbc_label_4.gridy = 7;
		contentPane.add(label_4, gbc_label_4);
		
		JButton btnRestoreEngine = new JButton("Restore Engine");
		GridBagConstraints gbc_btnRestoreEngine = new GridBagConstraints();
		gbc_btnRestoreEngine.insets = new Insets(0, 0, 5, 0);
		gbc_btnRestoreEngine.gridx = 4;
		gbc_btnRestoreEngine.gridy = 7;
		contentPane.add(btnRestoreEngine, gbc_btnRestoreEngine);
		
		JButton btnBrakeFailure = new JButton("Brake Failure");
		GridBagConstraints gbc_btnBrakeFailure = new GridBagConstraints();
		gbc_btnBrakeFailure.insets = new Insets(0, 0, 5, 0);
		gbc_btnBrakeFailure.gridx = 4;
		gbc_btnBrakeFailure.gridy = 8;
		contentPane.add(btnBrakeFailure, gbc_btnBrakeFailure);
		
		JLabel lblPassengerCount = new JLabel("Crew count:");
		GridBagConstraints gbc_lblPassengerCount = new GridBagConstraints();
		gbc_lblPassengerCount.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassengerCount.gridx = 0;
		gbc_lblPassengerCount.gridy = 9;
		contentPane.add(lblPassengerCount, gbc_lblPassengerCount);
		
		JLabel label_5 = new JLabel("0");
		GridBagConstraints gbc_label_5 = new GridBagConstraints();
		gbc_label_5.insets = new Insets(0, 0, 5, 5);
		gbc_label_5.gridx = 1;
		gbc_label_5.gridy = 9;
		contentPane.add(label_5, gbc_label_5);
		
		JButton btnRestoreBrakes = new JButton("Restore Brakes");
		GridBagConstraints gbc_btnRestoreBrakes = new GridBagConstraints();
		gbc_btnRestoreBrakes.insets = new Insets(0, 0, 5, 0);
		gbc_btnRestoreBrakes.gridx = 4;
		gbc_btnRestoreBrakes.gridy = 9;
		contentPane.add(btnRestoreBrakes, gbc_btnRestoreBrakes);
		
		JLabel lblCrewCount = new JLabel("Passenger count:");
		GridBagConstraints gbc_lblCrewCount = new GridBagConstraints();
		gbc_lblCrewCount.insets = new Insets(0, 0, 5, 5);
		gbc_lblCrewCount.gridx = 0;
		gbc_lblCrewCount.gridy = 10;
		contentPane.add(lblCrewCount, gbc_lblCrewCount);
		
		JLabel label_6 = new JLabel("0");
		GridBagConstraints gbc_label_6 = new GridBagConstraints();
		gbc_label_6.insets = new Insets(0, 0, 5, 5);
		gbc_label_6.gridx = 1;
		gbc_label_6.gridy = 10;
		contentPane.add(label_6, gbc_label_6);
		
		JButton btnSigPickupFailure = new JButton("Sig. Pickup Failure");
		GridBagConstraints gbc_btnSigPickupFailure = new GridBagConstraints();
		gbc_btnSigPickupFailure.insets = new Insets(0, 0, 5, 0);
		gbc_btnSigPickupFailure.gridx = 4;
		gbc_btnSigPickupFailure.gridy = 10;
		contentPane.add(btnSigPickupFailure, gbc_btnSigPickupFailure);
		
		JButton btnRestoreSigPickup = new JButton("Restore Sig. PIckup");
		GridBagConstraints gbc_btnRestoreSigPickup = new GridBagConstraints();
		gbc_btnRestoreSigPickup.insets = new Insets(0, 0, 5, 0);
		gbc_btnRestoreSigPickup.gridx = 4;
		gbc_btnRestoreSigPickup.gridy = 11;
		contentPane.add(btnRestoreSigPickup, gbc_btnRestoreSigPickup);
		
		JLabel lblLeftDoors = new JLabel("Left Doors:");
		GridBagConstraints gbc_lblLeftDoors = new GridBagConstraints();
		gbc_lblLeftDoors.insets = new Insets(0, 0, 5, 5);
		gbc_lblLeftDoors.gridx = 0;
		gbc_lblLeftDoors.gridy = 12;
		contentPane.add(lblLeftDoors, gbc_lblLeftDoors);
		
		JLabel lblClosed = new JLabel("Closed");
		GridBagConstraints gbc_lblClosed = new GridBagConstraints();
		gbc_lblClosed.insets = new Insets(0, 0, 5, 5);
		gbc_lblClosed.gridx = 1;
		gbc_lblClosed.gridy = 12;
		contentPane.add(lblClosed, gbc_lblClosed);
		
		JLabel lblRightDoors = new JLabel("Right Doors:");
		GridBagConstraints gbc_lblRightDoors = new GridBagConstraints();
		gbc_lblRightDoors.insets = new Insets(0, 0, 5, 5);
		gbc_lblRightDoors.gridx = 0;
		gbc_lblRightDoors.gridy = 13;
		contentPane.add(lblRightDoors, gbc_lblRightDoors);
		
		JLabel lblClosed_1 = new JLabel("Closed");
		GridBagConstraints gbc_lblClosed_1 = new GridBagConstraints();
		gbc_lblClosed_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblClosed_1.gridx = 1;
		gbc_lblClosed_1.gridy = 13;
		contentPane.add(lblClosed_1, gbc_lblClosed_1);
		
		JLabel lblSetPowerCommand = new JLabel("Set Power Command:");
		GridBagConstraints gbc_lblSetPowerCommand = new GridBagConstraints();
		gbc_lblSetPowerCommand.insets = new Insets(0, 0, 5, 0);
		gbc_lblSetPowerCommand.gridx = 4;
		gbc_lblSetPowerCommand.gridy = 13;
		contentPane.add(lblSetPowerCommand, gbc_lblSetPowerCommand);
		
		JLabel lblLights = new JLabel("Lights:");
		GridBagConstraints gbc_lblLights = new GridBagConstraints();
		gbc_lblLights.insets = new Insets(0, 0, 5, 5);
		gbc_lblLights.gridx = 0;
		gbc_lblLights.gridy = 14;
		contentPane.add(lblLights, gbc_lblLights);
		
		JLabel lblOff = new JLabel("Off");
		GridBagConstraints gbc_lblOff = new GridBagConstraints();
		gbc_lblOff.insets = new Insets(0, 0, 5, 5);
		gbc_lblOff.gridx = 1;
		gbc_lblOff.gridy = 14;
		contentPane.add(lblOff, gbc_lblOff);
		
		txtEnterPowerHere = new JTextField();
		txtEnterPowerHere.setText("Enter power(kW)");
		GridBagConstraints gbc_txtEnterPowerHere = new GridBagConstraints();
		gbc_txtEnterPowerHere.insets = new Insets(0, 0, 5, 0);
		gbc_txtEnterPowerHere.gridx = 4;
		gbc_txtEnterPowerHere.gridy = 14;
		contentPane.add(txtEnterPowerHere, gbc_txtEnterPowerHere);
		txtEnterPowerHere.setColumns(10);
		
		JLabel lblBeacon = new JLabel("Beacon:");
		GridBagConstraints gbc_lblBeacon = new GridBagConstraints();
		gbc_lblBeacon.insets = new Insets(0, 0, 5, 5);
		gbc_lblBeacon.gridx = 0;
		gbc_lblBeacon.gridy = 15;
		contentPane.add(lblBeacon, gbc_lblBeacon);
		
		JLabel lblMsg = new JLabel("Msg");
		GridBagConstraints gbc_lblMsg = new GridBagConstraints();
		gbc_lblMsg.insets = new Insets(0, 0, 5, 5);
		gbc_lblMsg.gridx = 1;
		gbc_lblMsg.gridy = 15;
		contentPane.add(lblMsg, gbc_lblMsg);
		
		JButton btnSetPower = new JButton("Set Power");
		GridBagConstraints gbc_btnSetPower = new GridBagConstraints();
		gbc_btnSetPower.insets = new Insets(0, 0, 5, 0);
		gbc_btnSetPower.gridx = 4;
		gbc_btnSetPower.gridy = 15;
		contentPane.add(btnSetPower, gbc_btnSetPower);
		
		JLabel lblServiceBrake = new JLabel("Service Brake:");
		GridBagConstraints gbc_lblServiceBrake = new GridBagConstraints();
		gbc_lblServiceBrake.insets = new Insets(0, 0, 5, 5);
		gbc_lblServiceBrake.gridx = 0;
		gbc_lblServiceBrake.gridy = 16;
		contentPane.add(lblServiceBrake, gbc_lblServiceBrake);
		
		JLabel lblstatus = new JLabel("[Status]");
		GridBagConstraints gbc_lblstatus = new GridBagConstraints();
		gbc_lblstatus.insets = new Insets(0, 0, 5, 5);
		gbc_lblstatus.gridx = 1;
		gbc_lblstatus.gridy = 16;
		contentPane.add(lblstatus, gbc_lblstatus);
		
		JLabel lblEmergencyBrake = new JLabel("Emergency Brake:");
		GridBagConstraints gbc_lblEmergencyBrake = new GridBagConstraints();
		gbc_lblEmergencyBrake.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmergencyBrake.gridx = 0;
		gbc_lblEmergencyBrake.gridy = 17;
		contentPane.add(lblEmergencyBrake, gbc_lblEmergencyBrake);
		
		JLabel lblstatus_1 = new JLabel("[Status]");
		GridBagConstraints gbc_lblstatus_1 = new GridBagConstraints();
		gbc_lblstatus_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblstatus_1.gridx = 1;
		gbc_lblstatus_1.gridy = 17;
		contentPane.add(lblstatus_1, gbc_lblstatus_1);
		
		JLabel lblEngineStatus = new JLabel("Engine Status:");
		GridBagConstraints gbc_lblEngineStatus = new GridBagConstraints();
		gbc_lblEngineStatus.insets = new Insets(0, 0, 5, 5);
		gbc_lblEngineStatus.gridx = 0;
		gbc_lblEngineStatus.gridy = 18;
		contentPane.add(lblEngineStatus, gbc_lblEngineStatus);
		
		JLabel lblstatus_2 = new JLabel("[Status]");
		GridBagConstraints gbc_lblstatus_2 = new GridBagConstraints();
		gbc_lblstatus_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblstatus_2.gridx = 1;
		gbc_lblstatus_2.gridy = 18;
		contentPane.add(lblstatus_2, gbc_lblstatus_2);
		
		JLabel lblBrakeStatus = new JLabel("Brake Status:");
		GridBagConstraints gbc_lblBrakeStatus = new GridBagConstraints();
		gbc_lblBrakeStatus.insets = new Insets(0, 0, 5, 5);
		gbc_lblBrakeStatus.gridx = 0;
		gbc_lblBrakeStatus.gridy = 19;
		contentPane.add(lblBrakeStatus, gbc_lblBrakeStatus);
		
		JLabel lblstatus_3 = new JLabel("[Status]");
		GridBagConstraints gbc_lblstatus_3 = new GridBagConstraints();
		gbc_lblstatus_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblstatus_3.gridx = 1;
		gbc_lblstatus_3.gridy = 19;
		contentPane.add(lblstatus_3, gbc_lblstatus_3);
		
		JLabel lblSignalPickupStatus = new JLabel("Signal Pickup Status:");
		GridBagConstraints gbc_lblSignalPickupStatus = new GridBagConstraints();
		gbc_lblSignalPickupStatus.insets = new Insets(0, 0, 5, 5);
		gbc_lblSignalPickupStatus.gridx = 0;
		gbc_lblSignalPickupStatus.gridy = 20;
		contentPane.add(lblSignalPickupStatus, gbc_lblSignalPickupStatus);
		
		JLabel lblstatus_4 = new JLabel("[Status]");
		GridBagConstraints gbc_lblstatus_4 = new GridBagConstraints();
		gbc_lblstatus_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblstatus_4.gridx = 1;
		gbc_lblstatus_4.gridy = 20;
		contentPane.add(lblstatus_4, gbc_lblstatus_4);
	}

}
