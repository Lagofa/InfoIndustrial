package main.java.ar.edu.untref.industrial.model;

import java.io.IOException;
import java.util.List;
import java.util.TimerTask;

import main.java.ar.edu.untref.industrial.grafico.EstadoTimer;
import main.java.ar.edu.untref.industrial.grafico.GraficoMapa;
import main.java.ar.edu.untref.industrial.multimedia.Multimedia;

public class Timer extends TimerTask {

	private java.util.Timer timer;
	private GraficoMapa mapa;
	private EstadoTimer estado;
	private List<RowFileGps> rowsFile=null;
	private int rowRecorrida;
	private Boolean grabando = Boolean.FALSE;
	
	public Timer(GraficoMapa mapa) {
		this.timer = new java.util.Timer();
		this.mapa = mapa;
		this.setEstado(EstadoTimer.STOP);
		double periodo = (double) 1 / 50 * 1000;
		this.timer.schedule(this, 0, (long) periodo);
	}

	@Override
	public void run() {
		if (this.estado.equals(EstadoTimer.CORRIENDO) && (rowsFile!=null) && (rowRecorrida < rowsFile.size() - 1)) {
			rowRecorrida = rowRecorrida + 1;
			this.mapa.update(rowsFile.get(rowRecorrida).getSatelites(), this.grabando);
			this.mapa.repaint();
		}
	}

	public void setEstado(EstadoTimer estado) {
		this.estado = estado;
	}

	public void grabar() {
		this.grabando = Boolean.TRUE;
	}

	public void exportarMp4() throws IOException {
		this.grabando = Boolean.FALSE;
        Multimedia.exportarMp4();
	}
	public void setRowsFile(List<RowFileGps> rowsFile) throws IOException{
		this.rowsFile = rowsFile;
		rowRecorrida=0;
		this.estado= EstadoTimer.STOP;
		this.mapa.update(null, this.grabando);
		this.mapa.repaint();
	}
}