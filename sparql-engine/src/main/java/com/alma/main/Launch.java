package com.alma.main;

import java.io.IOException;

public class Launch { 

	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		try{
			// --- CREATION OF GRAPH --- 
			
			RdfDataSet dataset = new RdfDataSet("src/main/resources/data/test.nq");
			
			// --- EXECUTE QUERY ---
			
			dataset.displayGraph();
			//dataset.selectQuery("src/main/resources/qry/qryTest.sparql");
			
		}
		catch(IllegalArgumentException e){
			System.out.println(e.getStackTrace());
		}
	}

}
