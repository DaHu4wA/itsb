package at.ac.fhsalzburg.mmtlb.mmtimage;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

/**
 * Global writer for {@link MMTImage}
 * 
 * @author Stefan Huber
 */
public class FileImageWriter {
	private static final Logger LOG = Logger.getLogger(FileImageWriter.class.getSimpleName());

	public static void write(MMTImage image, String filePath) {
		write(image, new File(filePath));
	}

	public static void write(MMTImage image, File file) {
		write(image, file, "jpg");
	}

	public static File write(MMTImage image, File file, String fileType) {
		LOG.debug(String.format("Writing image to %s", file.getAbsoluteFile()));

		try {
			ImageIO.write(image.toBufferedImage(), fileType, file);
		} catch (IOException e) {
			LOG.error("Error writing file", e);
		}
		LOG.info(String.format("-->> Image %s written successfully!", file.getName()));
		return file;
	}

	public static File writeToTempFile(MMTImage image) {
		try {
			return write(image, File.createTempFile("TempImage", ".jpg"), "jpg");
		} catch (IOException e) {
			LOG.error("Error creating temp file!", e);
			return null;
		}
	}
}
