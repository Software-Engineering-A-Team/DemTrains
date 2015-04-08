package ctc_office;

public class StationBlock extends DefaultBlock {
	public String stationName;

	public StationBlock(int num, double length, double sLimit, boolean occupiedStatus, boolean brokenStatus, String name) {
		super(num, length, sLimit, occupiedStatus, brokenStatus);
		stationName = name;
	}
}
