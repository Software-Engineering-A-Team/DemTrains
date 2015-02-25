import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;

public class TrainControllerGui {

  private JFrame frame;
  private JTextField targetVelocityTextField;
  private JTextField feedbackVelocityTextField;
  private JTextField enginePowerTextField;
  private JTextField serviceBrakeTextField;
  private TrainController trainController;

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
    frame.setBounds(100, 100, 450, 300);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(null);
    
    targetVelocityTextField = new JTextField();
    targetVelocityTextField.setText("0");
    targetVelocityTextField.setBounds(12, 99, 100, 19);
    frame.getContentPane().add(targetVelocityTextField);
    targetVelocityTextField.setColumns(10);
    
    JLabel lblTargetVelocity = new JLabel("Target Velocity (mph)");
    lblTargetVelocity.setFont(new Font("Dialog", Font.BOLD, 12));
    lblTargetVelocity.setBounds(12, 78, 167, 15);
    frame.getContentPane().add(lblTargetVelocity);
    
    JLabel lblFeedbackVelocity = new JLabel("Feedback Velocity (mph)");
    lblFeedbackVelocity.setBounds(12, 133, 187, 19);
    frame.getContentPane().add(lblFeedbackVelocity);
    
    feedbackVelocityTextField = new JTextField();
    feedbackVelocityTextField.setText("0");
    feedbackVelocityTextField.setBounds(12, 153, 100, 19);
    frame.getContentPane().add(feedbackVelocityTextField);
    feedbackVelocityTextField.setColumns(10);
    
    JLabel lblTrainController = new JLabel("Train Controller");
    lblTrainController.setFont(new Font("Dialog", Font.BOLD, 16));
    lblTrainController.setBounds(153, 12, 151, 15);
    frame.getContentPane().add(lblTrainController);
    
    JLabel lblEnginePower = new JLabel("Engine Power (kW)");
    lblEnginePower.setBounds(269, 78, 151, 15);
    frame.getContentPane().add(lblEnginePower);
    
    JLabel lblServiceBrake = new JLabel("Service Brake");
    lblServiceBrake.setBounds(269, 135, 114, 15);
    frame.getContentPane().add(lblServiceBrake);
    
    enginePowerTextField = new JTextField();
    enginePowerTextField.setEditable(false);
    enginePowerTextField.setText("0");
    enginePowerTextField.setBounds(269, 99, 100, 19);
    frame.getContentPane().add(enginePowerTextField);
    enginePowerTextField.setColumns(10);
    
    serviceBrakeTextField = new JTextField();
    serviceBrakeTextField.setEditable(false);
    serviceBrakeTextField.setText("disengaged");
    serviceBrakeTextField.setBounds(269, 153, 100, 19);
    frame.getContentPane().add(serviceBrakeTextField);
    serviceBrakeTextField.setColumns(10);
    
    JLabel lblInputs = new JLabel("Inputs");
    lblInputs.setFont(new Font("Dialog", Font.BOLD, 14));
    lblInputs.setBounds(12, 51, 70, 15);
    frame.getContentPane().add(lblInputs);
    
    JLabel lblOutputs = new JLabel("Outputs");
    lblOutputs.setFont(new Font("Dialog", Font.BOLD, 14));
    lblOutputs.setBounds(269, 51, 70, 15);
    frame.getContentPane().add(lblOutputs);
    
    trainController = new TrainController();
    
    JButton btnUpdateOutputs = new JButton("Update Outputs");
    btnUpdateOutputs.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        trainController.SetTargetVelocityEng(Double.parseDouble(targetVelocityTextField.getText()));
        trainController.SetCurrentVelocityEng(Double.parseDouble(feedbackVelocityTextField.getText()));
        
        //enginePowerTextField.setText(Double.toString(trainController.CalcEnginePowerKw()));
        enginePowerTextField.setText(String.format("%.3f", trainController.CalcEnginePowerKw()));
        
        if (trainController.CheckBrakeState())
        {
          serviceBrakeTextField.setText("engaged");
        }
        else
        {
          serviceBrakeTextField.setText("disengaged");
        }
      }
    });
    btnUpdateOutputs.setBounds(12, 206, 148, 25);
    frame.getContentPane().add(btnUpdateOutputs);
  }
}
