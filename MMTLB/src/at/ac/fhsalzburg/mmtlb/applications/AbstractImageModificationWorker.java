package at.ac.fhsalzburg.mmtlb.applications;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;
import javax.swing.Timer;

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

	Timer resetProgressTimer = new Timer(2000, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			LOG.info("progress reset");
			controller.setProgressBarVisible(false);
		}
	});

	private final IFImageController controller;
	private final MMTImage sourceImage;

	public AbstractImageModificationWorker(IFImageController controller, MMTImage sourceImage) {
		this.controller = controller;
		this.sourceImage = sourceImage;
		controller.setProgressBarVisible(true);

		startWorking();
	}

	/**
	 * Has to be application modal! TODO rethink this
	 */
	protected abstract void showDialogForData();

	protected abstract MMTImage modifyImage(MMTImage sourceImage);

	private void startWorking() {
		showDialogForData();

		execute();
	}

	@Override
	protected MMTImage doInBackground() throws Exception {
		LOG.info("Image modification started");

		publish(0);
		return modifyImage(sourceImage);
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
		if (!resetProgressTimer.isRunning()) {
			resetProgressTimer.setRepeats(false);
			resetProgressTimer.start();
		}
	}
}
