package com.alma.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.util.FileManager;

public class RdfDataSet {
	
	/**
	 * Specify that fresh Model with the default specification are stored in the dataset.
	 */
	public static final int BASIC_GRAPH = 0;
	
	/**
	 * Specify that graph stored in the dataset are Model through which all the RDFS entailments derivable from 
	 * the given data and schema models are accessible.
	 */
	public static final int INF_GRAPH = 1;
	
	private Dataset _dataset;
	private InfModel _owlInfModel;
	private int _graphType;
	
	// CONSTRUCTOR
	
	/**
	 * Create a dataset with default empty graph.
	 * 
	 * @param graphType the type of graph stored in the dataset (Basic or inferred).
	 * @exception if the graph type is invalid
	 */
	public RdfDataSet(int graphType) throws IllegalArgumentException{
		if(graphType != RdfDataSet.BASIC_GRAPH && graphType != RdfDataSet.INF_GRAPH) throw new IllegalArgumentException("The graph type is invalid");
		
		_dataset = TDBFactory.createDataset();
		_graphType = graphType;
	}
	
	// PRIVATES METHODS
	
	/*
	 * Return the contents of a file in a string.
	 */
	private String readFile(String filename) throws IOException{
        File file = new File(filename);
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
	
	/*
	 * Create a basic model from a turtle file
	 */
	private Model createBasicGraph(String inputFileName) throws IllegalArgumentException{
		Model model = ModelFactory.createDefaultModel();
		
		InputStream in = FileManager.get().open(inputFileName);
		if(in == null) throw new IllegalArgumentException("File : " + inputFileName + " not found.");
		model.read(in, null, "TURTLE");
		
		return model;
	}
	
	/*
	 * Create an inferred model from a turtle file
	 */
	private InfModel createInfGraph(String inputFileName) throws IllegalArgumentException{
		return ModelFactory.createRDFSModel(createBasicGraph(inputFileName));
	}
	
	// PUBLICS METHODS
	
	/**
	 * Modify the default graph in the dataset.
	 * 
	 * @param file the file that contains the new default graph.
	 */
	public void setDefaultGraph(String file) throws IllegalArgumentException{
		_dataset.getDefaultModel().removeAll();
		
		if(_graphType == RdfDataSet.BASIC_GRAPH){
			_dataset.getDefaultModel().add(createBasicGraph(file));
		}			
		else if(_graphType == RdfDataSet.INF_GRAPH){
			_dataset.getDefaultModel().add(createInfGraph(file));
		}
	}
	
	/**
	 * Add a new named graph in the dataset.
	 * 
	 * @param uri the uri of the new named graph.
	 * @param file the file that contains the graph added in the named graph.
	 */
	public void addNamedGraph(String uri, String file) throws IllegalArgumentException{		
		if(_graphType == RdfDataSet.BASIC_GRAPH){
			_dataset.addNamedModel(uri, createBasicGraph(file));
		}			
		else if(_graphType == RdfDataSet.INF_GRAPH){
			_dataset.addNamedModel(uri, createInfGraph(file));
		}
	}
	
	public void createOwlDataset(String inputFile1, String inputFile2){
		Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
		
		Model defaultGraph = FileManager.get().loadModel(inputFile1);
		Model namedGraph = FileManager.get().loadModel(inputFile2);
		
		Model union = ModelFactory.createUnion(defaultGraph, namedGraph);
		
		_owlInfModel = ModelFactory.createInfModel(reasoner, union);
	}
	
	/**
	 * Print the graph specify by the uri in Turtle format to the standard output.
	 * 
	 * @param uri the uri of the graph. If the uri does not exist or is null, the default graph is display
	 */
	public void displayGraph(String uri){
		if(uri != null && _dataset.containsNamedModel(uri))
			_dataset.getNamedModel(uri).write(System.out,"Turtle");
		else
			_dataset.getDefaultModel().write(System.out,"Turtle");
	}
	
	/**
	 * Execute select query
	 * 
	 * @param filename the file which contains the query
	 * @throws IOException if the file is invalid
	 */
	public void selectQuery(String filename) throws IOException{
		String request = readFile(filename);

		System.out.println("\n-> Request on file: \"" + filename + "\"");
		System.out.println(request);
		System.out.println("-> Perform request...\n");

		Query query = QueryFactory.create(request);
		QueryExecution qexec = QueryExecutionFactory.create(query, _dataset);
		ResultSet results =  qexec.execSelect();

		// Output query results		
		FileOutputStream fop = null;
		File file = null;
		
		try{
			file = new File("out");
			fop = new FileOutputStream(file);
			if(!file.exists()) file.createNewFile();
			ResultSetFormatter.out(fop, results, query);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		finally{
			if (fop != null) fop.close();
		}

		// Important - free up resources used running the query
		qexec.close();
		
		System.out.println("-> Done.\n");
	}
}
