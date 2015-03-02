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

public class TrainControllerGui {

  private JFrame frame;
  private JTextField enginePowerTextField;
  private JTextField serviceBrakeTextField;
  private TrainController trainController;
  private JTextField textField;
  private JTextField textField_1;
  private JTextField textField_2;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          TrainControllerGui window = new TrainControllerGui();
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
  public TrainControllerGui() {
    initialize();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    frame = new JFrame();
    frame.setBounds(100, 100, 500, 569);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(null);
    
    JLabel lblTargetVelocity = new JLabel("Target Velocity (mph)");
    lblTargetVelocity.setFont(new Font("Dialog", Font.BOLD, 12));
    lblTargetVelocity.setBounds(12, 120, 167, 15);
    frame.getContentPane().add(lblTargetVelocity);
    
    JLabel lblFeedbackVelocity = new JLabel("Current Velocity (mph)");
    lblFeedbackVelocity.setBounds(12, 168, 187, 19);
    frame.getContentPane().add(lblFeedbackVelocity);
    
    JLabel lblTrainController = new JLabel("Train Controller");
    lblTrainController.setFont(new Font("Dialog", Font.BOLD, 16));
    lblTrainController.setBounds(172, 12, 151, 15);
    frame.getContentPane().add(lblTrainController);
    
    JLabel lblEnginePower = new JLabel("Engine Power (kW)");
    lblEnginePower.setBounds(270, 91, 151, 15);
    frame.getContentPane().add(lblEnginePower);
    
    JLabel lblServiceBrake = new JLabel("Service Brake");
    lblServiceBrake.setBounds(270, 120, 114, 15);
    frame.getContentPane().add(lblServiceBrake);
    
    enginePowerTextField = new JTextField();
    enginePowerTextField.setEditable(false);
    enginePowerTextField.setText("0");
    enginePowerTextField.setBounds(416, 89, 70, 19);
    frame.getContentPane().add(enginePowerTextField);
    enginePowerTextField.setColumns(10);
    
    serviceBrakeTextField = new JTextField();
    serviceBrakeTextField.setEditable(false);
    serviceBrakeTextField.setText("disengaged");
    serviceBrakeTextField.setBounds(386, 118, 100, 19);
    frame.getContentPane().add(serviceBrakeTextField);
    serviceBrakeTextField.setColumns(10);
    
    trainController = new TrainController();
    
    JRadioButton rdbtnManual = new JRadioButton("Manual");
    rdbtnManual.setBounds(12, 33, 149, 23);
    frame.getContentPane().add(rdbtnManual);
    
    JRadioButton rdbtnAutomatic = new JRadioButton("Automatic");
    rdbtnAutomatic.setBounds(12, 60, 149, 23);
    frame.getContentPane().add(rdbtnAutomatic);
    
    JSlider slider = new JSlider();
    slider.setBounds(12, 147, 240, 16);
    frame.getContentPane().add(slider);
    
    textField = new JTextField();
    textField.setEditable(false);
    textField.setBounds(182, 118, 70, 19);
    frame.getContentPane().add(textField);
    textField.setColumns(10);
    
    JLabel lblSpeedLimit = new JLabel("Speed Limit (mph)");
    lblSpeedLimit.setBounds(12, 91, 134, 15);
    frame.getContentPane().add(lblSpeedLimit);
    
    textField_1 = new JTextField();
    textField_1.setBounds(182, 89, 70, 19);
    frame.getContentPane().add(textField_1);
    textField_1.setColumns(10);
    
    textField_2 = new JTextField();
    textField_2.setEditable(false);
    textField_2.setBounds(181, 168, 70, 19);
    frame.getContentPane().add(textField_2);
    textField_2.setColumns(10);
    
    JToggleButton tglbtnEmergencyBrake = new JToggleButton("Emergency Brake");
    tglbtnEmergencyBrake.setBounds(270, 162, 167, 25);
    frame.getContentPane().add(tglbtnEmergencyBrake);
  }
}