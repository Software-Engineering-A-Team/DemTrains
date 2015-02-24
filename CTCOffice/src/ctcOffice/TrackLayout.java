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
import java.util.List;

/*
 * Object that stores the layout of the track as a graph.
 */
public class TrackLayout {
	private String line;
	private Block head;
	private List<Block> trackBlocks = new ArrayList<Block>();
	private Block[] blockCurrentConnectionMap; // Map of all the connections for each
	private List<Block> yardConnections = new ArrayList<Block>();
	public TrackLayout(){}
	
	/*
	 * Creates reads in the excel file data and updates the track layout
	 * Each line in the csv file will be in the format:
	 * 		Line, Section, Block Number, Block Length (m), Block Grade (%), Speed Limit (Km/Hr), Infrastructure, ELEVATION (M), CUMALTIVE ELEVATION (M), Switch Block, Arrow Direction
	 */
	public void parseCsvFile(String fileName) throws IOException{
		File file = new File("track_layout.csv");
	    List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
	    String[][] trackBlockDescriptions = new String[lines.get(0).length()][lines.size()];
	    blockCurrentConnectionMap = new Block[lines.size()];
	    // Read in the blocks from a file and create the block objects
	    for (int i=0; i<lines.size(); i++) {
	        trackBlockDescriptions[i] = (lines.get(i).split(","));
	        if (trackBlockDescriptions[i].equals("SWITCH")){
	        	trackBlocks.add(new Switch(trackBlockDescriptions[i]));
	        }
	        else if (trackBlockDescriptions[i].equals("STATION")){
	        	trackBlocks.add(new Station(trackBlockDescriptions[i]));
	        	
	        }
	        else{
	        	trackBlocks.add(new Block(trackBlockDescriptions[i]));
	        }
	    }
	    // Declare global variables
	    line = trackBlockDescriptions[0][0];
	    
	    // Map out all the connections the blocks make
	    for (int i=0; i< trackBlocks.size(); i++){
	    	if (trackBlocks.get(i).containsSwitch()){
	    		//It is a switch, it will point to two blocks
	    		// Search the arraylist and find the twoo blocks it points to
	    		int count = 0;
	    		Switch currSwitch = (Switch)trackBlocks;
	    		Block[] connectedBlocks = new Block[2];
	    		for (int j=0; i< trackBlocks.size() && i != j; j++){
	    			if (currSwitch.getSwitchId() == trackBlocks.get(j).getSwitchId()){
	    				connectedBlocks[count] = trackBlocks.get(j);
	    				count++;
		    			if (count == 2){
		    				break;
		    			}
	    			}
	    		}
	    		currSwitch.setConnectedBlocks(connectedBlocks);
	    	}
	    	else if (Arrays.asList(trackBlocks.get(i).getArrowDirection()).contains("Yard")){
	    		//It touches the yard
	    		yardConnections.add(trackBlocks.get(i));
	    	}
	    	else{
	    		// It is a normal block
	    		trackBlocks.get(i).setNextBlock(trackBlocks.get(i));
	    	}
	    }
		
	}
}
