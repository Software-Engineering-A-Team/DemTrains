package train_controller;

import system_wrapper.SimClock;
import train_controller.VitalTrainControl;

public class VitalTrainControlPrimary extends VitalTrainControl{
  private double controlVar = 0;
  private double lastSpeedErrorMph = 0;
  
  //
  public double calcPower() {    
    if (emergencyBrake)
    {
      powerKw = 0;
      controlVar = 0;
    }
    else if (!serviceBrake && (manualMode || authorityMi > 0.0))
    {
      double speedErrorMph = targetSpeedMph - currentSpeedMph;
      
      if (!(powerKw == 0 && speedErrorMph < 0.0) && (powerKw < maxPowerKw || speedErrorMph < 0.0)) {
        controlVar += (SimClock.getDeltaS() / 2.0) * (speedErrorMph + lastSpeedErrorMph);
      }
      
      powerKw = Kp * speedErrorMph + Ki * controlVar;
      
      if (powerKw > maxPowerKw) {
        powerKw = maxPowerKw;
      }
      else if (powerKw < 0) {
        powerKw = 0;
      }
      
    }

    authorityMi -= currentSpeedMph * SimClock.getDeltaS() / 3600.0;
      
    return powerKw;
  }

  public void determineSafeSpeed() {
    if (!manualMode) {      
      // Use suggested speed.
      targetSpeedMph = speedAuthCmd.suggestedSpeedMph;
      
      // If we are above the speed limit, pull emergency brake automatically
      if (currentSpeedMph > speedLimitMph) {
        emergencyBrake = true;
      }
      
      // If authority has been exceeded, engage service brake
      if (authorityMi <= 0.0) {
        serviceBrake = true;
      }
      else
      {
        serviceBrake = false;
      }
    }

    
  }
}
