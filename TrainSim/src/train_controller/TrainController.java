package train_controller;

import system_wrapper.SpeedAuthCmd;



public class TrainController {
  private final VitalTrainControl vitalPrimary = new VitalTrainControlPrimary();
  private SpeedAuthCmd speedAuthCmd;
  private boolean airConditioningOn = false;
  private boolean leftDoorOpen = false;
  private boolean rightDoorOpen = false;
  private boolean manualMode = false;
  
  
  public void setCurrentSpeed(double currentSpeedMph) {
    vitalPrimary.currentSpeedMph = currentSpeedMph;
  }
  
  public void setTargetSpeed(double targetSpeedMph) {
    vitalPrimary.targetSpeedMph = targetSpeedMph;
  }
  
  public void setSpeedLimit(double speedLimitMph) {
    vitalPrimary.speedLimitMph = speedLimitMph;
  }
  
  public void setServiceBrake(boolean serviceBrake) {
    vitalPrimary.serviceBrake = serviceBrake;
  }
  
  public void setEmergencyBrake(boolean emergencyBrake) {
    vitalPrimary.emergencyBrake = emergencyBrake;
  }
  
  public void setSpeedAuthCmd(SpeedAuthCmd cmd) {
    
  }
  
  public double calcPower() {
    if (!manualMode) {
      vitalPrimary.determineSafeSpeed();
    }
    
    return vitalPrimary.calcPower();
  }
  
  public boolean getServiceBrake() {
    return false;
  }
  
  public boolean getEmergencyBrake() {
    return false;
  }
  
  public void setManualMode(boolean manualMode) {
    this.manualMode = manualMode;
  }
}