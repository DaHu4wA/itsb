package at.ac.fhsalzburg.mmtlb.gui;

import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

public interface IFImageController {
	
	void setCurrentImage(MMTImage newImage);
	
	void setProgressBarVisible(boolean visible);
	
	void setProgressStatus(int progress);

}
