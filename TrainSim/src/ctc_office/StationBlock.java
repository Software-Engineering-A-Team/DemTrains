package ctc_office;

public class StationBlock extends TrackBlock {
	public String stationName;

	public StationBlock(int num, double length, int sLimit, boolean occupiedStatus, boolean brokenStatus, String name) {
		super(num, length, sLimit, occupiedStatus, brokenStatus);
		stationName = name;
	}
}
