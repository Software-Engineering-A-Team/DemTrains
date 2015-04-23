package train_controller;

import system_wrapper.SpeedAuthCmd;
import system_wrapper.BeaconMessage;

// Class whose main function is to control the engine power of a TrainModel.
// It uses two vital controllers which control the power and braking given
// inputs from the TrainModel and the driver. If the vital controllers return
// different results, a vital error has occurred.
public class TrainController {
  private final VitalTrainControl vitalPrimary = new VitalTrainControlPrimary();
  private final VitalTrainControl vitalSecondary = new VitalTrainControlPrimary();
  
  // Non-vital variables
  private boolean airConditioningOn = false;
  private boolean leftDoorOpen = false;
  private boolean rightDoorOpen = false;
  private boolean lightsOn = false;
  private int id;
  
  // Vital variables. Copies appear in each vital controller.
  private boolean manualMode = false;
  private SpeedAuthCmd speedAuthCmd = new SpeedAuthCmd(0, 0);
  private double currentSpeedMph = 0;
  private double targetSpeedMph = 0;
  private boolean emergencyBrake = false;
  private boolean serviceBrake = false;
  private double speedLimitMph = 60;
  private double powerKw = 0;
  private double safeStoppingDistanceMi = 10;
  private BeaconMessage beaconMessage = new BeaconMessage();
  private boolean stopRequired = false;

  // If vital controllers give different results, there has been an error.
  private boolean vitalError = false;
  
  public TrainController() {
  }
  
  public TrainController(int id) {
    this.id = id;
  }
  
  // Current speed reported by TrainModel
  public void setCurrentSpeed(double currentSpeedMph) {
    this.currentSpeedMph = currentSpeedMph;
  }
  
  // Target speed set by driver, only works in manual mode
  public void setTargetSpeed(double targetSpeedMph) {
    if (manualMode) {
      this.targetSpeedMph = targetSpeedMph;
    }
  }
  
  // In automatic mode, the controller must keep the train below
  // this limit
  public void setSpeedLimit(double speedLimitMph) {
    this.speedLimitMph = speedLimitMph;
  }
  
  // The service brake can only be pulled by the driver while in manual mode.
  public void setServiceBrake(boolean serviceBrake) {
    if (manualMode) {
      this.serviceBrake = serviceBrake;
      vitalPrimary.serviceBrake = serviceBrake;
      vitalSecondary.serviceBrake = serviceBrake;
    }
  }
  
  // The emergency brake can be pulled by anyone at any time.
  public void setEmergencyBrake(boolean emergencyBrake) {
    this.emergencyBrake = emergencyBrake;
    vitalPrimary.emergencyBrake = emergencyBrake;
    vitalSecondary.emergencyBrake = emergencyBrake;
  }
  
  // The speed command will only be followed if in automatic mode.
  public void setSpeedAuthCmd(SpeedAuthCmd cmd) {
    speedAuthCmd.suggestedAuthMiles = cmd.suggestedAuthMiles;
    speedAuthCmd.suggestedSpeedMph = cmd.suggestedSpeedMph;
    
    if (!manualMode) {
      targetSpeedMph = cmd.suggestedSpeedMph;
    }
  }
  
  public void setSafeStoppingDistance(double safeStoppingDistanceMi) {
    this.safeStoppingDistanceMi = safeStoppingDistanceMi;
  }
  
  // Used by the driver to switch between manual and automatic control.
  public void setManualMode(boolean manualMode) {
    this.manualMode = manualMode;

    if (!manualMode) {
      targetSpeedMph = speedAuthCmd.suggestedSpeedMph;
    }
  }
  
  public void setVitalError(boolean vitalError) {
    this.vitalError = vitalError;
  }
  
  public void setLights(boolean lightsOn) {
    this.lightsOn = lightsOn;
  }
  
  public void setRightDoor(boolean rightDoorOpen) {
    this.rightDoorOpen = rightDoorOpen;
  }
  
  public void setLeftDoor(boolean leftDoorOpen) {
    this.leftDoorOpen = leftDoorOpen;
  }
  
  public void setAirConditioning(boolean airConditioningOn) {
    this.airConditioningOn = airConditioningOn;
  }
  
  public void setStopRequired(boolean stopRequired) {
    this.stopRequired = stopRequired;
  }
  
  public void setBeaconMessage(BeaconMessage beaconMessage) {
    if (beaconMessage.trainID == id) {
      this.beaconMessage.distanceFromStation = beaconMessage.distanceFromStation;
      this.beaconMessage.stationName = new String(beaconMessage.stationName);
      this.beaconMessage.leftSide = beaconMessage.leftSide;
      this.beaconMessage.rightSide = beaconMessage.rightSide;
      stopRequired = true;
    }
  }
  
