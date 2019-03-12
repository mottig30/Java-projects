
public class EndDay { //class for help to handled the end of the day
	
	private static EndDay endDaySituation = null;
	private boolean endDay;
	private Object ToSynceThisCode = new Object();
	
	
	public Object getToSynceThisCode() {
		return ToSynceThisCode;
	}

	protected EndDay() {	
	}
	
	public static synchronized EndDay getendDaySituation() {
		if(endDaySituation == null) {
			endDaySituation = new EndDay();
		}
		return endDaySituation;
	}


	public boolean isEndDay() {
		return endDay;
	}

	public synchronized void setEndDay(boolean endDay) { //
		synchronized (ToSynceThisCode) {
			this.endDay = endDay;
			ToSynceThisCode.notifyAll();
		}
	}
}
