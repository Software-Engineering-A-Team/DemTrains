package TrainController;

class BeaconMessage{
  public String stationName;
  public float distanceFromStation;
  public int trainID;
  boolean rightSide;
  boolean leftSide;
}

class SpeedAuthCmd{
  public float suggestedSpeedMph;
  public float suggestedAuthMiles;
}

class TrainController{  
  public void setCurrentVelocity(float currVelMph){
    
  }
  
  public void setTargetVelocity(float targetVelMph){
    
  }
  
  public void setSpeedLimit(float speedLimMph){
    
  }
  
  public void setServiceBrake(boolean sBrake){
    
  }
  
  public void setEmergencyBrake(boolean eBrake){
    
  }
  
  public void setSpeedAuthCmd(SpeedAuthCmd cmd){
    
  }
  
  public float calcPower(){
    return 0;
  }
  
  public boolean getServiceBrake(){
    return false;
  }
  
  public boolean getEmergencyBrake(){
    return false;
  }
}