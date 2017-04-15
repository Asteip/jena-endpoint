package com.alma.endpoint.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

public class LeftPan extends JPanel {

	private static final long serialVersionUID = 1L;

	// main panels

	private JPanel north;
	private JScrollPane center;
	private JPanel south;

	// components

	private JLabel dataLabel;
	private JList<String> dataFileList;
	private DefaultListModel<String> listModel;
	private JButton newDataFileButton;

	public LeftPan() {
		super();
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(200, 0));
		setMinimumSize(getPreferredSize());
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		initLeftPan();
	}

	private void initLeftPan() {

		// north pan

		dataLabel = new JLabel("Loaded data", SwingConstants.CENTER);
		dataLabel.setBorder(BorderFactory.createEmptyBorder(0, 50, 10, 50));
		dataLabel.setFont(new Font("Arial", Font.BOLD, 15));

		// center pan

		listModel = new DefaultListModel<String>();
		dataFileList = new JList<String>(listModel);
		dataFileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dataFileList.setLayoutOrientation(JList.VERTICAL);

		// south pan

		newDataFileButton = new JButton("New file", new ImageIcon("src/main/resources/img/new_button.png"));

		// main panel

		north = new JPanel();
		center = new JScrollPane(dataFileList);
		south = new JPanel();
		
		north.add(dataLabel);
		south.add(newDataFileButton);
		
		add(north, BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		add(south, BorderLayout.SOUTH);
	}

	public void addDataFile(String name) {
		listModel.addElement(name);
		repaint();
		revalidate();
	}

	public JButton getNewDataFileButton() {
		return newDataFileButton;
	}

	public JList<String> getDataFileList() {
		return dataFileList;
	}
}
