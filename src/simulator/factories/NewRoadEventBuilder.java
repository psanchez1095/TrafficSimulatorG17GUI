package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.enumerados.*;

public abstract class NewRoadEventBuilder extends Builder<Event>{
	 protected int time;
	    protected String id;
	    protected String cruceOrigen;
	    protected String cruceDestino;
	    protected int length;
	    protected int contaminacionLimit;
	    protected int maxspeed;
	    protected Weather tiempo;

	    public NewRoadEventBuilder(String type) {
	        super(type);
	    }

	    @Override
	    protected Event createTheInstance(JSONObject data) {
	        try {
	            time = (data.getInt("time"));
	            id = data.getString("id");
	            cruceOrigen = data.getString("src");
	            cruceDestino = data.getString("dest");
	            length = data.getInt("length");
	            contaminacionLimit = data.getInt("co2limit");
	            maxspeed = data.getInt("maxspeed");
	            tiempo = Weather.valueOf(data.getString("weather").toUpperCase());
	            }
	            catch(NullPointerException | ClassCastException e) {
	            System.out.println("The JSON object is incorrect" + e.getMessage());
	            return null;
	        }
	        return createTheRoad();
	    }

	    abstract Event createTheRoad();
}
