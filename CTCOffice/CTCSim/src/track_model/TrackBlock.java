package track_model;

import java.util.Map;

public class TrackBlock {
	
	public final static double METERTOYARD = 1.09361;
	public final static double KPHTOMPH = 0.62137;
	
	// attributes of the TrackBlock read in from data file
	public final int number;
	public final String section;
	public final String infrastructure;
	public final double speedLimit;
	public final double length;
	public final double grade;
	public final double elevation;
	public final double cumulativeElevation;
	public final boolean underground;
	
	// helper attributes for the purpose of building the track layout
	public final int[] connectsTo;
	public final String direction;
	
	// attributes of TrackBlock controllable by system
	public String weather;
	public String failure;
	public double commandedSpeed;
	public double commandedAuthority;
	public boolean occupancy;
	public boolean heater;
	public boolean lights;
	
	/**
	 * Creates a special instance of the TrackBlock class for the yard block.
	 * The only identifying information is that it is block #0 and no attributes are valid.
	 */	
	public TrackBlock() {
		this.number = 0;
		this.section = null;
		this.infrastructure = "yard";
		this.speedLimit = 0;
		this.length = 0;
		this.grade = 0;
		this.elevation = 0;
		this.cumulativeElevation = 0;
		this.underground = false;
		this.connectsTo = null;
		this.direction = null;
		this.occupancy = false;
	}
	
	/**
	 * Creates a new instance of the TrackBlock class.
	 * @param descriptor The descriptor of the TrackBlock read in from the track data file.
	 */	
	public TrackBlock(Map<String, String> descriptor) {
		this.number = Integer.parseInt(descriptor.get("number"));
		this.section = descriptor.get("section");
		this.infrastructure = descriptor.get("infrastructure");
		this.speedLimit = Double.parseDouble(descriptor.get("speedLimit")) * KPHTOMPH;
		this.length = Double.parseDouble(descriptor.get("length")) * METERTOYARD;
		this.grade = Double.parseDouble(descriptor.get("grade"));
		this.elevation = Double.parseDouble(descriptor.get("elevation")) * METERTOYARD;
		this.cumulativeElevation = Double.parseDouble(descriptor.get("cumulativeElevation")) * METERTOYARD;
		this.underground = descriptor.get("underground") != null ? true : false;
		String connectsToString = descriptor.get("connectsTo");
		if (connectsToString != null) {
			String[] connectsToArr = connectsToString.split(";", -1);
			this.connectsTo = new int[connectsToArr.length];
			for (int i = 0; i < connectsToArr.length; i++) {
				this.connectsTo[i] = Integer.parseInt(connectsToArr[i]);
			}
		} else {
			this.connectsTo = null;
		}
		this.direction = descriptor.get("direction");
		this.weather = null;
		this.failure = null;
		this.commandedSpeed = 0;
		this.commandedAuthority = 0;
		this.occupancy = false;
		this.heater = false;
		this.lights = false;
	}
	
	/**
	 * Checks if the TrackBlock has weather that would affect kinematics equations.
	 * @return The TrackBlock has adverse weather.
	 */	
	public boolean hasAdverseWeather() {
		return weather != null;
	}
	
	/**
	 * Checks if the TrackBlock is in a state of failure.
	 * @return The TrackBlock is in a state of failure.
	 */	
	public boolean hasFailure() {
		return failure != null;
	}
	
}