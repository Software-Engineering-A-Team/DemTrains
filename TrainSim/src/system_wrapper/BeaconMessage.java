package system_wrapper;

public class BeaconMessage {
  public String stationName;
  public double distanceFromStation;
  public int trainID;
  public boolean rightSide;
  public boolean leftSide;
  
  public BeaconMessage() {
    stationName = new String();
  }
}