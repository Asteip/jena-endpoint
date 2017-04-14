package com.alma.endpoint.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CorrectData {

	public void run(String inputFile, String outputFile) throws IOException {
		FileReader fileReader = new FileReader(inputFile);
		BufferedReader reader = new BufferedReader(fileReader);

		List<String> data = new ArrayList<String>();
		String rline = "";

		System.out.println("[INFO] Start correction...");

		// READ DATA

		while (reader.ready()) {
			if ((rline = reader.readLine()) != null)
				data.add(rline);
		}

		fileReader.close();

		// SAVE DATA
		
		FileWriter fileWritter = new FileWriter(outputFile);
		BufferedWriter writter = new BufferedWriter(fileWritter);
		
		List<String> fixedData = fix(data);

		for (String wline : fixedData) {			
			writter.write(wline);
			writter.newLine();
		}

		writter.close();

		System.out.println("[INFO] End correction...");
	}

	private List<String> fix(List<String> data) {
		List<String> result = new ArrayList<String>();
		List<String> nextLines = new ArrayList<String>();
		int index = 0;
		boolean found = false;

		while (index < data.size()) {			
			String line = data.get(index);
			String resultLine = "";
			
			// If the line contains only one double quote, we look if there
			// is the end of the object in the next lines. Otherwise, we add
			// a double quote.

			if (nbDoubleQuote(line) == 1 && (line.charAt(0) == '<' || line.charAt(0) == '_')){
				// Adding the current line
				nextLines.add(line);
				
				int i = index + 1;
				String nextLine = "";
				
				while (!found && i < data.size()) {
					nextLine = data.get(i);
					nextLines.add(nextLine);
					
					// We stop if the last line added contains a double quote
					if(nbDoubleQuote(nextLine) > 0){
						found = true;
					}
					
					++i;
				}

				if (nbDoubleQuote(nextLine) == 1){
						
					// Concat each next line with the current line.
					for (String str : nextLines){
						resultLine += str;
					}
					
					// We go to the next line which contains the single double quote
					index += nextLines.size() - 1;

				} else {
					
					// Adding a double quote at the end of the object
					String firstPart = line.substring(0,line.lastIndexOf("<") - 1);
					String secondPart = line.substring(line.lastIndexOf("<"));
					resultLine = firstPart + "\" " + secondPart;
				}
			} else if(line.charAt(0) != '<' && line.charAt(0) != '_'){	
				nextLines.add(line.substring(0,line.lastIndexOf("<")));
				
				int i = index + 1;
				String nextLine = "";
				
				while(!found && i < data.size()){
					nextLine = data.get(i);
					
					// In this case we don't add the last line because it contains a 
					// correct line.
					if(nextLine.charAt(0) == '<' || nextLine.charAt(0) == '_'){
						found = true;
					}
					else{
						nextLines.add(nextLine.substring(0, nextLine.lastIndexOf("<")));
					}
						
					++i;
				}
				
				// We take the last line we added in the result list 
				String prevLine = result.get(result.size() - 1);
				String firstPart = prevLine.substring(0,prevLine.lastIndexOf("<") - 1);
				String secondPart = prevLine.substring(prevLine.lastIndexOf("<"));
				
				String object = "";
				
				// Concat the object
				for (String str : nextLines){
					object += str;
				}
				
				result.set(result.size() - 1, clean(firstPart + " \"" + object  + secondPart));
				
				// We don't add this line because it's buggy
				resultLine = "";
				
				// We go to the next line
				index += nextLines.size() - 1;
			
			} else {
				resultLine = line;
			}
			
			if(resultLine != "")
				result.add(clean(resultLine));
			
			nextLines.clear();
			found = false;
			++index;
		}

		return result;
	}

	/*
	 * Returns the number of occurences of the character "
	 */
	private int nbDoubleQuote(String str) {
		int ret = 0;

		for (int i = 0; i < str.length(); ++i) {			
			if (str.charAt(i) == '"'){
				if(i > 0  && str.charAt(i-1) != '\\'){
					++ret;
				}
				else if(i == 0){
					++ret;
				}
			}
		}

		return ret;
	}

	/*
	 * Remove all occurences of the graph string in the line.
	 */
	private String clean(String str) {
		String ret = str;

		int indGraph = str.lastIndexOf("<");

		if (indGraph != -1) {

			String graph = str.substring(indGraph);
			String toClean = str.substring(0, indGraph);

			Pattern pattern = Pattern.compile(graph);
			Matcher matcher = pattern.matcher(toClean);
			
			if(nbDoubleQuote(toClean) == 1)
				ret = matcher.replaceAll("") + "\""	+ graph;
			else
				ret = matcher.replaceAll("") + graph;
		}

		return ret;
	}
}
