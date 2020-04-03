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

		
		if (roadList.isEmpty()) {
			return -1;
		}
		
		if (currGreen == -1) {
			
			int i = 0, mostCrowded = 0;
			
			 while(i < vehicleListofList.size()){
				 
		        if(vehicleListofList.get(i).size() > mostCrowded) mostCrowded = i; 
		        i++;                                     
		        
		}
			 return mostCrowded;
			 }                               
	            
		
		if (currTime - lastSwitchingTime < this.timeSlot ) {
			return currGreen;
		}
		
		else{
			int i = currGreen + 1, indexGreen = currGreen;
			
	        while (i % vehicleListofList.size() != currGreen){
	        	
	            if (vehicleListofList.get(i).size() > vehicleListofList.get(indexGreen).size()) indexGreen = i;
	            i++;
	            
	        }
	        
	        return indexGreen;
	}
	}

}
