import java.util.ArrayList;

public class BoundedQueue<T> extends Queue<T> {

	// Fields of the class:

	private int max;// max size of the queue

	// Constructors:

	public BoundedQueue(int max) {
		super();
		this.max = max;
	}

	// Methods of the class:

	public synchronized void insert(T item) {// insert item to the Queue
		while (q.size() == max) {
			try {
				wait();
			} catch (InterruptedException error) {
			}
		}
		super.insert(item);
	}

	public synchronized T extract() { // extracting item from the Queue
		while (q.size() == 0 && !endDay.isEndDay()) {
			try {
				wait();
			} catch (InterruptedException error) {
			}
		}
		if (!endDay.isEndDay()) {
			T item = q.get(0);
			q.remove(item);
			notifyAll();
			return item;
		} else
			return null;
	}
}// class
