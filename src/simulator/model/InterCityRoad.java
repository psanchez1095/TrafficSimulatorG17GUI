package simulator.model;

import simulator.enumerados.Weather;

public class InterCityRoad extends Road {

	protected InterCityRoad(String id, Junction cruceOrigen, Junction cruceDestino, int maxSpeed, int contaminacionLimite, int length,
			Weather weather){
		super(id, cruceOrigen, cruceDestino, maxSpeed, contaminacionLimite, length, weather);
	}
	
	@Override
	void reduceTotalContamination() {
		
		// TODO Auto-generated method stub
		
				int totalContaminacionCarretera = this.getContaminacionTotal();
		    	Weather tiempoCityRoad = this.getTiempo();
				
		    	if(tiempoCityRoad == Weather.SUNNY) { this.setContaminacionTotal((int) (((100.0 - 2) / 100.0) * totalContaminacionCarretera)); }
		    	else if(tiempoCityRoad == Weather.CLOUDY) {this.setContaminacionTotal( (int) (((100.0 - 3) / 100.0) * totalContaminacionCarretera)); }
		    	else if(tiempoCityRoad == Weather.RAINY) { this.setContaminacionTotal((int) (((100.0 - 10) / 100.0) * totalContaminacionCarretera)); }
		    	else if(tiempoCityRoad == Weather.WINDY) { this.setContaminacionTotal( (int) (((100.0 - 15) / 100.0) * totalContaminacionCarretera)); }
		    	else if(tiempoCityRoad == Weather.STORM) { this.setContaminacionTotal( (int) (((100.0 - 20) / 100.0) * totalContaminacionCarretera)); }
		    	
	}

	@Override
	void updateSpeedLimit() {

		 if(this.getContaminacionTotal() > this.getAlarmaContaminacion() ) {
	        	this.setActualSpeedLimit((int) (this.getMaxSpeed() * 0.5));
	        }
	        else this.setActualSpeedLimit(this.getMaxSpeed());

	}

	@Override
	protected int calculateVehicleSpeed(Vehicle v) {
		
		int vSpeed ;
		
    	if (this.getTiempo() != Weather.STORM) {
    		vSpeed = this.getActualSpeedLimit();
    	}
    	
    	else vSpeed = (int)(this.getActualSpeedLimit()*0.8);
    	
    	return vSpeed;
	}

}
