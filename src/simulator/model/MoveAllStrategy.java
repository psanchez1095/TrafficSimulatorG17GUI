package simulator.model;

import java.util.List;

public class MoveAllStrategy implements DequeuingStrategy{

	@Override
	public List<Vehicle> dequeue(List<Vehicle> listVehicle) {

		List<Vehicle> listaV;
		
		if(listVehicle.isEmpty()) listaV = null;
			
		else {
			listVehicle.remove(0);
			listaV = listVehicle;
		}
		
		return listaV;
	}

}
