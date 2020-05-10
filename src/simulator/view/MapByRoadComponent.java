package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;


public class MapByRoadComponent extends JComponent implements TrafficSimObserver {
	
	private Controller controller;
	private RoadMap map;
	private Image imageCar;
	private final int cRadius = 14;
	private final int carSize = 20;
	private final int estandarSize = 32;
	
	private static final Color COLOR_WHITE = Color.WHITE;
	private final Color colorBlue = Color.BLUE;
	private final Color colorBlack = Color.BLACK;
	private final String routeImageCar = "car.png";
	private final String routeImageSun = "sun.png";
	private final String routeImageStorm = "storm.png";
	private final String routeImageWind = "wind.png";
	private final String routeImageRain = "rain.png";
	private final String routeImageCloud = "cloud.png";
	
	
	public MapByRoadComponent(Controller control) {
		
		this.controller = control;
		control.addObserver(this);
		this.initGUI();
		
	}
	private void initGUI() {
		imageCar = cargar(routeImageCar);
        setPreferredSize(new Dimension(300,200));
    }
	
	private Image cargar(String img) {
		
		try {
			return ImageIO.read(new File("resources/icons/" + img));
		} catch (IOException e) {
			System.out.println("Image load exception");
		}
		
		return null;
	}
	
	public void paintComponent(Graphics graphics) {
		
		super.paintComponent(graphics);

		Graphics2D graphicsActual = (Graphics2D) graphics;
		graphicsActual.setBackground(COLOR_WHITE);
		
		if (map == null || map.getRoads().size() == 0){
			graphicsActual.setColor(Color.red);
			graphicsActual.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
		} 
		else drawingMapRoad(graphicsActual);
		
		
		
	}
	
	private void drawingMapRoad(Graphics graphics){
		
		int cordY;
		int cordX1 = 50;
		int cordX2 = getWidth()-100;
		
		
		for(int i = 0; i < map.getRoads().size(); i++){
			
			Road actualRoad = map.getRoads().get(i);
			cordY = (i+1)*50;
			
			//Dibujamos cada uno de los componentes del mapa en el grafico 2D
			this.drawingRoads(graphics, actualRoad, cordX1, cordX2, cordY);
			this.drawingJunctions(graphics,actualRoad, cordX1, cordX2, cordY);
			this.drawingVehicles(graphics,actualRoad, cordX1, cordX2, cordY);
			this.drawingWeather(graphics,actualRoad, cordX1, cordX2, cordY);
			this.drawingContaminacionClass(graphics,actualRoad, cordX1, cordX2, cordY);
			
		}
		
		
	}
	
	private void drawingRoads(Graphics graphic, Road road,int x1,int x2,int y){
		
		graphic.setColor(colorBlack);
		graphic.drawLine(x1, y, x2, y);
		graphic.setColor(colorBlack);
		graphic.drawString(road.getId(), x1 - 30, y);
		
	    }
	
	private void drawingJunctions(Graphics graphic, Road road, int x1, int x2, int y){
		
		//Cruces Origen
		graphic.setColor(colorBlue);
		graphic.fillOval(x1 - cRadius / 2, y - cRadius / 2, cRadius, cRadius);
		
		
		graphic.setColor(Color.MAGENTA);
		graphic.drawString(road.getCruceOrigen().getId(), x1, y - cRadius);
		
		//Cruces Destino
		
		//Se compara la carretera actual con la que tiene el semaforo en verde en la lista de carreteras entrantes del cruce destino de la misma
		if(road == map.getJunction(road.getCruceDestino().getId()).getInRoads().get(map.getJunction(road.getCruceDestino().getId()).getGreenLightIndex())) {
			graphic.setColor(Color.GREEN);
		}
		else graphic.setColor(Color.RED);
		
		
		graphic.fillOval(x2 - cRadius / 2, y - cRadius / 2, cRadius, cRadius);
		
		
		graphic.setColor(Color.MAGENTA);
		graphic.drawString(road.getCruceDestino().getId(), x2, y - cRadius);
		
		
	}
	
	private void drawingVehicles(Graphics graphic, Road r, int x1, int x2, int y) {
		
        for(Vehicle v : r.getListVehicle()) {
			
			int x = x1 + ( int ) ((x2 - x1) * (( double ) v.getLocation() / ( double ) r.getLength()));
			graphic.drawImage(imageCar, x, y - carSize / 2, carSize, carSize, this);
			
			int vLabelColor = (int) (25.0 * (10.0 - (double) v.getContaminacionClass()));
			graphic.setColor(new Color(0, vLabelColor, 0));
			graphic.drawString(v.getId(), x, y - carSize / 2);
			
		}
        
    }

	private void drawingWeather(Graphics graphic, Road road, int x1, int x2, int y) {

		switch(road.getTiempo()) {
		
		case SUNNY:
			graphic.drawImage(cargar(this.routeImageSun), x2 + estandarSize / 2, y - estandarSize, estandarSize, estandarSize, this);
			break;
			
		case CLOUDY:
			graphic.drawImage(cargar(this.routeImageCloud), x2 + estandarSize / 2, y - estandarSize, estandarSize, estandarSize, this);
			break;
			
		case RAINY:
			graphic.drawImage(cargar(this.routeImageRain), x2 + estandarSize / 2, y - estandarSize, estandarSize, estandarSize, this);
			break;
		case STORM:
			graphic.drawImage(cargar(this.routeImageStorm), x2 + estandarSize / 2, y - estandarSize, estandarSize, estandarSize, this);
			break;
		case WINDY:
			graphic.drawImage(cargar(this.routeImageWind), x2 + estandarSize / 2, y - estandarSize, estandarSize, estandarSize, this);
			break;
			
		}
		
	}
	
	private void drawingContaminacionClass(Graphics graphic, Road road, int x1, int x2, int y) {

		int contaminacionClass = ( int ) Math.floor(Math.min(( double ) road.getTotalCO2() /(1.0 + ( double ) road.getCO2Limit()),1.0) / 0.19);
		graphic.drawImage(cargar("cont_" + contaminacionClass + ".png"), x2 + estandarSize * 2, y - estandarSize, estandarSize, estandarSize, this);
		
	}
	
	
	public void update(RoadMap map) {
		this.map = map;
		repaint();
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		update(map);
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		update(map);
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		update(map);
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		update(map);
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		update(map);
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub

	}

}
