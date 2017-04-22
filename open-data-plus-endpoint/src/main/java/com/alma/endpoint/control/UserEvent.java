package com.alma.endpoint.control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.commons.io.FilenameUtils;

import com.alma.endpoint.data.CleanData;
import com.alma.endpoint.data.RDFDataset;
import com.alma.endpoint.view.MainView;

public class UserEvent implements ActionListener {

	private RDFDataset dataset;
	private MainView view;

	private List<File> loadedDataFile;
	private File loadedQueryFile;

	private CleanData cleanData;

	public UserEvent() {
		view = new MainView();
		dataset = new RDFDataset();
		loadedDataFile = new ArrayList<File>();
		cleanData = new CleanData();
		
		initListener();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object src = event.getSource();

		if (src == view.getLeftPan().getNewDataFileButton())
			newDataFileButtonEvent();

		if (src == view.getCenterPan().getQryRunButton())
			qryRunButtonEvent();

		if (src == view.getCenterPan().getQryOpenButton())
			qryOpenButtonEvent();

		if (src == view.getCenterPan().getQrySaveButton())
			qrySaveButtonEvent();
		
		if (src == view.getCenterPan().getQryNewButton())
			qryNewButtonEvent();

		if (src == view.getCenterPan().getDataCleanButton())
			dataCleanButtonEvent();

		if (src == view.getCenterPan().getResultExportTxtButton())
			resultExportTxtButtonEvent();

		if (src == view.getCenterPan().getResultExportCsvButton())
			resultExportCsvButtonEvent();
	}

	// PRIVATES METHODS

	private void initListener() {
		view.getLeftPan().getNewDataFileButton().addActionListener(this);
		view.getCenterPan().getQryRunButton().addActionListener(this);
		view.getCenterPan().getQryOpenButton().addActionListener(this);
		view.getCenterPan().getQrySaveButton().addActionListener(this);
		view.getCenterPan().getQryNewButton().addActionListener(this);
		view.getCenterPan().getDataCleanButton().addActionListener(this);
		view.getCenterPan().getResultExportTxtButton().addActionListener(this);
		view.getCenterPan().getResultExportCsvButton().addActionListener(this);
	}

