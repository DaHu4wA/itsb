package at.ac.fhsalzburg.mmtlb.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import at.ac.fhsalzburg.mmtlb.applications.FileImageConverter;
import at.ac.fhsalzburg.mmtlb.mmtimage.FileImageReader;
import at.ac.fhsalzburg.mmtlb.mmtimage.FileImageWriter;
import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

/**
 * Main window for the application with buttons and a image view panel
 * 
 * @author Stefan Huber
 */
public class MainWindow_Backup extends JFrame {
	private static final long serialVersionUID = -958626226425855658L;
	private static final Logger LOG = Logger.getLogger(MainWindow_Backup.class.getSimpleName());

	public static String TITLE_TEXT = "Assignment 1 - (C) 2013 Stefan Huber";
	private static String OPEN_IMAGE_TEXT = "Open image to convert";
	private static String CONVERT_FOLDER_TEXT = "Convert folder from jpg to png";
	private static String SAVE_FILE_TEXT = "Save MMT-Image as jpg";

	MMTImage mmtImage = null;

	private JButton openFileButton;
	private JButton convertWholeFolderToPNG;
	private JButton convertFileButton;

	public MainWindow_Backup() {
		setSize(650, 650);
		setTitle(TITLE_TEXT);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		addButtonsToGui();

		setVisible(true);
	}

	/**
	 * Adds the buttons to the gui and sets them enabled (or not)
	 */
	private void addButtonsToGui() {
		openFileButton = new JButton(OPEN_IMAGE_TEXT);
		convertWholeFolderToPNG = new JButton(CONVERT_FOLDER_TEXT);
		convertFileButton = new JButton(SAVE_FILE_TEXT);

		openFileButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Thread t = new Thread(new Runnable() {
					// new thread to prevent file access from gui-thread
					// (EDT-Thread)
					@Override
					public void run() {
						openFileChooserDialog();
					}
				});
				t.start();
				openFileButton.setEnabled(false);
				openFileButton.setText("Opening file selector...");
			}
		});

		convertFileButton.setEnabled(false);
		convertFileButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						saveFile();
					}
				});
				t.start();

			}
		});

		convertWholeFolderToPNG.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						convertFromJpgToPng();
					}
				});
				t.start();
				convertWholeFolderToPNG.setEnabled(false);
			}
		});

		// Panel to add the buttons
		JPanel buttonPanel = new JPanel(new FlowLayout(15));
		buttonPanel.add(convertWholeFolderToPNG);
		buttonPanel.add(openFileButton);
		buttonPanel.add(convertFileButton);

		// add button panel to bottom of main window
		add(buttonPanel, BorderLayout.SOUTH);
	}

	/**
	 * File chooser for opening an image
	 */
	private void openFileChooserDialog() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Open an IMAGE");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int returnVal = fileChooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			LOG.debug(String.format("File selected: %s", file.getName()));
			openImageFile(file);
			// TODO maybe enhance by filtering supported file types

		} else {
			LOG.info("No file selected");
			openFileButton.setEnabled(true);
			openFileButton.setText(OPEN_IMAGE_TEXT);
		}

	}

	private void convertFromJpgToPng() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Select folder to convert");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fileChooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File directory = fileChooser.getSelectedFile();

			// do the real conversion
			FileImageConverter.convertFolderFromJpgToPng(directory);

			postFileConvertSuccessMessage();
		} else {
			LOG.info("No folder selected");
		}
		convertWholeFolderToPNG.setEnabled(true);
	}

	private void postFileConvertSuccessMessage() {
		JOptionPane.showMessageDialog(this, "Conversion was successful!\n\nSee result in subfolder \"" + FileImageConverter.SUBFOLDER_NAME + "\"");
	}

	private void saveFile() {

		if (mmtImage == null) {
			LOG.error("Aborting - MMTImage not exists!!");
			return;
		}

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Save converted image");
		fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
		fileChooser.setSelectedFile(new File("result-assignm1-" + System.currentTimeMillis() + ".jpg"));

		int returnVal = fileChooser.showSaveDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			// write file if path was selected
			File file = fileChooser.getSelectedFile();
			FileImageWriter.write(mmtImage, file);

		} else {
			LOG.info("No file selected");
		}
	}

	private void openImageFile(File file) {
		mmtImage = FileImageReader.read(file);

		MMTImagePanel p = new MMTImagePanel(mmtImage);
		
		add(p, BorderLayout.CENTER);
		openFileButton.setEnabled(false);
		openFileButton.setText("Image opened.");
		convertFileButton.setEnabled(true);
		repaint();

	}

}
