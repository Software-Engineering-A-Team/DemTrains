package ctcOffice;

public class Station extends Block {
	private final String stationName;
	public Station(String[] blockDescriptor){
		super(blockDescriptor);
		stationName = blockDescriptor[6].substring(blockDescriptor[6].indexOf(":") + 1);
	}
	public String getStationName(){
		return stationName;
	}
}
