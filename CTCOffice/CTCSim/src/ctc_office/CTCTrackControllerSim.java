package ctc_office;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;




import track_model.TrackBlock;
import track_model.TrackCrossing;
import track_model.TrackSection;
import track_model.TrackStation;
import track_model.TrackSwitch;
import track_model.TLayout;


public class CTCTrackControllerSim {

	private ArrayList<BlockInterface> redBlockData;
	private ArrayList<SwitchBlock> allRedSwitches = new ArrayList<SwitchBlock>();
	private ArrayList<BlockInterface> greenBlockData;
	private ArrayList<SwitchBlock> allGreenSwitches = new ArrayList<SwitchBlock>();
	private HashMap<Integer, TrainRoute> redRoutes = new HashMap<Integer, TrainRoute>();
	private HashMap<Integer, TrainRoute> greenRoutes = new HashMap<Integer, TrainRoute>();
	
	
	public CTCTrackControllerSim() {
		// Read in the tracks for the Red and Green line
		readInTrack("Red", "red_with_connections.csv");
		readInTrack("Green", "green_with_connections.csv");
		
		redBlockData = CTCWrapper.ctcOffice.getTrackLayout("Red").getAllBlocks();
		greenBlockData = CTCWrapper.ctcOffice.getTrackLayout("Green").getAllBlocks();
		for (BlockInterface b : redBlockData){
			if (b.getClass().equals(SwitchBlock.class)){
	        	allRedSwitches.add((SwitchBlock) b);
	        }
		}
		for (BlockInterface b : greenBlockData){
			if (b.getClass().equals(SwitchBlock.class)){
	        	allGreenSwitches.add((SwitchBlock) b);
	        }
		}
		
	}

	public void runTrackController() {
		// TODO Auto-generated method stub
		controlSwitching();
		setBlocksAsOccupied();
	}
	
	public void controlSwitching() {
		// Check to see if a train is on either side of a switch.
		for (Train t : CTCWrapper.trainModels) {
			TrainRoute r;
			// Red Line
			// loop through the track layout and find all of the switches
			if (t.lineName.toLowerCase().startsWith("r")) {
				r = redRoutes.get(t.currentBlock);
			}
			// Green Line
			else if (t.lineName.toLowerCase().startsWith("g")) {
				r = greenRoutes.get(t.currentBlock);
			}
			else {
				continue;
			}

			for (SwitchBlock b : allRedSwitches){
				// Check to see if a train is on that block or the block that one points to
				// If yes, switch the block to the correct direction
				if (t.currentBlock == b.blockNumber) {
					if (r.route.size() > 1) {
						int nextBlock = r.route.get(1);
						if ((nextBlock == b.getPossibleNextBlocks()[0]) && (nextBlock != b.getNextBlock())) {
							b.toggleSwitch();
						}
						else if ((nextBlock == b.getPossibleNextBlocks()[1]) && (nextBlock != b.getNextBlock())) {
							b.toggleSwitch();
						}
					}
				}
				else if (t.currentBlock == b.getPossibleNextBlocks()[0]) {
					if (r.route.size() > 1) {
						int nextBlock = r.route.get(1);
						if ((nextBlock == b.blockNumber) && (t.currentBlock != b.getNextBlock())) {
							b.toggleSwitch();
						}
					}

				}
				else if (t.currentBlock == b.getPossibleNextBlocks()[1]) {
					if (r.route.size() > 1) {
						int nextBlock = r.route.get(1);
						if ((nextBlock == b.blockNumber) && (t.currentBlock != b.getNextBlock())) {
							b.toggleSwitch();
						}
					}						
				}
			}
		}
	}
	
