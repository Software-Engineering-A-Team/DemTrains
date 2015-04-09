package train_controller;

import system_wrapper.SpeedAuthCmd;

// Vital train controls are calculated multiple times by different
// implementations of VitalTrainControl and outputs are compared
// to detect vital errors.
abstract class VitalTrainControl {
  public double speedLimitMph;
  public double currentSpeedMph = 0;
  public double targetSpeedMph = 0;
  public boolean emergencyBrake = false;
  public boolean serviceBrake = false;
  public double authorityMi = 0;
  public double safeStoppingDistanceMi = 0;
  public boolean manualMode = false;
  public SpeedAuthCmd speedAuthCmd = new SpeedAuthCmd(0, 0);
  
  protected double powerW = 0;
  
  protected final double maxPowerW = 120000;
  protected final double Kp = 0.5;
  protected final double Ki = 0.0005;
  protected final double serviceBrakeThresholdMph = 15.0;
  protected final double emergencyBrakeThresholdMph = 0.05;
  protected final double brakeRecoveryThresholdMph = 0.2;
  
  public abstract double calcPower();
  public abstract void determineSafeSpeed();
}