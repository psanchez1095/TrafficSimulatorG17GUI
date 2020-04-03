package simulator.model;

import java.util.List;
import simulator.misc.Pair;

public class NewSetContClassEvent extends Event {

	private List<Pair<String,Integer>> pairStringInteger;
	
	public NewSetContClassEvent(int time, List<Pair<String,Integer>> StringInteger) {
		
		super(time);
		
		if(StringInteger != null) {
			this.pairStringInteger = StringInteger;
			}
		else {
			throw new IllegalArgumentException(" El par String-Integer no puede ser nulo");
			}
		}

	@Override
	void execute(RoadMap map) {
		
		for(Pair<String, Integer> pair: pairStringInteger){
			
			if(!map.getVehicle(pair.getFirst()).equals(null)) 
					map.getVehicle(pair.getFirst()).setContaminationClass(pair.getSecond());
			else throw new IllegalArgumentException("El vehiculo no existe en el mapa de carreteras");
			
		}
	}

}
