package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

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
	public int getColumnCount() {
		return columnas.length;
	}

	@Override
	public int getRowCount() {
		
		if(this.listRoadsTable == null) return 0;
		else return listRoadsTable.size();
		
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex){
		
		Object referenteRoad = null;
		
		switch (columnIndex) {
		
		case 0:
			referenteRoad = listRoadsTable.get(rowIndex).getId();
			break;
		case 1:
			referenteRoad = listRoadsTable.get(rowIndex).getLength();
			break;
		case 2:
			referenteRoad = listRoadsTable.get(rowIndex).getTiempo();
			break;
		case 3:
			referenteRoad = listRoadsTable.get(rowIndex).getMaxSpeed();
			break;
		case 4:
			referenteRoad = listRoadsTable.get(rowIndex).getActualSpeedLimit();
			break;
		case 5:
			referenteRoad = listRoadsTable.get(rowIndex).getTotalCO2();
			break;
		case 6:
			referenteRoad = listRoadsTable.get(rowIndex).getCO2Limit();
			break;
		}
		
		return referenteRoad;
		
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
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time){
		
		listRoadsTable = map.getRoads();
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
