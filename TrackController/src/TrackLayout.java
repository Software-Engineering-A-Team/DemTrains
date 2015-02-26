import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

	/*
	 * Object that stores the layout of the track as a graph.
	 */
	public class TrackLayout {
		private String line;
		private List<TrackBlock> trackBlocks = new ArrayList<TrackBlock>();
		private List<TrackBlock> yardConnections = new ArrayList<TrackBlock>();
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
				String[] blockDescriptor = new String[6];
				blockDescriptor = Arrays.copyOf(trackBlockDescriptions[i], 7);
				for (int j = trackBlockDescriptions[i].length; j < blockDescriptor.length; j++){
					blockDescriptor[j] = "";
				}
		        if (blockDescriptor[4].contains("SWITCH")){
		        	trackBlocks.add(new Switch(blockDescriptor));
		        }
		        else{
		        	trackBlocks.add(new TrackBlock(blockDescriptor));
		        }
		    }
		    // Declare global variables
		     line = trackBlockDescriptions[0][0];
		    
		    //Determine the directions of each section
	    	int endpointCount = 0;
		    for (int i=0; i<trackBlocks.size(); i++){
		    	TrackBlock trackBlock = (TrackBlock) trackBlocks.get(i);
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
		    	TrackBlock trackBlock = (TrackBlock) trackBlocks.get(i);
		    	if (trackBlock.containsSwitch()){
		    		//It is a switch, it will point to two blocks
		    		// Search the ArrayList and find the two blocks it points to
		    		int count = 0;
		    		Switch currSwitch = (Switch)trackBlocks.get(i);
		    		TrackBlock[] switchBlocks = new TrackBlock[2];
		    		for (int j=0; i< trackBlocks.size(); j++){
		    			if (i != j){
			    			TrackBlock tempBlock = (TrackBlock)trackBlocks.get(j);
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
		    		for (TrackBlock tempBlock : switchBlocks){
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
			    				TrackBlock tempBlock = (TrackBlock) trackBlocks.get(j);
			    				if (trackBlock.getSwitchId() == tempBlock.getSwitchId()){
			    					trackBlock.addConnectedBlock(tempBlock);
			    					break;
			    				}
		    				}
		    			}
		    		}
		    		
		    		// Set the connections to the next block.
		    		if (i < trackBlocks.size() - 1){
	    				TrackBlock tempBlock = (TrackBlock) trackBlocks.get(i + 1);
		    				// The block is connected to the block at i + 1
			    			trackBlock.addConnectedBlock(tempBlock);
			    			if (trackBlock.getSection() == tempBlock.getSection()){
			    				tempBlock.addConnectedBlock(trackBlock);
			    			}
		    		}
		    	}
		    }
		
		public TrackBlock[] getYardYardConnections(){
			return yardConnections.toArray(new TrackBlock[yardConnections.size()]);
		}
		
		public List<TrackBlock> createIterator(){
			Queue<TrackBlock> blockQueue = new LinkedList<TrackBlock>();
			List<TrackBlock> visitedList = new LinkedList<TrackBlock>();
			List<TrackBlock> iterator = new LinkedList<TrackBlock>();
			List<TrackBlock> nextBlocks = new LinkedList<TrackBlock>();
			
			// get the element connected to the yard
			TrackBlock current = yardConnections.get(0);
			for (int i=0; i<trackBlocks.size(); i++){
				nextBlocks = current.getConnectedBlocks();
				nextBlocks.remove(current);
				for (TrackBlock tempBlock : nextBlocks){
					// Add the blocks it connects to to the queue if the block hasn't been visited before.
					if (!visitedList.contains(tempBlock)){
						blockQueue.add(tempBlock);
					}
				}
				visitedList.add(current);
				iterator.add(current);
				current = blockQueue.remove();
			}
			
			return iterator;
		}
	}
