package simulator.model;

import simulator.enumerados.Weather;

public class NewCityRoadEvent extends NewRoadEvent {

	public NewCityRoadEvent(int time, String id, String cruceOrigen, String cruceDestino, int maxSpeed, int contaminacionLimite, int length, Weather weather) {
		super(time, id, cruceOrigen, cruceDestino, maxSpeed, contaminacionLimite, length, weather);
	}
	
	@Override
	void execute(RoadMap map) {
		map.addRoad(new CityRoad(_id,map.getJunction(cruceOrigen),map.getJunction(cruceDestino),maxSpeed,contaminacionLimite,length,tiempo));
	}

}
