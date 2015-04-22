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
  ArrayList<TestTrainModel> testTrainModels;
  private int trainIndex;
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
          trainController.setCurrentSpeed(Double.parseDouble(textFieldCurrentSpeed.getText()));
        }
      });
    }
    textFieldCurrentSpeed.setBounds(177, 190, 70, 19);
    this.getContentPane().add(textFieldCurrentSpeed);
    textFieldCurrentSpeed.setColumns(10);
    
    chckbxRightDoorsOpen = new JCheckBox("Right doors open");
    chckbxRightDoorsOpen.setBounds(12, 263, 149, 23);
    this.getContentPane().add(chckbxRightDoorsOpen);
    
    chckbxLeftDoorsOpen = new JCheckBox("Left doors open");
    chckbxLeftDoorsOpen.setBounds(12, 290, 149, 23);
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
    chckbxEmergencyBrake.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        AbstractButton button = (AbstractButton) e.getSource();
        if (button.getModel().isSelected()) {
          System.out.println("Ebrake engaged");
          trainController.setEmergencyBrake(true);
        }
        else {
          System.out.println("Ebrake disengaged");
          trainController.setEmergencyBrake(false);
        }
      }
    });
    chckbxEmergencyBrake.setBounds(266, 162, 151, 23);
    this.getContentPane().add(chckbxEmergencyBrake);
    
    chckbxLightsOn = new JCheckBox("Lights On");
    chckbxLightsOn.setBounds(266, 263, 129, 23);
    this.getContentPane().add(chckbxLightsOn);
    
    chckbxAirConditioning = new JCheckBox("Air Conditioning");
    chckbxAirConditioning.setBounds(266, 290, 144, 23);
    this.getContentPane().add(chckbxAirConditioning);
    
    JLabel lblBeacon = new JLabel("Beacon");
    lblBeacon.setBounds(12, 416, 70, 15);
    this.getContentPane().add(lblBeacon);
    
    JLabel lblDistanceFromStation = new JLabel("Distance from station (miles)");
    lblDistanceFromStation.setBounds(12, 443, 204, 15);
    this.getContentPane().add(lblDistanceFromStation);
    
    textFieldDistanceFromStation = new JTextField();
    textFieldDistanceFromStation.setEditable(false);
    textFieldDistanceFromStation.setBounds(238, 441, 70, 19);
    this.getContentPane().add(textFieldDistanceFromStation);
    textFieldDistanceFromStation.setColumns(10);
    
    JLabel lblStationName = new JLabel("Station Name");
    lblStationName.setBounds(12, 470, 96, 15);
    this.getContentPane().add(lblStationName);
    
    textFieldStationName = new JTextField();
    textFieldStationName.setEditable(false);
    textFieldStationName.setBounds(186, 470, 122, 19);
    this.getContentPane().add(textFieldStationName);
    textFieldStationName.setColumns(10);
    
    chckbxStopRequired = new JCheckBox("Stop Required");
    chckbxStopRequired.setEnabled(false);
    chckbxStopRequired.setBounds(12, 493, 129, 23);
    this.getContentPane().add(chckbxStopRequired);
    
    JLabel lblSafeStoppingDistance = new JLabel("Safe Stopping Distance (miles)");
    lblSafeStoppingDistance.setBounds(12, 328, 229, 15);
    this.getContentPane().add(lblSafeStoppingDistance);
    
    textFieldSafeStoppingDistance = new JTextField();
    textFieldSafeStoppingDistance.setEditable(false);
    textFieldSafeStoppingDistance.setBounds(238, 326, 70, 19);
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
    
    JLabel lblCommandedSpeedmph = new JLabel("Commanded Speed (mph)");
    lblCommandedSpeedmph.setBounds(225, 221, 192, 15);
    getContentPane().add(lblCommandedSpeedmph);
    
    textFieldCmdSpeed = new JTextField();
    textFieldCmdSpeed.setBounds(412, 219, 70, 19);
    textFieldCmdSpeed.setEditable(standalone);
    textFieldCmdSpeed.setText(formatter.format(trainController.getSpeedAuthCmd().suggestedSpeedMph));
    if (standalone) {
      textFieldCmdSpeed.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          SpeedAuthCmd cmd = new SpeedAuthCmd(trainController.getSpeedAuthCmd().suggestedSpeedMph, trainController.getSpeedAuthCmd().suggestedAuthMiles);
          cmd.suggestedSpeedMph = Double.parseDouble(textFieldCmdSpeed.getText());
          trainController.setSpeedAuthCmd(cmd);
        }
      });
    }
    getContentPane().add(textFieldCmdSpeed);
    textFieldCmdSpeed.setColumns(10);
  }
  
  public void updateDisplayData() {    
    textFieldTargetSpeed.setText(formatter.format(trainController.getTargetSpeed()));
    
    textFieldSafeStoppingDistance.setText(formatter.format(trainController.getSafeStoppingDistance()));
    enginePowerTextField.setText(formatter.format(trainController.getPower()));
    textFieldSpeedLimit.setText(formatter.format(trainController.getSpeedLimit()));
    textFieldAuthority.setText(formatter.format(trainController.getAuthority()));
    textFieldCurrentSpeed.setText(formatter.format(trainController.getCurrentSpeed()));
    
    
    
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
    
    if (!standalone)
    {
      this.textFieldCmdSpeed.setText(formatter.format(trainController.getSpeedAuthCmd().suggestedSpeedMph));
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