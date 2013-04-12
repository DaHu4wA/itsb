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
		controller.setProgressBarVisible(true);

	}

	protected abstract MMTImage modifyImage(MMTImage sourceImage);

	@Override
	protected MMTImage doInBackground() throws Exception {
		LOG.info("Image modification started");

		publish(0);
		return modifyImage(sourceImage); //TODO set progress??
	}

	@Override
	protected void process(List<Integer> chunks) {
		controller.setProgressStatus(chunks.get(0));
	}

	@Override
	protected void done() {
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
		controller.setProgressStatus(100);
	}
}
