package ctcOffice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

/*
 * Object that stores the layout of the track as a graph.
 */
public class TrackLayout {
	private String line;
	private List<Infrastructure> trackBlocks = new ArrayList<Infrastructure>();
	private List<Block> yardConnections = new ArrayList<Block>();
	private Hashtable<Character, Boolean[]> trackDirections = new Hashtable<Character, Boolean[]>();
	public TrackLayout(){}
	
	/*
	 * Creates reads in the excel file data and updates the track layout
	 * Each line in the csv file will be in the format:
	 * 		Line, Section, Block Number, Block Length (m), Block Grade (%), Speed Limit (Km/Hr), Infrastructure, ELEVATION (M), CUMALTIVE ELEVATION (M), Switch Block, Arrow Direction
	 */
	public void parseCsvFile(String fileName) throws IOException{
		File file = new File("track_layout.csv");
	    List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
	    String[][] trackBlockDescriptions = new String[lines.size()][lines.get(0).split(",").length];
	    // Read in the blocks from a file and create the block objects
	    for (int i=0; i<lines.size(); i++) {
	        trackBlockDescriptions[i] = (lines.get(i).split(","));
			String[] blockDescriptor = new String[11];
			blockDescriptor = Arrays.copyOf(trackBlockDescriptions[i], 11);
			for (int j = trackBlockDescriptions[i].length; j < blockDescriptor.length; j++){
				blockDescriptor[j] = "";
			}
	        if (blockDescriptor[6].contains("SWITCH")){
	        	trackBlocks.add(new Switch(blockDescriptor));
	        }
	        else if (blockDescriptor[6].contains("STATION")){
	        	trackBlocks.add(new Station(blockDescriptor));
	        	
	        }
	        else{
	        	trackBlocks.add(new Block(blockDescriptor));
	        }
	    }
	    // Declare global variables
	    line = trackBlockDescriptions[0][0];
	    
	    // Determine the directions of each section
    	int endpointCount = 0;
	    for (int i=0; i<trackBlocks.size(); i++){
	    	Block trackBlock = (Block) trackBlocks.get(i);
	    	Character currSection = trackBlock.getSection();
			if (!trackDirections.containsKey(currSection)){
				// Add it to the list
				Boolean [] temp = {false, false};
				trackDirections.put(currSection, temp);
			}
	    	String[] arrowDirections = trackBlock.getArrowDirection();

	    	for(int j=0;j<arrowDirections.length;j++){
		    	if (arrowDirections[j].equals("Head")){
			    	trackDirections.get(currSection)[endpointCount % 2] = true; // Set the correct endpoint for the section to true
			    	endpointCount++;
			    }
		    	else if (arrowDirections[j].equals("Tail")){
			    	endpointCount++;		    		
		    	}
		    }
	    	
	    }
	    
	    // Map out all the connections the blocks make
	    for (int i=0; i< trackBlocks.size(); i++){
	    	Block trackBlock = (Block) trackBlocks.get(i);
	    	if (trackBlock.containsSwitch()){
	    		//It is a switch, it will point to two blocks
	    		// Search the ArrayList and find the two blocks it points to
	    		int count = 0;
	    		Switch currSwitch = (Switch)trackBlocks.get(i);
	    		List<Block> connectedBlocks = new ArrayList<Block>();
	    		Block[] switchBlocks = new Block[2];
	    		for (int j=0; i< trackBlocks.size(); j++){
	    			if (i != j){
		    			Block tempBlock = (Block)trackBlocks.get(j);
		    			if (currSwitch.getSwitchId() == tempBlock.getSwitchId()){
		    				switchBlocks[count] = tempBlock;
		    				count++;
			    			if (count == 2){
			    				break;
			    			}
		    			}
	    			}
	    		}
	    		currSwitch.setSwitchBlocks(switchBlocks);
	    		connectedBlocks = Arrays.asList(switchBlocks);
	    		for (Block tempBlock : switchBlocks){
	    			if (!currSwitch.getConnectedBlocks().contains(tempBlock)){
	    				// add the current block as a connection for the next blocks
	    				currSwitch.addConnectedBlock(tempBlock);
	    			}
	    		}
	    		continue; //Remove this for the real simulation
	    	}
	    	else if (Arrays.asList(trackBlock.getArrowDirection()).contains("Yard")){
	    		//It touches the yard
	    		yardConnections.add(trackBlock);
	    	}
	    	//else{ //Uncomment this for the real simulation
	    		// It is a normal block
	    		
	    		if (trackBlock.getSwitchId() != null){
	    			// One end of the block connects to a switch
	    			for (int j=0; i< trackBlocks.size(); j++){
	    				if (i != j){
		    				Block tempBlock = (Block) trackBlocks.get(j);
		    				if (trackBlock.getSwitchId() == tempBlock.getSwitchId()){
		    					trackBlock.addConnectedBlock(tempBlock);
		    					break;
		    				}
	    				}
	    			}
	    		}
	    		
	    		// Set the connections to the next block.
	    		if (i < trackBlocks.size() - 1){
    				Block tempBlock = (Block) trackBlocks.get(i + 1);
	    				// The block is connected to the block at i + 1
		    			trackBlock.addConnectedBlock(tempBlock);
		    			if (trackBlock.getSection() == tempBlock.getSection()){
		    				tempBlock.addConnectedBlock(trackBlock);
		    			}
	    		}
	    	//}
	    }
	}
	
	public Block[] getYardYardConnections(){
		return yardConnections.toArray(new Block[yardConnections.size()]);
	}
}
