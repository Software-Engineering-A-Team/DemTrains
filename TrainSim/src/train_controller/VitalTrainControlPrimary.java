package train_controller;

import system_wrapper.SimClock;
import train_controller.VitalTrainControl;

public class VitalTrainControlPrimary extends VitalTrainControl{
  private double controlVar = 0;
  private double lastSpeedErrorMph = 0;
  
  
  public double calcPower() {
    double speedErrorMph;
    
    speedErrorMph = targetSpeedMph - currentSpeedMph;
    
    if (!(powerKw == 0 && speedErrorMph < 0.0) && (powerKw < maxPowerKw || speedErrorMph < 0.0)) {
      controlVar += (SimClock.getDeltaS() / 2.0) * (speedErrorMph + lastSpeedErrorMph);
    }
    
    powerKw = Kp * speedErrorMph + Ki * controlVar;
    
    //System.out.println("speedErrorMph = " + speedErrorMph + "   controlVar = " + controlVar);
    
    if (powerKw > maxPowerKw) {
      powerKw = maxPowerKw;
    }
    else if (powerKw < 0) {
      powerKw = 0;
    }
    
    return powerKw;
  }

  public void determineSafeSpeed() {
    if (!manualMode) {
      // Service brake should be activated if the current speed is above the target speed by
      // a predetermined threshold. If it is already activated, it will stay activated until
      // the difference between the current speed and the target speed is decreased by the
      // magnitude of the brake recovery threshold. This prevents continuous jerking back and
      // forth at short intervals.
      if (!serviceBrake && (currentSpeedMph - targetSpeedMph >= serviceBrakeThresholdMph)
          || serviceBrake && (currentSpeedMph - targetSpeedMph >= serviceBrakeThresholdMph - brakeRecoveryThresholdMph)) {
        serviceBrake = true;
      }
      else {
        serviceBrake = false;
      }
      
      // Similar scheme as above, but this threshold is the maximum allowed gap between the
      // the current speed and the speed limit, because we want to keep the train below the
      // limit by a certain amount.
      if (!emergencyBrake && (speedLimitMph - currentSpeedMph <= emergencyBrakeThresholdMph)
          || emergencyBrake && (speedLimitMph - currentSpeedMph >= serviceBrakeThresholdMph + brakeRecoveryThresholdMph)) {
        emergencyBrake = true;
      }
      else {
        emergencyBrake = false;
      }
      
      // Use suggested speed.
      targetSpeedMph = speedAuthCmd.suggestedSpeedMph;
    }
  }
}
