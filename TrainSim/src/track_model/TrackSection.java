package track_model;

import java.util.List;
import java.util.ArrayList;

public class TrackSection {
	
	public boolean leastToGreatest;
	public boolean greatestToLeast;
	public List<TrackBlock> blocks;
	
	/**
	 * Creates a new instance of the TrackSection class to build the track layout.
	 */	
	public TrackSection() {
		this.leastToGreatest = false;
		this.greatestToLeast = false;
		this.blocks = new ArrayList<TrackBlock>();
	}
	
}