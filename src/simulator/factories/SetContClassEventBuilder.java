package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;

public class SetContClassEventBuilder extends Builder<Event> {
	
	Event setContClassEvent;
	
	public SetContClassEventBuilder() {
		super("set_cont_class");
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		
		if(data.has("time") && data.has("info")) {
			
			List lista = new ArrayList<>();
			
			JSONArray info = (JSONArray) data.get("info");
			for(int i = 0; i < info.length(); i++) {
				lista.add(new Pair(info.getJSONObject(i).get("vehicle"), info.getJSONObject(i).get("class")));
			}
			setContClassEvent = new NewSetContClassEvent(data.getInt("time"), lista);
		}
		else setContClassEvent = null;
			
		return setContClassEvent;
	}

}
