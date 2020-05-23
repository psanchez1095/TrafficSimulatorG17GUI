package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy{

	private int timeSlot;
	
	public MostCrowdedStrategy(int timeSlot) {
		this.timeSlot = timeSlot;
	}
	
	@Override
	public int chooseNextGreen(List<Road> roadList, List<List<Vehicle>> vehicleListofList, int currGreen,
			int lastSwitchingTime, int currTime) {

		
		if(roadList.size() == 0) return -1;
		
		else if(currGreen == -1) {return 0;}
	
		
		else if(currTime - lastSwitchingTime < this.timeSlot) return currGreen;
		// TODO Auto-generated method stub
		return 0;
	}

}
