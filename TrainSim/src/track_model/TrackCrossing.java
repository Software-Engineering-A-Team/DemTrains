package track_model;

import java.util.Map;

public class TrackCrossing extends TrackBlock {
	
	public boolean state;
	
	/**
	 * Creates a new instance of the TrackCrossing subclass.
	 * @param descriptor The descriptor of the TrackCrossing read in from the track data file.
	 */
	public TrackCrossing(Map<String, String> descriptor) {
		super(descriptor);
		this.state = false;
	}
	
}