package at.ac.fhsalzburg.mmtlb.gui.datapanels;

public enum RasterSize {

	THREE(3, "3x3"), FIVE(5, "5x5"), SEVEN(7, "7x7"), NINE(9, "9x9"), ELEVEN(11, "11x11"), THIRTEEN(13, "13x13"), FIFTEEN(15, "15x15"), SEVENTEEN(
			17, "17x17");

	private int size;
	private String representation;

	RasterSize(int size, String representation) {
		this.size = size;
		this.representation = representation;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getRepresentation() {
		return representation;
	}

	public void setRepresentation(String representation) {
		this.representation = representation;
	}

}
