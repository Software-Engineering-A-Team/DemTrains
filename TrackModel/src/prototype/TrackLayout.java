package prototype;

import java.util.List;
import java.util.ArrayList;

public class TrackLayout {
	
	private String lineName;
	private List<TrackBlock> blocks; // temporary storage solution
	
	public TrackLayout( String lineName ) {
		this.lineName = lineName;
		this.blocks = new ArrayList<TrackBlock>();
	}
	
	public void addBlock( TrackBlock block ) {
		blocks.add( block );
	}
	
	public List<TrackBlock> getBlocks() {
		return blocks;
	}
	
}