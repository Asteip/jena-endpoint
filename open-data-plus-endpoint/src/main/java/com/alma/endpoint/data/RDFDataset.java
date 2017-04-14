package com.alma.endpoint.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

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

public class RDFDataset {

	private Dataset _dataset;

	// CONSTRUCTOR

	/**
	 * Creates a dataset from a data file.
	 * 
	 * @param fileName
	 *            The name of the file which contains data
	 */
	public RDFDataset(String fileName) {
		_dataset = RDFDataMgr.loadDataset(fileName);
	}

	// PRIVATES METHODS

	/*
	 * Return the contents of a file in a string for a query.
	 */
	private String readQryFile(String fileName) throws IOException {
		File file = new File(fileName);
		FileInputStream inputStream = new FileInputStream(file);
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
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
	 * Debug method, print all named graph with their size.
	 */
	public void displayGraph() {
		Model model = ModelFactory.createDefaultModel();
		int totalSize = 0;

		Iterator<String> it = _dataset.listNames();
		while (it.hasNext()) {
			String graphName = (String) it.next();
			model = _dataset.getNamedModel(graphName);
			System.out.println(graphName + " : " + model.size());
			totalSize += model.size();
		}

		System.out.println("--- Total size : " + totalSize);
	}

	/**
	 * Execute select query.
	 * 
	 * @param filename
	 *            the file which contains the query
	 * @throws IOException
	 *             if the file is invalid
	 */
	public void selectQuery(String input, String output) throws IOException {
		String request = readQryFile(input);

		System.out.println("\n[INFO] Request on file: \"" + input + "\"");
		System.out.println(request);
		System.out.println("[INFO] Perform request...\n");

		Query query = QueryFactory.create(request);
		QueryExecution qexec = QueryExecutionFactory.create(query, _dataset);
		ResultSet results = qexec.execSelect();

		// Output query results
		FileOutputStream fop = null;
		File file = null;

		try {
			file = new File(output);
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

		System.out.println("[INFO] Done.\n");
	}
}
