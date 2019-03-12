public class CrimeEvent extends Thread {
	private int serialNum;
	private String address;
	private int eventLevel;
	private int eventLocation;
	private double callArrivalTime;
	private int distance;

	public CrimeEvent(int serialNum, String address, int eventLevel, int eventLocation, double callArrivalTime) { // constructor
		this.serialNum = serialNum;
		this.callArrivalTime = callArrivalTime;
		this.address = address;
		this.eventLevel = eventLevel;
		this.eventLocation = eventLocation;
	}

	public int getEventLevel() {
		return eventLevel;
	}

	public int getSerialNum() {
		return serialNum;
	}

	public String getAddress() {
		return address;
	}

	public void setDistanceCalculated(int distance) {
		this.distance = distance;
	}

	public int getDistanceCalculated() {
		return distance;
	}

	public double getCallArrivalTime() {
		return callArrivalTime;
	}

	public int getEventLocation() {
		return eventLocation;
	}
}