	private void newDataFileButtonEvent() {
		JFileChooser fc = view.getNqDataFileFc();
		int returnVal = fc.showOpenDialog(view);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fc.getSelectedFile();

			if (!loadedDataFile.contains(selectedFile)) {
				loadedDataFile.add(selectedFile);
				view.getLeftPan().addDataFile(selectedFile.getName());
			}
		}
	}

	private void qryRunButtonEvent() {
		int index = -1;
		String dataFileName = "";
		String query = "";
		String result = "";

		if (!view.getLeftPan().getDataFileList().isSelectionEmpty()) {
			index = view.getLeftPan().getDataFileList().getSelectedIndex();
			dataFileName = loadedDataFile.get(index).getPath();
			query = view.getCenterPan().getQryTextArea().getText();

			if (query != null && !query.trim().equals("")) {

				if (!dataset.getDataFileName().equals(dataFileName)) {
					try {
						dataset.loadDataset(dataFileName);
					} catch (Exception e) {
						view.getCenterPan().getResultTextArea().setForeground(Color.RED);
						view.getCenterPan().getResultTextArea().setText(e.getMessage());
					}
				}

				result = dataset.selectQueryAsText(query);
				view.getCenterPan().getResultTextArea().setForeground(Color.BLACK);
				view.getCenterPan().getResultTextArea().setText(result);
			} else {
				JOptionPane.showMessageDialog(null, "No query", "Error", JOptionPane.ERROR_MESSAGE);
			}

		} else {
			JOptionPane.showMessageDialog(null, "No file selected", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void qryOpenButtonEvent() {
		JFileChooser fc = view.getSparqlQueryFc();
		int returnVal = fc.showOpenDialog(view);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			loadedQueryFile = fc.getSelectedFile();

			try {
				view.getCenterPan().getQryTextArea().setText(dataset.readTextFile(loadedQueryFile));
			} catch (IOException e) {
				view.getCenterPan().getResultTextArea().setForeground(Color.RED);
				view.getCenterPan().getResultTextArea().setText(e.getMessage());
			}
		}
	}

	private void qrySaveButtonEvent() {
		String query = view.getCenterPan().getQryTextArea().getText();

		try {
			if (loadedQueryFile != null) {
				dataset.writeTextFile(loadedQueryFile, query);
			} else {
				JFileChooser fc = view.getSparqlQueryFc();
				int returnVal = fc.showSaveDialog(view);

				if (returnVal == JFileChooser.APPROVE_OPTION && query != "") {
					loadedQueryFile = fc.getSelectedFile();

					if (FilenameUtils.getExtension(loadedQueryFile.getPath()).equalsIgnoreCase("sparql")) {
						dataset.writeTextFile(loadedQueryFile, query);
					} else {
						File tmpFile = new File(loadedQueryFile.getParentFile(),
								FilenameUtils.getBaseName(loadedQueryFile.getName()) + ".sparql");

						dataset.writeTextFile(tmpFile, query);

					}
				}
			}
		} catch (IOException e) {
			view.getCenterPan().getResultTextArea().setForeground(Color.RED);
			view.getCenterPan().getResultTextArea().setText(e.getMessage());
		}
	}
	
	private void qryNewButtonEvent(){
		loadedQueryFile = null;
		view.getCenterPan().getQryTextArea().setText("");
	}

	private void dataCleanButtonEvent() {
		int index = -1;
		String dataFileName = "";
		String dataFileNameRes = "";

		if (!view.getLeftPan().getDataFileList().isSelectionEmpty()) {
			index = view.getLeftPan().getDataFileList().getSelectedIndex();
			dataFileName = loadedDataFile.get(index).getPath();
			dataFileNameRes = dataFileName.replaceAll(".nq", "-fixed.nq");

			try {
				view.getCenterPan().getResultTextArea().setForeground(Color.BLACK);
				view.getCenterPan().getResultTextArea().setText("[INFO] Start correction...\n");
				cleanData.run(dataFileName, dataFileNameRes);
				view.getCenterPan().getResultTextArea().append("[INFO] End correction.");
			} catch (IOException e) {
				view.getCenterPan().getResultTextArea().setForeground(Color.RED);
				view.getCenterPan().getResultTextArea().setText(e.getMessage());
			}

		} else {
			JOptionPane.showMessageDialog(null, "No file selected", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void resultExportTxtButtonEvent() {
		if (dataset.isLoaded()) {
			String result = view.getCenterPan().getResultTextArea().getText();

			try {
				JFileChooser fc = view.getResultFc();
				int returnVal = fc.showSaveDialog(view);

				if (returnVal == JFileChooser.APPROVE_OPTION && result != "") {
					File file = fc.getSelectedFile();

					if (FilenameUtils.getExtension(file.getPath()).equalsIgnoreCase("txt")) {
						dataset.writeTextFile(file, result);
					} else {
						File tmpFile = new File(file.getParentFile(),
								FilenameUtils.getBaseName(file.getName()) + ".txt");

						dataset.writeTextFile(tmpFile, result);
					}
				}
			} catch (IOException e) {
				view.getCenterPan().getResultTextArea().setForeground(Color.RED);
				view.getCenterPan().getResultTextArea().setText(e.getMessage());
			}
		}
	}

	private void resultExportCsvButtonEvent() {
		if (dataset.isLoaded()) {
			String query = view.getCenterPan().getQryTextArea().getText();

			try {
				JFileChooser fc = view.getResultFc();
				int returnVal = fc.showSaveDialog(view);

				// Check if the query was run
				if (returnVal == JFileChooser.APPROVE_OPTION && !query.trim().equals("")) {
					File file = fc.getSelectedFile();

					if (FilenameUtils.getExtension(file.getPath()).equalsIgnoreCase("csv")) {
						dataset.selectQueryAsCsv(query, file);
					} else {
						File tmpFile = new File(file.getParentFile(),
								FilenameUtils.getBaseName(file.getName()) + ".csv");
						dataset.selectQueryAsCsv(query, tmpFile);
					}
				}
			} catch (IOException e) {
				view.getCenterPan().getResultTextArea().setForeground(Color.RED);
				view.getCenterPan().getResultTextArea().setText(e.getMessage());
			}
		}
	}
}
