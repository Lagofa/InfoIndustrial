package main.java.ar.edu.untref.industrial.mp4;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

import main.java.ar.edu.untref.industrial.holder.VideoImageHolder;

import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;

public class SequenceEncoder {

	private static final double FRAME_RATE = 50;

	private static final String outputFilename = "c:/grabacion.mp4";

	private static Dimension screenBounds;

	public static void exportToMp4() {

		// let's make a IMediaWriter to write the file.

		final IMediaWriter writer = ToolFactory.makeWriter(outputFilename);

		screenBounds = Toolkit.getDefaultToolkit().getScreenSize();

		// We tell it we're going to add one video stream, with id 0,

		// at position 0, and that it will have a fixed frame rate of
		// FRAME_RATE.

		writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_MPEG4,

		screenBounds.width / 2, screenBounds.height / 2);

		long startTime = System.nanoTime();

		for (int index = 0; index < VideoImageHolder.getImages().size(); index++) {

			// take the screen shot

			BufferedImage screen = VideoImageHolder.getImages().get(index);

			// convert to the right image type

			BufferedImage bgrScreen = convertToType(screen,

			BufferedImage.TYPE_3BYTE_BGR);

			// encode the image to stream #0

			writer.encodeVideo(0, bgrScreen, System.nanoTime() - startTime,

			TimeUnit.NANOSECONDS);

			// sleep for frame rate milliseconds

			try {

				Thread.sleep((long) (1000 / FRAME_RATE));

			}

			catch (InterruptedException e) {

				// ignore

			}

		}

		// tell the writer to close and write the trailer if needed

		writer.close();

	}

	public static BufferedImage convertToType(BufferedImage sourceImage,
			int targetType) {

		BufferedImage image;

		// if the source image is already the target type, return the source
		// image

		if (sourceImage.getType() == targetType) {

			image = sourceImage;

		}

		// otherwise create a new image of the target type and draw the new
		// image

		else {

			image = new BufferedImage(sourceImage.getWidth(),

			sourceImage.getHeight(), targetType);

			image.getGraphics().drawImage(sourceImage, 0, 0, null);

		}

		return image;

	}

}