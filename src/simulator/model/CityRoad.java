package simulator.model;

import simulator.enumerados.Weather;

public class CityRoad extends Road {

	protected CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	void updateSpeedLimit() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void reduceTotalContamination() {
		
		Weather tiempoCityRoad = this.getTiempo();
		
		if(tiempoCityRoad != Weather.WINDY && tiempoCityRoad != Weather.STORM) contaminacionTotal -= 2; 
		else contaminacionTotal -= 10;
			
		if(contaminacionTotal < 0) {
			contaminacionTotal = 0;
		}

	}

	@Override
	protected int calculateVehicleSpeed(Vehicle v) {
		return (int)Math.ceil((((11.0-v.getContaminacionClass())/11.0)*actualSpeedLimit)); 
	}

	

	
}
