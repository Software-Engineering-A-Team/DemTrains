package track_controller;

public interface PLCInterface {
	public boolean ctrlCrossing() {}
	public boolean ctrlSwitch() {}
	public boolean ctrlHeater() {}
	public boolean ctrlLights() {}
	public boolean ctrlSpeedAuthority(double speed, double authority) {}
	public boolean ctrlBlockClosed() {}
}
