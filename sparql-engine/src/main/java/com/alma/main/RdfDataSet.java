package com.alma.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.tdb.TDBFactory;

public class RdfDataSet {

	private Dataset _dataset;

	// CONSTRUCTOR

	/**
	 * Create a dataset with default empty graph.
	 * 
	 * @param graphType
	 *            the type of graph stored in the dataset (Basic or inferred).
	 * @exception if
	 *                the graph type is invalid
	 */
	public RdfDataSet(String file) {
		_dataset = TDBFactory.createDataset();

		Model model = ModelFactory.createDefaultModel();

		try {
			RDFDataMgr.read(_dataset.getDefaultModel(), file);
			_dataset.commit();
			_dataset.end();
		} catch (org.apache.jena.riot.RiotException e) {
			System.out.println("LINE IGNORE");
		}
	}

	// PRIVATES METHODS

	/*
	 * Return the contents of a file in a string for a query.
	 */
	private String readQryFile(String filename) throws IOException {
		File file = new File(filename);
		FileInputStream inputStream = new FileInputStream(file);
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				inputStream));
		StringBuilder data = new StringBuilder();
		String line;

		while ((line = reader.readLine()) != null) {
			data.append(line + "\n");
		}

		inputStream.close();
		return data.toString();
	}

	// PUBLICS METHODS

	/**
	 * Print the graph specify by the uri in Turtle format to the standard
	 * output.
	 * 
	 * @param uri
	 *            the uri of the graph. If the uri does not exist or is null,
	 *            the default graph is display
	 */
	public void displayGraph() {
		_dataset.getDefaultModel().write(System.out);
	}

	/**
	 * Execute select query.
	 * 
	 * @param filename
	 *            the file which contains the query
	 * @throws IOException
	 *             if the file is invalid
	 */
	public void selectQuery(String filename) throws IOException {
		String request = readQryFile(filename);

		System.out.println("\n-> Request on file: \"" + filename + "\"");
		System.out.println(request);
		System.out.println("-> Perform request...\n");

		Query query = QueryFactory.create(request);
		QueryExecution qexec = QueryExecutionFactory.create(query, _dataset);
		ResultSet results = qexec.execSelect();

		// Output query results
		FileOutputStream fop = null;
		File file = null;

		try {
			file = new File("out");
			fop = new FileOutputStream(file);
			if (!file.exists())
				file.createNewFile();
			ResultSetFormatter.out(fop, results, query);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fop != null)
				fop.close();
		}

		// Important - free up resources used running the query
		qexec.close();

		System.out.println("-> Done.\n");
	}
}
