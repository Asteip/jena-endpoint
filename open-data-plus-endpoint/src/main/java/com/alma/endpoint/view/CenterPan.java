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
	private JButton qryRunButton;
	private JButton qrySaveButton;
	private JButton qryOpenButton;
	private JScrollPane qryTextScrollPan;
	private JTextArea qryTextArea;

	private JPanel resultToolsPan;
	private JLabel resultLabel;
	private JButton resultExportTxtButton;
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

		qryToolsPan.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		qryToolsPan.add(qryLabel);
		qryToolsPan.add(qryRunButton);
		qryToolsPan.add(qryOpenButton);
		qryToolsPan.add(qrySaveButton);

		qryTextArea = new JTextArea();
		qryTextScrollPan = new JScrollPane(qryTextArea);

		qryPan.add(qryToolsPan, BorderLayout.NORTH);
		qryPan.add(qryTextScrollPan, BorderLayout.CENTER);

		// result panel

		resultToolsPan = new JPanel();
		resultLabel = new JLabel("Result");
		resultExportTxtButton = new JButton("Export (.txt)");
		
		resultToolsPan.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		resultToolsPan.add(resultLabel);
		resultToolsPan.add(resultExportTxtButton);

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

	public JButton getResultExportTxtButton() {
		return resultExportTxtButton;
	}

	public JTextArea getQryTextArea() {
		return qryTextArea;
	}

	public JTextArea getResultTextArea() {
		return resultTextArea;
	}
	
}
