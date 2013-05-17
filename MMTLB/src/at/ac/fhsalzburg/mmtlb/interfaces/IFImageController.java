package at.ac.fhsalzburg.mmtlb.interfaces;

import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

/**
 * Interface with basic functions needed by a SwingWorker to set the image and
 * progress. Needed by the filters to set the image back into the GUI
 * 
 * @author Stefan Huber
 */
public interface IFImageController {

	void setCurrentImage(MMTImage newImage);

	void setProgressBarVisible(boolean visible);

	void setProgressStatus(int progress);

	void blockController(Stoppable stoppable);

	void unblockController();

}
