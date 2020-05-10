package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver {
	
	private Controller controller;
    private List<Event> eventsTable;
    private String[] columnas;

    public EventsTableModel(Controller ctrl){
        super();
        eventsTable  = null;
        columnas = new String[]{"Time", "Description"};
        controller = ctrl;
        controller.addObserver(this);
    }
    	
	public void setEventsList(List<Event> events) {
		eventsTable = events;
		fireTableDataChanged();
	}
	
	@Override
	public int getColumnCount() {
		return columnas.length;
	}

	@Override
	public int getRowCount() {
		return eventsTable == null ? 0 : eventsTable.size();
	}

	@Override
	public Object getValueAt(int indexRow, int indexCol) {
		
		Object object = null;
		
		switch (indexCol) {
		
		case 0:
			object = eventsTable.get(indexRow).getTime();
			break;
			
		case 1:
			object = eventsTable.get(indexCol).toString();
			break;
		}
		
		return object;
		
		
	}
	
	public String getColumnName(int indice) { 
		return columnas[indice]; 
		}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time){
		
		List<Event> eventoX = new ArrayList<>();
		
		for(Event e : events) {
			
			if(e.getTime() > time)  eventoX.add(e);
			
		}
		
		setEventsList(eventoX);

	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		setEventsList(events);

	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub

	}

}
