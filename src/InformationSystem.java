import java.util.ArrayList;
public class InformationSystem {

//Fields of the class:
	
	private ArrayList<CrimeEvent> upTo10Km;
	private ArrayList<CrimeEvent> from10To20Km;
	private ArrayList<CrimeEvent> moreThan20Km;
    private DataBase dB;
	
//Constructors:
	
	public InformationSystem(DataBase dB) {
	
	this.upTo10Km=new ArrayList<CrimeEvent>();
	this.from10To20Km=new ArrayList<CrimeEvent>();
	this.moreThan20Km=new ArrayList<CrimeEvent>();
	this.dB=dB;
	}
	
//Methods of the class:	
	
	
	public synchronized void addEvent(CrimeEvent c) { //adding crime event to the info system lists
		if (c.getDistanceCalculated()<=10)
			upTo10Km.add(c);
		else if(c.getDistanceCalculated()>10&&c.getDistanceCalculated()<20)
			from10To20Km.add(c);
		else
			moreThan20Km.add(c);
		this.dB.insertToTable(c);
		this.notifyAll();
	}
	


	public synchronized CrimeEvent removeEvent(){ //removing crime event from the closest area
		while(this.listsAreEmpty()){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if(!(upTo10Km.isEmpty())){
			return	upTo10Km.remove(0);
		}
		else if(!from10To20Km.isEmpty()){
			return	from10To20Km.remove(0);
		}
		else
			return moreThan20Km.remove(0);
	}
	
	public synchronized boolean listsAreEmpty (){ //boolean check if all the lists are empty (Top-down)
		return upTo10Km.isEmpty()&& from10To20Km.isEmpty()&& moreThan20Km.isEmpty();
	}
}//class
