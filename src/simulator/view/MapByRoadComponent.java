package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import simulator.control.Controller;
import simulator.enumerados.Weather;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class MapByRoadComponent extends JComponent implements TrafficSimObserver {
	
	private final String routeImageCar = "car.png";
	private Image imageCar;
	
	private final int cRadio = 14;
	private final int carSize = 24;
	private final int normalSize = 32;
	
	private Controller controller;
	private RoadMap map;

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
		
		Image nulo = null;
		
		try {
			return ImageIO.read(new File("resources/icons/" + img));
		} catch (IOException e) {
			System.out.println("Image load exception");
		}
		
		return nulo;
	}
	
	public void paintComponent(Graphics graphics) {
		
		super.paintComponent(graphics);
		Graphics2D graphicsActual = (Graphics2D) graphics;
		graphicsActual.setBackground(Color.WHITE);
		
		if (map != null && map.getRoads().size() != 0) drawingMapRoad(graphicsActual);
		else {
			graphicsActual.setColor(Color.black);
			graphicsActual.drawString("Waiting Map!", getWidth() / 2 - 50, getHeight() / 2);
		}

	}
	
	private void drawingMapRoad(Graphics graphics){
		
		int cordX1 = 50;
		int cordX2 = getWidth()-100;
		int cordY;
		
		for(int i = 0; i < map.getRoads().size(); i++){
			
			Road actualRoad = map.getRoads().get(i);
			cordY = (i+1)*50;
			
			this.drawingRoads(graphics, actualRoad, cordX1, cordX2, cordY);
			this.drawingJunctions(graphics,actualRoad, cordX1, cordX2, cordY);
			this.drawingVehicles(graphics,actualRoad, cordX1, cordX2, cordY);
			this.drawingWeather(graphics,actualRoad, cordX1, cordX2, cordY);
			this.drawingContaminacionClass(graphics,actualRoad, cordX1, cordX2, cordY);
			
		}
		
	}
	
	private void drawingRoads(Graphics graphic, Road road,int x1,int x2,int y){
		
		graphic.setColor(Color.BLACK);
		graphic.drawLine(x1, y, x2, y);
		graphic.setColor(Color.BLACK);
		Font myFont = new Font ("Times New Roman", 3, 14);
		graphic.setFont(myFont);
		graphic.drawString(road.getId(), x1 - 30, y);
		
	    }
	
	private void drawingJunctions(Graphics graphic, Road road, int x1, int x2, int y){
		
		Font myFont = new Font ("Times New Roman", 3, 14);
		graphic.setFont(myFont);
		graphic.setColor(Color.BLUE);
		graphic.fillOval(x1 - cRadio / 2, y - cRadio / 2, cRadio, cRadio);
		graphic.setColor(Color.ORANGE);
		graphic.drawString(road.getCruceOrigen().getId(), x1, y - cRadio);
		
		if(road != map.getJunction(road.getCruceDestino().getId()).getInRoads().get(map.getJunction(road.getCruceDestino().getId()).getGreenLightIndex())) {
			graphic.setColor(Color.RED);
		}
		else graphic.setColor(Color.GREEN);
		
		graphic.fillOval(x2 - cRadio / 2, y - cRadio / 2, cRadio, cRadio);
		graphic.setColor(Color.ORANGE);
		graphic.drawString(road.getCruceDestino().getId(), x2, y - cRadio);
		
		
	}
	
	private void drawingVehicles(Graphics graphic, Road r, int x1, int x2, int y){
		
        for( Vehicle vehicleAux : r.getListVehicle() ){
			
			int x = x1 + ( int ) ((x2 - x1) * (( double ) vehicleAux.getLocation() / ( double ) r.getLength()));
			graphic.drawImage(imageCar, x, y - carSize / 2, carSize, carSize, this);
			
			int vLabelColor = (int) (25.0 * (10.0 - (double) vehicleAux.getContaminacionClass()));
			graphic.setColor(new Color(0, vLabelColor, 0));
			graphic.drawString(vehicleAux.getId(), x, y - carSize / 2);
			
		}
        
    }

	private void drawingWeather(Graphics graphic, Road road, int x1, int x2, int y) {
		
		String routeImageSun = "sun.png";
		String routeImageStorm = "storm.png";
		String routeImageWind = "wind.png";
		String routeImageRain = "rain.png";
		String routeImageCloud = "cloud.png";
		
		if (road.getTiempo()==Weather.SUNNY) {
			Image Sunny = cargar(routeImageSun);
			graphic.drawImage(Sunny, x2 + normalSize / 2, y - normalSize, normalSize, normalSize, this);
		}
		else if (road.getTiempo()==Weather.RAINY) {
			Image Rainny = cargar(routeImageRain);
			graphic.drawImage(Rainny, x2 + normalSize / 2, y - normalSize, normalSize, normalSize, this);
		}
		else if (road.getTiempo()==Weather.CLOUDY) {
			Image Cloudy = cargar(routeImageCloud);
			graphic.drawImage(Cloudy, x2 + normalSize / 2, y - normalSize, normalSize, normalSize, this);
		}	
		else if (road.getTiempo()==Weather.WINDY) {
			Image Windy = cargar(routeImageWind);
			graphic.drawImage(Windy, x2 + normalSize / 2, y - normalSize, normalSize, normalSize, this);
		}
		else if (road.getTiempo()==Weather.STORM) {
			Image Storm = cargar(routeImageStorm);
			graphic.drawImage(Storm, x2 + normalSize / 2, y - normalSize, normalSize, normalSize, this);
		}	
			
	}
	
	private void drawingContaminacionClass(Graphics graphic, Road road, int x1, int x2, int y) {

		int contaminacionClass = ( int ) Math.floor(Math.min(( double ) road.getTotalCO2() /(1.0 + ( double ) road.getCO2Limit()),1.0) / 0.19);
		String routeContaminacionClass = "cont_" + contaminacionClass + ".png";
		Image contaminacionImage = cargar(routeContaminacionClass);
		
		graphic.drawImage(contaminacionImage, x2 + normalSize * 2, y - normalSize, normalSize, normalSize, this);
		
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		update(map);
	}
	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		update(map);
	}
	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		update(map);
	}
	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		update(map);
	}
	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		update(map);
	}
	@Override
	public void onError(String err) {}
	

	public void update(RoadMap map) {
		this.map = map;
		repaint();
	}

}
