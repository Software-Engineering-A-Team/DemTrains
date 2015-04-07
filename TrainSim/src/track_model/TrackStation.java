package track_model;

import java.util.Map;
import java.util.Random;

public class TrackStation extends TrackBlock {
	
	public String stationName;
	public String beacon;
	
	/**
	 * Creates a new instance of the TrackStation subclass.
	 * @param descriptor The descriptor of the TrackStation read in from the track data file.
	 */	
	public TrackStation(Map<String, String> descriptor) {
		super(descriptor);
		this.stationName = descriptor.get("stationName");
		this.beacon = null;
	}
	
	/**
	 * Generates a random number of passengers waiting at the station.
	 * @return The number of passengers that will board the train.
	 */
	public int generatePassengers() {
		Random rand = new Random();
		int passengers = rand.nextInt(100);
		System.out.printf("Generated %d passengers at %s station waiting to board.\n", passengers, stationName);
		return passengers;
	}
	
}