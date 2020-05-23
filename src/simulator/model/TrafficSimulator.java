package simulator.model;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.json.JSONObject;

import simulator.misc.SortedArrayList;
import simulator.view.Observable;

public class TrafficSimulator implements Observable<TrafficSimObserver>{

	private RoadMap roadMap;
	private List<Event> eventList;
	private int time;
	private List<TrafficSimObserver> observers;
	
	
	public TrafficSimulator() {
		this.time=0;
		this.roadMap = new RoadMap();
		this.eventList = new SortedArrayList<Event>();
		observers = new ArrayList<>();
	}
	
	public void addEvent(Event e) {
		
		eventList.add(e);
		onEventAdd(roadMap,eventList,e,time);
		
	}
	
	public void advance() {
			
		time++;
		
		onAdvanceStart(roadMap,eventList,time);
		Iterator <Event> it = this.eventList.iterator();
		
		while(it.hasNext()) {
			Event e = it.next();
			if(e.getTime() == this.time) {
				onEventAdd(roadMap,eventList,e,time);
				e.execute(this.roadMap);
				it.remove();
			}
			
		}
	
		List<Junction> junctions = roadMap.getJunctions();
		for(Junction j : junctions) j.advance(time);
		
		List<Road> roads = roadMap.getRoads();
		for(Road r : roads) r.advance(time);
	
		onAdvanceEnd(roadMap,eventList,time);
		
	}
	
	
	public void reset() {
		
		time = 0;
		eventList.clear();
		roadMap.reset();
			
	}
	
	public JSONObject report(){
		
		JSONObject data = new JSONObject();	
		data.put("time", this.time);
		data.put("state", this.roadMap.report()); 
	
		return data;
	}

	@Override
	public void addObserver(TrafficSimObserver o) {
		
		this.observers.add(o);
		
		for(TrafficSimObserver obv : observers)
			obv.onRegister(roadMap,eventList,time);
		
	}

	@Override
	public void removeObserver(TrafficSimObserver o) {
		// TODO Auto-generated method stub
		this.observers.remove(o);
	}
	
	private void onAdvanceStart(RoadMap map,List<Event> events,int time) {
		for(TrafficSimObserver ob: this.observers) {
			ob.onAdvanceStart(map, events, time);
		}
	}
	private void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		for(TrafficSimObserver ob: this.observers) {
			ob.onAdvanceEnd(map, events, time);
		}
		
	}
	private void onEventAdd(RoadMap map, List<Event> events, Event e, int time) {
		for(TrafficSimObserver ob: observers) {
			ob.onEventAdded(map, events, e, time);
		}
	}
	private void onReset(RoadMap map, List<Event> events, int time) {
		for(TrafficSimObserver ob: observers) {
			ob.onReset(map, events, time);
		}
	}
	private void onRegister(RoadMap map, List<Event> events, int time) {
		
	}
	private void onError() {}
	
}