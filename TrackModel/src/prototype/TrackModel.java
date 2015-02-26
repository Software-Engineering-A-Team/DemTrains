package prototype;

import java.util.Map;
import java.util.HashMap;

import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.BufferedReader;

import java.lang.Exception;
import java.lang.IllegalArgumentException;

public class TrackModel {

	Map<String, TrackLayout> lines;

	public TrackModel() {
		lines = new HashMap<String, TrackLayout>();
	}
	
	public String[] getLineNames() {
		String[] lineNames = new String[lines.size()];
		int i = 0;
		for ( String key : lines.keySet() ) {
			lineNames[i++] = key;
		}
		return lineNames;
	}
	
	/**
	 * Populates the track model with track line data from specified file.
	 * @param String The filename of the track layout data.
	 * @throws IllegalArgumentException, IOException
	 */
	public boolean importLineFromFile( String filename ) throws IllegalArgumentException {
		if ( filename.isEmpty() ) {
			throw new IllegalArgumentException( "Filename must not be empty." );
		}
		try {
			BufferedReader file = new BufferedReader( new FileReader( filename ) );
			try {
				String nextLine = file.readLine();
				while ( ( nextLine = file.readLine() ) != null ) {
					TrackBlock block = null;
					// properly formatted data file should have 12 columns
					String[] columns = nextLine.split( ",", -1 );
					columns[6] = columns[6].toLowerCase();
					if ( columns.length != 12 ) {
						System.out.println( "Number of columns: " + columns.length );
						throw new Exception( "File import failed because file is improperly formatted." );
					}
					// populate a descriptor object with parsed data for a generic track block
					Map<String, String> descriptor = new HashMap<String, String>();
					String lineName = columns[0];
					descriptor.put( "section", columns[1] );
					descriptor.put( "number", columns[2] );
					descriptor.put( "length", columns[3] );
					descriptor.put( "grade", columns[4] );
					descriptor.put( "speedLimit", columns[5] );
					descriptor.put( "elevation", columns[8] );
					descriptor.put( "cumulativeElevation", columns[9] );
					descriptor.put( "underground", String.valueOf( columns[6].contains( "underground" ) ) );
					// split up infrastructure into separate details
					String[] infra = columns[6].split( "[:;]+" );
					for ( int i = 0; i < infra.length; i++ ) {
						switch ( infra[i].trim() ) {
							case "switch to yard":
							case "switch from yard":
							case "switch to/from yard":
							case "switch":
								// parse the unique switch ID from column
								descriptor.put( "switchNumber", columns[10].substring( 7 ) );
								descriptor.put( "infrastructure", "switch" );
								block = new TrackSwitch( descriptor );
								break;
							case "station":
								String stationName;
								// parse the unique station name from column
								if ( infra.length > i + 1 && !infra[i + 1].isEmpty() ) {
									stationName = infra[i + 1].trim();
								} else {
									stationName = "n/a";
								}
								descriptor.put( "stationName", stationName );
								descriptor.put( "infrastructure", "station" );
								block = new TrackStation( descriptor );
								break;
							case "railway crossing":
								descriptor.put( "infrastructure", "crossing" );
								block = new TrackCrossing( descriptor );
								break;
							default:
								block = new TrackBlock( descriptor );
								break;
						}
					}
					TrackLayout line;
					if ( lines.containsKey( lineName ) ) {
						line = lines.get( lineName );
					} else {
						line = addLine( lineName );
					}
					line.addBlock( block );
				}
			} catch ( IOException e ) {
				System.out.printf( "IOException: %s\n", e );
				return false;
			} catch ( Exception e ) {
				System.out.printf( "Exception: %s\n", e );
				return false;
			} finally {
				file.close();
			}
		} catch ( FileNotFoundException e ) {
			System.out.printf( "The file %s does not exist.\n", filename );
			return false;
		} catch ( IOException e ) {
			System.out.printf( "IOException: %s\n", e );
		}
		return true;
	}
	
	/**
	 * Verifies the integrity of the row of imported track data.
	 * @param String[] Columns of the track data file row.
	 * @return boolean The row is valid.
	 */
	public boolean verifyRow( String[] columns ) {
		// verifies block number, length, grade, and speed limit
		for ( int i = 1; i < 5; i++ ) {
			if ( !verifyInteger( columns[i] ) ) {
				return false;
			}
		}
		// verifies elevation and cumulative elevation
		for ( int i = 7; i < 9; i++ ) {
			if ( !verifyInteger( columns[i] ) ) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Verifies that the string passed consists of an integer.
	 * @param String Potential integer string.
	 * @return boolean The string consists of an integer.
	 */
	public boolean verifyInteger( String str ) {
		try {
			Integer.parseInt( str );
		} catch ( NumberFormatException e ) {
			return false;
		}
		return true;
	}
	
	/**
	 * Adds a new track line object to the collection of lines.
	 * @param String The name of the new track line.
	 * @return TrackLayout The track line object for the passed name.
	 * @throws Exception, IllegalArgumentException
	 */
	public TrackLayout addLine( String lineName ) throws Exception, IllegalArgumentException {
		if ( lineName.isEmpty() ) {
			throw new IllegalArgumentException( "Line name must not be empty." );
		}
		if ( lines.containsKey( lineName ) ) {
			return lines.get( lineName );
		}
		TrackLayout line = new TrackLayout( lineName );
		lines.put( lineName, line );
		return line;
	}
	
	/**
	 * Retrieves the track line object with the specified name.
	 * @return TrackLayout The track line object.
	 */
	public TrackLayout getLine( String lineName ) {
		return lines.get( lineName );
	}
	
}
