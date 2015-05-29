package main.java.ar.edu.untref.industrial.grafico;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import javax.swing.ImageIcon;
import javax.swing.JComponent;

import main.java.ar.edu.untref.industrial.model.*;
import main.java.ar.edu.untref.industrial.multimedia.Multimedia;

public class GraficoMapa extends JComponent {

	private static final long serialVersionUID = 1L;
	private Point center;
	private double size = 2;
	private Graphics2D graphics2D;
	private Boolean grabando;
	private List<Satelite>satelites;
	private List<RowFileGps> filasGps =new ArrayList<>();
	private boolean trace= false;
    
    
	/**
	 * Recibe el punto del centro del grafico
	 * 
	 * @param center
	 */
	public GraficoMapa(Point center) {
		this.center = center;
	}

	public void doDrawing() throws IOException {
		Rectangle2D.Double rectangle = new Rectangle2D.Double(0, 0, this.getWidth(), this.getHeight());
		
		graphics2D.setColor(Color.white);
		graphics2D.fill(rectangle);
		graphics2D.setColor(Color.black);
		
		int j = 10;
		while (j < 100) {
			double result = j * size;

			drawEllipse(result);

			j = j + 10;
		}
		graphics2D.drawString("NORTE", 225,50);
		graphics2D.drawString("OESTE", 20,250);
		graphics2D.drawString("ESTE", 440,250);
		graphics2D.drawString("SUR", 240,460);
		this.drawingSatellites();
		
	}

	private void drawingSatellites() throws IOException {
		RowFileGps fila = new RowFileGps();		
		if (satelites != null) {
			dibujar(satelites);
			if(trace==Boolean.TRUE){	
				dibujarTrace();
				fila.getSatelites().addAll(satelites);
				this.filasGps.add(fila);
			} 
			if (grabando) {
				Multimedia.captureScreen(new Point(0,0) , new Dimension(1200,600));
			}
		}
	}

	private void drawEllipse(double result) {
		Ellipse2D.Double circle = new Ellipse2D.Double(center.x - result,
				center.y - result, 2 * result, 2 * result);

		graphics2D.draw(circle);
	}

	@Override
	public void paintComponent(Graphics g) {
		this.graphics2D = (Graphics2D) g;
		
		super.paintComponent(g);
		
		try {
			doDrawing();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Satelite> getSatelites() {
		return satelites;
	}

	public void setSatelites(List<Satelite> satelites) {
		this.satelites = satelites;
	}
	
	public void update(List<Satelite> satelites, Boolean grabando) {
		this.satelites = satelites;
		this.grabando = grabando;
	}
	
	private Image getIcon(Satelite satelite) throws IOException {

		Image imgSatelite;
		
        switch (satelite.getPrn()) {
		case 11:
			imgSatelite = new ImageIcon(GraficoMapa.class.getResource("iconoGreen.gif")).getImage();
			break;
		case 13:
			imgSatelite = new ImageIcon(GraficoMapa.class.getResource("iconoGray.gif")).getImage();
			break;
		case 20:
			imgSatelite = new ImageIcon(GraficoMapa.class.getResource("iconoBlue.gif")).getImage();
			break;
		case 23:
			imgSatelite = new ImageIcon(GraficoMapa.class.getResource("iconoOrange.gif")).getImage();
			break;
		case 24:
			imgSatelite = new ImageIcon(GraficoMapa.class.getResource("iconoRed.gif")).getImage();
			break;
		case 32:
			imgSatelite = new ImageIcon(GraficoMapa.class.getResource("iconoMagenta.gif")).getImage();
			break;
		default:
			imgSatelite = new ImageIcon(GraficoMapa.class.getResource("iconoBlack.gif")).getImage();
			break;
		}

		return imgSatelite;
	}

	public void trace(boolean trace) {
		this.trace= trace;
		if(trace==Boolean.FALSE){
			filasGps.clear();
		}
	}
	
	private void dibujar(List<Satelite>satelites) throws IOException{
		for (Satelite unSatelite: satelites) {
			Point2D.Double pointSat = calcularPunto(unSatelite);
			graphics2D.setColor(getColor(unSatelite));
			graphics2D.drawString("" + unSatelite.getPrn(), (int)pointSat.x, (int)pointSat.y);
			graphics2D.drawImage(getIcon(unSatelite), (int)pointSat.x, (int)pointSat.y, null);
		}
	}
	
	private Point2D.Double calcularPunto(Satelite unSatelite){
		Point2D.Double pointSat;
		double elevacion = 90 - unSatelite.getElev();
		double azimuth = -unSatelite.getAz() + 90;
		double x = center.x + Math.cos(Math.toRadians(azimuth))* elevacion * size;
		double y = center.y - Math.sin(Math.toRadians(azimuth))* elevacion * size;
		pointSat = new Point2D.Double(x, y);
		return pointSat;
	}
	
	private void dibujarTrace(){
		for (RowFileGps unaFila: filasGps) {
			for (Satelite unSatelite: unaFila.getSatelites()) {
				Point2D.Double pointSat = calcularPunto(unSatelite);
				graphics2D.setColor(getColor(unSatelite));
				graphics2D.fillRect((int)pointSat.x-1, (int)pointSat.y-1,2,2);
			}
	    }
	}
	
	private Color getColor(Satelite satelite) {

		Color color;

		switch (satelite.getPrn()) {
		case 11:
			color = Color.GREEN;
			break;
		case 13:
			color = Color.GRAY;
			break;
		case 20:
			color = Color.BLUE;
			break;
		case 23:
			color = Color.ORANGE;
			break;
		case 24:
			color = Color.RED;
			break;
		case 32:
			color = Color.MAGENTA;
			break;
		default:
			color = Color.BLACK;
			break;
		}

		return color;
	}
	
}