package train_controller;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

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

import system_wrapper.BeaconMessage;
import system_wrapper.SimClock;
import system_wrapper.SpeedAuthCmd;

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
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;

// Gui class for interfacing with the TrainController
public class TrainControllerGui extends JFrame {
  private JTextField enginePowerTextField;
  private JTextField textFieldTargetSpeed;
  private JTextField textFieldSpeedLimit;
  private JTextField textFieldCurrentSpeed;
  private JTextField textFieldDistanceFromStation;
  private JTextField textFieldStationName;
  private JTextField textFieldSafeStoppingDistance;
  private JTextField textFieldAuthority;
  private JTextField textFieldCmdSpeed;
  private JRadioButton rdbtnManual;
  private JRadioButton rdbtnAutomatic;
  private JCheckBox chckbxServiceBrake;
  private JCheckBox chckbxStopRequired;
  private JCheckBox chckbxAirConditioning;
  private JCheckBox chckbxLeftDoorsOpen;
  private JCheckBox chckbxLightsOn;
  private JCheckBox chckbxEmergencyBrake;
  private JCheckBox chckbxRightDoorsOpen;
  private JCheckBox chckbxVitalError;
  private JComboBox comboBoxSelectedTrain;
  private ArrayList<TrainController> trainControllers;
  ArrayList<TestTrainModel> testTrainModels;
  private int trainIndex;
  public TrainController trainController;
  private NumberFormat formatter = new DecimalFormat("#0.00");
  
  private boolean standalone;   // Determines if GUI will be run by itself or with the rest of the subsystems
  

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          ArrayList<TrainController> trainControllers = new ArrayList<TrainController>();
          ArrayList<TestTrainModel> testTrainModels = new ArrayList<TestTrainModel>();
          
          for (int i = 0; i < 10; i++) {
            TrainController trainController = new TrainController(i);
            TestTrainModel trainModel = new TestTrainModel(trainController);
            
            trainControllers.add(trainController);
            testTrainModels.add(trainModel);
          }
          
          
          TrainControllerGui window = new TrainControllerGui(trainControllers, testTrainModels);
          
          
          window.setVisible(true);
          
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the application.
   * @wbp.parser.constructor
   */
  public TrainControllerGui() {
    standalone = false;
    trainControllers = new ArrayList<TrainController>();
    trainControllers.add(new TrainController());
    trainIndex = 0;
    trainController = trainControllers.get(trainIndex);
    initialize();
    this.updateDisplayData();
    displayTimer.start();
  }
  
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
    this.updateDisplayData();
    displayTimer.start();
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
    this.updateDisplayData();
    displayTimer.start();
    
