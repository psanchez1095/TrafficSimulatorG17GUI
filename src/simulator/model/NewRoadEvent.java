package simulator.model;

import simulator.enumerados.Weather;

public class NewRoadEvent extends Event {
	
	protected String _id;
	protected String cruceOrigen;
	protected String cruceDestino;
	protected int length,contaminacionLimite,maxSpeed;
	protected Weather tiempo;
	
	public NewRoadEvent(int time, String id, String origen, String destino, int maxSpeed, int co2Limit, int length, Weather tiemp){
			super(time);
		this._id = id;
		this.contaminacionLimite = co2Limit;
		this.cruceOrigen = origen;
		this.cruceDestino = destino;
		this.length = length;
		this.maxSpeed = maxSpeed;
		this.tiempo = tiemp;
	}

	@Override
	void execute(RoadMap map) {}

}
