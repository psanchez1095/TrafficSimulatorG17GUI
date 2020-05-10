package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;


import simulator.control.Controller;

public class MainWindow extends JFrame {
	
	private Controller _ctrl;
	
	public MainWindow(Controller ctrl) {
	super("Traffic Simulator");
	_ctrl = ctrl;
	initGUI();
	}
	
	private void initGUI() {
	JPanel mainPanel = new JPanel(new BorderLayout());
	this.setContentPane(mainPanel);
	mainPanel.add(new ControlPanel(_ctrl), BorderLayout.PAGE_START);
	mainPanel.add(new StatusBar(_ctrl),BorderLayout.PAGE_END);
	JPanel viewsPanel = new JPanel(new GridLayout(1, 2));
	mainPanel.add(viewsPanel, BorderLayout.CENTER);
	JPanel tablesPanel = new JPanel();
	tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
	viewsPanel.add(tablesPanel);
	JPanel mapsPanel = new JPanel();
	mapsPanel.setLayout(new BoxLayout(mapsPanel, BoxLayout.Y_AXIS));
	viewsPanel.add(mapsPanel);
	JPanel eventsView = createViewPanel( new JTable( new EventsTableModel( _ctrl )), "Events" );
	eventsView .setPreferredSize( new Dimension(500, 200));
	eventsView.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black,2), "Events"));
	tablesPanel .add( eventsView );
	
	
	JPanel vehiclesView = createViewPanel( new JTable( new VehiclesTableModel( _ctrl )), "Vehicles" );
	vehiclesView .setPreferredSize( new Dimension(500, 200));
	vehiclesView.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black,2), "Vehicles"));
	tablesPanel .add( vehiclesView );
	
	JPanel roadsView = createViewPanel( new JTable( new RoadsTableModel( _ctrl )), "Roads" );
	roadsView .setPreferredSize( new Dimension(500, 200));
	roadsView.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black,2), "Roads"));
	tablesPanel .add( roadsView );
	
	
	JPanel junctionsView = createViewPanel( new JTable( new JunctionsTableModel( _ctrl )), "Junctions" );
	junctionsView .setPreferredSize( new Dimension(500, 200));
	junctionsView.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black,2), "Junctions"));
	tablesPanel .add( junctionsView );
			
	
	// maps
	JPanel mapView = createViewPanel( new MapComponent( _ctrl ), "Map" );
	mapView .setPreferredSize( new Dimension(500, 400));
	mapView.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black,2), "Map"));
	mapsPanel .add( mapView );
	
	JPanel mapByRoadView = createViewPanel( new MapByRoadComponent( _ctrl ), "Map by Road" );
	mapByRoadView .setPreferredSize( new Dimension(500, 400));
	mapByRoadView.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black,2), "Map by Road"));
	mapsPanel .add( mapByRoadView );
	
	
	// TODO add a map for MapByRoadComponent
	// ...
	this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	this.pack();
	this.setVisible(true);
	}
	private JPanel createViewPanel(JComponent c, String title) {
	JPanel p = new JPanel( new BorderLayout() );
	// TODO add a framed border to p with title
	p.add(new JScrollPane(c));
	return p;
	}

}
