package TrainController;

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

public class TrainControllerGui extends JFrame{
  private JTextField enginePowerTextField;
  private JTextField textField;
  private JTextField textField_1;
  private JTextField textField_2;
  private JTextField textField_3;
  private JTextField textField_4;
  private JTextField textField_5;
  private JTextField textField_6;
  private TrainController trainController;

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
    
    JLabel lblTargetVelocity = new JLabel("Target Velocity (mph)");
    lblTargetVelocity.setFont(new Font("Dialog", Font.BOLD, 12));
    lblTargetVelocity.setBounds(12, 135, 167, 15);
    this.getContentPane().add(lblTargetVelocity);
    
    JLabel lblFeedbackVelocity = new JLabel("Current Velocity (mph)");
    lblFeedbackVelocity.setBounds(12, 190, 187, 19);
    this.getContentPane().add(lblFeedbackVelocity);
    
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
    
    JSlider slider = new JSlider();
    slider.setBounds(12, 162, 240, 16);
    this.getContentPane().add(slider);
    
    textField = new JTextField();
    textField.setEditable(false);
    textField.setBounds(178, 133, 70, 19);
    this.getContentPane().add(textField);
    textField.setColumns(10);
    
    JLabel lblSpeedLimit = new JLabel("Speed Limit (mph)");
    lblSpeedLimit.setBounds(12, 106, 134, 15);
    this.getContentPane().add(lblSpeedLimit);
    
    textField_1 = new JTextField();
    textField_1.setBounds(178, 104, 70, 19);
    this.getContentPane().add(textField_1);
    textField_1.setColumns(10);
    
    textField_2 = new JTextField();
    textField_2.setEditable(false);
    textField_2.setBounds(177, 190, 70, 19);
    this.getContentPane().add(textField_2);
    textField_2.setColumns(10);
    
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
    lblBeacon.setBounds(12, 315, 70, 15);
    this.getContentPane().add(lblBeacon);
    
    JLabel lblDistanceFromStation = new JLabel("Distance from station");
    lblDistanceFromStation.setBounds(12, 342, 167, 15);
    this.getContentPane().add(lblDistanceFromStation);
    
    textField_3 = new JTextField();
    textField_3.setEditable(false);
    textField_3.setBounds(178, 340, 70, 19);
    this.getContentPane().add(textField_3);
    textField_3.setColumns(10);
    
    JLabel lblStationName = new JLabel("Station Name");
    lblStationName.setBounds(12, 369, 96, 15);
    this.getContentPane().add(lblStationName);
    
    textField_4 = new JTextField();
    textField_4.setEditable(false);
    textField_4.setBounds(126, 367, 122, 19);
    this.getContentPane().add(textField_4);
    textField_4.setColumns(10);
    
    JCheckBox chckbxStopRequired = new JCheckBox("Stop Required");
    chckbxStopRequired.setEnabled(false);
    chckbxStopRequired.setBounds(12, 392, 129, 23);
    this.getContentPane().add(chckbxStopRequired);
    
    JLabel lblSafeStoppingDistance = new JLabel("Safe Stopping Distance (miles)");
    lblSafeStoppingDistance.setBounds(12, 286, 229, 15);
    this.getContentPane().add(lblSafeStoppingDistance);
    
    textField_5 = new JTextField();
    textField_5.setEditable(false);
    textField_5.setBounds(238, 284, 70, 19);
    this.getContentPane().add(textField_5);
    textField_5.setColumns(10);
    
    JLabel lblAuthoritymiles = new JLabel("Authority (miles)");
    lblAuthoritymiles.setBounds(266, 192, 129, 15);
    this.getContentPane().add(lblAuthoritymiles);
    
    textField_6 = new JTextField();
    textField_6.setBounds(412, 190, 70, 19);
    this.getContentPane().add(textField_6);
    textField_6.setColumns(10);
  }
}