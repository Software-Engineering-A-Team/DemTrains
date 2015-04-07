package train_controller;

import system_wrapper.SpeedAuthCmd;

public class TrainController {
  private final VitalTrainControl vitalPrimary = new VitalTrainControlPrimary();
  
  // Non-vital variables
  private SpeedAuthCmd speedAuthCmd;
  private boolean airConditioningOn = false;
  private boolean leftDoorOpen = false;
  private boolean rightDoorOpen = false;
  private boolean manualMode = false;
  
  // Vital variables. Copies appear in each vital controller.
  private boolean emergencyBrake;
  private boolean serviceBrake;
  private double currentSpeedMph;
  private double targetSpeedMph;
  private double speedLimitMph;
  
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
    }
  }
  
  // The emergency brake can be pulled by anyone at any time.
  public void setEmergencyBrake(boolean emergencyBrake) {
    this.emergencyBrake = emergencyBrake;
  }
  
  // The speed command will only be followed if in automatic mode.
  public void setSpeedAuthCmd(SpeedAuthCmd cmd) {
    speedAuthCmd = cmd;
    
    if (manualMode) {
      targetSpeedMph = speedAuthCmd.suggestedSpeedMph;
    }
  }
  
  private void setVitalControlInputs() {
    vitalPrimary.manualMode = manualMode;
    vitalPrimary.targetSpeedMph = targetSpeedMph;
    vitalPrimary.currentSpeedMph = currentSpeedMph;
    vitalPrimary.targetSpeedMph = targetSpeedMph;
    vitalPrimary.speedLimitMph = speedLimitMph;
    vitalPrimary.authorityMi = speedAuthCmd.suggestedAuthMiles;
  }
  
  // Used by the driver to switch between manual and automatic control.
  public void setManualMode(boolean manualMode) {
    this.manualMode = manualMode;
  }
  
  // 
  public double calcPower() {
    double primaryPowerW;
    
    setVitalControlInputs();
    
    vitalPrimary.determineSafeSpeed();
    
    primaryPowerW = vitalPrimary.calcPower();
    
    emergencyBrake = vitalPrimary.emergencyBrake;
    serviceBrake = vitalPrimary.serviceBrake;
    
    return primaryPowerW;
  }
  
  public double getTargetSpeed() {
    return targetSpeedMph;
  }
  
  public boolean isServiceBrakeOn() {
    return vitalPrimary.serviceBrake;
  }
  
  public boolean isEmergencyBrakeOn() {
    return vitalPrimary.emergencyBrake;
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
  
}