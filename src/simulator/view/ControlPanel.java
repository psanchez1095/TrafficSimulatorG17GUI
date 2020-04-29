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
	
	private Controller _ctrl;
	private JButton bCargaFich,bChangeCO2,bChangeW,bPlay,bStop,bExit;
	private boolean _stopped;
	private RoadMap _map;
	private int _time;
	
	public ControlPanel(Controller _ctrl) {
		this._ctrl = _ctrl;
		this.setLayout(new GridLayout(0,2));
		_ctrl.addObserver(this);
		initGUI();
		setName("ControlPanel");
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this._time = time;
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		_map = map;
		_time = time;
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub

	}
	
private void initGUI() {
		
		
		JPanel mainP = new JPanel();
		mainP.setLayout(new FlowLayout(FlowLayout.LEFT));

		//Boton Carga del fichero de eventos
		initBotonCarga(bCargaFich);
		actionBotonCarga(bCargaFich);
		
		//A�adimos el boton al Jpanel Principal
		mainP.add(bCargaFich);
		
		JSeparator sep = new JSeparator(SwingConstants.VERTICAL);
		sep.setPreferredSize(new Dimension(2,50));
		sep.setForeground(Color.black);
		sep.setBackground(Color.black);
		mainP.add(sep);
		
		//Panel Botones de Changeco2 y ChangeWeather
		JPanel pChange = new JPanel(); 
		pChange.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		
		//Boton Cambio de la clase de contaminaci�n de un veh�culo
		initBotonCambioClaseC02(bChangeCO2);
		actionBotonCambioClaseC02(bChangeCO2);
		
		//A�adimos el boton al Jpanel de Cambios(CO2 Y TIEMPO)
		pChange.add(bChangeCO2);
		
		//Boton Cambio de tiempo de una carretera
		initBotonCambioTiempo(bChangeW);
		actionBotonCambioTiempo(bChangeW);
		
		//A�adimos el boton al Jpanel de Cambios(CO2 Y TIEMPO)
		pChange.add(bChangeW);
		
		//A�adimos el JPanel de cambios al panel principal
		mainP.add(pChange);
		
		JSeparator sep2 = new JSeparator(SwingConstants.VERTICAL);
		sep2.setPreferredSize(new Dimension(3,50));
		sep2.setForeground(Color.black);
		sep2.setBackground(Color.black);
		mainP.add(sep2);
		
		
		//Panel de botones play/stop (instanciamos y definimos la ubicacion)
		JPanel pBPlayStop = new JPanel();
		pBPlayStop.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		
		
		//Spinner de ticks
		JLabel tTicks = new JLabel("Ticks: ");
		JSpinner spTicks = new JSpinner();
		SpinnerModel model =   new SpinnerNumberModel(10, 1, 1000, 1);
		spTicks.setModel(model);
		spTicks.setPreferredSize(new Dimension(60,40));
		
		//Boton Play
		initBotonPlay(bPlay);
		actionBotonPlay(bPlay,spTicks);
		
		//Boton Stop
		initBotonStop(bPlay);
		actionBotonStop(bPlay);
		
		//A�adimos el JPanel de PlayStop tanto el boton play como el boton stop	
		pBPlayStop.add(bStop);
		pBPlayStop.add(bStop);
		
		//A�adimos el JPanel de PlayStop al panel principal
		mainP.add(pBPlayStop);
		
		//A�adimos el Spinner de Tics al panel principal
		mainP.add(tTicks);
		mainP.add(spTicks);
		add(mainP);
		
		//Instanciamos el bot�n exit y definimos su ubicaci�n
		JPanel pExit = new JPanel();
		pExit.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JSeparator sep3 = new JSeparator(SwingConstants.VERTICAL);
		sep3.setPreferredSize(new Dimension (3,50));
		sep3.setBackground(Color.black);
		sep3.setForeground(Color.black);
		
		pExit.add(sep3);
		
		//Boton Exit
		initBotonExit(bExit);
		actionBotonExit(bExit);
		
		//A�adimos el panelExit al panel principal
		pExit.add(bExit);
		
		add(pExit);
	}
