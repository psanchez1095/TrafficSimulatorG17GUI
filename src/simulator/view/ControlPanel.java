package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
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

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.time = time;
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReset(RoadMap mapp, List<Event> events, int timee) {
		// TODO Auto-generated method stub
		this.map = mapp;
		this.time = timee;
	}

	@Override
	public void onRegister(RoadMap mapp, List<Event> events, int timee) {
		// TODO Auto-generated method stub
		this.map = mapp;
		this.time = timee;
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub

	}
	
private void initGUI() {
		
		this.setName("ControlPanel");
		this.setLayout(new GridLayout(0,2));
		
		JPanel mainP = new JPanel();
		mainP.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		this.botonCarga = new JButton();
		JDialog dialog = new JDialog();
		
		initBotonCarga(botonCarga);
		actionBotonCarga(botonCarga,dialog);
		
		mainP.add(botonCarga);
		
		JPanel pChange = new JPanel(); 
		pChange.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		
		this.botonContClass = new JButton();
		initBotonCambioClaseC02(botonContClass);
		actionBotonCambioClaseC02(botonContClass,dialog);
		
		pChange.add(botonContClass);
		
		this.botonTiempo = new JButton();
		initBotonCambioTiempo(botonTiempo);
		actionBotonCambioTiempo(botonTiempo,dialog);
		
		pChange.add(botonTiempo);
		
		mainP.add(pChange);
		
		JLabel tTicks = new JLabel("Ticks: ");
		JSpinner spTicks = new JSpinner();
		SpinnerModel model =   new SpinnerNumberModel(10, 1, 1000, 1);
		spTicks.setModel(model);
		spTicks.setPreferredSize(new Dimension(80,30));
		
		JPanel pBPlayStop = new JPanel();
		pBPlayStop.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));   
		
		this.botonPlay = new JButton();
		initBotonPlay(botonPlay);
		actionBotonPlay(botonPlay,spTicks);
		
		this.botonStop = new JButton();
		initBotonStop(botonStop);
		actionBotonStop(botonStop);
		
		pBPlayStop.add(botonPlay);
		pBPlayStop.add(botonStop);
		
		mainP.add(pBPlayStop);
		
		mainP.add(tTicks);
		mainP.add(spTicks);
		this.add(mainP);
		
		
		JPanel pExit = new JPanel();
		pExit.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		this.botonSalir = new JButton();
		initBotonExit(botonSalir);
		actionBotonExit(botonSalir);
		
		pExit.add(botonSalir);
		
		this.add(pExit);
	}

//Inicializamos boton carga fichero
private void initBotonCarga(JButton botonCarga) {
	
	botonCarga.setIcon(new ImageIcon("resources/icons/open.png"));
	botonCarga.setToolTipText("Open a file");
	
	
			
}
//Action boton carga fichero
private void actionBotonCarga(JButton botonCarga,JDialog dialog) {
	
	botonCarga.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent ae) {

    	
    	dialog.setTitle("Open");
    	dialog.setBounds(getParent().getWidth(), getParent().getHeight(), 800, 800);
    	JFileChooser fileChooser = new JFileChooser("resources/examples");
    	int returnValue = fileChooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {

			controller.reset();
			try {
				
				
				controller.loadEvents(new FileInputStream(fileChooser.getSelectedFile()));
				
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if(returnValue == JFileChooser.CANCEL_OPTION){
			fileChooser.setVisible(false);
		}
    	dialog.add(fileChooser);
    }

});

                      
}

//Inicializamos boton cambia clase CO2 de un vehiculo
private void initBotonCambioClaseC02(JButton botonCambioClaseC02) {
	
	botonCambioClaseC02.setIcon(new ImageIcon("resources/icons/co2class.png"));
	botonCambioClaseC02.setToolTipText("Change CO2 class of a Vehicle");
	
	
			
}
//Action boton carga fichero
private void actionBotonCambioClaseC02(JButton botonCambioClaseC02,JDialog dialog) {
	
	botonCambioClaseC02.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
        	ChangeCO2ClassDialog dialog = new ChangeCO2ClassDialog((JFrame) SwingUtilities.getWindowAncestor(botonCambioClaseC02));
        	
        	boolean status = dialog.open(map);
        	
        	if(status) {
        		List<Pair<String, Integer>> cs = new ArrayList<>();
        		cs.add(new Pair<String,Integer>(dialog.getVehicle().getId(),dialog.getContClass()));
        		controller.addEvent(new NewSetContClassEvent(time + dialog.getTicks(),cs));
        	}
        }
	});

                    
}

