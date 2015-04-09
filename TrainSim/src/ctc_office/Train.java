package ctc_office;

public class Train {
	public int previousBlock = -1;
	public int currentBlock = 0;
	public int destination = -1;
	public int remainingTravelTime = 0;
	public double authority = Double.MAX_VALUE; // how far the train is allowed to travel before stopping
	public double distanceTraveledOnBlock = 0;
	public double currSpeed = 0;
	public double totalDistanceTraveled = 0;
	public short trainId;
	public String trainName;
	public double maxSpeed = Double.MAX_VALUE;
	
	public Train(short tId, String name){
		trainId = tId;
		trainName = name;
	}
}
