package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import org.json.*;
import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimulator;

public class Controller {

	private Factory<Event> events_factory;
	private TrafficSimulator traffic_simulator;
	
	public  Controller(TrafficSimulator sim,Factory<Event> factoria) {
		if(sim == null) {
			throw new IllegalArgumentException(" El simulador tiene valor NULL");
		}
		else if (factoria == null)  {
			throw new IllegalArgumentException( "La factoria de eventos tiene valor NULL");
		}
		else {
		this.events_factory = factoria;
		this.traffic_simulator = sim;
		}

	}
	
	public void loadEvents(InputStream in) {
		
		JSONObject jo = new JSONObject(new JSONTokener(in));	
		
		if(!jo.has("events")) {
			throw new IllegalArgumentException(" El archivo de entrada no tiene la clave events");
		}
		else {
			JSONArray arrayEv = jo.getJSONArray("events");
			for(int i= 0; i < arrayEv.length() ; i++) {
			this.traffic_simulator.addEvent(this.events_factory.createInstance(arrayEv.getJSONObject(i)));
			}
		}	
	}
	
	public void run(int nTicks, OutputStream out) {
		
		JSONArray status = new JSONArray();
		PrintStream print = /*(out==null)? null :*/ new PrintStream(out);
		JSONObject status1 = new JSONObject();
		
		print.println("{");
		print.println("  \"states\": [");
		for(int i=0;i<nTicks;i++) {

			this.traffic_simulator.advance();
			//status.put(i, this.traffic_simulator.report());
			print.println(this.traffic_simulator.report()+ ",");
			
		}
		 print.println("]}");
		//status1.put("states", status);
		//print.println(status1);
		
		
	}
	
	private void reset(){
		this.traffic_simulator.reset();
	}
	
	
}