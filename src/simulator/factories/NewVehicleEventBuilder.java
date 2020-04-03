package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewVehicleEvent;

public class NewVehicleEventBuilder extends Builder<Event> {
	
	protected Event newVehicleEvent;
	
	public NewVehicleEventBuilder() {
		super("new_vehicle");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		
		if(data.has("time") && data.has("id") && data.has("maxspeed") && data.has("class") && data.has("itinerary")) {
			
			JSONArray array = data.getJSONArray("itinerary");
			
			ArrayList<String> a = new ArrayList<String>();
					
			for (int i = 0; i < array.length(); i++) {
				a.add(array.getString(i));
			}

			
			return new NewVehicleEvent(data.getInt("time"), data.getString("id"), data.getInt("maxspeed"), data.getInt("class"), a);

		}
		
		else newVehicleEvent = null;
		
		
		return newVehicleEvent;
		
	}

}
