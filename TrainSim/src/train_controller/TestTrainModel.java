package train_controller;

import train_controller.TrainController;
import system_wrapper.SimClock;

// Simple version of TrainModel used only for TrainController standalone mode.
public class TestTrainModel {
  public static final double EBRAKE_ACCEL = -0.00169634;                // mi/s^2
  public static final double SBRAKE_ACCEL = -0.000745645430684791725;   // mi/s^2
  public static final double ENGINE_ACCEL = 0.000005;
  
  public double currentSpeedMph = 0;
  public TrainController trainController;
  
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
      currentSpeedMph += 3600.0 * SimClock.getDeltaS() * EBRAKE_ACCEL;
    }
    else if (trainController.isServiceBrakeOn())
    {
      currentSpeedMph += 3600.0 * SimClock.getDeltaS() * SBRAKE_ACCEL;
    }
    else if (trainController.getTargetSpeed() > trainController.getCurrentSpeed())
    {
      currentSpeedMph += SimClock.getDeltaS() * ENGINE_ACCEL;
    }
    else if (trainController.getTargetSpeed() < trainController.getCurrentSpeed())
    {
      currentSpeedMph -= SimClock.getDeltaS() * ENGINE_ACCEL;
    }
    

    if (currentSpeedMph < 0.0)
    {
      currentSpeedMph = 0.0;
    }
    else if (currentSpeedMph > 100.0)
    {
      currentSpeedMph = 100.0;
    }
  }
}
