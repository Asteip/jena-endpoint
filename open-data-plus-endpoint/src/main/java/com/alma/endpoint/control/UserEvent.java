package com.alma.endpoint.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.alma.endpoint.data.RDFDataset;
import com.alma.endpoint.view.MainView;

public class UserEvent implements ActionListener {

	private RDFDataset dataset;
	private MainView view;

	public UserEvent() {
		//view = new MainView();

		try {
			dataset = new RDFDataset("src/main/resources/data/data_2015-fixed.nq");
			//dataset.displayGraph();
			dataset.selectQuery("src/main/resources/query/qryTest.sparql","src/main/resources/result/resultTest.txt");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initListener() {

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}
