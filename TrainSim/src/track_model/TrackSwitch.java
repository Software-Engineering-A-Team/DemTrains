package track_model;

import java.util.Map;

public class TrackSwitch extends TrackBlock {
	
	public boolean state;
	
	/**
	 * Creates a new instance of the TrackSwitch subclass.
	 * @param descriptor The descriptor of the TrackSwitch read in from the track data file.
	 */	
	public TrackSwitch(Map<String, String> descriptor) {
		super(descriptor);
		this.state = false;
	}
	
}