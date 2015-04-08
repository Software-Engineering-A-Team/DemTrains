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
    
    /**
     * Connects all of the blocks within each section together.
     */ 
    public void connectBlocks() {
        // iterate over all of the TrackSections in the TrackLayout
        for (int i = 0; i < sections.size(); i++) {
            // retrieve the first TrackBlock in the current section
            TrackSection currentSection = sections.get(i);
            TrackBlock firstBlockInCurrentSection = currentSection.blocks.get(0);
            // if we're past the first section, we need to connect them
            if (i > 0) {
                // retrieve the last TrackBlock in the previous section
                TrackSection previousSection = sections.get(i - 1);
                TrackBlock lastBlockInPreviousSection = previousSection.blocks.get(previousSection.blocks.size() - 1);
                // add edges between the section-connecting TrackBlocks in this section
                if (currentSection.forward) {
                    layout.addEdge(firstBlockInCurrentSection.number, lastBlockInPreviousSection.number);
                }
                if (currentSection.backward) {
                    layout.addEdge(lastBlockInPreviousSection.number, firstBlockInCurrentSection.number);
                }
            }
            // iterate over all of the TrackBlocks in this TrackSection
            for (int j = 0; j < currentSection.blocks.size() - 1; j++) {
                TrackBlock block = currentSection.blocks.get(j);
                TrackBlock nextBlock = currentSection.blocks.get(j + 1);
                // if this block connects to a "switch block", find reference to correct vertex and add edge
                if (block.connectsToSwitch != null) {
                    int targetBlockNumber = switchToBlocks.get(block.connectsToSwitch);
                    // if the working block is the same as the switch block to connect to, we need to skip!
                    if (block.number != targetBlockNumber) {
                        System.out.printf("Adding edges between block #%d and switch block #%d (switch #%d).\n", 
                                block.number, targetBlockNumber, block.connectsToSwitch);
                        if (currentSection.forward) {
                            layout.addEdge(block.number, targetBlockNumber);
                        }
                        if (currentSection.backward) {
                            layout.addEdge(targetBlockNumber, block.number);
                        }
                    }
                }
                // add edges between consecutive blocks in this section
                if (currentSection.forward) {
                    layout.addEdge(block.number, nextBlock.number);
                }
                if (currentSection.backward) {
                    layout.addEdge(nextBlock.number, block.number);
                }
            }
        }
    }

}