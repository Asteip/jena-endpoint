package com.alma.main;

import java.util.Iterator;

import org.apache.jena.query.Dataset;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.RDFDataMgr;

public class Launch {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// --- CREATION OF GRAPH ---

			// RdfDataSet dataset = new
			// RdfDataSet("src/main/resources/data/out.nq");

			// --- EXECUTE QUERY ---

			// dataset.displayGraph();
			// dataset.selectQuery("src/main/resources/qry/qryTest.sparql");

			Model model = ModelFactory.createDefaultModel();
			Dataset dataset = RDFDataMgr.loadDataset("src/main/resources/data/out_2.nq");

			Iterator it = dataset.listNames();
			while (it.hasNext()) {
				String graphName = (String) it.next();
				model = dataset.getNamedModel(graphName);
				System.out.println(graphName + ":" + model.size());
			}

			/*CorrectData cd = new CorrectData();
			cd.run("src/main/resources/data/data.nq", "src/main/resources/data/out_2.nq");*/

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
