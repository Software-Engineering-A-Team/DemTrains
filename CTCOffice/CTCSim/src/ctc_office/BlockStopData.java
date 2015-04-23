package ctc_office;

public class BlockStopData extends StopData {
	public final int endBlock;
	public final double speed;
	public final double authority;
	public BlockStopData(int b, double s, double a){
		super(null, null, -1);
		endBlock = b;
		speed = s;
		authority = a;
	}
	
}
