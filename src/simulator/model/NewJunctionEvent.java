package simulator.model;

public class NewJunctionEvent extends Event {
	
	private String _id;
	private LightSwitchingStrategy lightSwitchingStrat;
	private DequeuingStrategy dequeStrategy;
	private int x;
	private int y;
	
	public NewJunctionEvent(int time, String id, LightSwitchingStrategy lStrategy, DequeuingStrategy dequStrategy, int cordX, int cordY) {
		
		super(time);
		this._time = time;
		this._id = id;
		this.lightSwitchingStrat = lStrategy;
		this.dequeStrategy = dequStrategy;
		this.x = cordX;
		this.y = cordY;
		
	}

	@Override
	void execute(RoadMap map) {
		map.addJunction(new Junction(this._id, this.lightSwitchingStrat, this.dequeStrategy, this.x, this.y));
	}


}
