package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;        
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;
import simulator.model.RoadMap;
import simulator.model.SetWeatherEvent;
import simulator.model.TrafficSimObserver;
import simulator.enumerados.Weather;

public class ControlPanel extends JPanel implements TrafficSimObserver {
	
	private Controller controller;
	private RoadMap map;
	private boolean parado;
	private int time;
	
	private JButton botonContClass,botonTiempo,botonCarga,botonPlay,botonStop,botonSalir;
	
	public ControlPanel(Controller control) {
		
		this.controller = control;
		controller.addObserver(this);
		initGUI();
		
	}

	
	private void initGUI(){
		
		Font myFont = new Font ("Times New Roman", 3, 14);
		
		this.setName("ControlPanel");
		this.setLayout(new GridLayout(0,2));
		
		JPanel panelExit = new JPanel();
		this.botonSalir = new JButton();
		
		JPanel panelPrincipal = new JPanel();
		JDialog dialog = new JDialog();
		panelPrincipal.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		this.botonCarga = new JButton();
		initBotonCarga(botonCarga);
		actionBotonCarga(botonCarga,dialog);
		
		
		JPanel panelChange = new JPanel(); 
		panelChange.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		
		this.botonContClass = new JButton();
		initBotonCambioClaseC02(botonContClass);
		actionBotonCambioClaseC02(botonContClass,dialog);
		panelChange.add(botonContClass);
		
		this.botonTiempo = new JButton();
		initBotonCambioTiempo(botonTiempo);
		actionBotonCambioTiempo(botonTiempo,dialog);
		panelChange.add(botonTiempo);
		
		JLabel tTicks = new JLabel("Ticks: ");
		tTicks.setFont(myFont);
		JSpinner spTicks = new JSpinner();
		SpinnerModel model =   new SpinnerNumberModel(10, 1, 300, 1);
		spTicks.setModel(model);
		spTicks.setPreferredSize(new Dimension(50,30));
		spTicks.setFont(myFont);
		
		JPanel panelPlayStop = new JPanel();
		panelPlayStop.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		
		this.botonPlay = new JButton();
		initBotonPlay(botonPlay);
		actionBotonPlay(botonPlay,spTicks);
		
		this.botonStop = new JButton();
		initBotonStop(botonStop);
		actionBotonStop(botonStop);
		
		panelPlayStop.add(botonPlay);
		panelPlayStop.add(botonStop);
		
		panelPrincipal.add(botonCarga);
		panelPrincipal.add(panelChange);
		panelPrincipal.add(panelPlayStop);
		panelPrincipal.add(tTicks);
		panelPrincipal.add(spTicks);
		
		this.add(panelPrincipal);
		
		botonExit(panelExit,this.botonSalir);
	
	}

	private void initBotonCarga(JButton botonCarga) {
		botonCarga.setIcon(new ImageIcon("resources/icons/open.png"));
		botonCarga.setToolTipText("Open a json file");
	}

