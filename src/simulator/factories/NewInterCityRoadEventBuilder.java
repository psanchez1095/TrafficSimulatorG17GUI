package simulator.factories;

import org.json.JSONObject;

import simulator.enumerados.Weather;
import simulator.model.Event;
import simulator.model.NewInterCityRoadEvent;

public class NewInterCityRoadEventBuilder extends Builder<Event> {
	
	protected Event newInterCityRoadEvent;
	
	public NewInterCityRoadEventBuilder() {
		super("new_inter_city_road");
	}

	
	@Override
	protected Event createTheInstance(JSONObject data) {
		
		if(data.has("time") && data.has("id") && data.has("src") && data.has("dest") && data.has("length") && data.has("co2limit") && data.has("maxspeed") && data.has("weather")) newInterCityRoadEvent = new NewInterCityRoadEvent(data.getInt("time"),data.getString("id"), data.getString("src"), data.getString("dest"),data.getInt("maxspeed"),data.getInt("co2limit"),data.getInt("length"), (Weather) Weather.valueOf((String) data.get("weather")));
		else newInterCityRoadEvent =  null;
		
		
		return newInterCityRoadEvent;
	}
	
	
	
}
