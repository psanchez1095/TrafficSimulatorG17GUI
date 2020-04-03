package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class NewVehicleEvent extends Event {
	
	private String _id;
	private int maxSpeed, contaminacionClass;
	private List<String> itinerarioStr;
	private List<Junction> itinerario = new ArrayList<>();
	private Vehicle vehicle;
	
	public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> itinerary){
		
			super(time);
			this._id = id;
			this.contaminacionClass = contClass;
			this.maxSpeed = maxSpeed;
			this.itinerarioStr = itinerary;
			
	}

	@Override
	void execute(RoadMap map) {
		   
		itinerario = new ArrayList<>();
		
		for(String jId: itinerarioStr) {
			itinerario.add(map.getJunction(jId));
		}
		
		this.vehicle = new Vehicle(this._id, this.maxSpeed, this.contaminacionClass, itinerario);
		map.addVehicle(vehicle);
		map.getVehicle(_id).moveToNextRoad();
		
		
	}

}
