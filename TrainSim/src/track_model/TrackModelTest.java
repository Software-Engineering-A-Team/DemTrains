package track_model;

import java.util.List;
import java.util.Set;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedMultigraph;

public class TrackModelTest {
	
	public static void main(String[] args) {
		TrackModel model = new TrackModel();
		TrackLayout green = model.trackLayouts.get("Green");
		TrackLayout red = model.trackLayouts.get("Red");
		System.out.println(green.layout.toString());
		System.out.println(red.layout.toString());
	}
	
}