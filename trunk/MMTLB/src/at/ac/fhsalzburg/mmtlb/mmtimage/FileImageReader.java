package at.ac.fhsalzburg.mmtlb.mmtimage;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
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
		Raster raster = null;

		try {
			BufferedImage coloredImage = ImageIO.read(file);

			if (coloredImage == null) {
				return null;
			}

			// convert colored image to grayscale image
			bufferedImage = toGrayScaleImage(coloredImage);

			raster = bufferedImage.getData();
		} catch (IOException e) {
			LOG.error("Image could not be opened!", e);
		}

		if (raster == null) {
			return null;
		}

		int width = raster.getWidth();
		int height = raster.getHeight();

		MMTImage image = new MMTImage(height, width);

		raster.getPixels(0, 0, width, height, image.getImageData());
		image.setName(file.getName().substring(0, file.getName().lastIndexOf('.')));
		return image;
	}

	/**
	 * Converts an image into a grayscaled image
	 * 
	 * @param coloredImage
	 * @return image converted to grayscale
	 */
	private static BufferedImage toGrayScaleImage(BufferedImage coloredImage) {
		ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
		ColorConvertOp op = new ColorConvertOp(cs, null);
		return op.filter(coloredImage, null);
	}

}
