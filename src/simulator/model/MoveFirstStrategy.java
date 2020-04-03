package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveFirstStrategy implements DequeuingStrategy{

	@Override
	public List<Vehicle> dequeue(List<Vehicle> listVehicle){
		
		List<Vehicle> listaV = new ArrayList<Vehicle>();
		
		if(listVehicle.isEmpty()) listaV = null;
		else listaV.add(listVehicle.get(0));
		
		return listaV;
	}

}
