public class EventCommander extends Thread {

	private PoliceStation pStation;
	private PoliceStationManeger pManeger;
	private BoundedQueue<ReadyEvent> ReadyEventBoundedQueue;
	private EndDay endDay;
	private static int toCheck = 0;

	public EventCommander(PoliceStation pStation, PoliceStationManeger pManeger,
			BoundedQueue<ReadyEvent> ReadyEventBoundedQueue) {
		this.pStation = pStation;
		this.pManeger = pManeger;
		this.ReadyEventBoundedQueue = ReadyEventBoundedQueue;
		this.endDay = EndDay.getendDaySituation();
	}

	public void run() { // starting commander thread
		while (!endDay.isEndDay()) { // as long as the day isn't over
			if (!checkCommanderAndTake())
				break;
			ReadyEvent readyEvent = ReadyEventBoundedQueue.extract();
			if (readyEvent != null) {
				checkVehicleAndTake(readyEvent);
				try {
					sleep((CalculateTimeForHandle(readyEvent)) * 1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				closingEvent(readyEvent);
			}
		}
	}

	private boolean checkCommanderAndTake() {// check if there are enough commanders, taking 1 and returns true
		while (!pStation.removeNumOfCommanders(1)) {
			if (endDay.isEndDay())
				return false; // if the day is over, no commander needed
			try {
				pStation.synchThisCode().wait(); // wait until there is commander to take
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	private boolean checkVehicleAndTake(ReadyEvent readyEvent) { // take the vehicle to the ready event
		while (!pStation.removeCarsAndMotorcycle(readyEvent.getNumOfCars(), readyEvent.getNumOfMotorcycle())) {
			if (endDay.isEndDay())
				return false; // if the day is over, no vehicles needed
			try {
				pStation.synchThisCode().wait(); // wait until there is vehicles to take
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	private long CalculateTimeForHandle(ReadyEvent readyEvent) { // Calculate the time for handle the event
		return (long) (((readyEvent.getEventLevel() * 2) + readyEvent.getDistance())
				/ (readyEvent.getNumOfMotorcycle() + readyEvent.getNumOfCars()));
	}

	private void closingEvent(ReadyEvent readyEvent) {
		pStation.addNumOfCarsAndMotorcycle(readyEvent.getNumOfCars(), readyEvent.getNumOfMotorcycle()); // returning the used vehicles back to the station
		pStation.addNumOfCommanders(1); // returning the used commander back to the station
		pManeger.UpdateHandeledEvents(); // updating the event counter
		if (readyEvent.getEventLevel() == 5) {
			pManeger.UpdateSevereEvents();
		} // to tell the manager if the event was severe
	}
}
