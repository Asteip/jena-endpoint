package com.alma.main;

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

	public void fix(String inputFile) throws IOException {

		System.out.println("[INFO] Start correction...");

		// READ DATA

		List<String> lines = new ArrayList<String>();
		String line = "";
		String nextLine = "";
		String resultLine = "";
		String subLine[] = null;

		FileReader fileReader = new FileReader(inputFile);
		BufferedReader reader = new BufferedReader(fileReader);

		while (reader.ready()) {
			if ((line = reader.readLine()) != null) {
				subLine = line.split("\"");

				// if the sub line is equal to 2 then the line contains only one
				// quote, we loop until we find the next quote. We also chek if
				// the first part of the line contains < or _:
				
				if(subLine.length == 2){
					if(subLine[0].contains("<") || subLine[0].contains("_:")){
						nextLine = reader.readLine();
						
						if(nextLine != null && nextLine.contains("\"")){
							
						}
					}
				}
				

				if (subLine.length == 2){
					while ((nextLine = reader.readLine()) != null && !(nextLine).contains("\"")) {
						line += nextLine;
					}

					line += nextLine;
				}

				resultLine = clean(line);

				if (resultLine.charAt(0) != '<' && resultLine.charAt(0) != '_') {
					// resultLine = "============> \"" + resultLine;
				} else {
					lines.add(resultLine);
				}
			}
		}

		fileReader.close();

		// SAVE DATA

		FileWriter fileWritter = new FileWriter(
				"src/main/resources/data/out.nq");
		BufferedWriter writter = new BufferedWriter(fileWritter);

		for (String data : lines) {
			writter.write(data);
			writter.newLine();
		}

		writter.close();

		System.out.println("[INFO] End correction...");
	}
	
	private int nbOccurence(String str, char c){
		int ret = 0;
		
		for (int i = 0 ; i < str.length() ; ++i){
			if(str.charAt(i) == c){
				++ret;
			}
		}
		
		return ret;
	}

	private String clean(String str) {
		String ret = str;

		int indGraph = str.lastIndexOf("<");
		int indObjectBegin = str.indexOf("\"");
		int indObjectEnd = str.lastIndexOf("\"");

		if (indGraph != -1 && indObjectBegin != -1 && indObjectEnd != -1
				&& indObjectBegin < indObjectEnd) {
			String subStr[] = str.split("\"");

			String graph = str.substring(indGraph);
			String object = str.substring(indObjectBegin + 1, indObjectEnd);

			Pattern pattern = Pattern.compile(graph);
			Matcher matcher = pattern.matcher(object);

			ret = subStr[0] + "\"" + matcher.replaceAll("") + "\""
					+ subStr[subStr.length - 1];
		}

		return ret;
	}
}
