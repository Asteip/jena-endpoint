package com.alma.endpoint.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MainView extends JFrame {

	private static final long serialVersionUID = 1L;
	
	// Left panel
	private JPanel leftPan;
	
	private JPanel loadDataPan;
	private JLabel loadDataLabel;
	private JButton loadDataButton;
	
	private JPanel loadedDataPan;
	private JScrollPane loadedDataScrollPan;
	private ButtonGroup loadedDataGroup;
	
	// Center panel
	private JPanel centerPan;
	
	private JPanel qryPan;
	private JPanel qryToolsPan;
	private JLabel qryLabel;
	private JButton qryRunButton;
	private JButton qrySaveButton;
	private JButton qryOpenButton;
	private JScrollPane qryTextPan;
	private JTextArea qryTextArea;
	
	private JPanel resultPan;
	private JPanel resultToolsPan;
	private JLabel resultLabel;
	private JButton resultSaveButton;
	private JScrollPane resultTextPan;
	private JTextArea resultTextArea;

	/* --- CONSTRUCTOR --- */

	public MainView() {
		super("Open data plus endpoint");
		setSize(1000, 600);
		setMinimumSize(new Dimension(500, 300));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		// Create interface
		guiInit();
		guiPlacement();
		
		// Display the frame !
		setVisible(true);
	}

	/* --- PRIVATE METHODS --- */

	private void guiInit() {
		
		// 1 - Init the left panel 
		
		leftPan = new JPanel();
		
		// panel which contains data label and the new button
		
		loadDataPan = new JPanel();
		loadDataLabel = new JLabel("Loaded data : ");
		loadDataButton = new JButton(new ImageIcon("src/main/resources/img/new_button.png"));
		loadDataButton.setBackground(getBackground());
		loadDataButton.setFocusPainted(false);
		loadDataButton.setMargin(new Insets(0,0,0,0));
		
		// panel which contains the loaded data with radio button
		
		loadedDataPan = new JPanel();
		loadedDataScrollPan = new JScrollPane(loadDataPan);
		loadedDataGroup = new ButtonGroup();
		
		// 2 - Init the center panel
		
		centerPan = new JPanel();
		
		// panel which contains the query
		
		qryPan = new JPanel();
		
		qryToolsPan = new JPanel();
		qryLabel = new JLabel("Query : ");
		qryRunButton = new JButton("Run");
		qrySaveButton = new JButton("Save");
		qryOpenButton = new JButton("Open");
		
		qryTextArea = new JTextArea();
		qryTextPan = new JScrollPane(qryTextArea);
		
		// panel which contains the results
		
		resultPan = new JPanel();
		
		resultToolsPan = new JPanel();
		resultLabel = new JLabel("Result : ");
		resultSaveButton = new JButton("Save");
		
		resultTextArea = new JTextArea();
		resultTextPan = new JScrollPane(resultTextArea);				
	}

	private void guiPlacement() {
		
		// 1 - Placement of left panel
		
		leftPan.setLayout(new BorderLayout());
		leftPan.add(loadDataPan, BorderLayout.NORTH);
		leftPan.add(loadedDataScrollPan, BorderLayout.CENTER);
		
		loadDataPan.setLayout(new BorderLayout());
		loadDataPan.add(loadDataLabel, BorderLayout.CENTER);
		loadDataPan.add(loadDataButton, BorderLayout.EAST);
		
		loadedDataPan.setLayout(new GridLayout(0, 1));
		
		// 2 - Placement of the center panel
		
		qryPan.setLayout(new BorderLayout());
		qryPan.add(qryToolsPan, BorderLayout.NORTH);
		qryPan.add(qryTextPan, BorderLayout.CENTER);
		
		qryToolsPan.setLayout(new FlowLayout());
		qryToolsPan.add(qryLabel);
		qryToolsPan.add(qryRunButton);
		qryToolsPan.add(qryOpenButton);
		qryToolsPan.add(qrySaveButton);
		
		resultPan.setLayout(new BorderLayout());
		resultPan.add(resultToolsPan, BorderLayout.NORTH);
		resultPan.add(resultTextPan, BorderLayout.CENTER);
		
		resultToolsPan.setLayout(new FlowLayout());
		resultToolsPan.add(resultLabel);
		resultToolsPan.add(resultSaveButton);
		
		centerPan.setLayout(new GridLayout(2,1));
		centerPan.add(qryPan);
		centerPan.add(resultPan);
		
		setLayout(new BorderLayout());
		add(leftPan, BorderLayout.WEST);
		add(centerPan, BorderLayout.CENTER);
	}

	/* --- PUBLIC METHODS --- */
	
	public void addFile(String fileName){
		JRadioButton newButton = new JRadioButton(fileName);
		loadedDataGroup.add(newButton);
		repaint();
		revalidate();
	}

	public JButton getLoadDataButton() {
		return loadDataButton;
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

	public JButton getResultSaveButton() {
		return resultSaveButton;
	}
	
}





