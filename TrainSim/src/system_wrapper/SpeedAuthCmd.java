package system_wrapper;
public class SpeedAuthCmd {
  public double suggestedSpeedMph;
  public double suggestedAuthMiles;
  
  public SpeedAuthCmd(double speedMph, double authMi) {
    suggestedSpeedMph = speedMph;
    suggestedAuthMiles = authMi;
  }
}