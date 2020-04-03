package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.enumerados.Weather;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;

public class SetWeatherEventBuilder extends Builder<Event> {
	
	protected Event setWeatherEvent;
	
	public SetWeatherEventBuilder() {
		super("set_weather");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		
		if(data.has("time") && data.has("info")) {
			
			/*List<Pair<String,Weather>> lista = new ArrayList<>();     
			JSONArray arrayP = (JSONArray) data.get("info"); 
			
			for (int i=0;i<arrayP.length();i++){ 
				lista.add(new Pair<String,Weather>(arrayP.getJSONObject(i).getString("road"), Weather.valueOf((String)arrayP.getJSONObject(i).get("weather"))));
			} 
			
			setWeatherEvent = new SetWeatherEvent(data.getInt("time"), lista);*/
			int time = (int) data.get("time");
			JSONArray info = data.getJSONArray("info");
			Pair<String, Weather> pair;
			List<Pair<String,Weather>> roadWeather = new ArrayList<>();
			JSONObject pairJSON;
			
			for(int i = 0; i < info.length(); i++) {
				
				pairJSON = info.getJSONObject(i);
				String road = (String) pairJSON.get("road");
				Weather weather = Weather.valueOf(pairJSON.getString("weather"));
				
				pair = new Pair<>(road, weather);
				roadWeather.add(pair);
				
			}
			
			setWeatherEvent = new SetWeatherEvent(time, roadWeather);
			
			
		}
		else setWeatherEvent = null;
		
		
		return setWeatherEvent;
	}

}
