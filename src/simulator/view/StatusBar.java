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
	
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		timeLabel.setText(currentTime(time));
		}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.time = time;
		timeLabel.setText(currentTime(time));
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		newEventText.setText("Event added (" + events.get(events.size() - 1).toString() + ")" );
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) { // Nuevo mensaje añadido
		// TODO Auto-generated method stub
		timeLabel.setText(currentTime(time));
		 newEventText.setText(noEvent());
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.mapRoad = map;
		this.time = time;
		
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
	}
	
	private void initGUI() {
		
		setLayout(new BorderLayout());
		
		timeLabel = new JLabel("Time: " + time);
		newEventText = new JLabel("No Events Yet");
		
		JPanel pTime = new JPanel();
		pTime.setPreferredSize(new Dimension(100,25));
		pTime.setLayout(new FlowLayout(FlowLayout.LEFT));
		pTime.add(timeLabel);
		add(pTime,BorderLayout.WEST);
		
		JPanel pMsg = new JPanel();
		pMsg.setLayout(new FlowLayout(FlowLayout.LEFT));
		pMsg.add(newEventText);
		add(pMsg,BorderLayout.CENTER);
		
	}
	
	private String currentTime(int time){
        return "Time:  " + time;
    }

	private String noEvent(){
        return "Go! Go!";
    }
}
