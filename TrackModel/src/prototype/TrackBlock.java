package prototype;

import java.util.Map;

enum TrackWeather {
	Clear, Snow, Rain, Ice;
}

enum TrackLights {
	Green, Red;
}

enum TrackFailure {
	None, BrokenRail, CircuitFailure, PowerFailure;
}

enum TrackInfrastructure {
	
	None( "none" ), 
	Switch( "switch" ),
	Station( "station" ), 
	Crossing( "crossing" );
	
	TrackInfrastructure( String type ) {
		
	}
	
}

enum TrackDirection {
	Head, Tail;
}

public class TrackBlock {
	
	// properties read in from track layout file
	private final String section;
	private final int number;
	private final int speedLimit;
	private final double length;
	private final double grade;
	private final double elevation;
	private final double cumulativeElevation;
	private final boolean underground;
	private final String infrastructure;
	private final String direction; // needs confirmation
	
	// properties controllable by other sub-systems
	private int occupancy;
	private int commandedSpeed;
	private int commandedAuthority;
	private boolean heater;
	private TrackWeather weather;
	private TrackLights lights;
	private TrackFailure failure;
	
	public TrackBlock() {
		throw new UnsupportedOperationException( "Track block must be initialized with complete data." );
	}
	
	public TrackBlock( Map<String, String> descriptor ) {
		this.section = descriptor.get( "section" );
		this.number = Integer.parseInt( descriptor.get( "number" ) );
		this.speedLimit = Integer.parseInt( descriptor.get( "speedLimit" ) );
		this.length = Double.parseDouble( descriptor.get( "length" ) );
		this.grade = Double.parseDouble( descriptor.get( "grade" ) );
		this.elevation = Double.parseDouble( descriptor.get( "elevation" ) );
		this.cumulativeElevation = Double.parseDouble( descriptor.get( "cumulativeElevation" ) );
		this.underground = descriptor.get( "underground" ).equals( "1" ) ? true : false;
		this.infrastructure = descriptor.get( "infrastructure" ); // needs changing
		this.direction = descriptor.get( "direction" ); // needs changing
		this.occupancy = 0;
		this.commandedSpeed = 0;
		this.commandedAuthority = 0;
		this.heater = false;
		this.weather = TrackWeather.Clear;
		this.lights = TrackLights.Green;
		this.failure = TrackFailure.None;
	}
	
	public int getNumber() {
		return this.number;
	}
	
	public String[][] getModel() {
		return new String[][] {
				{ "Section", this.section },
				{ "Number", String.valueOf( this.number ) },
				{ "Length (m)", String.valueOf( this.length ) },
				{ "Speed Limit (km/hr)", String.valueOf( this.speedLimit ) },
				{ "Grade (%)", String.valueOf( this.grade ) },
				{ "Elevation (m)", String.valueOf( this.elevation ) },
				{ "Cumulative Elevation (m)", String.valueOf( this.cumulativeElevation ) },
				{ "Underground", String.valueOf( this.underground ) },
				{ "Infrastructure", this.infrastructure },
				{ "Direction", this.direction },
				{ "Occupancy", String.valueOf( this.occupancy ) },
				{ "Commanded Speed (km/hr)", String.valueOf( this.commandedSpeed ) },
				{ "Commanded Authority (m)", String.valueOf( this.commandedAuthority ) },
				{ "Heater", String.valueOf( this.heater ) },
				{ "Weather", "Clear" },
				{ "Lights", "Green" },
				{ "Failure", "None" }
		};
	}
	
}