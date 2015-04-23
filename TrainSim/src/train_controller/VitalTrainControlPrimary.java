package train_controller;

import system_wrapper.SimClock;
import train_controller.VitalTrainControl;

public class VitalTrainControlPrimary extends VitalTrainControl{
  private double controlVar = 0;
  private double lastSpeedErrorMph = 0;
  
  //
  public double calcPower() {
    double distanceTravelledMi = currentSpeedMph * SimClock.getDeltaS() / 3600.0;
    
    if (emergencyBrake)
    {
      powerKw = 0;
      controlVar = 0;
    }
    else if (manualMode || authorityMi > 0.0)
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

    authorityMi -= distanceTravelledMi;
    safeStoppingDistanceMi -= distanceTravelledMi;
    
    distanceFromStationMi -= distanceTravelledMi;
      
    return powerKw;
  }

  public void manageSafeSpeedAndBraking() {
    if (!manualMode) {      
      // Use suggested speed.
      //targetSpeedMph = speedAuthCmd.suggestedSpeedMph;
      
      // If we are above the speed limit or close to the safe stopping distance, pull emergency brake automatically
      if (this.safeStoppingDistanceMi <= 0.1) {
        emergencyBrake = true;
      }
      
      // Turn on service brake if we are close to exceeding authority or stopping distance
      if (currentSpeedMph > speedLimitMph || authorityMi <= 0.2 || this.safeStoppingDistanceMi <= 0.2 || currentSpeedMph > targetSpeedMph) {
        serviceBrake = true;
      }
      else{
        serviceBrake = false;
      }
      
      if (stopRequired && this.distanceFromStationMi <= 0.2) {
        serviceBrake = true;
        
        if (this.currentSpeedMph == 0.0) {
          targetSpeedMph = 0.0;
          powerKw = 0.0;
          controlVar = 0.0;
          stopRequired = false;
        }
      }
    }

    
  }
}
