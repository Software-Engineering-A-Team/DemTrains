package train_controller;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.Timer;

import java.awt.Font;

import javax.swing.JToggleButton;
import javax.swing.JRadioButton;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.JCheckBox;
import javax.swing.ButtonGroup;

import java.awt.Component;

import javax.swing.Box;

import system_wrapper.SimClock;
import system_wrapper.SystemWrapper;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.AbstractButton;

import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JComboBox;

public class TrainControllerGui extends JFrame {
  private JTextField enginePowerTextField;
  private JTextField textFieldTargetSpeed;
  private JTextField textFieldSpeedLimit;
  private JTextField textFieldCurrentSpeed;
  private JTextField textFieldDistanceFromStation;
  private JTextField textFieldStationName;
  private JTextField textFieldSafeStoppingDistance;
  private JTextField textFieldAuthority;
  private JSlider sliderTargetSpeed;
  private JRadioButton rdbtnManual;
  private JRadioButton rdbtnAutomatic;
  private JCheckBox chckbxServiceBrake;
  private JCheckBox chckbxStopRequired;
  private JCheckBox chckbxAirConditioning;
  private JCheckBox chckbxLeftDoorsOpen;
  private JCheckBox chckbxLightsOn;
  private JCheckBox chckbxEmergencyBrake;
  private JCheckBox chckbxRightDoorsOpen;
  private JComboBox comboBoxSelectedTrain;
  private ArrayList<TrainController> trainControllers;
  private short trainIndex;
  public TrainController trainController;
  private NumberFormat formatter = new DecimalFormat("#0.00");
  
  private boolean standalone;
  

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          ArrayList<TrainController> trainControllers = new ArrayList<TrainController>();
          for (int i = 0; i < 10; i++) {
            trainControllers.add(new TrainController());
          }
          
          
          TrainControllerGui window = new TrainControllerGui(trainControllers, true);
          
          
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
  public TrainControllerGui(ArrayList<TrainController> trainControllers) {
    standalone = false;
    this.trainControllers = trainControllers;
    if (trainControllers.size() == 0) {
      trainController = new TrainController();
    }
    else {
      trainIndex = 0;
      trainController = trainControllers.get(trainIndex); 
    }
    initialize();
    displayTimer.start();
    controllerTimer.start();
  }
  
