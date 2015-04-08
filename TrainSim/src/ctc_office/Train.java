package ctc_office;

public class Train {
	public int currentBlock = 0;
	public int destination = -1;
	public int remainingTravelTime = 0;
	public double authority = 0;
	public double distanceTraveledOnBlock = 0;
	public double speed = 0;
	public double totalDistanceTraveled = 0;
	public short trainId;
	public String trainName;
	public Train(short tId, String name){
		trainId = tId;
		trainName = name;
	}
}
