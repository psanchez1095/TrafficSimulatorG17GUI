package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;
import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;


public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver {
	
	private Controller controller;
	private List<Road> listRoadsTable;
    private String[] columnas;

    public RoadsTableModel(Controller ctrl){
     
    	listRoadsTable  = null;
        columnas = new String[]{"Id", "Length", "Weather", "Max.Speed" , "Speed Limit", "Total CO2" , "CO2 Limit"};
        controller = ctrl;
        controller.addObserver(this);
        
    }
  
	@Override
	public Object getValueAt(int rowIndex, int columnIndex){
		
		Object referenteRoad = null;
		
		if(columnIndex==0) {
			return referenteRoad = listRoadsTable.get(rowIndex).getId();
		}
		else if(columnIndex==1) {
			return referenteRoad = listRoadsTable.get(rowIndex).getLength();
		}
		else if(columnIndex==2) {
			return referenteRoad = listRoadsTable.get(rowIndex).getTiempo();
		}
		else if(columnIndex==3) {
			return referenteRoad = listRoadsTable.get(rowIndex).getMaxSpeed();
		}
		else if(columnIndex==4) {
			return referenteRoad = listRoadsTable.get(rowIndex).getActualSpeedLimit();
		}
		else if(columnIndex==5) {
			return referenteRoad = listRoadsTable.get(rowIndex).getTotalCO2();
		}
		else if(columnIndex==6) {
			return referenteRoad = listRoadsTable.get(rowIndex).getCO2Limit();
		}
		
		return referenteRoad;
		
	}
	
	public String getColumnName(int indice) { 
		return columnas[indice]; 
	}
	    
		@Override
	public int getColumnCount() {
		return columnas.length;
	}

		@Override
	public int getRowCount() {
		if(this.listRoadsTable == null) return 0;
		else return listRoadsTable.size();
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time){
		listRoadsTable = map.getRoads();
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
