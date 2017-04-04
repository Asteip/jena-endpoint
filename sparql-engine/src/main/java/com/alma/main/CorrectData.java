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

		FileReader fileReader = new FileReader(inputFile);
		BufferedReader reader = new BufferedReader(fileReader);

		while (reader.ready()) {
			line = reader.readLine();

			while (buggy(line) && (nextLine = reader.readLine()) != null) {
				line += " " + nextLine;
			}

			lines.add(clean(line));
		}

		fileReader.close();
		
		for (String data : lines){
			System.out.println(data);
		}

		// SAVE DATA
		
		/*FileWriter fileWritter = new FileWriter(inputFile);
		BufferedWriter writter = new BufferedWriter(fileWritter);

		for (String data : lines) {
			writter.write(data);
			writter.newLine();
		}

		writter.close();*/
		
		System.out.println("[INFO] End correction...");
	}

	private String clean(String str) {
		String ret = str;

		if (str.contains("\"")) {
			String subStr[] = str.split("\"");

			String graph = str.substring(str.lastIndexOf("<"));
			String object = str.substring(str.indexOf("\"") + 1,
					str.lastIndexOf("\""));

			Pattern pattern = Pattern.compile(graph);
			Matcher matcher = pattern.matcher(object);

			ret = subStr[0] + "\"" + matcher.replaceAll("") + "\""
					+ subStr[subStr.length - 1];
		}

		return ret;
	}

	private boolean buggy(String str) {
		int count = 0;
		boolean ret = false;

		if (str != null || str != "") {
			Pattern pattern = Pattern.compile("\"");
			Matcher matcher = pattern.matcher(str);

			while (matcher.find())
				++count;

			if ((count % 2) != 0)
				ret = true;
		}

		return ret;
	}
}
