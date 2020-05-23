package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveAllStrategy implements DequeuingStrategy{

	@Override
	public List<Vehicle> dequeue(List<Vehicle> listVehicle) {
		List<Vehicle> listaV = new ArrayList<Vehicle>(listVehicle);
		return listaV;
	}

}
