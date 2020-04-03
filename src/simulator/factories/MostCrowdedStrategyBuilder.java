package simulator.factories;

import org.json.JSONObject;
import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;

public class MostCrowdedStrategyBuilder extends Builder<LightSwitchingStrategy> {

	private final int defaultTime = 1;
	
	public MostCrowdedStrategyBuilder() {
		super("most_crowded_lss");
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		
		MostCrowdedStrategy aux;
		
		if(!data.has("timeslot")) aux = new MostCrowdedStrategy(defaultTime);
		else  aux = new MostCrowdedStrategy((int)data.get("timeslot"));
		
		
		return aux;
	}

}
