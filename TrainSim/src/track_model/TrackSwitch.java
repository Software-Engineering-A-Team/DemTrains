package track_model;

import java.util.Map;

public class TrackSwitch extends TrackBlock {
	
	public boolean state;
	public int[] out;
	
	/**
	 * Creates a new instance of the TrackSwitch subclass.
	 * @param descriptor The descriptor of the TrackSwitch read in from the track data file.
	 */	
	public TrackSwitch(Map<String, String> descriptor) {
		super(descriptor);
		this.state = false;
		this.out = new int[2];
		String[] outs = descriptor.get("out").split(";", -1);
		for (int i = 0; i < outs.length; i++) {
		    this.out[i] = Integer.parseInt(outs[i]);
		}
	}
	
}