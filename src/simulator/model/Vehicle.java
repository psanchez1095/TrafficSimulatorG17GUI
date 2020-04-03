package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONObject;

import simulator.enumerados.VehicleStatus;

public class Vehicle extends SimulatedObject implements Comparable<Vehicle>{
	
	private VehicleStatus estado;
	private List<Junction> itinerario;
	private int maxSpeed;
	private int speed;
	private Road roadV;
	private int location;
	private int lasLocation;
	private int contaminacionClass; //un numero entre o y 10 para calcular la cantida de CO2 en cada paso de la simulacion
	private int contaminacionTotal; //el total de CO2 emitido por el vehículo durante su trayectoria
	private int totalRecorrido;
	protected List<Junction> itinerarioAux;

	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
			
			super(id);
			
			if(contClass < 0 || contClass > 10) {
				throw new IllegalArgumentException("La contaminacion debe ser un valor entre 0 y 10 ");
			}
			else this.contaminacionClass = contClass;
			
			if(maxSpeed > 0) {
				this.maxSpeed = maxSpeed;
			}
			else throw new IllegalArgumentException("La velocidad debe ser mayor que cero");
			
			
			if(itinerary.size() < 2) {
				throw new IllegalArgumentException("La longitud es demasiado corta");
			}
			else itinerarioAux = Collections.unmodifiableList(new ArrayList<>(itinerary));
			
			
			estado = VehicleStatus.PENDING;
	}
	
	@Override
	void advance(int time) {
		
		if(estado.equals(VehicleStatus.TRAVELING)) {

			lasLocation = location;
			
			if((location + speed) < roadV.length) {
				location = location + speed;
			}
			else{
				
				location = roadV.length;
				estado = VehicleStatus.WAITING;
				speed = 0;
				roadV.cruceDestino.enter(this); //Al haber llegado al final de la carretera entra en su cruce destino
				
			}
			
			totalRecorrido += (location - lasLocation);
			int cont = (location - lasLocation) * contaminacionClass;
			
			contaminacionTotal += cont;
			roadV.addContamination(cont);
		}else {
			speed = 0;
		}
		

	}
	
	void moveToNextRoad() {
		if(estado.equals(VehicleStatus.WAITING) || estado.equals(VehicleStatus.PENDING)) {
			
			if(estado.equals(VehicleStatus.WAITING)) {
				
				roadV.exit(this);
				if(itinerarioAux.indexOf(roadV.cruceDestino) >= itinerarioAux.size() - 1){
					estado = VehicleStatus.ARRIVED;
				}else{ 
					
					roadV = itinerarioAux.get(itinerarioAux.indexOf(roadV.cruceDestino)).roadTo(itinerarioAux.get(itinerarioAux.indexOf(roadV.cruceDestino) + 1));
					this.location = 0;
					roadV.enter(this);
					estado = VehicleStatus.TRAVELING;
					
				}
				
			}else{ 
				roadV = itinerarioAux.get(0).getMapExitsRoads().get(itinerarioAux.get(1));
				roadV.enter(this);
				estado = VehicleStatus.TRAVELING;
				
			}
			
		}
		
	}
	
	//MANEJO JSON
	
	@Override
	public JSONObject report() {

		JSONObject json = new JSONObject();
		
		json.put("id", _id);
		json.put("speed", speed);
		json.put("distance", totalRecorrido);
		json.put("co2", contaminacionTotal);
		json.put("class", contaminacionClass);
		json.put("status", estado);
		
		if(estado != VehicleStatus.ARRIVED) {
			json.put("road", roadV._id);
			json.put("location", location);
		}
		
		return json;
		
	}
	
	//METODO QUE UTILIZA SORTED ARRAY LIST
	@Override
	public int compareTo(Vehicle v) {
		return this.location - v.location;
	}
	
	
	// GETTERS Y SETTERS
	
	void setSpeed(int speedd) {
		
		if(speedd >= 0) {
			
			if(speedd <= maxSpeed){
				speed = speedd;
			}
			else speed = maxSpeed;
			
		}
		
		else{
			throw new IllegalArgumentException("La velocidad s debe ser positiva");
		}
		
	}
	
	void setContaminationClass(int gradoContaminacion) {
		
		if(gradoContaminacion < 0 || gradoContaminacion > 10) {
			throw new IllegalArgumentException("El grado de contaminacion c debe estar entre 0 y 10");
		}
		else  contaminacionClass = gradoContaminacion;
	}
	
	public Road getRoadV() {
		return this.roadV;
	}
	
	public int getContaminacionClass() {
		return contaminacionClass;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public int getLocation() {
		return location;
	}

	

}
