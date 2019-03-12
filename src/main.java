import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class main {

	private int numOfCommanders;
	private int numOfcars;
	private int numOfmotorscycels;
	private int StationWorkersWorkingTime;
	private EndDay endDay;

	public main(int numOfCommanders, int numOfcars, int numOfmotorscycels, int StationWorkersWorkingTime) { // constructor
		this.numOfCommanders = numOfCommanders;
		this.numOfcars = numOfcars;
		this.numOfmotorscycels = numOfmotorscycels;
		this.StationWorkersWorkingTime = StationWorkersWorkingTime;
		this.endDay = EndDay.getendDaySituation();
	}

	// read file methods:

	private ArrayList<Call> readAndCreatCalls(Queue<Call> CallsQ) throws IOException { // reading calls from file and creating them
		ArrayList<Call> newCallsArray = new ArrayList<Call>();
		BufferedReader br = new BufferedReader(new FileReader("src/callsData.txt"));
		try {
			br.readLine();
			String line = br.readLine();
			while (line != null) {
				Call c = readAndCraetCallObject(line, CallsQ);
				newCallsArray.add(c);
				line = br.readLine();
			}
		} finally {
			br.close();
		}
		return newCallsArray;
	}

	private Call readAndCraetCallObject(String line, Queue<Call> CallsQ) { // creating the call
		String[] arr = line.split("\t");
		Call c2 = new Call(arr[0], arr[1], arr[2], arr[3], arr[4], CallsQ);
		return c2;
	}

	private void intialize() {
		endDay.setEndDay(false);
	}

	public void startStation() throws IOException {
		intialize();
		StationWorkersWorkingTime = StationWorkersWorkingTime * 1000;
		PoliceStation Station = new PoliceStation(numOfCommanders, numOfmotorscycels, numOfcars); // creating station
		Queue<Call> CallsQ = new Queue<Call>(); // creating calls queue
		ArrayList<Call> CALLS = readAndCreatCalls(CallsQ); // list of all the calls
		ArrayList<Dispatcher> Dispatchers = new ArrayList<Dispatcher>();
		ArrayList<EventHandler> EventHandlers = new ArrayList<EventHandler>();
		ArrayList<StationWorker> StationWorkers = new ArrayList<StationWorker>();
		ArrayList<Thread> Threads = new ArrayList<Thread>(); // array list that contains all the threads
		int numOfCalls = CALLS.size();
		Queue<CrimeEvent> CrimeEventQ = new Queue<CrimeEvent>();
		BoundedQueue<ReadyEvent> ReadyEventQ = new BoundedQueue<ReadyEvent>(15);
		DataBase DB = new DataBase();
		InformationSystem infoSystem = new InformationSystem(DB);

		for (int i = 0; i < 5; i++) { // creating the dispatchers
			Dispatcher Dispatcher = new Dispatcher("D1", CallsQ, CrimeEventQ, numOfCalls);
			Dispatcher.initializeDoneCalls();
			Dispatchers.add(Dispatcher);
			Threads.add(Dispatcher);
		}

		for (int i = 0; i < 3; i++) {// creating the EventHandlers
			EventHandler EventHandler = new EventHandler("E1", CrimeEventQ, infoSystem);
			EventHandlers.add(EventHandler);
			Threads.add(EventHandler);
		}

		for (int i = 0; i < 3; i++) {// creating the StationWorkers
			StationWorker StationWorker = new StationWorker("S1", Station, StationWorkersWorkingTime, ReadyEventQ,
					infoSystem);
			StationWorkers.add(StationWorker);
			Threads.add(StationWorker);
		}

		PoliceStationManeger maneger = new PoliceStationManeger(numOfCalls, StationWorkers, EventHandlers, Dispatchers,
				Station);//// creating manager
		for (int i = 0; i < 5; i++) {// creating event commanders
			EventCommander Commander = new EventCommander(Station, maneger, ReadyEventQ);
			Threads.add(Commander);
		}

		if (numOfCommanders > 8) {// creating the additional event commanders
			for (int i = 0; i < 8; i++) {
				EventCommander Commander = new EventCommander(Station, maneger, ReadyEventQ);
				Threads.add(Commander);
			}
		}

		else {
			for (int i = 0; i < numOfCommanders; i++) {
				EventCommander Commander = new EventCommander(Station, maneger, ReadyEventQ);
				Threads.add(Commander);
			}
		}

		for (int i = 0; i < CALLS.size(); i++) {
			CALLS.get(i).setSerialNum(i + 1);
			CALLS.get(i).start(); // starting the calls threads
		}

		for (Thread T : Threads) { // starting all the threads
			T.start();

		}

		maneger.start();

		synchronized (EndDay.getendDaySituation().getToSynceThisCode()) {
			try {
				EndDay.getendDaySituation().getToSynceThisCode().wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		for (Thread T : Threads) {
			T.stop();
		}
	}
}// class
