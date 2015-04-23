package track_model;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.graph.DefaultEdge;

import track_model.TrackSection;

public class TLayout {
	
	public DirectedMultigraph<Integer, DefaultEdge> layout;
	public List<TrackSection> sections;
	public List<TrackBlock> blocks;
	public Map<Integer, Integer> switchToBlocks;
	public Map<String, Integer> trains; // maps from train ID to block ID
	
	/**
	 * Creates a new instance of the TrackLayout class.
	 */	
	public TLayout() {
		layout = new DirectedMultigraph<Integer, DefaultEdge>(DefaultEdge.class);
		sections = new ArrayList<TrackSection>();
		blocks = new ArrayList<TrackBlock>();
		switchToBlocks = new HashMap<Integer, Integer>();
		trains = new HashMap<String, Integer>();
	}
	
	/**
	 * Retrieves the TrackBlock corresponding to a specific train and its total distance traveled.
	 * @param trainID The unique identifier of the train requesting the TrackBlock.
	 * @param totalDistance The total distance traveled by the train from the yard.
	 * @return The TrackBlock on which the train with unique identifier trainID is located.
	 */	
	public TrackBlock getCurrentBlock(String trainName, double totalDistance, TrackBlock previousBlock) {
		
		return null;
	}
    
    /**
     * Connects all of the blocks within each section together.
     */ 
    public void connectBlocks() {
        // iterate over all of the TrackSections in the TrackLayout
        for (int i = 0; i < sections.size(); i++) {
            // retrieve the first TrackBlock in the current section
            TrackSection section = sections.get(i);
            // iterate over all of the TrackBlocks in this TrackSection
            for (int j = 0; j < section.blocks.size(); j++) {
                TrackBlock block = section.blocks.get(j);
                System.out.printf("Iterating over block #%d...\n", block.number);
                // if there is another block in this section...
                if (section.blocks.size() > j + 1) {
                    TrackBlock nextBlock = section.blocks.get(j + 1);
                    System.out.printf("And grabbing nextBlock #%d...\n", nextBlock.number);
                    // add edges between consecutive blocks in this section
                    if (section.leastToGreatest) {
                    	connectBlocks(block.number, nextBlock.number);
                    }
                    if (section.greatestToLeast) {
                    	connectBlocks(nextBlock.number, block.number);
                    }
                }
                // if this is the first or last block in the section, we need to connect to the next/previous section
                if (block.connectsTo != null) {
                	System.out.printf("Block #%d connects to another section...\n", block.number);
                	// special case for single blocked sections which requires distinction between previous/next blocks
                	if (section.blocks.size() == 1) {
                		if (section.leastToGreatest) {
                			connectBlocks(block.connectsTo[0], block.number);
                			connectBlocks(block.number, block.connectsTo[1]);
                		}
                		if (section.greatestToLeast) {
                			connectBlocks(block.connectsTo[1], block.number);
                			connectBlocks(block.number, block.connectsTo[0]);
                		}
                	} else {
	                	for (int k = 0; k < block.connectsTo.length; k++) {
	                		System.out.printf("...block #%d.\n", block.connectsTo[k]);
	                		// add edges between the section-connecting TrackBlocks in this section
	                		// TODO: simplify this logic down (possible?)
	                		if (section.leastToGreatest) {
	                			if (j == 0) {
	                				connectBlocks(block.connectsTo[k], block.number);
	                			} else {
	                				connectBlocks(block.number, block.connectsTo[k]);
	                			}
	                		}
	                		if (section.greatestToLeast) {
	                			if (j == 0) {
	                				connectBlocks(block.number, block.connectsTo[k]);
	                			} else {
	                				connectBlocks(block.connectsTo[k], block.number);
	                			}
	                		}
	                	}
                	}
                }
            }
        }
    }
    
    /**
     * Connects the two blocks with an edge from the source to the target.
     */ 
    public void connectBlocks(int source, int target) {
    	if (!layout.containsEdge(source, target)) {
    		System.out.printf("Adding edge between block #%d --> block #%d.\n", source, target);
    		layout.addEdge(source, target);
    	}
    }

}