	public void setBlocksAsOccupied() {
		for (Train t : CTCWrapper.trainModels) {

			ArrayList<BlockInterface> blockData;
			HashMap<Integer, TrainRoute> trainRoutes;
			if (t.lineName.toLowerCase().startsWith("r")) {
				blockData = redBlockData;
				trainRoutes = redRoutes;
			}
			// Green Line
			else if (t.lineName.toLowerCase().startsWith("g")) {
				blockData = greenBlockData;
				trainRoutes = greenRoutes;
			}
			else {
				continue;
			}
			if (!trainRoutes.containsKey(t.currentBlock)) {
				continue;
			}
			// calculate the distance traveled since the last tick
			double speed = t.currSpeed * 0.488888889; // in yards/second
			double elapsedTime = SimClock.getDeltaMs() * 0.001; //in seconds
			double distanceTraveled = speed*elapsedTime; //yards
			DefaultBlock current = ((DefaultBlock)blockData.get(t.currentBlock));
			// if the distance traveled on the block will be greater than the current
			// block change which block is occupied
			if ((t.distanceTraveledOnBlock + distanceTraveled) > current.blockLength) {
				if (trainRoutes.get(t.currentBlock).route.size() > 1) {
					DefaultBlock next = (DefaultBlock) blockData.get(trainRoutes.get(t.currentBlock).route.get(1));
					next.occupied = true;
					current.occupied = false;
				}
			}
		}
	}

	public TrainRoute addRoute(TrainRoute t) {
		if (t.lineName.toLowerCase().startsWith("r")) {
			redRoutes.put(t.startingBlock, t);
		}
		if (t.lineName.toLowerCase().startsWith("g")) {
			greenRoutes.put(t.startingBlock, t);
		}
		return null;
	}
	
	public void readInTrack(String lineName, String csv) {
		// Read in the track and build the graph that will be sent to the CTCOffice
		BufferedReader file = null;
		try {
			System.out.printf("Loading track data file with csv %s.\n", csv);
			file = new BufferedReader(new FileReader(csv));
			String row = file.readLine();
			TLayout tLayout = new TLayout();
			// split the row into columns to grab the name of the line
			String[] firstRowAttributes = row.split(",", -1);
			// add the all-important yard block as block #0 in this TLayout
			System.out.printf("Adding the yard block to the track layout with name %s.\n", lineName);
			tLayout.blocks.add(new TrackBlock());
            tLayout.layout.addVertex(0);
            // add the TLayout to the TrackModel map of layouts
			System.out.printf("Adding a new TLayout object to list of track layouts with name %s.\n", lineName);
			// create a new section of blocks for the creation of a graph layout later
			TrackSection section = new TrackSection();
			String sectionName = firstRowAttributes[1];
            System.out.printf("Creating new TrackSection for section %s.\n", sectionName);
			tLayout.sections.add(section);
			while (row != null) {
				// split the row into columns which denote the attributes
				String[] attributes = row.split(",", -1);
				// this is an empty row for dividng up sections, let's skip
				if (attributes[0].isEmpty()) {
					row = file.readLine();
					continue;
				}
				// if we are in a new section of blocks, we need to create a new TrackSection object
                if (!sectionName.equals(attributes[1])) {
                    String firstDirection = section.blocks.get(0).direction;
                    String lastDirection = section.blocks.get(section.blocks.size() - 1).direction;
                    // calculate the direction of travel for the whole section
                    section.leastToGreatest = lastDirection.equals("head") || lastDirection.equals("tail/head") || lastDirection.equals("head/head");
                    section.greatestToLeast = firstDirection.equals("head") || firstDirection.equals("head/tail") || firstDirection.equals("head/head");
                    System.out.printf("For section %s, leastToGreatest is %b and greatestToLeast is %b.\n", sectionName, section.leastToGreatest, section.greatestToLeast);
                    // create a new TrackSection for the next set of blocks
                    section = new TrackSection();
                    sectionName = attributes[1];
                    System.out.printf("Section names don't match. Creating a new TrackSection for section %s.\n", sectionName);
                    tLayout.sections.add(section);
                }
				// create a new TrackBlock object from the attributes
				TrackBlock block = createBlock(attributes);
				// populate our TLayout object data structures with the new TrackBlock
				System.out.printf("Adding block number %d to TLayout blocks list.\n", block.number);
				tLayout.blocks.add(block);
				System.out.printf("Adding block number %d to TLayout layout graph.\n", block.number);
				tLayout.layout.addVertex(block.number);
                // add the new TrackBlock object to the current section of blocks
                System.out.printf("Adding block number %d to TrackSection with name %s.\n", block.number, sectionName);
                section.blocks.add(block);
				row = file.readLine();
			}
			String firstDirection = section.blocks.get(0).direction;
            String lastDirection = section.blocks.get(section.blocks.size() - 1).direction;
            // calculate the direction of travel for the whole section
            section.leastToGreatest = lastDirection.equals("head") || lastDirection.equals("tail/head") || lastDirection.equals("head/head");
            section.greatestToLeast = firstDirection.equals("head") || firstDirection.equals("head/tail") || firstDirection.equals("head/head");
            System.out.printf("For section %s, leastToGreatest is %b and greatestToLeast is %b.\n", sectionName, section.leastToGreatest, section.greatestToLeast);
            // connect all of the blocks within sections and to switches
	        tLayout.connectBlocks();
			System.out.printf("Successfully loaded track data file with csv %s.\n", csv);
			CTCWrapper.ctcOffice.setTrackLayout(lineName, tLayout.layout, tLayout.blocks);
		} catch (FileNotFoundException e) {
			System.out.printf("Track data file %s could not be found.\n", csv);
		} catch (IOException e) {
			System.out.printf("There was a problem loading track data file %s.\n", csv);
		} finally {
			try {
				file.close();
			} catch (IOException e) {
				System.out.printf("There was a problem closing track data file %s.\n", csv);
			}
		}
		
		
	}
	

