package train_controller;

import train_controller.TrainController;
import system_wrapper.SimClock;

// Simple version of TrainModel used only for TrainController standalone mode.
public class TestTrainModel {
  public static final double EBRAKE_ACCEL = -6.106824;   // mph per second
  public static final double SBRAKE_ACCEL = -2.684322;   // mph per second
  public static final double TRAIN_MASS_KG = 40900;        // kg
  public static final double MPS_PER_MPH =  0.44704;    // m/s per mph
  public static final double FRICTION_COEFF = 0.1;
  
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
      double forceN;
      double accelMps2;
      double deltaSpeedMps;
      double deltaSpeedMph;
      

      forceN = powerKw / 0.001;
      
      /*
      if (currentSpeedMph == 0) {
        forceN = powerW / 0.001;
      }
      else {
        double speedMps;
        
        speedMps = MPS_PER_MPH * currentSpeedMph;
        forceN = powerW / speedMps;
      }
      */
      
      accelMps2 = (forceN / TRAIN_MASS_KG) - (FRICTION_COEFF * 9.80);
      
      deltaSpeedMps = accelMps2 * SimClock.getDeltaS();
      
      deltaSpeedMph = deltaSpeedMps / MPS_PER_MPH;
      
      currentSpeedMph += deltaSpeedMph;
      
      //System.out.println("accelMps2 = " + accelMps2);
      
      //currentSpeedMph += SimClock.getDeltaS() * powerKw / 10.0 - currentSpeedMph * .001;
    }
    

    if (currentSpeedMph < 0.0)
    {
      currentSpeedMph = 0.0;
    }
  }
}
