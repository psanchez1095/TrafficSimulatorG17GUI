package simulator.model;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator {

	private RoadMap roadMap;
	private List<Event> eventList;
	private int time;
	
	//La clase TrafficSimulator tiene sólo una constructora pública por defecto, que inicializa
	//los campos a sus valores por defecto
	public TrafficSimulator() {
		this.time=0;
		this.roadMap = new RoadMap();
		this.eventList = new SortedArrayList<Event>();;
	}
	
	
	//Añade el evento e a la lista de eventos. Recuerda que
	//la lista de eventos tiene que estar ordenada como se describió anteriormente.
	public void addEvent(Event e) {
		/*int pos = 0;
		for (int i = eventList.size() - 1; i > 0; i++) 
			if (e.getTime() < eventList.get(i).getTime())
				pos = i;
		
		eventList.add(pos, e);*/
		if(e != null) {
			this.eventList.add(e);
		}
	}
	/*Avanza el estado de la simulación de la siguiente forma (el
			orden de los puntos que aparecen a continuación es muy importante!):
			1. incrementa el tiempo de la simulación en 1.
			2. ejecuta todos los eventos cuyo tiempo sea el tiempo actual de la simulación y
			los elimina de la lista. Después llama a sus correspondientes métodos execute.
			3. llama al método advance de todos los cruces.
			4. llama al método advance de todas las carreteras.*/
	public void advance() {
			time++;
			Iterator <Event> it = this.eventList.iterator();
			
			while(it.hasNext()) {
				Event e = it.next();
				if(e.getTime() == this.time) {
					e.execute(this.roadMap);
					
				}
				
			}
			
		/*for (Event e : this.eventList)
			if (e.getTime() == time) {
				e.execute(roadMap);
				eventList.remove(e);
			}
		*/
		for (Junction j : roadMap.getJunctions())
			j.advance(time);
		
		for (Road r : roadMap.getRoads())
			r.advance(time);
	}
	
	//limpia el mapa de carreteras y la lista de eventos, y pone el tiempo
	//de la simulación a 0.
	public void reset() {
		roadMap.reset();
        eventList = new SortedArrayList<Event>();
        time = 0;
	}
	/*{
		"time" : 3,
		"state" : {
		"junctions" : [...],
		"road" : [...],
		"vehicles" : [...]
		}
	}
donde “time” es el tiempo de la simulación actual, y “state” es lo que devuelve
el método report() del mapa de carreteras.
	 */
	public JSONObject report(){
		
		
		JSONObject data = new JSONObject();	
		data.put("time", this.time);
		data.put("state", this.roadMap.report()); 
	
		return data;
	}
}