	private TrackBlock createBlock(String[] attributes) {
		TrackBlock block = null;
		// generate a TrackBlock descriptor object with imported attribute values
		Map<String, String> descriptor = new HashMap<String, String>();
		descriptor.put("section", attributes[1]);
		descriptor.put("number", attributes[2]);
		descriptor.put("length", attributes[3]);
		descriptor.put("grade", attributes[4]);
		descriptor.put("speedLimit", attributes[5]);
		descriptor.put("elevation", attributes[8]);
		descriptor.put("cumulativeElevation", attributes[9]);
		// if this block has an "underground" infrastructure
		if (attributes[6].contains("underground")) {
			descriptor.put("underground", "1");
		}
		// if there is a "switch block" associated with this block (connects to a switch)
		if (!attributes[10].isEmpty()) {
			String switchNumber = attributes[10].toLowerCase().replace("switch", "").trim();
			descriptor.put("connectsToSwitch", switchNumber);
		}
		// if there is a "direction" associated with this block (head/tail)
		if (!attributes[11].isEmpty()) {
			descriptor.put("direction", attributes[11].toLowerCase());			
		}
		// if this is the first or last block in the section, add the associated section connection
		if (!attributes[12].isEmpty()) {
			descriptor.put("connectsTo", attributes[12]);
		}
		// split up the raw infrastructure attribute into individual details
		String[] infrastructures = attributes[6].split("[:;]+");
		// if there is at least one infrastructure that we need to process
		if (!infrastructures[0].isEmpty()) {
			for (int i = 0; i < infrastructures.length; i++) {
				switch (infrastructures[i].trim().toLowerCase()) {
					case "switch to yard":
					case "switch from yard":
					case "switch to/from yard":
					case "switch":
						System.out.printf("Creating a TrackSwitch object for block number %s.\n", attributes[2]);
						descriptor.put("infrastructure", "switch");
						// if this is a switch, it should have the block numbers for the "out" blocks
						if (!attributes[13].isEmpty()) {
							descriptor.put("out", attributes[13]);
						}
						block = new TrackSwitch(descriptor);
						break;
					case "station":
						System.out.printf("Creating a TrackStation object for block number %s.\n", attributes[2]);
						descriptor.put("stationName", infrastructures.length > i + 1 ? infrastructures[i + 1].trim() : "");
						descriptor.put("infrastructure", "station");
						block = new TrackStation(descriptor);
						break;
					case "railway crossing":
						System.out.printf("Creating a TrackCrossing object for block number %s.\n", attributes[2]);
						descriptor.put("infrastructure", "crossing");
						block = new TrackCrossing(descriptor);
						break;
					default:
						System.out.printf("Creating a TrackBlock object for block number %s.\n", attributes[2]);
						block = new TrackBlock(descriptor);
						break;
				}
				// if we have already instantiated a block, we're done here!
				if (block != null) {
					break;
				}
			}
		} else {
			System.out.printf("Creating a TrackBlock object for block number %s.\n", attributes[2]);
			block = new TrackBlock(descriptor);
		}
		return block;
	}
	
}