	private void actionBotonCarga(JButton botonCarga,JDialog dialog) {
	
	botonCarga.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent ae) {

    	dialog.setTitle("Open");
    	dialog.setBounds(getParent().getWidth(), getParent().getHeight(), 800, 800);
    	
    	JFileChooser fileChooser = new JFileChooser("resources/examples");
    	int returnValue = fileChooser.showOpenDialog(null);
    	
    	if(returnValue == JFileChooser.CANCEL_OPTION) fileChooser.setVisible(false);
		
		else if (returnValue == JFileChooser.APPROVE_OPTION) {

			try {
				
				controller.reset();
				controller.loadEvents(new FileInputStream(fileChooser.getSelectedFile()));
				
				
			} catch (Exception  ex) {
                JOptionPane.showMessageDialog(ControlPanel.this, "Traffic Simulator couldn´t open this file", "Error", JOptionPane.ERROR_MESSAGE);
            }
			
		}
    	
    	dialog.add(fileChooser);
    }

});

                      
}

	private void initBotonCambioClaseC02(JButton botonCambioClaseC02) {
		botonCambioClaseC02.setIcon(new ImageIcon("resources/icons/co2class.png"));
		        botonCambioClaseC02.setToolTipText("Change CO2 class of a Vehicle");	
	}

	private void actionBotonCambioClaseC02(JButton botonCambioClaseC02,JDialog dialog) {
	
	botonCambioClaseC02.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
        	ChangeCO2ClassDialog dialog = new ChangeCO2ClassDialog((JFrame) SwingUtilities.getWindowAncestor(botonCambioClaseC02));
        	
        	boolean status;
			try {
				status = dialog.open(map);
			
        	
        	if(status) {
        		List<Pair<String, Integer>> cs = new ArrayList<>();
        		cs.add(new Pair<String,Integer>(dialog.getVehicle().getId(),dialog.getContClass()));
        		controller.addEvent(new NewSetContClassEvent(time + (Integer)dialog.getTicks(),cs));
        	}
			} catch (Exception e) {
				
				JOptionPane.showMessageDialog(ControlPanel.this, "No events Yet! You can´t change Class Contamination Class at the moment", "Change CO2Class Error", JOptionPane.ERROR_MESSAGE);
			}
        	}
		});
	
	}
	                    
	private void initBotonCambioTiempo(JButton botonTiempo) {
		botonTiempo.setIcon(new ImageIcon("resources/icons/weather.png"));
		botonTiempo.setToolTipText("Change Weather of a Road");
	}

	private void actionBotonCambioTiempo(JButton botonTiempo,JDialog dialog) {
	
		botonTiempo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				
				ChangeWeatherDialog weatherJDialog = new ChangeWeatherDialog((JFrame) SwingUtilities.getWindowAncestor(botonTiempo));
				
				try {
					boolean status = weatherJDialog.open(map);
        	
					if(status) {
        		
						List<Pair<String, Weather>> stringWeather = new ArrayList<>();
        		
						stringWeather.add(new Pair<String,Weather>(weatherJDialog.getRoad().getId(),weatherJDialog.getWeather()));
						controller.addEvent(new SetWeatherEvent(time + (Integer)weatherJDialog.getTicks(),stringWeather));
        		
					}
					
				}catch (Exception e ){
					JOptionPane.showMessageDialog(ControlPanel.this, "No events Yet! You can´t change Weather at the moment", "Change Weather Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	
                  
	}

	private void initBotonPlay(JButton botonPlay) {
		botonPlay.setIcon(new ImageIcon("resources/icons/run.png"));
		botonPlay.setToolTipText("Play Traffic Simulator");		
	}

	private void actionBotonPlay(JButton botonPlay,JSpinner nTicksSpinner) {
	
		botonPlay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
        	
				parado = false;
				changeEnableTool(false);
				executeRun((int)nTicksSpinner.getValue());
    		
			}
		});
         
	}


	private void initBotonStop(JButton botonStop) {
		botonStop.setIcon(new ImageIcon("resources/icons/stop.png"));
		botonStop.setToolTipText("Stop Traffic Simulator");		
	}

	private void actionBotonStop(JButton botonStop) {
	
		botonStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				stop();
			}
		});
		
	}

	private void actionBotonExit(JButton botonSalir) {
	
		botonSalir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
        	
				int op = JOptionPane.showOptionDialog( (JFrame) SwingUtilities.getWindowAncestor(botonSalir),"Are you sure you want to quit the traffic simulator?", "Exit", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,    new Object[] { "Yes", "No"},   null);
        	
				if(op != 1) {
					System.exit(0);
				}
				
			}
		});

              
	}

	private void botonExit(JPanel panelExit,JButton botonExit) {
	
		panelExit.setLayout(new FlowLayout(FlowLayout.RIGHT));
		botonExit.setIcon(new ImageIcon("resources/icons/exit.png"));
		botonExit.setToolTipText("Exit the simulator");
	
		actionBotonExit(botonSalir);
	
		panelExit.add(botonSalir);
	
		this.add(panelExit);
		
	}

	private void executeRun( int nTicks ){
	
	
		if ( nTicks > 0 &&  !parado ) {
			try {
				this.controller .run(1);
			} catch (Exception e ) {
				JOptionPane.showMessageDialog(this.getParent(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				parado = true ;
				return ;
			}
			SwingUtilities.invokeLater( new Runnable() {
				@Override
				public void run() {
					executeRun( nTicks - 1);
				}
			});
		} 
		else{
			changeEnableTool(true);
			parado = true ;
			
		}
	}

	private void changeEnableTool(Boolean state) {
		botonCarga.setEnabled(state);
		botonContClass.setEnabled(state);
		botonTiempo.setEnabled(state);
		botonPlay.setEnabled(state);
	}

	private void stop() {
		parado = true ;
		changeEnableTool(true);
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		this.map=map;
		this.time =time;
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {}

	@Override
	public void onReset(RoadMap mapp, List<Event> events, int timee) {}

	@Override
	public void onRegister(RoadMap mapp, List<Event> events, int timee) {}

	@Override
	public void onError(String err) {}


}
