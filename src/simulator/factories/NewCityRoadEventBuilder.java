package simulator.factories;

import org.json.JSONObject;

import simulator.enumerados.Weather;
import simulator.model.Event;
import simulator.model.NewCityRoadEvent;
import simulator.model.NewInterCityRoadEvent;

public class NewCityRoadEventBuilder extends Builder<Event> {
	
	protected Event newCityRoadEvent;
	
	public NewCityRoadEventBuilder() {
		super("new_city_road");
	}

	@Override
	protected Event createTheInstance(JSONObject json) {
		
		if(json.has("time") && json.has("id") && json.has("src") && json.has("dest") && json.has("length") && json.has("co2limit") && json.has("maxspeed") && json.has("weather")) newCityRoadEvent = new NewCityRoadEvent(json.getInt("time"),json.getString("id"), json.getString("src"), json.getString("dest"),json.getInt("maxspeed"),json.getInt("co2limit"),json.getInt("length"), (Weather) Weather.valueOf((String) json.get("weather")));
		else newCityRoadEvent =  null;
		
		return newCityRoadEvent;
		
	}
	
}
