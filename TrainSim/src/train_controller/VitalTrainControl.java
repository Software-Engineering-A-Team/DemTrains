package train_controller;

// Vital train controls are calculated multiple times by different
// implementations of VitalTrainControl and outputs are compared
// to detect vital errors.
public abstract class VitalTrainControl {
  public double speedLimitMph;
  public double currentSpeedMph = 0;
  public double targetSpeedMph = 0;
  public boolean emergencyBrake = false;
  public boolean serviceBrake = false;
  public double authorityMi = 0;
  public double safeStopDistanceMi = 0;
  
  protected double powerW = 0;
  
  protected final double maxPowerW = 12000;
  protected final double Kp = .9;
  protected final double Ki = 0.1;
  
  public abstract double calcPower();
}