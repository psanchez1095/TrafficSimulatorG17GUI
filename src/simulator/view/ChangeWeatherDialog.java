package simulator.view;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.enumerados.Weather;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeWeatherDialog extends JDialog {
	
	private RoadMap map;
	private boolean boolOK;
	private final JPanel contPanel = new JPanel();

	private JSpinner ticksSpinner;
	private JComboBox<Road> roadsComboBox;
	private JComboBox<Weather> weatherComboBox;
	private DefaultComboBoxModel<Road> roadsModel;
	
	public ChangeWeatherDialog(JFrame parent) {
		super(parent,true);
		initGUI();
	}
	
	private void initGUI() {
		
		setTitle("Change Road Weather");
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
        setBounds(100, 100, 450, 160);
        boolOK = false;
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contPanel, BorderLayout.NORTH);
        contPanel.setBackground(Color.white);
        
        String infoDialog = "Schedule an event to change the weather of a road after a given number of simulation ticks from now.";
        JTextArea texto = new JTextArea(infoDialog);
        
        texto.setBounds(20, 20, 440, 220);
        texto.setWrapStyleWord(true);
        texto.setLineWrap(true);
        texto.setEditable(false);
        
        contPanel.add(texto);
        
        JPanel panelC = new JPanel();
        getContentPane().add(panelC, BorderLayout.CENTER); 
        panelC.setBackground(Color.white);
        
        JPanel panelCMain = new JPanel();
        panelC.add(panelCMain); 
        panelCMain.setLayout(new FlowLayout());
        panelCMain.setBackground(Color.white);
        
        
        JLabel tRoad = new JLabel(" Road: " );
        JLabel tWeather = new JLabel(" Weather: " );
        JLabel tTicks = new JLabel(" Ticks: " );
        JButton bCancel = new JButton("Cancel");
        JButton bOk = new JButton("OK");
        ticksSpinner = new JSpinner();
        
        panelCMain.add(tRoad);
        
        roadsModel = new DefaultComboBoxModel<Road>();
        roadsComboBox = new JComboBox();
        
        roadsComboBox.setModel(roadsModel);
        panelCMain.add(roadsComboBox);
        
        panelCMain.add(tWeather);   
        weatherComboBox = new JComboBox<Weather> (Weather.values());
        panelCMain.add(weatherComboBox);
       
        panelCMain.add(tTicks); 
        SpinnerModel model =   new SpinnerNumberModel(1, 0, 20, 1);
        ticksSpinner.setModel(model);
        panelCMain.add(ticksSpinner); 
       
        actionBotonCancelar(bCancel);
        actionBotonOK(bOk);
        
        JPanel panelB = new JPanel();
        panelB.setBackground(Color.white);
        panelB.add(bCancel);
        panelB.add(bOk);
        getContentPane().add(panelB, BorderLayout.SOUTH);
        
        this.pack();
    	this.setLocationRelativeTo(null);
    	
	}
	
	private void actionBotonCancelar(JButton botonCancelar) {
		botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
            	boolOK = false;
            	setVisible(false);
            }
		});
	}
	
	private void actionBotonOK(JButton botonOK) {
		botonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
            	boolOK = true;
            	setVisible(false);
            }
		});
	}
	
	boolean open(RoadMap map) throws Exception {
		
		this.map = map;
		
		for(Road r : map.getRoads()) roadsModel.addElement(r);
		
		setVisible(true);
		
		return boolOK;
	}

	protected int getTicks() {
		return (int) ticksSpinner.getValue();
	}
	protected Road getRoad() {
		return (Road) roadsComboBox.getSelectedItem();
	}
	protected Weather getWeather() {
		return (Weather) weatherComboBox.getSelectedItem();
	}
	
		
	
	
	
	
	
}
