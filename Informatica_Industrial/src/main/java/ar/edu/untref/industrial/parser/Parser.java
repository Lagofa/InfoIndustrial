package main.java.ar.edu.untref.industrial.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.java.ar.edu.untref.industrial.model.RowFileGps;
import main.java.ar.edu.untref.industrial.model.Satelite;

public class Parser {

	private static final String SEPARATOR_FILE_ROW = ",";

	public static List<RowFileGps> parsear(File archivoGPS) {
		BufferedReader br = leerArchivo(archivoGPS);

		List<RowFileGps> resultado = new ArrayList<>();

		String row = leerPrimerLinea(br);
		while (row != null) {
			String[] actual = row.split(SEPARATOR_FILE_ROW);
			
			String [] proximosSatelites = new String [12];
			
			for(int i = 11; i < 23; i++){
				proximosSatelites[i-11] = actual [i];
			}

			RowFileGps rowFileGPS = new RowFileGps();
			rowFileGPS.setFecha(actual[0]);
			rowFileGPS.setHora(actual[1]);
			rowFileGPS.setValLat(actual[2]);
			rowFileGPS.setUniLat(actual[3]);
			rowFileGPS.setValLong(actual[4]);
			rowFileGPS.setUniLong(actual[5]);
			
			rowFileGPS.setSatTrack(Integer.valueOf(actual[6]));

			for (int i = 0; i < rowFileGPS.getSatTrack(); i++) {
				if(!Integer.valueOf(proximosSatelites[i]).equals(0)){
					Satelite satelite = new Satelite();
					satelite.setPrn(Integer.valueOf(actual[27 + i * 4]));
					satelite.setElev(Integer.valueOf(actual[28 + i * 4]));
					satelite.setAz(Integer.valueOf(actual[29 + i * 4]));
					satelite.setSnr(Integer.valueOf(actual[30 + i * 4]));
					rowFileGPS.getSatelites().add(satelite);
				}
			}

			resultado.add(rowFileGPS);

			row = leerLinea(br);
		}

		return resultado;
	}

	private static String leerPrimerLinea(BufferedReader br) {
		leerLinea(br);
		leerLinea(br);
		leerLinea(br);
		leerLinea(br);
		
		return leerLinea(br);
	}

	private static String leerLinea(BufferedReader br) {
		try {
			return br.readLine();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private static BufferedReader leerArchivo(File informacionGPS) {
		try {
			FileReader fr = new FileReader(informacionGPS);

			return new BufferedReader(fr);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}