//Inicializamos boton cambia clase CO2 de un vehiculo
private void initBotonCambioTiempo(JButton botonTiempo) {
	
	botonTiempo.setIcon(new ImageIcon("resources/icons/weather.png"));
	botonTiempo.setToolTipText("Change Weather of a Road");
	
	
			
}
//Action boton carga fichero
private void actionBotonCambioTiempo(JButton botonTiempo,JDialog dialog) {
	
	botonTiempo.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
        	ChangeWeatherDialog dialog = new ChangeWeatherDialog((JFrame) SwingUtilities.getWindowAncestor(botonTiempo));
        	
        	boolean status = dialog.open(map);
        	
        	
        	if(status) {
        		
        		List<Pair<String, Weather>> stringWeather = new ArrayList<>();
        		
        		stringWeather.add(new Pair<String,Weather>(dialog.getRoad().getId(),dialog.getWeather()));
        		controller.addEvent(new SetWeatherEvent(time + dialog.getTicks(),stringWeather));
        		
        	}
        }
	});

                  
}

//Inicializamos boton cambia clase CO2 de un vehiculo
private void initBotonPlay(JButton botonPlay) {
	
	botonPlay.setIcon(new ImageIcon("resources/icons/run.png"));
	botonPlay.setToolTipText("Run the simulator");
	
			
}
//Action boton carga fichero
private void actionBotonPlay(JButton botonPlay,JSpinner nTicksSpinner) {
	
	botonPlay.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
        	
        	parado = false;
        	
        	botonCarga.setEnabled(false);
    		botonContClass.setEnabled(false);
    		botonTiempo.setEnabled(false);
    		botonPlay.setEnabled(false);;
    		for(int i =(int)nTicksSpinner.getValue() ; i >= 0 ;i--)
    		executeRun(i);
    		
        }
	});
         
}

//Inicializamos boton cambia clase CO2 de un vehiculo
private void initBotonStop(JButton botonStop) {
	
	botonStop.setIcon(new ImageIcon("resources/icons/stop.png"));
	botonStop.setToolTipText("Stop the simulator");
	
	
			
}
//Action boton carga fichero
private void actionBotonStop(JButton botonStop) {
	
	botonStop.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
        	stop();
        }
	});

                
}

//Inicializamos boton cambia clase CO2 de un vehiculo
private void initBotonExit(JButton botonSalir) {
	
	botonSalir.setIcon(new ImageIcon("resources/icons/exit.png"));
	botonSalir.setToolTipText("Exit the simulator");
	
	
			
}
//Action boton carga fichero
private void actionBotonExit(JButton botonSalir) {
	
	botonSalir.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
        	int seleccion = JOptionPane.showOptionDialog( (JFrame) SwingUtilities.getWindowAncestor(botonSalir),"Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,    new Object[] { "Yes", "No"},   null);
        	
        	if(seleccion == 0) {
        		System.exit(0);
        	}
        	
        }
	});

              
}


private void executeRun( int nTicks ){
	
	
	if (  !parado &&  nTicks > 0 ) {
		try {
		this.controller .run(1);
		} catch (Exception e ) {
		JOptionPane.showMessageDialog(this.getParent(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		parado = true ;
		return ;
		}
		/*SwingUtilities.invokeLater( new Runnable() { // Volvemos a llamar a run como un nuevo Runnable hasta que nticks = 0 o parado
			@Override
			public void run() {
				executeRun( nTicks - 1);
				
			}
		});*/
	} 
	else{
		System.out.print("PareDefinitivamente");
		parado = true ;
		
		botonCarga.setEnabled(true);
		botonContClass.setEnabled(true);
		botonTiempo.setEnabled(true);
		botonPlay.setEnabled(true);;
		
		
		
	}
}
	


private void stop() {
	parado = true ;
	botonCarga.setEnabled(true);
	botonContClass.setEnabled(true);
	botonTiempo.setEnabled(true);
	botonPlay.setEnabled(true);;
}

}
