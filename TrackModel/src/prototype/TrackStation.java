package prototype;

import java.util.Map;
import java.util.Random;

public class TrackStation extends TrackBlock {
	
	String stationName;
	
	public TrackStation( Map<String, String> descriptor ) {
		super( descriptor );
		this.stationName = descriptor.get( "stationName" );
	}
	
	/**
	 * Generates the number of passengers waiting to board the next train.
	 * @return int Number of passengers.
	 */
	public int generatePassengers() {
		Random rand = new Random();
		return rand.nextInt( 100 );
	}
	
}