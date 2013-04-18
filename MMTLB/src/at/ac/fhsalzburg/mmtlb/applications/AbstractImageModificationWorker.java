package at.ac.fhsalzburg.mmtlb.applications;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import org.apache.log4j.Logger;

import at.ac.fhsalzburg.mmtlb.gui.IFImageController;
import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

/**
 * Worker for all image conversion tools
 * 
 * @author Stefan Huber
 */
public abstract class AbstractImageModificationWorker extends SwingWorker<MMTImage, Integer> {
	private static final Logger LOG = Logger.getLogger(AbstractImageModificationWorker.class.getSimpleName());

	private final IFImageController controller;
	private final MMTImage sourceImage;

	public AbstractImageModificationWorker(IFImageController controller, MMTImage sourceImage) {
		this.controller = controller;
		this.sourceImage = sourceImage;
		if (controller != null) {
			controller.setProgressBarVisible(true);
		}
	}

	protected abstract MMTImage modifyImage(MMTImage sourceImage);

	protected void publishProgress(MMTImage originalImage, int currentPosition) {
		int progress = (int) (((double) currentPosition) / ((double) originalImage.getImageData().length) * 100);
		if (progress % 5 == 0) {
			publish(progress);
		}
	}

	@Override
	protected MMTImage doInBackground() throws Exception {
		LOG.info("Image modification started");
		return modifyImage(sourceImage);
	}

	@Override
	protected void process(List<Integer> chunks) {
		if (controller != null) {
			controller.setProgressStatus(chunks.get(0));
		}
	}

	@Override
	protected void done() {
		if (controller == null) {
			return;
		}
		try {
			LOG.info("Image modification finished");
			MMTImage result = get();
			controller.setCurrentImage(result);
			finalizeProgressBar();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	private void finalizeProgressBar() {
		if (controller != null) {
			controller.setProgressStatus(100);
		}
	}
}
