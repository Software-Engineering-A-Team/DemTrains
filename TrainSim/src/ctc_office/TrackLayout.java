package ctc_office;

import java.util.ArrayList;
import java.util.HashMap;

import org.jgrapht.graph.DirectedMultigraph;

public class TrackLayout {
	private final DirectedMultigraph<Integer, DefaultEdge> layout;
	private final ArrayList<TrackBlock> blockData = new ArrayList<TrackBlock>();
	private final TrainRouter trainRouter = new TrainRouter();

	public TrackLayout(DirectedMultigraph<Integer, DefaultEdge> tLayout, ArrayList<track_model.TrackBlock> tBlockData) {
		layout = tLayout;
		for (track_model.TrackBlock blockData : blockData) {
			// build the ctc trackBlocks from the actual trackBlocks
			// TrackBlock b = new TrackBlock();
			// blockData.add(TrackBlock.blockNumber, b);
		}
	}

	/*
	 * returns a list of beacon strings that need to be updated for trains approaching a station
	 */
	public HashMap<Integer, String> getBeaconStrings() {
		HashMap<Integer, String> beaconStrings = new HashMap<Integer, String>();
		// for each station
			// find train that will reach the station next
			// build the beaconString
			// add it to the list
		return beaconStrings;
	}
	
	/*
	 * Gets the train route for all dispatched trains for this tick
	 */
	public ArrayList<TrainRoute> getUpdatedTrainRoutes() {
		ArrayList<TrainRoute> routes = new ArrayList<TrainRoute>();
		
		return routes;
	}
 }
 