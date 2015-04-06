package train_controller;

import system_wrapper.SystemWrapper;

public class VitalTrainControlPrimary extends VitalTrainControl{
  private double controlVar = 0;
  double lastSpeedErrorMph = 0;
  
  public double calcPower() {
    double speedErrorMph = targetSpeedMph - currentSpeedMph;
    
    if (powerW < maxPowerW) {
      controlVar += (SystemWrapper.simClock.getDeltaS() / 2.0)
          * (speedErrorMph + lastSpeedErrorMph);
    }
    
    powerW = Kp * speedErrorMph + Ki * controlVar;
    
    return powerW;
  }
}
