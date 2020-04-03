package simulator.model;

import java.util.List;

public class RoundRobinStrategy implements LightSwitchingStrategy{

	private int timeSlot;
	
	public RoundRobinStrategy(int numTicks) {
		this.timeSlot = numTicks;
	}

	@Override
	public int chooseNextGreen(List<Road> roadList, List<List<Vehicle>> listOfVehicleList, int currGreen, int lastSwitchingTime, int currTime){
		
		if (roadList.isEmpty()) {
			return -1;
		}
		
		if (currGreen == -1) {
			return 0;
		}
		
		if (currTime - lastSwitchingTime < this.timeSlot ) {
			return currGreen;
		}
		
		else{
			return (currGreen + 1) % roadList.size();

        }
	}
	
	
	
	
	
	
	
}
