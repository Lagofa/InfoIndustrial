package main.java.ar.edu.untref.industrial.formularios;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import main.java.ar.edu.untref.industrial.grafico.AccionVideo;
import main.java.ar.edu.untref.industrial.grafico.EstadoTimer;
import main.java.ar.edu.untref.industrial.grafico.GraficoMapa;
import main.java.ar.edu.untref.industrial.model.Timer;
import main.java.ar.edu.untref.industrial.multimedia.Multimedia;
import main.java.ar.edu.untref.industrial.parser.Parser;

public class Form extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private GraficoMapa graficoMapa;
	private Timer timer;
	private JPanel contentPane;
	private JMenu archivo;
	private JMenuItem abrir;
    private JMenuBar menu;
	
	public Form() {
		graficoMapa = new GraficoMapa(new Point(250, 250));
		timer = new Timer(this.graficoMapa);
		initUI();
	}

	private void initUI() {
		setSize(1200, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel subPanel = cargarPaneles();
		cargarBotones(subPanel);
		cargarMenu();
		timer.run();
	}

	private JPanel cargarPaneles() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane,  new GridBagConstraints());
		scrollPane.setViewportView(this.graficoMapa);
		scrollPane.setPreferredSize(new Dimension(500, 500));
		
		JPanel subPanel = new JPanel();
		panel.add(subPanel, new GridBagConstraints());
		subPanel.setLayout(new GridBagLayout());
		
		return subPanel;
	}

	private void cargarBotones(JPanel subPanel) {
		JButton buttonPlay = new JButton();
		buttonPlay.setText("PLAY");
		buttonPlay.setPreferredSize(new Dimension(90, 30));
		buttonPlay.setFocusable(false);
		buttonPlay.setBackground(Color.white);
		buttonPlay.setActionCommand(AccionVideo.PLAY.toString());
		buttonPlay.addActionListener(this);
		subPanel.add(buttonPlay, new GridBagConstraints());

		JButton buttonPause = new JButton();
		buttonPause.setText("PAUSA");
		buttonPause.setPreferredSize(new Dimension(90, 30));
		buttonPause.setFocusable(false);
		buttonPause.setBackground(Color.white);
		buttonPause.setActionCommand(AccionVideo.PAUSA.toString());
		buttonPause.addActionListener(this);
		subPanel.add(buttonPause, new GridBagConstraints());
		
		JButton buttonGrabar = new JButton();
		buttonGrabar.setText("GRABAR");
		buttonGrabar.setPreferredSize(new Dimension(90, 30));
		buttonGrabar.setFocusable(false);
		buttonGrabar.setBackground(Color.white);
		buttonGrabar.setActionCommand(AccionVideo.GRABAR.toString());
		buttonGrabar.addActionListener(this);
		subPanel.add(buttonGrabar, new GridBagConstraints());
		
		JButton buttonDetenerGrab = new JButton();
		buttonDetenerGrab.setText("DETENER GRABACION");
		buttonDetenerGrab.setPreferredSize(new Dimension(160, 30));
		buttonDetenerGrab.setFocusable(false);
		buttonDetenerGrab.setBackground(Color.white);
		buttonDetenerGrab.setActionCommand(AccionVideo.DETENER_GRABACION.toString());
		buttonDetenerGrab.addActionListener(this);
		subPanel.add(buttonDetenerGrab, new GridBagConstraints());
		
		JButton buttonTraceOn= new JButton();
		buttonTraceOn.setText("ON");
		buttonTraceOn.setPreferredSize(new Dimension(90, 30));
		buttonTraceOn.setFocusable(false);
		buttonTraceOn.setBackground(Color.white);
		buttonTraceOn.setActionCommand(AccionVideo.TRACE_ON.toString());
		buttonTraceOn.addActionListener(this);
		subPanel.add(buttonTraceOn, new GridBagConstraints());
		
		JButton buttonTraceOff= new JButton();
		buttonTraceOff.setText("OFF");
		buttonTraceOff.setPreferredSize(new Dimension(90, 30));
		buttonTraceOff.setFocusable(false);
		buttonTraceOff.setBackground(Color.white);
		buttonTraceOff.setActionCommand(AccionVideo.TRACE_OFF.toString());
		buttonTraceOff.addActionListener(this);
		subPanel.add(buttonTraceOff, new GridBagConstraints());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String name = e.getActionCommand();

		if (name.equals(AccionVideo.PLAY.toString())) {
			this.timer.setEstado(EstadoTimer.CORRIENDO);
		} else if (name.equals(AccionVideo.PAUSA.toString())) {
			this.timer.setEstado(EstadoTimer.PAUSA);
		} else if (name.equals(AccionVideo.GRABAR.toString())) {
			this.timer.setEstado(EstadoTimer.PAUSA);
			if(Multimedia.path()){
				this.timer.setEstado(EstadoTimer.CORRIENDO);
				this.timer.grabar();
			}else{
				JOptionPane.showMessageDialog(null, "Error: Debe seleccionar una carpeta para guardar el video");
			}
		} else if (name.equals(AccionVideo.DETENER_GRABACION.toString())) {
			this.timer.setEstado(EstadoTimer.STOP);
			try {
				this.timer.exportarMp4();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Error: no se pudo generar el video");
			}
		} else if (name.equals(AccionVideo.TRACE_ON.toString())){
			this.graficoMapa.trace(Boolean.TRUE);
		} else if (name.equals(AccionVideo.TRACE_OFF.toString())){
			this.graficoMapa.trace(Boolean.FALSE);
		}else if(name.equals(abrir.getActionCommand())){
			    File archivo=Multimedia.getArchivo();
			      if(archivo!=null){
			         try {
						timer.setRowsFile(Parser.parsear(archivo));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
			      }
			   }
	}
	
    private void cargarMenu(){
		menu = new JMenuBar();
		setJMenuBar(menu);
		archivo=new JMenu("Archivo");
	    menu.add(archivo);
	    abrir=new JMenuItem("Abrir");
	    abrir.addActionListener(this);
	    archivo.add(abrir);
    }
 
}