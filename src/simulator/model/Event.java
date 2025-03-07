package simulator.model;

public abstract class Event implements Comparable<Event> {

	protected int _time;

	Event(int time) {
		if (time < 1)
			throw new IllegalArgumentException("El tiempo debe ser positivo (" + time + ")");
		else
			_time = time;
	}

	public int getTime() {
		return _time;
	}

	@Override
	public int compareTo(Event o) {
		
		if(o._time < this._time) return 1;
		else if(o._time>this._time) return -1;
		else return 0;
	
		
	}

	abstract void execute(RoadMap map);
}
