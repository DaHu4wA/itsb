package at.ac.fhsalzburg.mmtlb.applications.tools;

import java.io.File;

import org.apache.log4j.Logger;

import at.ac.fhsalzburg.mmtlb.mmtimage.FileImageReader;
import at.ac.fhsalzburg.mmtlb.mmtimage.FileImageWriter;
import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

/**
 * Converts a whole folders jpg content into png
 * 
 * @author Stefan Huber
 */
public class FileImageConverter {
	private static final Logger LOG = Logger.getLogger(FileImageConverter.class.getSimpleName());
	public static final String SUBFOLDER_NAME = "convertedByMMT";

	/**
	 * Converts all files within given folder from.jpg and .jpeg into .png
	 * 
	 * @param directory
	 *            to convert
	 * @return count of converted files
	 */
	public static int convertFolderFromJpgToPng(File directory) {
		int convertedCount = 0;

		if (!directory.isDirectory()) {
			LOG.error("File is not a directory!!");
			return 0;
		}
		System.out.println(directory.getAbsoluteFile());
		File targetDir = new File(directory.getAbsoluteFile() + "\\" + SUBFOLDER_NAME + "\\");
		targetDir.mkdirs();

		File[] files = directory.listFiles();

		for (File f : files) {

			if (f.isFile() && isSupported(f)) {

				LOG.info("Converting file " + f.getName());
				MMTImage image = FileImageReader.read(f);

				String newFileName = f.getName().substring(0, f.getName().lastIndexOf('.')) + ".png";
				FileImageWriter.write(image, new File(targetDir, newFileName), "png");
				convertedCount++;
			}
		}
		LOG.info("--> Folder successfully converted. See subfolder >> " + SUBFOLDER_NAME + " << inside chosen directory");
		return convertedCount;
	}

	/**
	 * @returns true if file is supported to be converted
	 */
	private static boolean isSupported(File f) {
		if (f.getName().toLowerCase().endsWith(".jpg") || f.getName().toLowerCase().endsWith(".jpeg")) {
			return true;
		}
		return false;
	}

}
