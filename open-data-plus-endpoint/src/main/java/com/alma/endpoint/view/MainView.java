package com.alma.endpoint.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainView extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private LeftPan leftPan;
	private CenterPan centerPan;
	
	private JFileChooser nqDataFileFc;
	private JFileChooser sparqlQueryFc;
	private JFileChooser resultFc;

	public MainView() {
		super("Open data plus endpoint");
		setSize(1000, 600);
		setMinimumSize(new Dimension(500, 300));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		// Create interface
		leftPan = new LeftPan();
		centerPan = new CenterPan();
		
		setLayout(new BorderLayout());
		add(leftPan, BorderLayout.WEST);
		add(centerPan, BorderLayout.CENTER);
		
		// Create File chooser
		
		nqDataFileFc = new JFileChooser("..");
		nqDataFileFc.setFileFilter(new FileNameExtensionFilter("N-quad file", "nq"));	
		
		sparqlQueryFc = new JFileChooser("..");
		sparqlQueryFc.setFileFilter(new FileNameExtensionFilter("Sparql file", "sparql"));	
		
		resultFc = new JFileChooser("..");
		
		// Display the frame !
		setVisible(true);
	}

	public LeftPan getLeftPan() {
		return leftPan;
	}

	public CenterPan getCenterPan() {
		return centerPan;
	}

	public JFileChooser getNqDataFileFc() {
		return nqDataFileFc;
	}

	public JFileChooser getSparqlQueryFc() {
		return sparqlQueryFc;
	}

	public JFileChooser getResultFc() {
		return resultFc;
	}
}