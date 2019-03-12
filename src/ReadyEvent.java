public class ReadyEvent {
	private String address;
	private double distance;
	private int eventLevel;
	private int numOfMotorcycle;
	private int numOfCars;
	
	public ReadyEvent(String address, double distance, int eventLevel, int numOfMotorcycle, int numOfCars) {
		this.address = address;
		this.distance = distance;
		this.eventLevel = eventLevel;
		this.numOfMotorcycle = numOfMotorcycle;
		this.numOfCars = numOfCars;
	}

	public String getAddress() {
		return address;
	}

	public double getDistance() {
		return distance;
	}

	public int getEventLevel() {
		return eventLevel;
	}

	public int getNumOfMotorcycle() {
		return numOfMotorcycle;
	}

	public int getNumOfCars() {
		return numOfCars;
	}
}