  public TrainControllerGui(ArrayList<TrainController> trainControllers, boolean standalone) {
    this.standalone = standalone;
    this.trainControllers = trainControllers;
    if (trainControllers.size() == 0) {
      trainController = new TrainController();
    }
    else {
      trainIndex = 0;
      trainController = trainControllers.get(trainIndex); 
    }
    initialize();
    displayTimer.start();
    controllerTimer.start();
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
    
    rdbtnManual = new JRadioButton("Manual");
    rdbtnManual.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if ("disable".equals(e.getActionCommand())) {
          trainController.setManualMode(false);
        }
        else {
          trainController.setManualMode(true);
        }
      }
    });
    rdbtnManual.setSelected(trainController.isManualMode());
    rdbtnManual.setBounds(12, 48, 149, 23);
    this.getContentPane().add(rdbtnManual);
    
    rdbtnAutomatic = new JRadioButton("Automatic");
    rdbtnAutomatic.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if ("disable".equals(e.getActionCommand())) {
          trainController.setManualMode(true);
        }
        else {
          trainController.setManualMode(false);
        }
      }
    });
    rdbtnAutomatic.setSelected(!trainController.isManualMode());
    rdbtnAutomatic.setBounds(12, 75, 149, 23);
    this.getContentPane().add(rdbtnAutomatic);
    
    ButtonGroup modeButtons = new ButtonGroup();
    modeButtons.add(rdbtnManual);
    modeButtons.add(rdbtnAutomatic);
    
    sliderTargetSpeed = new JSlider();
    sliderTargetSpeed.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        
        if (!source.getValueIsAdjusting()) {
          trainController.setTargetSpeed(source.getValue());
        }
      }
    });
    sliderTargetSpeed.setBounds(12, 162, 240, 16);
    sliderTargetSpeed.setValue(0);
    this.getContentPane().add(sliderTargetSpeed);
    
    textFieldTargetSpeed = new JTextField();
    textFieldTargetSpeed.setEditable(false);
    textFieldTargetSpeed.setBounds(178, 133, 70, 19);
    this.getContentPane().add(textFieldTargetSpeed);
    textFieldTargetSpeed.setColumns(10);
    
    JLabel lblSpeedLimit = new JLabel("Speed Limit (mph)");
    lblSpeedLimit.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        trainController.setManualMode(false);
      }
    });
    lblSpeedLimit.setBounds(12, 106, 134, 15);
    this.getContentPane().add(lblSpeedLimit);
    
    textFieldSpeedLimit = new JTextField();
    textFieldSpeedLimit.setEditable(standalone);
    textFieldSpeedLimit.setBounds(178, 104, 70, 19);
    this.getContentPane().add(textFieldSpeedLimit);
    textFieldSpeedLimit.setColumns(10);
    
    textFieldCurrentSpeed = new JTextField();
    textFieldCurrentSpeed.setEditable(standalone);
    if (standalone) {
      textFieldCurrentSpeed.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          System.out.println("current speed = " + textFieldCurrentSpeed.getText());
          trainController.setCurrentSpeed(Double.parseDouble(textFieldCurrentSpeed.getText()));
        }
      });
    }
    textFieldCurrentSpeed.setBounds(177, 190, 70, 19);
    this.getContentPane().add(textFieldCurrentSpeed);
    textFieldCurrentSpeed.setColumns(10);
    
    chckbxRightDoorsOpen = new JCheckBox("Right doors open");
    chckbxRightDoorsOpen.setBounds(12, 221, 149, 23);
    this.getContentPane().add(chckbxRightDoorsOpen);
    
    chckbxLeftDoorsOpen = new JCheckBox("Left doors open");
    chckbxLeftDoorsOpen.setBounds(12, 248, 149, 23);
    this.getContentPane().add(chckbxLeftDoorsOpen);
    
    chckbxServiceBrake = new JCheckBox("Service Brake");
    chckbxServiceBrake.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        AbstractButton button = (AbstractButton) e.getSource();
        if (trainController.isManualMode()) {
          if (button.getModel().isSelected()) {
            trainController.setServiceBrake(true);
          }
          else {
            trainController.setServiceBrake(false);
          }
        }
      }
    });
    chckbxServiceBrake.setBounds(266, 131, 129, 23);
    this.getContentPane().add(chckbxServiceBrake);
    
    chckbxEmergencyBrake = new JCheckBox("Emergency Brake");
    chckbxEmergencyBrake.setBounds(266, 162, 151, 23);
    this.getContentPane().add(chckbxEmergencyBrake);
    
    chckbxLightsOn = new JCheckBox("Lights On");
    chckbxLightsOn.setBounds(266, 221, 129, 23);
    this.getContentPane().add(chckbxLightsOn);
    
    chckbxAirConditioning = new JCheckBox("Air Conditioning");
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
    
    chckbxStopRequired = new JCheckBox("Stop Required");
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
    
    JLabel lblSelectedTrain = new JLabel("Selected Train");
    lblSelectedTrain.setBounds(339, 328, 109, 15);
    getContentPane().add(lblSelectedTrain);
    
    comboBoxSelectedTrain = new JComboBox();
    comboBoxSelectedTrain.setBounds(339, 350, 109, 24);
    getContentPane().add(comboBoxSelectedTrain);
  }
  
  public void updateDisplayData() {
    textFieldTargetSpeed.setText(formatter.format(trainController.getTargetSpeed()));
    
    textFieldSafeStoppingDistance.setText(formatter.format(trainController.getSafeStoppingDistance()));
    enginePowerTextField.setText(formatter.format(trainController.getPower()));
    textFieldSpeedLimit.setText(formatter.format(trainController.getSpeedLimit()));
    
    
    if (!standalone) {
      textFieldCurrentSpeed.setText(formatter.format(trainController.getCurrentSpeed()));
    }
    
    if (trainController.isManualMode()) {
      trainController.setManualMode(true);
      sliderTargetSpeed.setEnabled(true);
      rdbtnManual.setSelected(true);
      rdbtnAutomatic.setSelected(false);
    }
    else {
      trainController.setManualMode(false);
      sliderTargetSpeed.setEnabled(false);
      rdbtnManual.setSelected(false);
      rdbtnAutomatic.setSelected(true);
      chckbxServiceBrake.setSelected(trainController.isServiceBrakeOn());
      
      this.chckbxRightDoorsOpen.setSelected(trainController.isRightDoorOpen());
      this.chckbxLeftDoorsOpen.setSelected(trainController.isLeftDoorOpen());
      this.chckbxLightsOn.setSelected(trainController.isLightOn());
      this.textFieldCurrentSpeed.setText(formatter.format(trainController.getCurrentSpeed()));
    }
  }
  
  private Timer displayTimer = new Timer(100, new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      updateDisplayData();
      repaint();
    }
  });
  
  private Timer controllerTimer = new Timer(10, new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      SimClock.tick();
      
      for (int i = 0; i < trainControllers.size(); i++) {

        trainControllers.get(i).calcPower();
      }
    }
  });
}