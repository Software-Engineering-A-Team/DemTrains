package train_model;


import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextField;

import system_wrapper.SystemWrapper;
import track_model.TrackBlock;
import train_controller.TrainController;


public class TrainModelGUI extends JFrame {
	ArrayList<TrainModel> trainList = null;
	String sysModeStr = null;
	boolean guiMode;
	
	// TrainModel to control / monitor using the GUI
	TrainModel currTrain = null;
	
	private JPanel contentPane;
	private JTextField txtSetPower;

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
		this.guiMode = mode;
		// Setup the GUI system variables depending on how it was started
		if (this.guiMode == true) {
			this.sysModeStr = "System mode (multiple trains)";
			this.trainList = SystemWrapper.trainModels;
		} else if (this.guiMode == false) {
			this.sysModeStr = "Standalone mode (single train)";
			this.trainList = new ArrayList<TrainModel>(1);
			this.trainList.add(new TrainModel("SingleTrain", (short)1));
		}
		
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
		
		JLabel lblMode = new JLabel(sysModeStr);
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
		// TODO: Setup ComboBox to provide a list of trains for user to select
		
		comboBox.addItemListener(new ItemListener() {
		      @Override
		      public void itemStateChanged(ItemEvent e) {
		        if (e.getStateChange() == ItemEvent.SELECTED) {
		          if (trainList != null && trainList.size() > 0) {
		            int trainIndex = comboBox.getSelectedIndex();
		            
		            //System.out.println(trainIndex);
		            
		            if (trainIndex >= 0 && trainIndex < trainList.size()) {
		              currTrain = trainList.get(trainIndex);
		            }
		          }
		        }
		      }
		    });
		    PopupMenuListener selectedTrainPopupListener = new PopupMenuListener() {
		      @Override
		      public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
		        comboBox.removeAllItems();

		        for (TrainModel t : trainList) {
		          comboBox.addItem((int)t.trainID);
		        }
		      }

		      @Override
		      public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {}

