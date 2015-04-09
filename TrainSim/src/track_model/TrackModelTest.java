package track_model;

import java.util.List;
import java.util.Set;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedMultigraph;

public class TrackModelTest {
	
	public static void main(String[] args) {
		TrackModel model = new TrackModel();
		TrackLayout green = model.trackLayouts.get("Green");
//		for (TrackBlock block : green.blocks) {
//			System.out.println(block.number + " " + block.infrastructure);
//		}
		// test all of the switches' edges
		DirectedMultigraph layout = green.layout;
		List<TrackBlock> blocks = green.blocks;
		System.out.println(layout.toString());
	}
	
}