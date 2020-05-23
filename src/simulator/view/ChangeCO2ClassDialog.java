package simulator.view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

import simulator.model.RoadMap;
import simulator.model.Vehicle;

public class ChangeCO2ClassDialog extends JDialog {
	
	private boolean boolOK;
	private RoadMap map;
	private final JPanel contentPanel = new JPanel();
	private JSpinner spTicks;
	private JComboBox<Vehicle> vehicles;
	private JComboBox<Integer> cbCO2;
	private DefaultComboBoxModel<Vehicle> vehiclesModel;
	
	public ChangeCO2ClassDialog(JFrame parent) {
		super(parent,true);
		initGUI();
	}
	
	private void initGUI() {

		setTitle("Change CO2 Class");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
        setBounds(100, 100, 450, 160);
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.NORTH);
        contentPanel.setBackground(Color.white);
        
        String infoDialog = "Schedule an event to change the CO2 class of a vehicle after a given number of simulation ticks from now.";
        JTextArea texto = new JTextArea(infoDialog);
        texto.setBounds(20, 20, 440, 220);
        texto.setWrapStyleWord(true);
        texto.setLineWrap(true);
        texto.setEditable(false);
        contentPanel.add(texto);
        
        JPanel panelC = new JPanel();
        getContentPane().add(panelC, BorderLayout.CENTER);
        panelC.setBackground(Color.white);
        
        JPanel panelCMain = new JPanel();
        panelC.add(panelCMain);
        panelCMain.setLayout(new FlowLayout());
        panelCMain.setBackground(Color.white);
        
        boolOK = false;
        
        JLabel tVehicle = new JLabel(" Vehicle: " );
        JLabel tCO2 = new JLabel(" CO2 Class: " );
        JLabel tTicks = new JLabel(" Ticks: " );
        JButton bCancel = new JButton("Cancel");
        JButton bOk = new JButton("OK");
        Integer [] contClass = {0,1,2,3,4,5,6,7,8,9,10};
        cbCO2 = new JComboBox<Integer> (contClass);
        
        vehiclesModel = new DefaultComboBoxModel<Vehicle>();
        vehicles = new JComboBox();
        vehicles.setModel(vehiclesModel);
        
        spTicks = new JSpinner();
        SpinnerModel model =   new SpinnerNumberModel(1, 0, 20, 1);
        spTicks.setModel(model);
        
        panelCMain.add(tVehicle);
        panelCMain.add(vehicles);
        panelCMain.add(tCO2);   
        panelCMain.add(cbCO2);
        panelCMain.add(tTicks); 
        panelCMain.add(spTicks); 
        
        actionBotonCancelar(bCancel);
        actionBotonOK(bOk);
        
        JPanel panelB = new JPanel();
        getContentPane().add(panelB, BorderLayout.SOUTH);
        panelB.setBackground(Color.white);
        panelB.add(bCancel);
        panelB.add(bOk);
        
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
	
	protected boolean open(RoadMap map) throws Exception {
		
		this.map = map;
		
		for(Vehicle v : map.getVehicles()) {
	        vehiclesModel.addElement(v);
	    }
		
		setVisible(true);
		
		return boolOK;
		
	}

	protected int getTicks() {
		return (int) spTicks.getValue();
	}
	
	protected int getContClass() {
		return (int) cbCO2.getSelectedItem();
	}
	
	protected Vehicle getVehicle() {
		return (Vehicle) vehicles.getSelectedItem();
	}
	
}
