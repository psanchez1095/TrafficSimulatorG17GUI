package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

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
	public int getColumnCount() {
		return this.columnas.length;
	}

	@Override
	public int getRowCount() {
		
		if(this.listJunctionsTable == null) return 0;
		else return listJunctionsTable.size();
		
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex){
		
		Junction cruce = listJunctionsTable.get(rowIndex);
		
        if(columnIndex==0) return cruce.getId();
        
        else if(columnIndex==1){
        	
                if(cruce.getGreenLightIndex() == -1) return "NONE";
                else return cruce.getInRoads().get(cruce.getGreenLightIndex());
                
                }
        
        else if(columnIndex==2){
        	
                String queueVehicleString = "";
                
                for (int i = 0; i < cruce.getInRoads().size(); i++) {
                	
                	queueVehicleString = queueVehicleString + cruce.getInRoads().get(i) + ":" + cruce.getListOfVehicleList().get(i) + " ";
                }
                
                return queueVehicleString;
        }
        
        else  return null;
    }
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		listJunctionsTable = map.getJunctions();
		this.fireTableDataChanged();

	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		

	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub

	}
	
	public String getColumnName(int indice) { 
		return columnas[indice]; 
		}

}
