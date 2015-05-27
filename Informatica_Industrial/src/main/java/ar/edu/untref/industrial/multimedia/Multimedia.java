
 package main.java.ar.edu.untref.industrial.multimedia;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.jcodec.api.SequenceEncoder;


public class Multimedia {

private static int numeroCaptura=1;
        
 private static BufferedImage image;   
 private static String path;
    
	public static void captureScreen(Point p , Dimension screenSize)  {
        try {
            Rectangle screenRectangle = new Rectangle(p, screenSize);
            Robot robot = new Robot();
            image = robot.createScreenCapture(screenRectangle);            
            Guardar_Foto();
        } catch (Exception ex) {
        }
    }

    private static void Guardar_Foto(){
                guardar_imagen(path + "/captura" + numeroCaptura + ".jpg");
                numeroCaptura++;
    }
    
    private static void guardar_imagen(String f){
        try {            
            ImageIO.write(image, "jpg", new File(f));
       }catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: no se pudo guardar la imagen");
        }
    }

    public static void exportarMp4() throws IOException {
		  SequenceEncoder encoder = new SequenceEncoder(new File(path + "/video.mp4"));
		  JOptionPane.showMessageDialog(null, "Generando  archivo de video");
		  File fichero;
		  for (int i = 1; i < numeroCaptura; i++) {
			  BufferedImage bi = ImageIO.read(new File(String.format(path + "/captura" + i + ".jpg")));
		      encoder.encodeImage(bi); 
		      fichero = new File(path + "/captura" + i + ".jpg");
		      fichero.delete();
		  } 
		  numeroCaptura=1;
		  encoder.finish(); 
		  JOptionPane.showMessageDialog(null, "Archivo de video generado");
	}
    
	public static void path(){
		JFileChooser chooser = new JFileChooser(); 
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
		int returnVal = chooser.showOpenDialog(chooser); 
		if(returnVal == JFileChooser.APPROVE_OPTION) {  
			path = chooser.getSelectedFile().getAbsolutePath(); 
		}
    }
    
}