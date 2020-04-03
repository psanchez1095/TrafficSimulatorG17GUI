package simulator.model;

import simulator.enumerados.Weather;

public class NewInterCityRoadEvent extends NewRoadEvent {

	public NewInterCityRoadEvent(int time, String id, String srcJun, String destJunc, int maxSpeed, int co2Limit, int length, Weather weather) {
		super(time, id, srcJun, destJunc, maxSpeed, co2Limit, length, weather);
	}

	@Override
	void execute(RoadMap map) {
		map.addRoad(new InterCityRoad(_id,map.getJunction(cruceOrigen),map.getJunction(cruceDestino),maxSpeed,contaminacionLimite,length,tiempo));
	}

}
