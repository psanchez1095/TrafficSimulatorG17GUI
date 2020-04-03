package simulator.model;

import java.util.List;

import simulator.enumerados.Weather;
import simulator.misc.Pair;

public class SetWeatherEvent extends Event {

	private List<Pair<String,Weather>>  weatherString;
	
	public SetWeatherEvent(int time, List<Pair<String,Weather>> pairWS) {
		
		super(time);
		
		if(pairWS != null) {
			this.weatherString = pairWS;
		}
		
		else throw new IllegalArgumentException(" El par weather-string no puede ser nulo");
		
	}
	
	@Override
	void execute(RoadMap map) {
		
	
		for(int i = 0; i < weatherString.size(); i++) {
			
			if(!map.getRoad(weatherString.get(i).getFirst()).equals(null)) {
				
				map.getRoad(weatherString.get(i).getFirst()).setTiempo(weatherString.get(i).getSecond());
				
			}else throw new IllegalArgumentException(" La carretera no existe en el mapa de carreteras");
				
			}
		}


}