  // Send all vital input data to vital controllers.
  private void setVitalControlInputs() {
    vitalPrimary.manualMode = manualMode;
    vitalPrimary.targetSpeedMph = targetSpeedMph;
    vitalPrimary.currentSpeedMph = currentSpeedMph;
    vitalPrimary.targetSpeedMph = targetSpeedMph;
    vitalPrimary.speedLimitMph = speedLimitMph;
    vitalPrimary.authorityMi = speedAuthCmd.suggestedAuthMiles;
    vitalPrimary.safeStoppingDistanceMi = safeStoppingDistanceMi;
    vitalPrimary.speedAuthCmd = speedAuthCmd;
    vitalPrimary.stopRequired = stopRequired;
    vitalPrimary.distanceFromStationMi = beaconMessage.distanceFromStation;

    vitalSecondary.manualMode = manualMode;
    vitalSecondary.targetSpeedMph = targetSpeedMph;
    vitalSecondary.currentSpeedMph = currentSpeedMph;
    vitalSecondary.targetSpeedMph = targetSpeedMph;
    vitalSecondary.speedLimitMph = speedLimitMph;
    vitalSecondary.authorityMi = speedAuthCmd.suggestedAuthMiles;
    vitalSecondary.safeStoppingDistanceMi = safeStoppingDistanceMi;
    vitalSecondary.speedAuthCmd = speedAuthCmd;
    vitalSecondary.stopRequired = stopRequired;
    vitalSecondary.distanceFromStationMi = beaconMessage.distanceFromStation;
  }
  
  private void getVitalControlOutputs() {
    if (vitalPrimary.emergencyBrake != vitalSecondary.emergencyBrake
        || vitalPrimary.serviceBrake != vitalSecondary.serviceBrake
        || vitalPrimary.powerKw != vitalSecondary.powerKw
        || vitalPrimary.authorityMi != vitalSecondary.authorityMi
        || vitalPrimary.powerKw != vitalSecondary.powerKw
        || vitalPrimary.safeStoppingDistanceMi != vitalSecondary.safeStoppingDistanceMi
        || vitalPrimary.stopRequired != vitalSecondary.stopRequired
        || vitalPrimary.distanceFromStationMi != vitalSecondary.distanceFromStationMi) {
      vitalError = true;
    }
    
    emergencyBrake = vitalPrimary.emergencyBrake;
    serviceBrake = vitalPrimary.serviceBrake;
    targetSpeedMph = vitalPrimary.targetSpeedMph;
    speedAuthCmd.suggestedAuthMiles = vitalPrimary.authorityMi;
    powerKw = vitalPrimary.powerKw;
    safeStoppingDistanceMi = vitalPrimary.safeStoppingDistanceMi;
    stopRequired = vitalPrimary.stopRequired;
    beaconMessage.distanceFromStation = vitalPrimary.distanceFromStationMi;
  }
  
  // Called by TrainModel to 
  public double calcPower() {   
    setVitalControlInputs();
    
    vitalPrimary.update();
    vitalSecondary.update();
    
    getVitalControlOutputs();
    
    if (vitalError)
    {
      emergencyBrake = true;
      powerKw = 0.0;
    }
    
    return powerKw;
  }
  
  public double getTargetSpeed() {
    return targetSpeedMph;
  }
  
  public double getPower() {
    return powerKw;
  }
  
  public double getSafeStoppingDistance() {
    return safeStoppingDistanceMi;
  }
  
  public double getCurrentSpeed() {
    return currentSpeedMph;
  }
  
  public double getSpeedLimit() {
    return speedLimitMph;
  }
  
  public SpeedAuthCmd getSpeedAuthCmd() {
    return speedAuthCmd;
  }
  
  public boolean isServiceBrakeOn() {
    return serviceBrake;
  }
  
  public boolean isEmergencyBrakeOn() {
    return emergencyBrake;
  }
  
  public boolean isAirConditioningOn() {
    return airConditioningOn;
  }
  
  public boolean isLeftDoorOpen() {
    return leftDoorOpen;
  }
  
  public boolean isRightDoorOpen() {
    return rightDoorOpen;
  }
  
  public boolean isManualMode() {
    return manualMode;
  }
  
  public boolean isLightOn() {
    return lightsOn;
  }
  
  public int getId() {
    return id;
  }
  
  public double getAuthority() {
    return speedAuthCmd.suggestedAuthMiles;
  }
  
  public boolean vitalErrorOccurred() {
    return vitalError;
  }
  
  public boolean isStopRequired() {
    return stopRequired;
  }
  
  public BeaconMessage getBeaconMessage() {
    return beaconMessage;
  }
}