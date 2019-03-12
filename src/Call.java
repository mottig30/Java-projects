import java.util.Random;

public class Call extends Thread {

	// Fields of the class:
	private String address;
	private int level;
	private int area;
	private int callTimeBeforQ;
	private double callDispatcherTime;
	private boolean callEnded;
	private Queue<Call> callq;
	private int serialNumber;

	// Constructors:

	public Call(String area, String level, String callDispatcherTime, String callTimeBeforQ, String address,
			Queue<Call> callq) {
		this.address = address;
		this.level = Integer.parseInt(level);
		this.area = Integer.parseInt(area);
		this.callTimeBeforQ = Integer.parseInt(callTimeBeforQ);
		this.callq = callq;
		this.callEnded = false;

		if (this.level == 1)
			this.callDispatcherTime = Double.parseDouble(callDispatcherTime) * 1000 + random(1);
		if (this.level == 2)
			this.callDispatcherTime = Double.parseDouble(callDispatcherTime) * 1000 + random(2);
		if (this.level == 3)
			this.callDispatcherTime = Double.parseDouble(callDispatcherTime) + random(3);
	}

	// Methods of the class:

	public void run() {
		try {
			Thread.sleep(1000 * callTimeBeforQ);// the time of the call before entering to the q
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		callq.insert(this);
		try {
			waitingCall(); // waiting until call finished
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private synchronized void waitingCall() throws InterruptedException {
		while (!callEnded) {
			this.wait();
		}
	}

	public synchronized void callIsEnded() {
		this.callEnded = true;
		this.notifyAll();
	}

	public int random(int level) {
		Random rand = new Random();
		if (level == 2) {
			int n = 500 + rand.nextInt(500);
			return n;
		} else if (level == 1) {
			int n = 1000 + rand.nextInt(1000);
			return n;
		} else {
			int n = 20000 + rand.nextInt(1000);
			return n;
		}
	}

	public String getAddress() {
		return address;
	}

	public int getLevel() {
		return level;
	}

	public int getArea() {
		return area;
	}

	public int getCallTimeBeforQ() {
		return callTimeBeforQ;
	}

	public double getCallDispatcherTime() {
		return callDispatcherTime;
	}

	public int getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNum(int i) {
		this.serialNumber = i;

	}
}// class