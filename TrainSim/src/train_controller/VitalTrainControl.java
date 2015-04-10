package train_controller;

import system_wrapper.SpeedAuthCmd;

// Vital train controls are calculated multiple times by different
// implementations of VitalTrainControl and outputs are compared
// to detect vital errors.
abstract class VitalTrainControl {
  // Attributes which TrainController manipulates
  public double speedLimitMph;
  public double currentSpeedMph;
  public double targetSpeedMph;
  public boolean emergencyBrake;
  public boolean serviceBrake;
  public double authorityMi;
  public double safeStoppingDistanceMi;
  public boolean manualMode;
  public SpeedAuthCmd speedAuthCmd;
  public double powerKw;
  
  // Constants
  protected final double maxPowerKw = 120;
  protected final double Kp = 0.5;
  protected final double Ki = 0.0000005;
  protected final double serviceBrakeThresholdMph = 15.0;
  protected final double emergencyBrakeThresholdMph = 0.05;
  protected final double brakeRecoveryThresholdMph = 0.2;
  
  public abstract double calcPower();
  public abstract void determineSafeSpeed();
  public void update() {
    determineSafeSpeed();
    calcPower();
  }
}