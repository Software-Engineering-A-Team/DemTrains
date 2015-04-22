package train_controller;

import train_controller.TrainController;
import system_wrapper.SimClock;

// Simple version of TrainModel used only for TrainController standalone mode.
public class TestTrainModel {
  public static final double EBRAKE_ACCEL = -6.106824;   // mph per second
  public static final double SBRAKE_ACCEL = -2.684322;   // mph per second
  public static final double ENGINE_ACCEL = 2;
  
  public double currentSpeedMph = 0;
  public TrainController trainController;
  public double lastPowerKw = 0;
  
  public TestTrainModel(TrainController trainController)
  {
    this.trainController = trainController;
  }
  
  public void update() {
    double powerKw;
    
    trainController.setCurrentSpeed(currentSpeedMph);
    
    //System.out.println("Updating test train model");
    powerKw = trainController.calcPower();
    
    if (trainController.isEmergencyBrakeOn())
    {
      currentSpeedMph += SimClock.getDeltaS() * EBRAKE_ACCEL;
    }
    else if (trainController.isServiceBrakeOn())
    {
      currentSpeedMph += SimClock.getDeltaS() * SBRAKE_ACCEL;
    }
    else
    {
      currentSpeedMph += SimClock.getDeltaS() * powerKw / 10.0 - currentSpeedMph * .001;
    }
    

    if (currentSpeedMph < 0.0)
    {
      currentSpeedMph = 0.0;
    }
    else if (currentSpeedMph > 100.0)
    {
      currentSpeedMph = 100.0;
    }
    
    lastPowerKw = powerKw;
  }
}
