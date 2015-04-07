package track_model;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedMultigraph;

public class TrackModel {

	private TrackBlock yard;
	private Map<String, TrackLayout> trackLayouts;
	
	public TrackModel() {
		System.out.println("Begin TrackModel initialization...");
		trackLayouts = new HashMap<String, TrackLayout>();
		yard = new TrackBlock();
		loadTracks(new String[] {"green.csv", "red.csv"});
		System.out.println("Finish TrackModel initialization.");
	}
	
	/**
	 * Retrieves all the TrackLayout objects in the model.
	 * @return A map of all the TrackLayout objects in the model.
	 */	
	public Map<String, TrackLayout> getLines() {
		return trackLayouts;
	}
	
	/**
	 * Retrieves the TrackBlock corresponding to a specific train and its total distance traveled.
	 * @param trainID The unique identifier of the train requesting the TrackBlock.
	 * @param totalDistance The total distance traveled by the train from the yard.
	 * @return The TrackBlock on which the train with unique identifier trainID is located.
	 */	
	public TrackBlock getCurrentBlock(int trainID, int totalDistance) {
		return null;
	}
	
	/**
	 * Creates a TrackBlock object using the attributes given.
	 * @param attributes The attributes describing the TrackBlock object read in from track data file.
	 * @return A complete TrackBlock object.
	 */	
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
			descriptor.put("direction", attributes[11]);			
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
	
	/**
	 * Retrieves the TrackLayout object corresponding to the passed track line name.
	 * If TrackLayout object does not exist yet, it is created and the new instance is returned.
	 * @param lineName The name of the track line.
	 * @return The TrackLayout corresponding to the passed track line name.
	 */	
	private TrackLayout getLine(String lineName) {
		if (trackLayouts.containsKey(lineName)) {
			System.out.printf("Track line with name %s already exists. Returning instance.\n", lineName);
			return trackLayouts.get(lineName);
		} else {
			System.out.printf("Creating a new track line with name %s.\n", lineName);
			TrackLayout newLine = new TrackLayout();
			List<TrackBlock> blocks = newLine.blocks;
			blocks.add(yard);
			DirectedMultigraph<Integer, DefaultEdge> layout = newLine.layout;
			layout.addVertex(0);
			trackLayouts.put(lineName, newLine);
			return newLine;
		}
	}
	
	/**
	 * Loads all of the track data files with the given filenames.
	 * @param filenames The list of filenames of the track data files to load.
	 */	
	private void loadTracks(String[] filenames) {
		for (String filename : filenames) {
			loadTrack(filename);
		}
	}
	
	/**
	 * Loads the track data file with the given filename.
	 * @param filename The filename of the track data file to load.
	 */	
	private void loadTrack(String filename) {
		BufferedReader file = null;
		try {
			System.out.printf("Loading track data file with filename %s.\n", filename);
			file = new BufferedReader(new FileReader(filename));
			String header = file.readLine();
			String row = file.readLine();
			TrackLayout trackLayout = new TrackLayout();
			// split the row into columns to grab the name of the line
			String lineName = row.split(",", -1)[0];
			System.out.printf("Adding a new TrackLayout object to list of track layouts with name %s.\n", lineName);
			trackLayouts.put(lineName, trackLayout);
			while (row != null) {
				// split the row into columns which denote the attributes
				String[] attributes = row.split(",", -1);
				// create a new TrackBlock object from the attributes
				TrackBlock block = createBlock(attributes);
				// populate our TrackLayout object with the new TrackBlock
				System.out.printf("Adding block number %d to TrackLayout blocks list.\n", block.number);
				trackLayout.blocks.add(block);
				System.out.printf("Adding block number %d to TrackLayout layout graph.\n", block.number);
				trackLayout.layout.addVertex(block.number);
				// populate the switch-block number reference map with this TrackSwitch object
				if (block.infrastructure != null && block.infrastructure.equals("switch")) {
					System.out.printf("Adding a switch #%d to block #%d reference to TrackLayout.\n", block.connectsToSwitch, block.number);
					trackLayout.switchToBlocks.put(block.connectsToSwitch, block.number);
				}
				row = file.readLine();
			}
			System.out.printf("Successfully loaded track data file with filename %s.\n", filename);
		} catch (FileNotFoundException e) {
			System.out.printf("Track data file %s could not be found.\n", filename);
		} catch (IOException e) {
			System.out.printf("There was a problem loading track data file %s.\n", filename);
		} finally {
			try {
				file.close();
			} catch (IOException e) {
				System.out.printf("There was a problem closing track data file %s.\n", filename);
			}
		}
	}
	
}