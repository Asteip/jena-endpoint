package com.alma.endpoint.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.commons.io.FilenameUtils;

import com.alma.endpoint.data.RDFDataset;
import com.alma.endpoint.view.MainView;

public class UserEvent implements ActionListener {

	private RDFDataset dataset;
	private MainView view;

	private List<File> loadedFile;
	private File loadedQuery;

	public UserEvent() {
		view = new MainView();
		dataset = new RDFDataset();
		loadedFile = new ArrayList<File>();
		initListener();
	}

	private void initListener() {
		view.getLeftPan().getNewDataFileButton().addActionListener(this);
		view.getCenterPan().getQryRunButton().addActionListener(this);
		view.getCenterPan().getQrySaveButton().addActionListener(this);
		view.getCenterPan().getQryOpenButton().addActionListener(this);
		view.getCenterPan().getResultSaveButton().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object src = event.getSource();

		// New data file button

		if (src == view.getLeftPan().getNewDataFileButton()) {
			JFileChooser fc = view.getNqDataFileFc();
			int returnVal = fc.showOpenDialog(view);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fc.getSelectedFile();

				if (!loadedFile.contains(selectedFile)) {
					loadedFile.add(selectedFile);
					view.getLeftPan().addDataFile(selectedFile.getName());
				}
			}
		}

		// Run query button

		if (src == view.getCenterPan().getQryRunButton()) {
			int index = -1;
			String dataFileName = "";
			String query = "";
			String queryResult = "";

			if (!view.getLeftPan().getDataFileList().isSelectionEmpty()) {
				index = view.getLeftPan().getDataFileList().getSelectedIndex();
				dataFileName = loadedFile.get(index).getPath();
				query = view.getCenterPan().getQryTextArea().getText();

				if (!dataset.getDataFileName().equals(dataFileName)) {
					try {
						dataset.loadDataset(dataFileName);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				if (query != null && !query.trim().equals("")) {
					queryResult = dataset.selectQuery(query);
					view.getCenterPan().getResultTextArea().setText(queryResult);
				} else {
					JOptionPane.showMessageDialog(null, "No query", "Error", JOptionPane.ERROR_MESSAGE);
				}

			} else {
				JOptionPane.showMessageDialog(null, "No file selected", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}

		// Open query button

		if (src == view.getCenterPan().getQryOpenButton()) {
			JFileChooser fc = view.getSparqlQueryFc();
			int returnVal = fc.showOpenDialog(view);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				loadedQuery = fc.getSelectedFile();

				try {
					view.getCenterPan().getQryTextArea().setText(dataset.readTextFile(loadedQuery));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		// Save query button

		if (src == view.getCenterPan().getQrySaveButton()) {
			String query = view.getCenterPan().getQryTextArea().getText();

			try {
				if (loadedQuery != null) {
					dataset.writeTextFile(loadedQuery, query);
				} else {
					JFileChooser fc = view.getSparqlQueryFc();
					int returnVal = fc.showSaveDialog(view);
					
					if (returnVal == JFileChooser.APPROVE_OPTION && query != "") {
						loadedQuery = fc.getSelectedFile();

						if (FilenameUtils.getExtension(loadedQuery.getPath()).equalsIgnoreCase("sparql")) {
							dataset.writeTextFile(loadedQuery, query);
						} else {
							File tmpFile = new File(loadedQuery.getParentFile(),
									FilenameUtils.getBaseName(loadedQuery.getName()) + ".sparql");

							dataset.writeTextFile(tmpFile, query);

						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Save result button

		if (src == view.getCenterPan().getResultSaveButton()) {

		}
	}
}
