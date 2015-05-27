package main.java.ar.edu.untref.industrial.model;

import java.util.ArrayList;
import java.util.List;

public class RowFileGps {

	private String fecha;
	private String hora;
	private String valLat;
	private String uniLat;
	private String valLong;
	private String uniLong;
	private int satTrack;
	private List<Satelite> satelites = new ArrayList<>();

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getValLat() {
		return valLat;
	}

	public void setValLat(String valLat) {
		this.valLat = valLat;
	}

	public String getUniLat() {
		return uniLat;
	}

	public void setUniLat(String uniLat) {
		this.uniLat = uniLat;
	}

	public String getValLong() {
		return valLong;
	}

	public void setValLong(String valLong) {
		this.valLong = valLong;
	}

	public String getUniLong() {
		return uniLong;
	}

	public void setUniLong(String uniLong) {
		this.uniLong = uniLong;
	}

	public int getSatTrack() {
		return satTrack;
	}

	public void setSatTrack(int satTrack) {
		this.satTrack = satTrack;
	}

	public List<Satelite> getSatelites() {
		return satelites;
	}

	public void setSatelites(List<Satelite> satelites) {
		this.satelites = satelites;
	}
	
}
