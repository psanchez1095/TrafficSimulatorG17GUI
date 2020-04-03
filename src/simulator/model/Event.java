package simulator.model;

public abstract class Event implements Comparable<Event> {

	protected int _time;

	Event(int time) {
		if (time < 1)
			throw new IllegalArgumentException("El tiempo debe ser positivo (" + time + ")");
		else
			_time = time;
	}

	int getTime() {
		return _time;
	}

	@Override
	public int compareTo(Event o) {
		return Integer.compare(_time, o._time);
	}

	abstract void execute(RoadMap map);
}
