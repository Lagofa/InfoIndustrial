package main.java.ar.edu.untref.industrial.model;

import java.util.List;
import java.util.TimerTask;

import main.java.ar.edu.untref.industrial.grafico.EstadoTimer;
import main.java.ar.edu.untref.industrial.grafico.GraficoMapa;
import main.java.ar.edu.untref.industrial.holder.VideoImageHolder;
import main.java.ar.edu.untref.industrial.mp4.SequenceEncoder;

public class Timer extends TimerTask {

	private java.util.Timer timer;
	private GraficoMapa mapa;
	private EstadoTimer estado;
	private List<RowFileGps> rowsFile;
	private int rowRecorrida;
	private Boolean grabando = Boolean.FALSE;
	
	public Timer(GraficoMapa mapa, List<RowFileGps> rowsFile) {
		this.timer = new java.util.Timer();
		this.mapa = mapa;
		this.setEstado(EstadoTimer.STOP);
		double periodo = (double) 1 / 50 * 1000;
		this.timer.schedule(this, 0, (long) periodo);
		this.rowsFile = rowsFile;
	}

	@Override
	public void run() {
		if (this.estado.equals(EstadoTimer.CORRIENDO) && (rowRecorrida < rowsFile.size() - 1)) {
			rowRecorrida = rowRecorrida + 1;
			this.mapa.update(rowsFile.get(rowRecorrida).getSatelites(), this.grabando);
			this.mapa.repaint();
		}
	}

	public void setEstado(EstadoTimer estado) {
		this.estado = estado;
	}

	public void tomarImagenCadaMedioSegundo() {
		this.grabando = Boolean.TRUE;
		VideoImageHolder.getImages().clear();
	}

	public void exportarMp4() {
		this.grabando = Boolean.FALSE;
		SequenceEncoder.exportToMp4();
	}
}