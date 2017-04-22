package com.alma.endpoint.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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

	private Dataset dataset;
	private String dataFileName;

	/**
	 * Creates new instance of RDFDataset
	 */
	public RDFDataset() {
		dataFileName = "";
	}

	/**
	 * Loads a dataset from a data file.
	 * 
	 * @param fileName
	 *            The name of the file which contains data.
	 */
	public void loadDataset(String fileName) {
		dataFileName = fileName;
		dataset = RDFDataMgr.loadDataset(fileName);
	}

	/**
	 * Debug method, print all named graph with their size.
	 */
	public void displayGraph() {
		Model model = ModelFactory.createDefaultModel();
		int totalSize = 0;

		Iterator<String> it = dataset.listNames();
		while (it.hasNext()) {
			String graphName = (String) it.next();
			model = dataset.getNamedModel(graphName);
			System.out.println(graphName + " : " + model.size());
			totalSize += model.size();
		}

		System.out.println("--- Total size : " + totalSize);
	}

	/**
	 * Execute select query.
	 * 
	 * @param query
	 *            The query string.
	 * @throws IOException
	 *             if the file is invalid.
	 */
	public String selectQuery(String strQuery) {
		String strResult = "";

		Query query = QueryFactory.create(strQuery);
		QueryExecution qexec = QueryExecutionFactory.create(query, dataset);
		ResultSet qResult = qexec.execSelect();
		strResult = ResultSetFormatter.asText(qResult, query);
		qexec.close();

		return strResult;
	}

	/**
	 * Read a query from a file.
	 * 
	 * @param fileName
	 *            The file name.
	 * @return The query string
	 * @throws IOException
	 */
	public String readTextFile(File file) throws IOException {
		FileInputStream fip = new FileInputStream(file);
		BufferedReader reader = new BufferedReader(new InputStreamReader(fip));

		StringBuilder data = new StringBuilder();
		String line;

		while ((line = reader.readLine()) != null) {
			data.append(line + "\n");
		}

		fip.close();

		return data.toString();
	}

	/**
	 * Write a query result into a file.
	 * 
	 * @param fileName
	 *            The file name.
	 * @param text
	 *            The string query result.
	 * @throws IOException
	 */
	public void writeTextFile(File file, String text) throws IOException {
		FileOutputStream fop = new FileOutputStream(file);
		BufferedWriter writter = new BufferedWriter(new OutputStreamWriter(fop));

		writter.write(text);
		writter.close();

		if (fop != null)
			fop.close();
	}

	/**
	 * Returns the current loaded data file name.
	 * 
	 * @return The data file name.
	 */
	public String getDataFileName() {
		return dataFileName;
	}
}
