package train_controller;

import system_wrapper.SpeedAuthCmd;

// Vital train controls are calculated multiple times by different
// implementations of VitalTrainControl and outputs are compared
// to detect vital errors.
abstract class VitalTrainControl {
  // Attributes which TrainController manipulates
  public double speedLimitMph = 0;
  public double currentSpeedMph = 0;
  public double targetSpeedMph = 0;
  public boolean emergencyBrake = false;
  public boolean serviceBrake = false;
  public double authorityMi = 0;
  public double safeStoppingDistanceMi = 0;
  public boolean manualMode = false;
  public SpeedAuthCmd speedAuthCmd = new SpeedAuthCmd(0, 0);
  public double powerKw = 0;
  public double distanceFromStationMi = 0;
  public boolean stopRequired = false;
  
  // Constants
  protected final double maxPowerKw = 120;
  protected final double Kp = 10;
  protected final double Ki = 6;
  protected final double serviceBrakeThresholdMph = 15.0;
  protected final double emergencyBrakeThresholdMph = 0.05;
  protected final double brakeRecoveryThresholdMph = 0.2;
  
  public abstract double calcPower();
  public abstract void manageSafeSpeedAndBraking();
  public void update() {
    manageSafeSpeedAndBraking();
    calcPower();
  }
}