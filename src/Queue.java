
import java.util.ArrayList;

public class Queue<T> {

	// Fields of the class:

	protected ArrayList<T> q;
	protected EndDay endDay;

	public Queue() {
		q = new ArrayList<T>();
		this.endDay = EndDay.getendDaySituation();
	}
	// Methods of the class:

	public synchronized void insert(T item) {// insert item to the Queue
		q.add(item);
		notifyAll();
	}

	public synchronized T extract() {// extract item from the Queue
		while (q.size() == 0 && !endDay.isEndDay()) {
			try {
				wait();
			} catch (InterruptedException error) {
			}
		}

		if (endDay.isEndDay()) {
			return null;
		} else {
			T itemOnList = q.get(0);
			q.remove(itemOnList);
			return itemOnList;
		}
	}

	public synchronized void wake() {
		this.notifyAll();
	}

	public int size() {
		return q.size();
	}

	public T get(int i) {
		return q.get(i);
	}

}// class