import java.util.ArrayList;;

public class PoliceStationManeger extends Thread {
	private EndDay endDay;
	private int numHandeledEvent;
	private int numOfCalls;
	private ArrayList<StationWorker> stationWorkers;
	private ArrayList<EventHandler> eventHandlers;
	private ArrayList<Dispatcher> dispatchers;
	private int numEventSevere;
	private PoliceStation pStation;

	public PoliceStationManeger(int numOfCalls, ArrayList<StationWorker> stationWorkers,
			ArrayList<EventHandler> eventHandlers, ArrayList<Dispatcher> dispatchers, PoliceStation pStation) {// Manager constructor
		this.numOfCalls = numOfCalls;
		this.stationWorkers = stationWorkers;
		this.eventHandlers = eventHandlers;
		this.dispatchers = dispatchers;
		this.endDay = EndDay.getendDaySituation();
		this.pStation = pStation;
		numHandeledEvent = 0;
		numEventSevere = 0;
	}

	public void run() { // starting manager thread
		waitForCallsToEnter();
		endDay.setEndDay(true); // to tell everyone that the day is over
		synchronized (pStation.synchThisCode()) {
			pStation.synchThisCode().notifyAll();
		}
		printSummary();
	}

	private synchronized void waitForCallsToEnter() {
		while (numOfCalls > numHandeledEvent) { // "wait" until all the event handled
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized void UpdateHandeledEvents() { // updating the handled event counter and notify to handled the next event
		numHandeledEvent++;
		this.notifyAll();
	}

	public synchronized void UpdateSevereEvents() { // count the severe events
		numEventSevere++;
	}

	public void printSummary() { // printing the summary of the handled events
		System.out.println("Number of Events: " + numHandeledEvent);
		System.out.println("Number of Severe Events: " + numEventSevere);
	}

	public int getHandeledEventcounter() {
		return numHandeledEvent;
	}

	public int getNumOfCalls() {
		return numOfCalls;
	}
}