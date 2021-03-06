package com.alma.endpoint.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class CenterPan extends JPanel {

	private static final long serialVersionUID = 1L;

	// main panels

	private JPanel qryPan;
	private JPanel resultPan;

	// components

	private JPanel qryToolsPan;
	private JLabel qryLabel;
	private JLabel dataLabel;
	private JButton qryRunButton;
	private JButton qrySaveButton;
	private JButton qryOpenButton;
	private JButton qryNewButton;
	private JButton dataCleanButton;
	private JScrollPane qryTextScrollPan;
	private JTextArea qryTextArea;

	private JPanel resultToolsPan;
	private JLabel resultLabel;
	private JButton resultExportTxtButton;
	private JButton resultExportCsvButton;
	private JScrollPane resultTextScrollPan;
	private JTextArea resultTextArea;

	public CenterPan() {
		super();
		setLayout(new GridLayout(2, 1));
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.GRAY),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		initCenterPan();
	}

	private void initCenterPan() {
		qryPan = new JPanel();
		qryPan.setLayout(new BorderLayout());
		resultPan = new JPanel();
		resultPan.setLayout(new BorderLayout());

		add(qryPan);
		add(resultPan);

		// query panel

		qryToolsPan = new JPanel();
		qryLabel = new JLabel("Query");
		qryRunButton = new JButton("Run");
		qrySaveButton = new JButton("Save");
		qryOpenButton = new JButton("Open");
		qryNewButton =  new JButton("New");
		
		dataLabel = new JLabel("Data");
		dataCleanButton = new JButton("Clean");

		qryToolsPan.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		qryToolsPan.add(qryLabel);
		qryToolsPan.add(qryRunButton);
		qryToolsPan.add(qryOpenButton);
		qryToolsPan.add(qrySaveButton);
		qryToolsPan.add(qryNewButton);
		
		qryToolsPan.add(dataLabel);
		qryToolsPan.add(dataCleanButton);

		qryTextArea = new JTextArea();
		qryTextScrollPan = new JScrollPane(qryTextArea);

		qryPan.add(qryToolsPan, BorderLayout.NORTH);
		qryPan.add(qryTextScrollPan, BorderLayout.CENTER);

		// result panel

		resultToolsPan = new JPanel();
		resultLabel = new JLabel("Result");
		resultExportTxtButton = new JButton("Export (.txt)");
		resultExportCsvButton = new JButton("Export (.csv)");
		
		resultToolsPan.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		resultToolsPan.add(resultLabel);
		resultToolsPan.add(resultExportTxtButton);
		resultToolsPan.add(resultExportCsvButton);

		resultTextArea = new JTextArea();
		resultTextArea.setEditable(false);
		resultTextScrollPan = new JScrollPane(resultTextArea);

		resultPan.add(resultToolsPan, BorderLayout.NORTH);
		resultPan.add(resultTextScrollPan, BorderLayout.CENTER);
	}

	public JButton getQryRunButton() {
		return qryRunButton;
	}

	public JButton getQrySaveButton() {
		return qrySaveButton;
	}

	public JButton getQryOpenButton() {
		return qryOpenButton;
	}

	public JButton getQryNewButton() {
		return qryNewButton;
	}

	public JButton getDataCleanButton() {
		return dataCleanButton;
	}

	public JButton getResultExportTxtButton() {
		return resultExportTxtButton;
	}

	public JButton getResultExportCsvButton() {
		return resultExportCsvButton;
	}

	public JTextArea getQryTextArea() {
		return qryTextArea;
	}

	public JTextArea getResultTextArea() {
		return resultTextArea;
	}	
}
