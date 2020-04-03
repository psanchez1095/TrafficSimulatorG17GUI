package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class Junction extends SimulatedObject {

	private List<Road> roadList; 
	private List<List<Vehicle>> listOfVehicleList;
	private Map<Junction,Road> mapExitsRoads; 
	private LightSwitchingStrategy lightStrategy;
	private DequeuingStrategy dequeueStrategy;
	private int x,y;
	private int indexGreenRoad;
	private int lastLightSwitching;
	
	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCord, int yCord){
		
		super(id); 
		
        this.lightStrategy = lsStrategy;
        this.dequeueStrategy = dqStrategy;
        this.mapExitsRoads = new HashMap<Junction,Road>();
        this.roadList  = new ArrayList<>();
        this.listOfVehicleList = new LinkedList<>();
	   
	    
		if(lightStrategy == null) {
			throw new IllegalArgumentException("La estrategia de semaforos no puede ser nula");
		}
	    if(dequeueStrategy == null) {
	    	throw new IllegalArgumentException("La estrategia de deque no puede ser nula");
	    }
	    if(x < 0) {
	    	throw new NumberFormatException("X debe ser positivo (" + this.x + ")");
	    }
	    else this.x = xCord;
	    if(y < 0) {
	    	throw new NumberFormatException("Y debe ser positivo (" + this.y + ")");
	    }
	    else this.y = yCord;
	    indexGreenRoad = -1;
	    
	}
	

	@Override
	void advance(int time) {
		
		if(roadList.size() > 0){ 
			
			if(indexGreenRoad != -1 && listOfVehicleList.size() > 0) { 
				
				List<Vehicle> exitV = dequeueStrategy.dequeue(listOfVehicleList.get(indexGreenRoad));
				
				if(exitV != null) {
					
					for(int i = 0; i < exitV.size(); i++) {
						exitV.get(i).advance(time);
						exitV.get(i).moveToNextRoad();
						listOfVehicleList.get(indexGreenRoad).remove(exitV.get(i)); 
					}
					
				}
			}
			
			int nextGreen = lightStrategy.chooseNextGreen(roadList, listOfVehicleList, indexGreenRoad, lastLightSwitching, time);
			
			if(nextGreen != indexGreenRoad) {
				indexGreenRoad = nextGreen;
				lastLightSwitching = time;
			}
			
		}
	}
	
	void enter(Vehicle v) {
		listOfVehicleList.get(roadList.indexOf(v.getRoadV())).add(v);
	}
	
	Road roadTo(Junction j) {
		
		if(mapExitsRoads.containsKey(j)) {
			return mapExitsRoads.get(j);
		}
		else return null;
		
	}

	void addIncomingRoad(Road road) {
		
		if(road.cruceDestino.equals(this)) {
			roadList.add(road);
			listOfVehicleList.add(new LinkedList<>(road.listVehicle));
		}
		else throw new IllegalArgumentException(road.getId() + " : No tiene " + _id + " como destino");
		
		
	}
	
	void addOutGoingRoad(Road road) {
		
		if(!road.cruceOrigen.equals(this) && mapExitsRoads.containsKey(road.cruceDestino)) throw new IllegalArgumentException("El destino de la carretera y este cruce no son el mismo");
		else mapExitsRoads.put(road.cruceDestino, road);
		
	}
	
	//GETTER Y SETTERS
	
	public Map<Junction, Road> getMapExitsRoads() {
		return mapExitsRoads;
	}

	public void setMapExitsRoads(Map<Junction, Road> mapExitsRoads) {
		this.mapExitsRoads = mapExitsRoads;
	}
	
	//MANEJO JSON
	
	@Override
	public JSONObject report() {
		
		JSONObject json = new JSONObject();
		json.put("id", _id);
		
		if(indexGreenRoad == -1) {
			json.put("green", "none");
		}
		else json.put("green", roadList.get(indexGreenRoad));
		
		JSONArray listRoadQueue = new JSONArray();
		
		for(int i = 0; i < roadList.size(); i++){
			
			JSONObject queue = new JSONObject();
			JSONArray vehicles = new JSONArray();
			
			for(int j = 0; j < listOfVehicleList.get(i).size(); j++) {
				vehicles.put(listOfVehicleList.get(i).get(j));
			}
			
			queue.put("road", roadList.get(i)._id);
			queue.put("vehicles", vehicles);
			listRoadQueue.put(queue);
			
		}
		
		json.put("queues", listRoadQueue);
		
		
		return json;
	}

}
