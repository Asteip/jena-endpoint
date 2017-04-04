package com.alma.main;

import java.io.IOException;
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
			
			RdfDataSet dataset = new RdfDataSet("src/main/resources/data/test.nq");

			// --- EXECUTE QUERY ---

			// dataset.displayGraph();
			// dataset.selectQuery("src/main/resources/qry/qryTest.sparql");


		} catch (Exception e) {
			e.getStackTrace();
		}
	}
}
