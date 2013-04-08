package at.ac.fhsalzburg.mmtlb.mmtimage;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Arrays;

import org.apache.log4j.Logger;

/**
 * Image for mmt assignment 1 containing a 1D-array grayscale image
 * 
 * @author Stefan Huber
 */
public class MMTImage {
	private static final Logger LOG = Logger.getLogger(MMTImage.class.getSimpleName());

	private int[] imageaData;
	private String name;
	private int height, width;

	public MMTImage(int height, int width) {
		this.height = height;
		this.width = width;
		this.imageaData = new int[height * width];

		LOG.debug("Height: "+height+", Width: "+width+". Array with size " + imageaData.length + " created.");
	}
	
	/**
	 * Deep copy a mmtImage
	 */
	public MMTImage(MMTImage other) {
		this.height = other.height;
		this.width = other.width;
		this.imageaData = Arrays.copyOf(other.getImageData(), other.getImageData().length);

		LOG.debug("Deep copy of MMTImage created");
	}
	
	/**
	 * @returns the {@link MMTImage} as BufferedImage
	 */
	public BufferedImage toBufferedImage() {
		BufferedImage bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster raster = bufferedImage.getRaster();
		raster.setSamples(0, 0, getWidth(), getHeight(), 0, getImageData());
		return bufferedImage;
	}

	/**
	 * @returns the pixel value for specific position
	 */
	public int getPixel(int i) {
		return imageaData[i];
	}

	/**
	 * Returns the pixel value for specific position
	 * 
	 * @param xPos
	 *            the x Position starting from 0
	 * @param yPos
	 *            the y Position starting from 0
	 */
	public int getPixel2D(int xPos, int yPos) {
		return imageaData[getArrayIndexOf(xPos, yPos)];
	}

	public void setPixel2D(int xPos, int yPos, int value) {
		imageaData[getArrayIndexOf(xPos, yPos)] = value;
	}

	/**
	 * 1-Dimensional Set the value of a specific pixel
	 * 
	 * @param i
	 *            the pixel
	 * @param val
	 *            the value to set
	 */
	public void setPixel1D(int i, int value) {
		imageaData[i] = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int[] getImageData() {
		return imageaData;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	/**
	 * @returns the index of the 1D data array for a specific 2D-coordinate
	 */
	private int getArrayIndexOf(int xPos, int yPos) {

		int position = 0;
		position += width * (yPos - 1); // add the width for every row
		position += xPos; // go to the correct column
		position -= 1; // array starts with 0
		return position;
	}
	
}
