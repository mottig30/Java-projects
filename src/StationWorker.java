public class StationWorker extends Thread {
	private String workerName;
	private EndDay endDay;
	private int timeToWork;
	private PoliceStation station;
	private InformationSystem infoSystem; // All the worker work with the same information system
	private BoundedQueue<ReadyEvent> ReadyEventBoundedQueue;

	public StationWorker(String name, PoliceStation station, double timeToWork,
			BoundedQueue<ReadyEvent> ReadyEventBoundedQueue, InformationSystem infoSystem) {// constructor
		this.workerName = name;
		this.station = station;
		this.endDay = EndDay.getendDaySituation();
		this.timeToWork = (int) timeToWork;
		this.ReadyEventBoundedQueue = ReadyEventBoundedQueue;
		this.infoSystem = infoSystem;
	}

	public void run() { // starting the station worker thread
		while (!endDay.isEndDay()) {// the day is not finish
			CrimeEvent e = infoSystem.removeEvent();
			if (e != null) {
				try {
					sleep(timeToWork);
				} catch (InterruptedException E) {
					E.printStackTrace();
				}
				int available = station.getNumOfMotorcycles();
				int numOfMotorcycles;
				int numOfCars = 0;
				if (e.getEventLevel() == 1) {
					numOfMotorcycles = Math.max(2, available);
					if (numOfMotorcycles > 3)
						numOfMotorcycles = 3;
				} else if (e.getEventLevel() == 2) {
					numOfMotorcycles = Math.max(2, available) + 1;
					if (numOfMotorcycles > 4)
						numOfMotorcycles = 4;
				} else if (e.getEventLevel() == 3) {
					numOfMotorcycles = Math.max(2, available) + 3;
					numOfCars = 1;
					if (numOfMotorcycles > 6)
						numOfMotorcycles = 6;
				} else if (e.getEventLevel() == 4) {
					numOfMotorcycles = Math.max(2, available) + 5;
					numOfCars = 2;
					if (numOfMotorcycles > 8)
						numOfMotorcycles = 8;
				} else {
					numOfMotorcycles = Math.max(2, available) + 6;
					numOfCars = 3;
					if (numOfMotorcycles > 10)
						numOfMotorcycles = 10;
				}
				ReadyEvent newReadyEvent = new ReadyEvent(e.getAddress(), e.getDistanceCalculated(), e.getEventLevel(),
						numOfMotorcycles, numOfCars); // creating new "ready event"
				ReadyEventBoundedQueue.insert(newReadyEvent); // adding the ready event to the ready events queue
			}
		}
	}
}
