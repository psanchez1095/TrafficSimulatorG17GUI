package simulator.model;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoadMap {
	
	protected Map<String,Junction> junctionMap;
	protected Map<String,Road> roadMap;
	protected Map<String,Vehicle> vehicleMap;
	protected List<Junction> listJunctions;
	protected List<Road> listRoads;
	protected List<Vehicle> listVehicle;
	
	protected RoadMap() {
		
		this.junctionMap = new HashMap<>();
		this.roadMap = new HashMap<>();
		this.vehicleMap = new HashMap<>();
		this.listJunctions = new ArrayList<>();
		this.listRoads = new ArrayList<>();
		this.listVehicle = new ArrayList<>();
		
	}
	
	protected void addJunction(Junction junc) {
		
		if(!junctionMap.containsKey(junc.getId())) {
			
			this.listJunctions.add(junc);
			this.junctionMap.put(junc.getId(), junc);
			
			}
		
			else {
	            throw new IllegalArgumentException("El cruce ya existe en el mapa");
	        }
		
	}
	
		protected void addVehicle(Vehicle vehicle) {
		
		if(!vehicleMap.containsKey(vehicle.getId())) {
			
			this.listVehicle.add(vehicle);
			this.vehicleMap.put(vehicle._id, vehicle);
			
		}
		
		else{
			throw new IllegalArgumentException("No se pudo añadir el vehiculo");
		}
		
	}

	protected void addRoad(Road ro){
		
		if(listRoads.contains(ro)){
			throw new IllegalArgumentException(" La carretera no exite en la lista de carreteras");
		}
		
		else{
			
		if(this.roadMap.containsValue(ro)){
			
			throw new IllegalArgumentException(" La carretera ya existe en el mapa de carreteras ");
			
			}
		}
		
		if(!roadMap.containsKey(ro.getId())) {
				
				this.listRoads.add(ro);
				this.roadMap.put(ro._id,ro); 
		}
			
	}
	
	public Junction getJunction(String id) {
		
		Junction junc = null ;
		
		if(junctionMap.containsKey(id)) {
			junc = junctionMap.get(id);
		}
		
		return junc;
	
	}
	
	public Vehicle getVehicle(String id) {
		Vehicle vehi = null;
		if(vehicleMap.containsKey(id)) {
			vehi = vehicleMap.get(id);
		}
		return vehi;
	}
	
	public Road getRoad(String id) {
		Road road = null;
		if(roadMap.containsKey(id)) {
			road = roadMap.get(id);
		}
			return road;
		
	}
	
	//MANEJO JSON
	
	public JSONObject report() {
		
		JSONObject o = new JSONObject();

        JSONArray jArray = new JSONArray();
        o.put("junctions", jArray);
        for(Junction j : getJunctions()){
            jArray.put(j.report());
        }

        JSONArray rArray = new JSONArray();
        o.put("roads", rArray);
        for (Road r : getRoads()) {
            rArray.put(r.report());
        }

        JSONArray vArray = new JSONArray();
        o.put("vehicles", vArray);
        for (Vehicle v : getVehicles()) {
            vArray.put(v.report());
        }
        return o;
		
	}
	
	//GETTERS Y SETTERS
	
	public List<Junction>getJunctions(){
		return Collections.unmodifiableList(new ArrayList<>(listJunctions));
	}
	
	public List<Road>getRoads() {
		return Collections.unmodifiableList(new ArrayList<>(listRoads));
	}
	
	public List<Vehicle>getVehicles(){
		return Collections.unmodifiableList(new ArrayList<>(listVehicle));
	}
	
	protected void reset() {
		
		junctionMap.clear();
		roadMap.clear();
		vehicleMap.clear();
		listJunctions.clear();
		listRoads.clear();
		listVehicle.clear();
		
		
	}
	
	
}
