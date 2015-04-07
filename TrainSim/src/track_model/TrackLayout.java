package track_model;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.graph.DefaultEdge;

public class TrackLayout {
	
	public DirectedMultigraph<Integer, DefaultEdge> layout;
	public List<TrackSection> sections;
	public List<TrackBlock> blocks;
	public Map<Integer, Integer> switchToBlocks;
	public Map<Integer, Integer> trains; // maps from train ID to block ID
	
	/**
	 * Creates a new instance of the TrackLayout class.
	 */	
	public TrackLayout() {
		layout = new DirectedMultigraph<Integer, DefaultEdge>(DefaultEdge.class);
		sections = new ArrayList<TrackSection>();
		blocks = new ArrayList<TrackBlock>();
		switchToBlocks = new HashMap<Integer, Integer>();
		trains = new HashMap<Integer, Integer>();
	}

}