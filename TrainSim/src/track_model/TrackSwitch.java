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
		String outString = descriptor.get("out");
		if (outString != null) {
			String[] outArr = outString.split(";", -1);
			this.out = new int[outArr.length];
			for (int i = 0; i < outArr.length; i++) {
				this.out[i] = Integer.parseInt(outArr[i]);
			}
		} else {
			this.out = null;
		}
	}
	
}