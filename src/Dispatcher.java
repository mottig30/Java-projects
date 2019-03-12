
public class Dispatcher extends Thread {

	private static int countTheHandledCalls;// counting all the calls
	private String DispatcherName;
	private Queue<CrimeEvent> crimeEventsQueue;
	private Queue<Call> callsQueue;
	private EndDay endDay;
	private int TotalCalls;
	private static Object syncThisCode = new Object();

	public Dispatcher(String DispatcherName, Queue<Call> callsQueue, Queue<CrimeEvent> crimeEventsQueue,
			int TotalCalls) { // constructor
		this.DispatcherName = DispatcherName;
		this.callsQueue = callsQueue;
		this.crimeEventsQueue = crimeEventsQueue;
		this.TotalCalls = TotalCalls;
		this.endDay = EndDay.getendDaySituation();
	}

	public void run() {
		while (countTheHandledCalls < TotalCalls) { // if there is call that not handled
			Call c = callsQueue.extract();
			if (c != null) {
				if (countTheHandledCalls == TotalCalls) {
					callsQueue.wake();
				}
				try {
					Thread.sleep((long) c.getCallDispatcherTime()); // letting the call wait according to the arrival time
				} catch (InterruptedException eror) {
				}

				CrimeEvent newCrimeEvent = new CrimeEvent(c.getSerialNumber(), c.getAddress(), c.getLevel(),
						c.getArea(), c.getCallTimeBeforQ());// make new crime event
				crimeEventsQueue.insert(newCrimeEvent);// inserting the new event generated from the call to the events queue
				increaseNumOfCalls();
				c.callIsEnded(); // dispatcher finished his work with the call and change tell everyone
			}
		}
	}

	private synchronized static void increaseNumOfCalls() {
		countTheHandledCalls++; // updating the number of calls
	}

	public synchronized void wake() {
		callsQueue.notifyAll();
	}

	public void initializeDoneCalls() {
		countTheHandledCalls = 0;
	}

	public Queue<CrimeEvent> getCrimeEventsQueue() {
		return crimeEventsQueue;
	}
}