//Inicializamos boton carga fichero
private void initBotonCarga(JButton botonCarga) {
	
	botonCarga = new JButton();
	botonCarga.setIcon(new ImageIcon("resources/icons/open.png"));
	botonCarga.setToolTipText("Open a file");
	
	
			
}
//Action boton carga fichero
private void actionBotonCarga(JButton botonCarga) {
	
	botonCarga.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent ae) {

    	JDialog dialog = new JDialog();
    	dialog.setTitle("Open");
    	dialog.setBounds(getParent().getWidth(), getParent().getHeight(), 500, 300);
    	JFileChooser fileChooser = new JFileChooser("resources/examples");
    	int returnValue = fileChooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {

			_ctrl.reset();
			try {
				

				_ctrl.loadEvents(new FileInputStream(fileChooser.getSelectedFile()));
				
				
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
	
	botonCambioClaseC02 = new JButton();
	botonCambioClaseC02.setIcon(new ImageIcon("resources/icons/co2class.png"));
	botonCambioClaseC02.setToolTipText("Change CO2 class of a Vehicle");
	
	
			
}
//Action boton carga fichero
private void actionBotonCambioClaseC02(JButton botonCambioClaseC02) {
	
	bChangeCO2.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
        	ChangeCO2ClassDialog dialog = new ChangeCO2ClassDialog((JFrame) SwingUtilities.getWindowAncestor(bChangeCO2));
        	
        	int status = dialog.open(_map);
        	
        	if(status == 1) {
        		List<Pair<String, Integer>> cs = new ArrayList<>();
        		cs.add(new Pair<String,Integer>(dialog.getVehicle().getId(),dialog.getContClass()));
        		_ctrl.addEvent(new NewSetContClassEvent(_time + dialog.getTicks(),cs));
        	}
        }
	});

                    
}

//Inicializamos boton cambia clase CO2 de un vehiculo
private void initBotonCambioTiempo(JButton botonCambioTiempo) {
	
	botonCambioTiempo = new JButton();
	botonCambioTiempo.setIcon(new ImageIcon("resources/icons/weather.png"));
	botonCambioTiempo.setToolTipText("Change Weather of a Road");
	
	
			
}
//Action boton carga fichero
private void actionBotonCambioTiempo(JButton botonCambioTiempo) {
	
	botonCambioTiempo.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
        	ChangeWeatherDialog dialog = new ChangeWeatherDialog((JFrame) SwingUtilities.getWindowAncestor(bChangeCO2));
        	
        	int status = dialog.open(_map);
        	
        	if(status == 1) {
        		List<Pair<String, Weather>> cs = new ArrayList<>();
        		cs.add(new Pair<String,Weather>(dialog.getRoad().getId(),dialog.getWeather()));
        		_ctrl.addEvent(new SetWeatherEvent(_time + dialog.getTicks(),cs));
        	}
        }
	});

                  
}

//Inicializamos boton cambia clase CO2 de un vehiculo
private void initBotonPlay(JButton botonPlay) {
	
	botonPlay = new JButton();
	botonPlay.setIcon(new ImageIcon("resources/icons/run.png"));
	botonPlay.setToolTipText("Run the simulator");
	
			
}
//Action boton carga fichero
private void actionBotonPlay(JButton botonPlay,JSpinner spinner) {
	
	botonPlay.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
        	_stopped = false;
        	enableToolBar(false);
        	run_sim((int)spinner.getValue());
        }
	});
         
}

//Inicializamos boton cambia clase CO2 de un vehiculo
private void initBotonStop(JButton botonStop) {
	
	botonStop = new JButton();
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
private void initBotonExit(JButton botonExit) {
	
	botonExit = new JButton();
	botonExit.setIcon(new ImageIcon("resources/icons/exit.png"));
	botonExit.setToolTipText("Exit the simulator");
	
	
			
}
//Action boton carga fichero
private void actionBotonExit(JButton botonExit) {
	
	botonExit.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
        	int seleccion = JOptionPane.showOptionDialog( (JFrame) SwingUtilities.getWindowAncestor(bExit),
        			   "Are you sure you want to quit?", 
        			   "Quit",
        			   JOptionPane.YES_NO_OPTION,
        			   JOptionPane.QUESTION_MESSAGE,
        			   null,    
        			   new Object[] { "No", "Yes"},   
        			   null);
        	
        	
        	if(seleccion == 1) {
        		System.exit(0);
        	}
        }
	});

              
}


private void run_sim( int n ) {
	if ( n > 0 && ! _stopped ) {
		try {
		_ctrl .run(1);
		} catch (Exception e ) {
		// TODO show error message
		_stopped = true ;
		return ;
		}
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			public void run() {
			run_sim( n - 1);
			}
		});
	} else {
		enableToolBar( true );
		_stopped = true ;
	}
}
	

private void enableToolBar(boolean enable) {
	
	bCargaFich.setEnabled(enable);
	bChangeCO2.setEnabled(enable);
	bChangeW.setEnabled(enable);
	bPlay.setEnabled(enable);
}

private void stop() {
	_stopped = true ;
	enableToolBar(true);
}

}
