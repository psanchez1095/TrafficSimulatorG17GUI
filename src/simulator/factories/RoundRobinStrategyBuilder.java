package simulator.factories;

import org.json.JSONObject;
import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;


public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy>{
	
	protected RoundRobinStrategy strategyR;
	private final int defaultTime = 1;

	public RoundRobinStrategyBuilder() {
		super("round_robin_lss");
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		
		if(data.has("timeslot")) strategyR = new RoundRobinStrategy((int)data.get("timeslot"));
		else strategyR = new RoundRobinStrategy(defaultTime);
		
		return strategyR;
	}

}
