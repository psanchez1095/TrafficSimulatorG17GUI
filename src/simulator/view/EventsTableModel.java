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
       
    	eventsTable = null;
        columnas = new String[]{"Time", "Description"};
        controller = ctrl;
        controller.addObserver(this);
        
    }
	
	@Override
	public int getColumnCount() {
		return columnas.length;
	}

	@Override
	public int getRowCount() {
		
		if(eventsTable == null) return 0;
		else return eventsTable.size();
		
	}

	@Override
	public Object getValueAt(int indexRow, int indexCol) {
		
		Event event = eventsTable.get(indexRow);
		
       if(indexCol==0) return event.getTime();
            
       else if(indexCol==1)return event.toString();
        
       else  return null;
		
		
	}
	
	public String getColumnName(int indice) { 
		return columnas[indice]; 
		}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		eventsTable = events;
		this.fireTableDataChanged();
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time){
		
		List<Event> eventoX = new ArrayList<>();
		
		for(Event e : events) {
			
			if(e.getTime() > time)  eventoX.add(e);
			
		}
		
		eventsTable = events;
		this.fireTableDataChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		eventsTable = events;
		this.fireTableDataChanged();

	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		eventsTable = events;
		this.fireTableDataChanged();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		eventsTable = events;
		this.fireTableDataChanged();
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub

	}
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

}
