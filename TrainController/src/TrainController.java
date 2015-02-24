public class TrainController {
  final private double METERS_PER_MILE = 1609.34;
  final private double SECONDS_PER_HOUR = 3600;
  
  final private double MAX_SPEED_SI = 19.444444444;   // meters/second
  final private double MAX_POWER_W = 120000;       // watts
  private double targetVelSi;   // meters/second
  private double currentVelSi;  // meters/second
  private double enginePowerW;  // watts
  private boolean serviceBrakeState;
  
  public TrainController(){
    targetVelSi = 0;
    enginePowerW = 0;
  }
  
  private double ConvertVelocityEngToSi(double velEng){
    return velEng * METERS_PER_MILE / SECONDS_PER_HOUR;
  }
  
  public void SetTargetVelocityEng(double targetVelEng){
    targetVelSi = ConvertVelocityEngToSi(targetVelEng);
  }
  
  public void SetCurrentVelocityEng(double currentVelEng){
    currentVelSi = ConvertVelocityEngToSi(currentVelEng);
  }
  
  public double CalcEnginePowerW(){
    // If the current velocity is above the target or the max, set
    // engine power to zero
    if (currentVelSi > targetVelSi || currentVelSi > MAX_SPEED_SI)
    {
      enginePowerW = 0;
    }
    // Otherwise, calculate the engine power.
    else
    {
      enginePowerW = MAX_POWER_W * (targetVelSi - currentVelSi) / MAX_SPEED_SI;
    }
    
    return enginePowerW;
  }
  
  public boolean CheckBrakeState(){
    // If the current velocity is above the target or the max, engage
    // the service brake.
    if (currentVelSi > targetVelSi || currentVelSi > MAX_SPEED_SI)
    {
      serviceBrakeState = true;
    }
    // Otherwise, turn it off.
    else
    {
      serviceBrakeState = false;
    }
    
    return serviceBrakeState;
  }
}
