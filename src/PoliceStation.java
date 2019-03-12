public class PoliceStation {
	private int Commanders;
	private int Motorcycles; // number of motorcycles counter
	private int Cars; // number of cars counter
	private static Object synchThisCode = new Object();

	PoliceStation(int x, int y, int z) { // constructor
		Commanders = 5 + x;
		Motorcycles = 50 + y;
		Cars = 10 + z;
		if (x > 8) {// if the input is higher then 8, the default is 8
			System.out.println("The number of commanders initialized to 8!");
			Commanders = 8;
		}
	}

	public synchronized boolean removeNumOfCommanders(int numCommandersToRemove) {// removing the number of commanders from the station
		if (numCommandersToRemove <= Commanders) {
			Commanders = Commanders - numCommandersToRemove;
			return true;
		}
		return false;
	}

	public synchronized boolean removeCarsAndMotorcycle(int numCarsToRemove, int numMotorcycleToRemove) { // removing trucks and planes as specified
		if (numCarsToRemove <= Cars && numMotorcycleToRemove <= Motorcycles) {
			addNumOfCarsAndMotorcycle(-numCarsToRemove, -numMotorcycleToRemove);
			return true;
		}
		return false;
	}

	public synchronized void addNumOfCarsAndMotorcycle(int numCarsToAdd, int numMotorcycleToAdd) { // adding trucks and planes as specified
		synchronized (synchThisCode) {
			synchThisCode.notifyAll();
		}
		Cars = Cars + numCarsToAdd;
		Motorcycles = Motorcycles + numMotorcycleToAdd;
	}

	public synchronized void addNumOfCommanders(int numCommandersToAdd) {
		Commanders = Commanders + numCommandersToAdd;
	}

	public int getNumOfMotorcycles() {
		return Motorcycles;
	}

	public Object synchThisCode() {
		return synchThisCode;
	}
}