		      @Override
		      public void popupMenuCanceled(PopupMenuEvent e) {}
		    };
		    comboBox.addPopupMenuListener(selectedTrainPopupListener);
		
		
		    
		    
		
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
		
		JLabel currPowLabel = new JLabel("0000");
		GridBagConstraints gbc_currPowLabel = new GridBagConstraints();
		gbc_currPowLabel.insets = new Insets(0, 0, 5, 5);
		gbc_currPowLabel.gridx = 1;
		gbc_currPowLabel.gridy = 3;
		contentPane.add(currPowLabel, gbc_currPowLabel);
		
		JLabel lblForce = new JLabel("Force:");
		GridBagConstraints gbc_lblForce = new GridBagConstraints();
		gbc_lblForce.insets = new Insets(0, 0, 5, 5);
		gbc_lblForce.gridx = 0;
		gbc_lblForce.gridy = 4;
		contentPane.add(lblForce, gbc_lblForce);
		
		JLabel currForceLabel = new JLabel("0000");
		GridBagConstraints gbc_currForceLabel = new GridBagConstraints();
		gbc_currForceLabel.insets = new Insets(0, 0, 5, 5);
		gbc_currForceLabel.gridx = 1;
		gbc_currForceLabel.gridy = 4;
		contentPane.add(currForceLabel, gbc_currForceLabel);
		
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
		
		JLabel currAccelLabel = new JLabel("0000");
		GridBagConstraints gbc_currAccelLabel = new GridBagConstraints();
		gbc_currAccelLabel.insets = new Insets(0, 0, 5, 5);
		gbc_currAccelLabel.gridx = 1;
		gbc_currAccelLabel.gridy = 5;
		contentPane.add(currAccelLabel, gbc_currAccelLabel);
		
		JLabel lblVelocity = new JLabel("Velocity:");
		GridBagConstraints gbc_lblVelocity = new GridBagConstraints();
		gbc_lblVelocity.insets = new Insets(0, 0, 5, 5);
		gbc_lblVelocity.gridx = 0;
		gbc_lblVelocity.gridy = 6;
		contentPane.add(lblVelocity, gbc_lblVelocity);
		
		JLabel currVelocityLabel = new JLabel("0000");
		GridBagConstraints gbc_currVelocityLabel = new GridBagConstraints();
		gbc_currVelocityLabel.insets = new Insets(0, 0, 5, 5);
		gbc_currVelocityLabel.gridx = 1;
		gbc_currVelocityLabel.gridy = 6;
		contentPane.add(currVelocityLabel, gbc_currVelocityLabel);
		
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
		
		JLabel currPositionLabel = new JLabel("0000");
		GridBagConstraints gbc_currPositionLabel = new GridBagConstraints();
		gbc_currPositionLabel.insets = new Insets(0, 0, 5, 5);
		gbc_currPositionLabel.gridx = 1;
		gbc_currPositionLabel.gridy = 7;
		contentPane.add(currPositionLabel, gbc_currPositionLabel);
		
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
		
		JLabel crewCountLabel = new JLabel("0");
		GridBagConstraints gbc_crewCountLabel = new GridBagConstraints();
		gbc_crewCountLabel.insets = new Insets(0, 0, 5, 5);
		gbc_crewCountLabel.gridx = 1;
		gbc_crewCountLabel.gridy = 9;
		contentPane.add(crewCountLabel, gbc_crewCountLabel);
		
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
		
		JLabel passCountLabel = new JLabel("0");
		GridBagConstraints gbc_passCountLabel = new GridBagConstraints();
		gbc_passCountLabel.insets = new Insets(0, 0, 5, 5);
		gbc_passCountLabel.gridx = 1;
		gbc_passCountLabel.gridy = 10;
		contentPane.add(passCountLabel, gbc_passCountLabel);
		
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
		
		JLabel leftDoorLabel = new JLabel("Closed");
		GridBagConstraints gbc_leftDoorLabel = new GridBagConstraints();
		gbc_leftDoorLabel.insets = new Insets(0, 0, 5, 5);
		gbc_leftDoorLabel.gridx = 1;
		gbc_leftDoorLabel.gridy = 12;
		contentPane.add(leftDoorLabel, gbc_leftDoorLabel);
		
		JLabel lblRightDoors = new JLabel("Right Doors:");
		GridBagConstraints gbc_lblRightDoors = new GridBagConstraints();
		gbc_lblRightDoors.insets = new Insets(0, 0, 5, 5);
		gbc_lblRightDoors.gridx = 0;
		gbc_lblRightDoors.gridy = 13;
		contentPane.add(lblRightDoors, gbc_lblRightDoors);
		
		JLabel rightDoorLabel = new JLabel("Closed");
		GridBagConstraints gbc_rightDoorLabel = new GridBagConstraints();
		gbc_rightDoorLabel.insets = new Insets(0, 0, 5, 5);
		gbc_rightDoorLabel.gridx = 1;
		gbc_rightDoorLabel.gridy = 13;
		contentPane.add(rightDoorLabel, gbc_rightDoorLabel);
		
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
		
		JLabel lightsLabel = new JLabel("Off");
		GridBagConstraints gbc_lightsLabel = new GridBagConstraints();
		gbc_lightsLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lightsLabel.gridx = 1;
		gbc_lightsLabel.gridy = 14;
		contentPane.add(lightsLabel, gbc_lightsLabel);
		
		txtSetPower = new JTextField();
		txtSetPower.setText("Enter power(kW)");
		GridBagConstraints gbc_txtSetPower = new GridBagConstraints();
		gbc_txtSetPower.insets = new Insets(0, 0, 5, 0);
		gbc_txtSetPower.gridx = 4;
		gbc_txtSetPower.gridy = 14;
		contentPane.add(txtSetPower, gbc_txtSetPower);
		txtSetPower.setColumns(10);
		
		JLabel lblBeacon = new JLabel("Beacon:");
		GridBagConstraints gbc_lblBeacon = new GridBagConstraints();
		gbc_lblBeacon.insets = new Insets(0, 0, 5, 5);
		gbc_lblBeacon.gridx = 0;
		gbc_lblBeacon.gridy = 15;
		contentPane.add(lblBeacon, gbc_lblBeacon);
		
		JLabel beaconLabel = new JLabel("Msg");
		GridBagConstraints gbc_beaconLabel = new GridBagConstraints();
		gbc_beaconLabel.insets = new Insets(0, 0, 5, 5);
		gbc_beaconLabel.gridx = 1;
		gbc_beaconLabel.gridy = 15;
		contentPane.add(beaconLabel, gbc_beaconLabel);
		
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
		
		JLabel sBrakeLabel = new JLabel("[Status]");
		GridBagConstraints gbc_sBrakeLabel = new GridBagConstraints();
		gbc_sBrakeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_sBrakeLabel.gridx = 1;
		gbc_sBrakeLabel.gridy = 16;
		contentPane.add(sBrakeLabel, gbc_sBrakeLabel);
		
		JLabel lblEmergencyBrake = new JLabel("Emergency Brake:");
		GridBagConstraints gbc_lblEmergencyBrake = new GridBagConstraints();
		gbc_lblEmergencyBrake.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmergencyBrake.gridx = 0;
		gbc_lblEmergencyBrake.gridy = 17;
		contentPane.add(lblEmergencyBrake, gbc_lblEmergencyBrake);
		
		JLabel eBrakeLabel = new JLabel("[Status]");
		GridBagConstraints gbc_eBrakeLabel = new GridBagConstraints();
		gbc_eBrakeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_eBrakeLabel.gridx = 1;
		gbc_eBrakeLabel.gridy = 17;
		contentPane.add(eBrakeLabel, gbc_eBrakeLabel);
		
		JLabel lblEngineStatus = new JLabel("Engine Status:");
		GridBagConstraints gbc_lblEngineStatus = new GridBagConstraints();
		gbc_lblEngineStatus.insets = new Insets(0, 0, 5, 5);
		gbc_lblEngineStatus.gridx = 0;
		gbc_lblEngineStatus.gridy = 18;
		contentPane.add(lblEngineStatus, gbc_lblEngineStatus);
		
		JLabel engineStatusLabel = new JLabel("[Status]");
		GridBagConstraints gbc_engineStatusLabel = new GridBagConstraints();
		gbc_engineStatusLabel.insets = new Insets(0, 0, 5, 5);
		gbc_engineStatusLabel.gridx = 1;
		gbc_engineStatusLabel.gridy = 18;
		contentPane.add(engineStatusLabel, gbc_engineStatusLabel);
		
		JLabel lblBrakeStatus = new JLabel("Brake Status:");
		GridBagConstraints gbc_lblBrakeStatus = new GridBagConstraints();
		gbc_lblBrakeStatus.insets = new Insets(0, 0, 5, 5);
		gbc_lblBrakeStatus.gridx = 0;
		gbc_lblBrakeStatus.gridy = 19;
		contentPane.add(lblBrakeStatus, gbc_lblBrakeStatus);
		
		JLabel brakeStatusLabel = new JLabel("[Status]");
		GridBagConstraints gbc_brakeStatusLabel = new GridBagConstraints();
		gbc_brakeStatusLabel.insets = new Insets(0, 0, 5, 5);
		gbc_brakeStatusLabel.gridx = 1;
		gbc_brakeStatusLabel.gridy = 19;
		contentPane.add(brakeStatusLabel, gbc_brakeStatusLabel);
		
		JLabel lblSignalPickupStatus = new JLabel("Signal Pickup Status:");
		GridBagConstraints gbc_lblSignalPickupStatus = new GridBagConstraints();
		gbc_lblSignalPickupStatus.insets = new Insets(0, 0, 5, 5);
		gbc_lblSignalPickupStatus.gridx = 0;
		gbc_lblSignalPickupStatus.gridy = 20;
		contentPane.add(lblSignalPickupStatus, gbc_lblSignalPickupStatus);
		
		JLabel sigPickupLabel = new JLabel("[Status]");
		GridBagConstraints gbc_sigPickupLabel = new GridBagConstraints();
		gbc_sigPickupLabel.insets = new Insets(0, 0, 5, 5);
		gbc_sigPickupLabel.gridx = 1;
		gbc_sigPickupLabel.gridy = 20;
		contentPane.add(sigPickupLabel, gbc_sigPickupLabel);
	}

}
