package main.java.ar.edu.untref.industrial.holder;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class VideoImageHolder {
	
	private static List<BufferedImage> images = new ArrayList<>();

	public static List<BufferedImage> getImages() {
		return images;
	}

}
