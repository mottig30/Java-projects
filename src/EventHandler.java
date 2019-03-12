
public class EventHandler extends Thread {

	// Fields of the class:

	private Queue<CrimeEvent> q;
	private String EventHandlerName;
	private EndDay dayEnded;
	private InformationSystem infoSystem;

	// Constructors:

	public EventHandler(String EventHandlerName, Queue<CrimeEvent> q, InformationSystem infoSystem) {
		this.EventHandlerName = EventHandlerName;
		this.dayEnded = EndDay.getendDaySituation();
		this.q = q;
		this.infoSystem = infoSystem;
	}

	// Methods of the class:

	public void run() {
		while (!dayEnded.isEndDay()) {// the day isnot over
			CrimeEvent newEvent = q.extract();
			if (newEvent != null) {
				int distance = calcDistanceToEvent(newEvent);
				newEvent.setDistanceCalculated(distance);
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				infoSystem.addEvent(newEvent);
				System.out.println("Notice - New Emergency " + newEvent.getSerialNum());
			}
		}
	}

	private int calcDistanceToEvent(CrimeEvent c) {
		Character firstChar = c.getAddress().charAt(0);
		String word = c.getAddress();
		String[] arr = word.split(" ");
		int size = arr.length;
		if (Character.isDigit(firstChar)) {
			int number = Character.getNumericValue(firstChar);
			return number + size;
		} else
			return size;
	}
}// class
