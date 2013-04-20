package at.ac.fhsalzburg.mmtlb.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import at.ac.fhsalzburg.mmtlb.applications.ContrastStretching;
import at.ac.fhsalzburg.mmtlb.applications.GammaCorrection;
import at.ac.fhsalzburg.mmtlb.applications.HistogramEqualization;
import at.ac.fhsalzburg.mmtlb.applications.filters.AveragingFilter;
import at.ac.fhsalzburg.mmtlb.applications.filters.HighboostFilter;
import at.ac.fhsalzburg.mmtlb.applications.filters.LaplacianFilter;
import at.ac.fhsalzburg.mmtlb.applications.filters.LaplacianFilterType;
import at.ac.fhsalzburg.mmtlb.applications.filters.MedianFilter;
import at.ac.fhsalzburg.mmtlb.applications.filters.SobelFilter;
import at.ac.fhsalzburg.mmtlb.applications.threshold.GlobalThresholding;
import at.ac.fhsalzburg.mmtlb.applications.threshold.IterativeGlobalThresholding;
import at.ac.fhsalzburg.mmtlb.applications.threshold.OtsuThresholding;
import at.ac.fhsalzburg.mmtlb.gui.datapanels.AdditionalComboBoxDataPanel;
import at.ac.fhsalzburg.mmtlb.gui.datapanels.AdditionalSliderDataPanel;
import at.ac.fhsalzburg.mmtlb.gui.datapanels.HighboostDataPanel;
import at.ac.fhsalzburg.mmtlb.gui.datapanels.NoAdditionalDataPanel;
import at.ac.fhsalzburg.mmtlb.gui.datapanels.RasterSizePanel;

/**
 * Sets the panels needed for the selected filter
 * 
 * @author Stefan Huber
 */
public class ImageModificationHelper {

	private final MainController controller;

	public ImageModificationHelper(MainController controller) {
		this.controller = controller;
	}

	public void applyOtsuThresholding() {
		NoAdditionalDataPanel otsuPanel = new NoAdditionalDataPanel();
		controller.getView().getApplicationsPanel().setAdditionalDataPanel(otsuPanel);

		otsuPanel.getGo().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new OtsuThresholding(controller, controller.getCurrentImage()).execute();
			}
		});

	}

	public void applyIterativeThesholdingFilter() {
		NoAdditionalDataPanel iterativePanel = new NoAdditionalDataPanel();
		controller.getView().getApplicationsPanel().setAdditionalDataPanel(iterativePanel);

		iterativePanel.getGo().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new IterativeGlobalThresholding(controller, controller.getCurrentImage()).execute();
			}
		});
	}

	public void applyGlobalThresholdFilter() {
		Integer[] values = { 15, 30, 45, 60, 75, 90, 105, 120, 135, 150, 165, 180, 195, 210, 225, 240, 255 };
		final AdditionalComboBoxDataPanel globalThresholdPanel = new AdditionalComboBoxDataPanel(values, 120);
		controller.getView().getApplicationsPanel().setAdditionalDataPanel(globalThresholdPanel);

		globalThresholdPanel.getGo().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int threshold = (Integer) globalThresholdPanel.getValue();
				new GlobalThresholding(controller, controller.getCurrentImage(), threshold).execute();
			}
		});
	}

	public void applyHighboostFilter() {
		final HighboostDataPanel highboostPanel = new HighboostDataPanel();
		controller.getView().getApplicationsPanel().setAdditionalDataPanel(highboostPanel);

		highboostPanel.getGo().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Double currentFactor = highboostPanel.getFactor();
				int rasterSize = highboostPanel.getRaster();
				HighboostFilter hbf = new HighboostFilter(controller, controller.getCurrentImage(), rasterSize, currentFactor);
				hbf.execute();
			}
		});
	}

	public void applySobelFilter() {
		NoAdditionalDataPanel sobelPanel = new NoAdditionalDataPanel();
		controller.getView().getApplicationsPanel().setAdditionalDataPanel(sobelPanel);

		sobelPanel.getGo().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SobelFilter sobel = new SobelFilter(controller, controller.getCurrentImage());
				sobel.execute();
			}
		});
	}

	public void applyLaplacianFilter() {
		final AdditionalComboBoxDataPanel comboPanel = new AdditionalComboBoxDataPanel(LaplacianFilterType.values(), LaplacianFilterType.FOUR_NEIGHBOURHOOD);
		controller.getView().getApplicationsPanel().setAdditionalDataPanel(comboPanel);

		comboPanel.getGo().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LaplacianFilter lapl = new LaplacianFilter(controller, controller.getCurrentImage(), (LaplacianFilterType) comboPanel.getValue());
				lapl.execute();
			}
		});
	}

	public void applyMedianFilter() {
		Integer[] values = { 3, 5, 7, 9, 11, 13 };
		final RasterSizePanel medianPanel = new RasterSizePanel(values, 3);
		controller.getView().getApplicationsPanel().setAdditionalDataPanel(medianPanel);

		medianPanel.getGo().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MedianFilter medianFilter = new MedianFilter(controller, controller.getCurrentImage(), medianPanel.getValue());
				medianFilter.execute();
			}
		});
	}

	public void applyAveragingFilter() {
		Integer[] items = { 3, 5, 7, 9, 11, 13, 15, 17, 19, 23, 27, 31, 33, 51 };
		final RasterSizePanel goAverage = new RasterSizePanel(items, 3);
		controller.getView().getApplicationsPanel().setAdditionalDataPanel(goAverage);

		goAverage.getGo().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AveragingFilter average = new AveragingFilter(controller, controller.getCurrentImage(), goAverage.getValue());
				average.execute();
			}
		});
	}

	public void applyHistogramEqualization() {
		NoAdditionalDataPanel go2 = new NoAdditionalDataPanel();
		controller.getView().getApplicationsPanel().setAdditionalDataPanel(go2);

		go2.getGo().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				HistogramEqualization hist = new HistogramEqualization(controller, controller.getCurrentImage());
				hist.execute();
			}
		});
	}

	public void applyGammaCorrection() {
		final AdditionalSliderDataPanel addData = new AdditionalSliderDataPanel(1, 1000, 100);
		controller.getView().getApplicationsPanel().setAdditionalDataPanel(addData);

		addData.getGo().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				double gamma = ((new Double(addData.getValue())) / 100d);
				GammaCorrection gammaCorr = new GammaCorrection(controller, controller.getCurrentImage(), gamma);
				gammaCorr.execute();
			}
		});
	}

	public void applyContrastStretching() {
		NoAdditionalDataPanel go = new NoAdditionalDataPanel();
		controller.getView().getApplicationsPanel().setAdditionalDataPanel(go);

		go.getGo().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ContrastStretching stretcher = new ContrastStretching(controller, controller.getCurrentImage());
				stretcher.execute();
			}
		});
	}
}
