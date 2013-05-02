package at.ac.fhsalzburg.mmtlb.applications;

import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import org.apache.log4j.Logger;

import at.ac.fhsalzburg.mmtlb.Stoppable;
import at.ac.fhsalzburg.mmtlb.gui.IFImageController;
import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

/**
 * Worker for all image conversion tools. This is a classic swingWoker, where the execute() method does the image
 * modification. The publish method is used to publish the progress into the progess bar . When finished, the result
 * is published into the GUI.
 * 
 * @author Stefan Huber
 */
public abstract class AbstractImageModificationWorker extends SwingWorker<MMTImage, Integer> implements Stoppable {
    private static final Logger LOG = Logger.getLogger(AbstractImageModificationWorker.class.getSimpleName());

    private final IFImageController controller;
    private final MMTImage sourceImage;

    public AbstractImageModificationWorker(IFImageController controller, MMTImage sourceImage) {
        this.controller = controller;
        this.sourceImage = sourceImage;
        if (controller != null) {
            controller.setProgressBarVisible(true);
            controller.blockController(this);
        }
    }

    public void stopTask() {
        cancel(true);
        controller.unblockController();
    }

    /**
     * This method has to be implemented by the filter
     * 
     * @param sourceImage the image that should be processed
     * @return a new filtered image
     */
    protected abstract MMTImage modifyImage(MMTImage sourceImage);

    /**
     * Use this when publish should be processed while iterating over every pixel
     * 
     * @param originalImage the image that is being filtered
     * @param currentPosition the current iteration position
     */
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
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        catch (CancellationException e) {
            if (controller != null) {
                controller.setProgressStatus(-1);
            }
        }
        finally {
            finalizeProgressBar();
            controller.unblockController();
        }
    }

    private void finalizeProgressBar() {
        if (controller != null) {
            controller.setProgressStatus(100);
        }
    }
}
