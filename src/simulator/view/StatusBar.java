package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver {
	
	private Controller controller;
	private RoadMap mapRoad;
	private int time;
	
	private JLabel timeLabel,newEventText;
	
	StatusBar(Controller control){
		this.controller = control;
		control.addObserver(this);
		initGUI();
	}
	
	private void initGUI() {
		
		timeLabel = new JLabel("Welcome User");
		newEventText = new JLabel("");
		setLayout(new BorderLayout());
		
		JPanel eventosStringPanel = new JPanel();
		eventosStringPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		eventosStringPanel.add(newEventText);
		add(eventosStringPanel,BorderLayout.CENTER);
		
		JPanel tiempoPanel = new JPanel();
		tiempoPanel.setPreferredSize(new Dimension(100,25));
		tiempoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		tiempoPanel.add(timeLabel);
		add(tiempoPanel,BorderLayout.WEST);
		
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		timeLabel.setText(currentTime(time));
		newEventText.setText(noEvent());
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		this.time = time;
		timeLabel.setText(currentTime(time));
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		timeLabel.setText(currentTime(time));
		newEventText.setText("Event added (" + e.toString() + ")" );
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) { 
		timeLabel.setText(currentTime(time));
		newEventText.setText(noEvent());
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		this.mapRoad = map;
		this.time = time;
	}

	@Override
	public void onError(String err) {}
	
	
	private String currentTime(int time){
        return "Time:  " + time;
    }

	private String noEvent(){
        return "Simulator advances without new events";
    }
}
