package at.ac.fhsalzburg.mmtlb.mmtimage;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

/**
 * Global reader for {@link MMTImage}
 * 
 * @author Stefan Huber
 */
public class FileImageReader {
	private static final Logger LOG = Logger.getLogger(FileImageReader.class.getSimpleName());

	public static MMTImage read(String filePath) {
		LOG.debug(String.format("Opening file %s", filePath));

		return read(new File(filePath));
	}

	public static MMTImage read(File file) {

		BufferedImage bufferedImage = null;

		try {
			bufferedImage = ImageIO.read(file);
		} catch (IOException e) {
			LOG.error("Image could not be opened!", e);
		}

		if (bufferedImage == null) {
			return null;
		}

		Raster raster = bufferedImage.getData();

		int width = raster.getWidth();
		int height = raster.getHeight();

		MMTImage image = new MMTImage(height, width);

		raster.getPixels(0, 0, width, height, image.getImageData());
		image.setName(file.getName().substring(0, file.getName().lastIndexOf('.')));
		return image;
	}

}
