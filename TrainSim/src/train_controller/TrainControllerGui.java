package train_controller;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JToggleButton;
import javax.swing.JRadioButton;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.JCheckBox;
import javax.swing.ButtonGroup;
import java.awt.Component;
import javax.swing.Box;

public class TrainControllerGui extends JFrame{
  private JTextField enginePowerTextField;
  private JTextField textFieldTargetSpeed;
  private JTextField textFieldSpeedLimit;
  private JTextField textFieldCurrentSpeed;
  private JTextField textFieldDistanceFromStation;
  private JTextField textFieldStationName;
  private JTextField textFieldSafeStoppingDistance;
  private JTextField textFieldAuthority;
  private TrainController trainController = new TrainController();

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    
    
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          TrainControllerGui window = new TrainControllerGui();
          
          window.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the application.
   */
  public TrainControllerGui() {
    initialize();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    this.setBounds(100, 100, 500, 569);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.getContentPane().setLayout(null);
    
    JLabel lblTargetSpeed = new JLabel("Target Speed (mph)");
    lblTargetSpeed.setFont(new Font("Dialog", Font.BOLD, 12));
    lblTargetSpeed.setBounds(12, 135, 167, 15);
    this.getContentPane().add(lblTargetSpeed);
    
    JLabel lblCurrentSpeed = new JLabel("Current Speed (mph)");
    lblCurrentSpeed.setBounds(12, 190, 187, 19);
    this.getContentPane().add(lblCurrentSpeed);
    
    JLabel lblTrainController = new JLabel("Train Controller");
    lblTrainController.setFont(new Font("Dialog", Font.BOLD, 16));
    lblTrainController.setBounds(168, 27, 151, 15);
    this.getContentPane().add(lblTrainController);
    
    JLabel lblEnginePower = new JLabel("Engine Power (kW)");
    lblEnginePower.setBounds(266, 106, 151, 15);
    this.getContentPane().add(lblEnginePower);
    
    enginePowerTextField = new JTextField();
    enginePowerTextField.setEditable(false);
    enginePowerTextField.setText("0");
    enginePowerTextField.setBounds(412, 104, 70, 19);
    this.getContentPane().add(enginePowerTextField);
    enginePowerTextField.setColumns(10);
    
    JRadioButton rdbtnManual = new JRadioButton("Manual");
    rdbtnManual.setBounds(12, 48, 149, 23);
    this.getContentPane().add(rdbtnManual);
    
    JRadioButton rdbtnAutomatic = new JRadioButton("Automatic");
    rdbtnAutomatic.setBounds(12, 75, 149, 23);
    this.getContentPane().add(rdbtnAutomatic);
    
    ButtonGroup modeButtons = new ButtonGroup();
    modeButtons.add(rdbtnManual);
    modeButtons.add(rdbtnAutomatic);
    
    JSlider sliderTargetSpeed = new JSlider();
    sliderTargetSpeed.setBounds(12, 162, 240, 16);
    this.getContentPane().add(sliderTargetSpeed);
    
    textFieldTargetSpeed = new JTextField();
    textFieldTargetSpeed.setEditable(false);
    textFieldTargetSpeed.setBounds(178, 133, 70, 19);
    this.getContentPane().add(textFieldTargetSpeed);
    textFieldTargetSpeed.setColumns(10);
    
    JLabel lblSpeedLimit = new JLabel("Speed Limit (mph)");
    lblSpeedLimit.setBounds(12, 106, 134, 15);
    this.getContentPane().add(lblSpeedLimit);
    
    textFieldSpeedLimit = new JTextField();
    textFieldSpeedLimit.setBounds(178, 104, 70, 19);
    this.getContentPane().add(textFieldSpeedLimit);
    textFieldSpeedLimit.setColumns(10);
    
    textFieldCurrentSpeed = new JTextField();
    textFieldCurrentSpeed.setEditable(false);
    textFieldCurrentSpeed.setBounds(177, 190, 70, 19);
    this.getContentPane().add(textFieldCurrentSpeed);
    textFieldCurrentSpeed.setColumns(10);
    
    JCheckBox chckbxRightDoorsOpen = new JCheckBox("Right doors open");
    chckbxRightDoorsOpen.setBounds(12, 221, 149, 23);
    this.getContentPane().add(chckbxRightDoorsOpen);
    
    JCheckBox chckbxLeftDoorsOpen = new JCheckBox("Left doors open");
    chckbxLeftDoorsOpen.setBounds(12, 248, 149, 23);
    this.getContentPane().add(chckbxLeftDoorsOpen);
    
    JCheckBox chckbxServiceBrake = new JCheckBox("Service Brake");
    chckbxServiceBrake.setBounds(266, 131, 129, 23);
    this.getContentPane().add(chckbxServiceBrake);
    
    JCheckBox chckbxEmergencyBrake = new JCheckBox("Emergency Brake");
    chckbxEmergencyBrake.setBounds(266, 162, 151, 23);
    this.getContentPane().add(chckbxEmergencyBrake);
    
    JCheckBox chckbxLightsOn = new JCheckBox("Lights On");
    chckbxLightsOn.setBounds(266, 221, 129, 23);
    this.getContentPane().add(chckbxLightsOn);
    
    JCheckBox chckbxAirConditioning = new JCheckBox("Air Conditioning");
    chckbxAirConditioning.setBounds(266, 248, 144, 23);
    this.getContentPane().add(chckbxAirConditioning);
    
    JLabel lblBeacon = new JLabel("Beacon");
    lblBeacon.setBounds(12, 328, 70, 15);
    this.getContentPane().add(lblBeacon);
    
    JLabel lblDistanceFromStation = new JLabel("Distance from station (miles)");
    lblDistanceFromStation.setBounds(12, 355, 204, 15);
    this.getContentPane().add(lblDistanceFromStation);
    
    textFieldDistanceFromStation = new JTextField();
    textFieldDistanceFromStation.setEditable(false);
    textFieldDistanceFromStation.setBounds(238, 353, 70, 19);
    this.getContentPane().add(textFieldDistanceFromStation);
    textFieldDistanceFromStation.setColumns(10);
    
    JLabel lblStationName = new JLabel("Station Name");
    lblStationName.setBounds(12, 382, 96, 15);
    this.getContentPane().add(lblStationName);
    
    textFieldStationName = new JTextField();
    textFieldStationName.setEditable(false);
    textFieldStationName.setBounds(186, 382, 122, 19);
    this.getContentPane().add(textFieldStationName);
    textFieldStationName.setColumns(10);
    
    JCheckBox chckbxStopRequired = new JCheckBox("Stop Required");
    chckbxStopRequired.setEnabled(false);
    chckbxStopRequired.setBounds(12, 405, 129, 23);
    this.getContentPane().add(chckbxStopRequired);
    
    JLabel lblSafeStoppingDistance = new JLabel("Safe Stopping Distance (miles)");
    lblSafeStoppingDistance.setBounds(12, 286, 229, 15);
    this.getContentPane().add(lblSafeStoppingDistance);
    
    textFieldSafeStoppingDistance = new JTextField();
    textFieldSafeStoppingDistance.setEditable(false);
    textFieldSafeStoppingDistance.setBounds(238, 284, 70, 19);
    this.getContentPane().add(textFieldSafeStoppingDistance);
    textFieldSafeStoppingDistance.setColumns(10);
    
    JLabel lblAuthority = new JLabel("Authority (miles)");
    lblAuthority.setBounds(266, 192, 129, 15);
    this.getContentPane().add(lblAuthority);
    
    textFieldAuthority = new JTextField();
    textFieldAuthority.setBounds(412, 190, 70, 19);
    this.getContentPane().add(textFieldAuthority);
    textFieldAuthority.setColumns(10);
  }
  
  public void updateDisplayData() {
    
  }
}