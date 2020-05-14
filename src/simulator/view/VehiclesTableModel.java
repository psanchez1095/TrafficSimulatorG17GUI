package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver {
	
	private Controller controller;
	private List<Vehicle> listVehiclesTable;
    private String[] columnas;

    public VehiclesTableModel(Controller ctrl){
     
    	listVehiclesTable  = null;
        columnas = new String[]{"id", "Location", "Itinerary", "CO2 Class" , "Max.Speed", "Speed" , "Total CO2", "Distance" };
        controller = ctrl;
        controller.addObserver(this);
        
    }
    
    
	@Override
	public int getColumnCount() {
		return columnas.length;
	}

	@Override
	public int getRowCount() {
		
		if(this.listVehiclesTable == null) return 0;
		else return listVehiclesTable.size();
		
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex){
		
		
		Object referenteVehic = null;
		
			if (columnIndex == 0) {
				referenteVehic = listVehiclesTable.get(rowIndex).getId();
				return referenteVehic ;
			}
			else if (columnIndex == 1) {
				referenteVehic = statusVehicle(rowIndex);
				return referenteVehic ;
			}
			else if (columnIndex == 2) {
				referenteVehic = listVehiclesTable.get(rowIndex).getItinerario();
				return referenteVehic ;
			}
			else if (columnIndex == 3) {
				referenteVehic = listVehiclesTable.get(rowIndex).getContaminacionClass();
				return referenteVehic ;
			}
			else if (columnIndex == 4) {
				referenteVehic = listVehiclesTable.get(rowIndex).getMaxSpeed();
				return referenteVehic ;
			}
			else if (columnIndex == 5) {
				referenteVehic = listVehiclesTable.get(rowIndex).getSpeed();
				return referenteVehic ;
			}
			else if (columnIndex == 6) {
				referenteVehic = listVehiclesTable.get(rowIndex).getContaminacionTotal();
				return referenteVehic ;
			}
			else if (columnIndex == 7) {
				referenteVehic = listVehiclesTable.get(rowIndex).getTotalRecorrido();
				return referenteVehic ;
			}
			else return referenteVehic = null;
			
	}
	
	public Object statusVehicle(int fila) {
		Object status =null;
		
		switch(listVehiclesTable.get(fila).getStatus()) {
		case PENDING: 
			status = "Pending";
			break;
		case TRAVELING:
			status = listVehiclesTable.get(fila).getRoadV() + ":" + listVehiclesTable.get(fila).getLocation();
			break;
		case WAITING:
			status = "Waiting";
			break;
		case ARRIVED:
			status = "Arrived";
			break;
	}
		return status;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {

		listVehiclesTable = map.getVehicles();
		this.fireTableDataChanged();

	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		listVehiclesTable = map.getVehicles();
		this.fireTableDataChanged();

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
