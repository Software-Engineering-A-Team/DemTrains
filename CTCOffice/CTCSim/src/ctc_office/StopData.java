package ctc_office;

public class StopData {
	public final String startStation;
	public final String destinationStation;
	public final double travelTime;
	public StopData(String block, String dest, double m){
		startStation = block;
		destinationStation = dest;
		travelTime = m;
	}
	
}
