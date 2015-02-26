package prototype;

import java.util.Map;

public class TrackSwitch extends TrackBlock {
	
	int switchNumber;
	
	public TrackSwitch( Map<String, String> descriptor ) {
		super( descriptor );
		this.switchNumber = Integer.parseInt( descriptor.get( "switchNumber" ) );
	}
	
}