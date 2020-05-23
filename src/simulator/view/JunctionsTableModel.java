package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;
import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;


public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver {
	
	private Controller controller;
	private List<Junction> listJunctionsTable;
    private String[] columnas;

    public JunctionsTableModel(Controller ctrl){
     
    	listJunctionsTable  = null;
        columnas = new String[]{"ID", "Green Light", "Queues"};;
        controller = ctrl;
        controller.addObserver(this);
        
    }
    
    @Override
	public Object getValueAt(int rowIndex, int columnIndex){
		
    	Object referenteJunctionsTable = null;
    	
		if(columnIndex==0) {
			referenteJunctionsTable = listJunctionsTable .get(rowIndex).getId();
			return referenteJunctionsTable;
		}
		else if(columnIndex==1) {
			
			if(listJunctionsTable.get(rowIndex).getGreenLightIndex() == -1) return "NONE";
			
			referenteJunctionsTable = listJunctionsTable .get(rowIndex).getGreenLightIndex();
			return referenteJunctionsTable;
			
		}
		else if(columnIndex==2) {
			
			 String queueVehicleString = "";
			 
             for (int i = 0; i < listJunctionsTable.get(rowIndex).getInRoads().size(); i++) {
             	queueVehicleString = queueVehicleString + listJunctionsTable.get(rowIndex).getInRoads().get(i) + ": " + listJunctionsTable.get(rowIndex).getListOfVehicleList().get(i) + " ";
             }
             
             return queueVehicleString;
		}
		
		return referenteJunctionsTable;
    
    }
    
    @Override
    public String getColumnName(int indice) { 
		return columnas[indice]; 
	}

	@Override
	public int getColumnCount() {
		return this.columnas.length;
	}

	@Override
	public int getRowCount() {
		
		if(this.listJunctionsTable == null) return 0;
		else return listJunctionsTable.size();
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		listJunctionsTable = map.getJunctions();
		this.fireTableDataChanged();
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {}
	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {}
	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {}
	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {}
	@Override
	public void onError(String err) {}
	
}
