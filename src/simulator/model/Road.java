package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

import simulator.enumerados.Weather;

public abstract class Road extends SimulatedObject{

	protected Junction cruceOrigen;
 	protected Junction cruceDestino;
    protected int length;         
    protected int maxSpeed;
    protected int actualSpeedLimit;     
    protected int alarmaContaminacion;     
    protected Weather tiempo;
    protected int contaminacionTotal;
    protected List<Vehicle> listVehicle;
	
	Road(String id, Junction cOrigen, Junction cDestino, int maxSpeed, int contLimit, int length, Weather weather){

		super(id);
		this.cruceOrigen = cOrigen;
		this.cruceDestino = cDestino;
		this.maxSpeed = maxSpeed;
		this.length = length;
		this.tiempo = weather;
		this.alarmaContaminacion = contLimit;
		this.listVehicle = new ArrayList<Vehicle>();
		this.cruceOrigen.addOutGoingRoad(this);
		this.cruceDestino.addIncomingRoad(this);
		this.actualSpeedLimit = this.maxSpeed;
		
		if(maxSpeed > 0 && contLimit > 0 && length > 0 && cruceOrigen != null && cruceDestino != null && weather !=null ) {
			
		}
		
		else throw new IllegalArgumentException("Argumentos incorrectos");
		
	    }
			
		void enter(Vehicle v){
			
			int vehicleLocation = v.getLocation();
			int vehicleSpeed = v.getSpeed();
    	
			if (vehicleLocation != 0 ||vehicleSpeed != 0) throw new IllegalArgumentException("VehicleLocation y VehicleSpeed deben ser mayor que cero");
    	
			listVehicle.add(v);
    	
		}
	
		void exit(Vehicle v) {
			listVehicle.remove(v);
		}
	
		void setTiempo(Weather t) {
			
			if(t == null)	throw new IllegalArgumentException("El tiempo no puede ser null");
			else tiempo = t;
		
		}
	
		void addContamination(int c){
			
			if(c < 0) throw new IllegalArgumentException("La contaminacion c debe ser mayor a 0");
			else contaminacionTotal += c;
			
		}
	
		@Override
		protected void advance(int t) {

			reduceTotalContamination();
			updateSpeedLimit();
			for (int i=0; i<listVehicle.size(); i++ ) {
	    		
	    		listVehicle.get(i).setSpeed(calculateVehicleSpeed(listVehicle.get(i))); 
	    		listVehicle.get(i).advance(t); 
	    		
	    	}
	    	
	    	Collections.sort(listVehicle); 
	
		}

		@Override
		public JSONObject report(){

	    	JSONObject o = new JSONObject();
	        o.put("id", getId());
	        o.put("speedlimit", getActualSpeedLimit());
	        o.put("weather", getTiempo());
	        o.put("co2", getContaminacionTotal());

	        JSONArray vArray = new JSONArray();
	        o.put("vehicles", vArray);

	        for (Vehicle v : getListVehicle()) {
	            vArray.put(v.getId());
	        }
	        return o;
	    
		}
		
		//Metodos Abstractos Implementados en CityRoad y InterCityRoad
    
		abstract void reduceTotalContamination();
		abstract void updateSpeedLimit();
		abstract int calculateVehicleSpeed(Vehicle v);
    
		//GETTERS Y SETTERS
		public Junction getCruceOrigen() {
			return cruceOrigen;
		}

		public void setCruceOrigen(Junction cruceOrigen) {
			this.cruceOrigen = cruceOrigen;
		}

		public Junction getCruceDestino() {
			return cruceDestino;
		}

		public void setCruceDestino(Junction cruceDestino) {
			this.cruceDestino = cruceDestino;
		}

		public int getLength() {
			return length;
		}

		public void setLength(int length) {
			this.length = length;
		}

		public int getMaxSpeed() {
			return maxSpeed;
		}

		public void setMaxSpeed(int maxSpeed) {
			this.maxSpeed = maxSpeed;
		}

		public int getActualSpeedLimit() {
			return actualSpeedLimit;
		}

		public void setActualSpeedLimit(int actualSpeedLimit) {
			this.actualSpeedLimit = actualSpeedLimit;
		}

		public int getAlarmaContaminacion() {
			return alarmaContaminacion;
		}

		public void setAlarmaContaminacion(int alarmaContaminacion) {
			this.alarmaContaminacion = alarmaContaminacion;
		}

		public int getContaminacionTotal() {
			return contaminacionTotal;
		}

		public void setContaminacionTotal(int contaminacionTotal) {
			this.contaminacionTotal = contaminacionTotal;
		}

		public List<Vehicle> getListVehicle() {
			return listVehicle;
		}

		public void setListVehicle(List<Vehicle> listVehicle) {
			this.listVehicle = listVehicle;
		}

		public Weather getTiempo() {
			return tiempo;
		}	

	

}
