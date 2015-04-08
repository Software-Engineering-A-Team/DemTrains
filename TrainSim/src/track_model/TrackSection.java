package track_model;

import java.util.List;
import java.util.ArrayList;

public class TrackSection {
	
	public boolean forward;
	public boolean backward;
	public List<TrackBlock> blocks;
	
	/**
	 * Creates a new instance of the TrackSection class to build the track layout.
	 */	
	public TrackSection() {
		this.forward = false;
		this.backward = false;
		this.blocks = new ArrayList<TrackBlock>();
	}
	
}