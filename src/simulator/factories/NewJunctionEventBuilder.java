package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;

public class NewJunctionEventBuilder extends Builder<Event> {
	
	protected Event newJunctionEvent;
	private Factory<LightSwitchingStrategy> LightStrategyFactory;
	private Factory<DequeuingStrategy> DequeStrategyFactory;
	
	public NewJunctionEventBuilder(Factory<LightSwitchingStrategy> LightStrategyFactoryy, Factory<DequeuingStrategy> DequeStrategyFactoryy) {
		
		super("new_junction");
		this.LightStrategyFactory = LightStrategyFactoryy;
		this.DequeStrategyFactory = DequeStrategyFactoryy;
		
	}
	
	@Override
	protected Event createTheInstance(JSONObject data) {
		
		if(data.has("time") && data.has("id") && data.has("coor") && data.has("ls_strategy") && data.has("dq_strategy")) newJunctionEvent = new NewJunctionEvent(data.getInt("time"),data.getString("id"), LightStrategyFactory.createInstance((JSONObject) data.get("ls_strategy")),DequeStrategyFactory.createInstance((JSONObject) data.get("dq_strategy")), (int) data.getJSONArray("coor").get(0), (int) data.getJSONArray("coor").get(1));
		else newJunctionEvent = null;
		
		
		return newJunctionEvent;
	}

}
