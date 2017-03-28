package com.alma.main;

import java.io.IOException;

public class Launch { 

	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		try{
			// --- CREATION OF GRAPH --- 
			
			RdfDataSet dataset = new RdfDataSet(RdfDataSet.BASIC_GRAPH);
			
			dataset.addNamedGraph("research-graph/", "research.ttl");
			dataset.setDefaultGraph("budget_RD.ttl");
			
			//dataset.createOwlDataset("budget_RD.ttl", "research.ttl");
			
			// --- EXECUTE QUERY ---
			
			dataset.selectQuery("qryTmp.sparql");
			
		}
		catch(IllegalArgumentException e){
			System.out.println(e.getStackTrace());
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
