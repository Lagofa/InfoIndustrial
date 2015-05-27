package test.java.ar.edu.untref.industrial.tests;

import java.io.File;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import main.java.ar.edu.untref.industrial.model.RowFileGps;
import main.java.ar.edu.untref.industrial.parser.Parser;

public class ParserTest {

	@Test
	public void testValorLongitudPrimerLinea() {
		List<RowFileGps> rows = Parser.parsear(new File("src/test/resources/gps1.txt"));
		
		Assert.assertEquals("Verifico el valor de la primer linea", "05907.8820", rows.get(0).getValLong());
	}
	
	@Test
	public void testFechaPrimerLinea() {
		List<RowFileGps> rows = Parser.parsear(new File("src/test/resources/gps1.txt"));
		
		Assert.assertEquals("Verifico el valor de la primer linea", "280911", rows.get(0).getFecha());
	}
	
	@Test
	public void testValorLatitudSegundaLinea() {
		List<RowFileGps> rows = Parser.parsear(new File("src/test/resources/gps1.txt"));
		
		Assert.assertEquals("Verifico el valor de la primer linea", "3719.6313", rows.get(1).getValLat());
	}
	
}