    if (standalone) {
      controllerTimer.start();
    }
  }
  
  public TrainControllerGui(ArrayList<TrainController> trainControllers, ArrayList<TestTrainModel> testTrainModels) {
    this.standalone = true;
    this.trainControllers = trainControllers;
    this.testTrainModels = testTrainModels;
    
    if (trainControllers.size() == 0) {
      trainController = new TrainController();
    }
    else {
      trainIndex = 0;
      trainController = trainControllers.get(trainIndex); 
    }
    initialize();
    this.updateDisplayData();
    displayTimer.start();
    
    testModelTimer.start();
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
    lblCurrentSpeed.setBounds(13, 162, 187, 19);
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
    
    textFieldTargetSpeed = new JTextField();
    textFieldTargetSpeed.setEditable(false);
    textFieldTargetSpeed.setBounds(178, 133, 70, 19);
    textFieldTargetSpeed.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        trainController.setTargetSpeed(Double.parseDouble(textFieldTargetSpeed.getText()));
        textFieldTargetSpeed.setText(formatter.format(trainController.getSpeedAuthCmd().suggestedSpeedMph));
        requestFocus();
      }
    });
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
    textFieldSpeedLimit.setText(formatter.format(trainController.getSpeedLimit()));
    if (standalone) {
      textFieldSpeedLimit.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          trainController.setSpeedLimit(Double.parseDouble(textFieldSpeedLimit.getText()));
          textFieldSpeedLimit.setText(formatter.format(trainController.getSpeedLimit()));
          requestFocus();
        }
      });
    }
    textFieldSpeedLimit.setBounds(178, 104, 70, 19);
    this.getContentPane().add(textFieldSpeedLimit);
    textFieldSpeedLimit.setColumns(10);
    
    textFieldCurrentSpeed = new JTextField();
    textFieldCurrentSpeed.setEditable(false);
    textFieldCurrentSpeed.setBounds(178, 162, 70, 19);
    this.getContentPane().add(textFieldCurrentSpeed);
    textFieldCurrentSpeed.setColumns(10);
    
    chckbxRightDoorsOpen = new JCheckBox("Right doors open");
    chckbxRightDoorsOpen.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        AbstractButton button = (AbstractButton) e.getSource();
        trainController.setRightDoor(button.getModel().isSelected());
      }
    });
    chckbxRightDoorsOpen.setBounds(12, 263, 149, 23);
    this.getContentPane().add(chckbxRightDoorsOpen);
    
    chckbxLeftDoorsOpen = new JCheckBox("Left doors open");
    chckbxLeftDoorsOpen.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        AbstractButton button = (AbstractButton) e.getSource();
        trainController.setLeftDoor(button.getModel().isSelected());
      }
    });
    chckbxLeftDoorsOpen.setBounds(12, 290, 149, 23);
    this.getContentPane().add(chckbxLeftDoorsOpen);
    
    chckbxServiceBrake = new JCheckBox("Service Brake");
    chckbxServiceBrake.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        AbstractButton button = (AbstractButton) e.getSource();
        if (trainController.isManualMode()) {
          trainController.setServiceBrake(button.getModel().isSelected());
        }
      }
    });
    chckbxServiceBrake.setBounds(266, 131, 129, 23);
    this.getContentPane().add(chckbxServiceBrake);
    
    chckbxEmergencyBrake = new JCheckBox("Emergency Brake");
    chckbxEmergencyBrake.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        AbstractButton button = (AbstractButton) e.getSource();
        trainController.setEmergencyBrake(button.getModel().isSelected());
      }
    });
    chckbxEmergencyBrake.setBounds(266, 162, 151, 23);
    this.getContentPane().add(chckbxEmergencyBrake);
    
    chckbxLightsOn = new JCheckBox("Lights On");
    chckbxLightsOn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        AbstractButton button = (AbstractButton) e.getSource();
        trainController.setLights(button.getModel().isSelected());
      }
    });
    chckbxLightsOn.setBounds(266, 263, 129, 23);
    this.getContentPane().add(chckbxLightsOn);
    
    chckbxAirConditioning = new JCheckBox("Air Conditioning");
    chckbxAirConditioning.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        AbstractButton button = (AbstractButton) e.getSource();
        trainController.setAirConditioning(button.getModel().isSelected());
      }
    });
    chckbxAirConditioning.setBounds(266, 290, 144, 23);
    this.getContentPane().add(chckbxAirConditioning);
    
    JLabel lblBeacon = new JLabel("Beacon");
    lblBeacon.setBounds(12, 416, 70, 15);
    this.getContentPane().add(lblBeacon);
    
    JLabel lblDistanceFromStation = new JLabel("Distance from station (miles)");
    lblDistanceFromStation.setBounds(12, 443, 204, 15);
    this.getContentPane().add(lblDistanceFromStation);
    
    textFieldDistanceFromStation = new JTextField();
    textFieldDistanceFromStation.setEditable(standalone);
    if (standalone) {
      textFieldDistanceFromStation.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          BeaconMessage beaconMessage = trainController.getBeaconMessage();
          
          beaconMessage.trainID = trainController.getId();
          beaconMessage.distanceFromStation = Double.parseDouble(textFieldDistanceFromStation.getText());
          
          trainController.setBeaconMessage(beaconMessage);
          
          //textFieldDistanceFromStation.setText(formatter.format(trainController.getBeaconMessage()));
          requestFocus();
        }
      });
    }
    textFieldDistanceFromStation.setBounds(238, 441, 70, 19);
    this.getContentPane().add(textFieldDistanceFromStation);
    textFieldDistanceFromStation.setColumns(10);
    
    JLabel lblStationName = new JLabel("Station Name");
    lblStationName.setBounds(12, 470, 96, 15);
    this.getContentPane().add(lblStationName);
    
    textFieldStationName = new JTextField();
    textFieldStationName.setEditable(standalone);
    textFieldStationName.setBounds(186, 470, 122, 19);
    if (standalone) {
      textFieldStationName.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          trainController.getBeaconMessage().stationName = new String(textFieldStationName.getText());
          
          //textFieldDistanceFromStation.setText(formatter.format(trainController.getBeaconMessage()));
          requestFocus();
        }
      });
    }
    this.getContentPane().add(textFieldStationName);
    textFieldStationName.setColumns(10);
    
    chckbxStopRequired = new JCheckBox("Stop Required");
    chckbxStopRequired.setEnabled(true);
    chckbxStopRequired.setBounds(12, 493, 129, 23);
    chckbxStopRequired.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        AbstractButton button = (AbstractButton) e.getSource();
        trainController.setStopRequired(button.getModel().isSelected());
      }
    });
    this.getContentPane().add(chckbxStopRequired);
    
    JLabel lblSafeStoppingDistance = new JLabel("Safe Stopping Distance (miles)");
    lblSafeStoppingDistance.setBounds(12, 351, 229, 15);
    this.getContentPane().add(lblSafeStoppingDistance);
    
    textFieldSafeStoppingDistance = new JTextField();
    textFieldSafeStoppingDistance.setEditable(standalone);
    textFieldSafeStoppingDistance.setBounds(238, 349, 70, 19);
    if (standalone) {
      textFieldSafeStoppingDistance.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          trainController.setSafeStoppingDistance((Double.parseDouble(textFieldSafeStoppingDistance.getText())));
          textFieldSafeStoppingDistance.setText(formatter.format(trainController.getSafeStoppingDistance()));
          requestFocus();
        }
      });
    }
    this.getContentPane().add(textFieldSafeStoppingDistance);
    textFieldSafeStoppingDistance.setColumns(10);
    
    JLabel lblAuthority = new JLabel("Authority (miles)");
    lblAuthority.setBounds(266, 208, 129, 15);
    this.getContentPane().add(lblAuthority);
    
    textFieldAuthority = new JTextField();
    textFieldAuthority.setBounds(412, 206, 70, 19);
    textFieldAuthority.setEditable(standalone);
    textFieldAuthority.setText(formatter.format(trainController.getAuthority()));
    if (standalone) {
      textFieldAuthority.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          SpeedAuthCmd cmd = new SpeedAuthCmd(trainController.getSpeedAuthCmd().suggestedSpeedMph, trainController.getSpeedAuthCmd().suggestedAuthMiles);
          cmd.suggestedAuthMiles = Double.parseDouble(textFieldAuthority.getText());
          System.out.println("Setting auth " + cmd.suggestedAuthMiles);
          trainController.setSpeedAuthCmd(cmd);
          textFieldAuthority.setText(formatter.format(trainController.getAuthority()));
          requestFocus();
        }
      });
    }
    this.getContentPane().add(textFieldAuthority);
    textFieldAuthority.setColumns(10);
    
    JLabel lblSelectedTrain = new JLabel("Selected Train");
    lblSelectedTrain.setBounds(373, 416, 109, 15);
    getContentPane().add(lblSelectedTrain);
    
    comboBoxSelectedTrain = new JComboBox<Integer>();
    comboBoxSelectedTrain.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
          if (trainControllers != null && trainControllers.size() > 0) {
            trainIndex = comboBoxSelectedTrain.getSelectedIndex();
            
            //System.out.println(trainIndex);
            
            if (trainIndex >= 0 && trainIndex < trainControllers.size()) {
              trainController = trainControllers.get(trainIndex);
            }
          }
        }
      }
    });
    PopupMenuListener selectedTrainPopupListener = new PopupMenuListener() {
      @Override
      public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        comboBoxSelectedTrain.removeAllItems();

        for (TrainController t : trainControllers) {
          comboBoxSelectedTrain.addItem(t.getId());
        }
      }

      @Override
      public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {}

      @Override
      public void popupMenuCanceled(PopupMenuEvent e) {}
    };
    comboBoxSelectedTrain.addPopupMenuListener(selectedTrainPopupListener);
    comboBoxSelectedTrain.setBounds(373, 438, 109, 24);
    getContentPane().add(comboBoxSelectedTrain);
    
    JLabel lblCommandedSpeedmph = new JLabel("Command Speed (mph)");
    lblCommandedSpeedmph.setBounds(12, 206, 167, 15);
    getContentPane().add(lblCommandedSpeedmph);
    
    textFieldCmdSpeed = new JTextField();
    textFieldCmdSpeed.setBounds(178, 206, 70, 19);
    textFieldCmdSpeed.setEditable(standalone);
    textFieldCmdSpeed.setText(formatter.format(trainController.getSpeedAuthCmd().suggestedSpeedMph));
    if (standalone) {
      textFieldCmdSpeed.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          SpeedAuthCmd cmd = new SpeedAuthCmd(trainController.getSpeedAuthCmd().suggestedSpeedMph, trainController.getSpeedAuthCmd().suggestedAuthMiles);
          cmd.suggestedSpeedMph = Double.parseDouble(textFieldCmdSpeed.getText());
          trainController.setSpeedAuthCmd(cmd);
          textFieldCmdSpeed.setText(formatter.format(trainController.getSpeedAuthCmd().suggestedSpeedMph));
          requestFocus();
        }
      });
    }
    getContentPane().add(textFieldCmdSpeed);
    textFieldCmdSpeed.setColumns(10);
    
    chckbxVitalError = new JCheckBox("Vital Error");
    chckbxVitalError.setBounds(373, 470, 117, 23);
    chckbxVitalError.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        AbstractButton button = (AbstractButton) e.getSource();
        if (button.getModel().isSelected()) {
          trainController.setVitalError(true);
        }
        else {
          trainController.setVitalError(false);
        }
      }
    });
    getContentPane().add(chckbxVitalError);
  }
  
  public void updateDisplayData() {    
    if (!textFieldTargetSpeed.isFocusOwner()) {
      textFieldTargetSpeed.setText(formatter.format(trainController.getTargetSpeed()));
    }
    
    enginePowerTextField.setText(formatter.format(trainController.getPower()));
    
    if (!(standalone && textFieldSpeedLimit.isFocusOwner())) {
      textFieldSpeedLimit.setText(formatter.format(trainController.getSpeedLimit()));
    }
    
    if (!(standalone && textFieldAuthority.isFocusOwner())) {
      textFieldAuthority.setText(formatter.format(trainController.getAuthority()));
    }
    
    if (!(standalone && textFieldCmdSpeed.isFocusOwner())) {
      textFieldCmdSpeed.setText(formatter.format(trainController.getSpeedAuthCmd().suggestedSpeedMph));
    }
    
    if (!(standalone && textFieldDistanceFromStation.isFocusOwner())) {
      textFieldDistanceFromStation.setText(formatter.format(trainController.getBeaconMessage().distanceFromStation));
    }

    if (!(standalone && textFieldStationName.isFocusOwner())) {
      if (trainController.isStopRequired()) {
        this.textFieldStationName.setText(trainController.getBeaconMessage().stationName);
      }
      else {
        this.textFieldStationName.setText("");
      }
    }

    this.chckbxStopRequired.setSelected(trainController.isStopRequired());
    
    textFieldCurrentSpeed.setText(formatter.format(trainController.getCurrentSpeed()));
    
    if (trainController.isEmergencyBrakeOn() != chckbxEmergencyBrake.isSelected()) {
      chckbxEmergencyBrake.setSelected(trainController.isEmergencyBrakeOn());
    }
    
    if (trainController.isServiceBrakeOn() != chckbxServiceBrake.isSelected()) {
      chckbxServiceBrake.setSelected(trainController.isServiceBrakeOn());
    }
    
    if (trainController.vitalErrorOccurred() != chckbxVitalError.isSelected()) {
      chckbxVitalError.setSelected(trainController.vitalErrorOccurred());
    }
    
    this.chckbxAirConditioning.setSelected(trainController.isAirConditioningOn());
    this.chckbxLeftDoorsOpen.setSelected(trainController.isLeftDoorOpen());
    this.chckbxRightDoorsOpen.setSelected(trainController.isRightDoorOpen());
    this.chckbxLightsOn.setSelected(trainController.isLightOn());
  
    textFieldTargetSpeed.setEditable(trainController.isManualMode());
    rdbtnManual.setSelected(trainController.isManualMode());
    rdbtnAutomatic.setSelected(!trainController.isManualMode());
    
    if (!(standalone && textFieldSafeStoppingDistance.isFocusOwner())) {
      textFieldSafeStoppingDistance.setText(formatter.format(trainController.getSafeStoppingDistance()));
    }
  }
  
  private Timer displayTimer = new Timer(100, new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      updateDisplayData();
      repaint();
    }
  });
  
  private Timer controllerTimer = new Timer(SimClock.getDeltaMs(), new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      SimClock.tick();
      
      for (int i = 0; i < trainControllers.size(); i++) {
        trainControllers.get(i).calcPower();
      }
    }
  });
  

  
  private Timer testModelTimer = new Timer(SimClock.getDeltaMs(), new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      SimClock.tick();
      
      for (int i = 0; i < testTrainModels.size(); i++) {
        testTrainModels.get(i).update();
      }
    }
  });
}