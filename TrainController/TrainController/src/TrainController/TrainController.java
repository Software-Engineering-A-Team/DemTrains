package TrainController;

public class TrainController {
  final private double METERS_PER_MILE = 1609.34;
  final private double SECONDS_PER_HOUR = 3600;
  final private double MAX_SPEED_SI = 19.444444444;   // meters/second
  final private double MAX_POWER_W = 120000;       // watts
  final private double propGain = 1;
  final private double intGain = 1;
  private double targetVelSi;   // meters/second
  private double feedbackVelSi;  // meters/second
  private double enginePowerW;  // watts
  private boolean serviceBrakeState;
  private double lastIntCoeff;
  private double lastVelError;
  
  
  public TrainController(){
    targetVelSi = 0;
    feedbackVelSi = 0;
    enginePowerW = 0;
    serviceBrakeState = false;
  }
  
  private double ConvertVelocityEngToSi(double velEng){
    return velEng * METERS_PER_MILE / SECONDS_PER_HOUR;
  }
  
  private double ConvertVelocitySiToEng(double velSi){
    return velSi * SECONDS_PER_HOUR / METERS_PER_MILE;
  }
  
  public double GetTargetVelocityEng()
  {
    return ConvertVelocitySiToEng(targetVelSi);
  }
  
  public double GetFeedbackVelocityEng()
  {
    return ConvertVelocitySiToEng(feedbackVelSi);
  }
  
  public void SetTargetVelocityEng(double targetVelEng){
    targetVelSi = ConvertVelocityEngToSi(targetVelEng);
    
    if (targetVelSi > MAX_SPEED_SI)
    {
      targetVelSi = MAX_SPEED_SI;
    }
  }
  
  public void SetCurrentVelocityEng(double currentVelEng){
    feedbackVelSi = ConvertVelocityEngToSi(currentVelEng);
  }
  
  public double CalcEnginePowerKw(double timestepS){
    double intCoeff;
    double velError;
    
    velError = targetVelSi - feedbackVelSi;
    
    if (enginePowerW < MAX_POWER_W){
      
      intCoeff = lastIntCoeff + (timestepS * (velError + lastVelError) / 2);
    }
    else{
      intCoeff = lastIntCoeff;
    }
    
    
    enginePowerW = (propGain * velError) + (intGain * intCoeff);
        
    lastIntCoeff = intCoeff;
    lastVelError = velError;
    
    return enginePowerW / 1000.0;
  }
  
  public boolean CheckBrakeState(){
    // If the current velocity is above the target or the max, engage
    // the service brake.
    if (feedbackVelSi > targetVelSi || feedbackVelSi > MAX_SPEED_SI)
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