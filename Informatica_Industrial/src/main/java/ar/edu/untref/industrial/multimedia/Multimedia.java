
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
 private static String path=null;
    
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
		  if(path!=null){
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
	}
    
	public static Boolean path(){
		Boolean escogio=Boolean.FALSE;
		JFileChooser eleccion = new JFileChooser(); 
		eleccion.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
		int returnVal = eleccion.showOpenDialog(eleccion); 
		if(returnVal == JFileChooser.APPROVE_OPTION) {  
			path = eleccion.getSelectedFile().getAbsolutePath(); 
		}
		if(path!=null){
			escogio=Boolean.TRUE;
		}
			
		return escogio;
    }
	
	public static File getArchivo(){
		File archivo = null;
		JFileChooser file=new JFileChooser(); 
		file.setFileSelectionMode(JFileChooser.FILES_ONLY); 
		int returnVal = file.showOpenDialog(file); 
		if(returnVal == JFileChooser.APPROVE_OPTION) {  
			 archivo=file.getSelectedFile(); 
		}
		return archivo;
		
	}
    
}