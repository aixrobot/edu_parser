package edu_parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class DicReader {

	static String filePath = "D://edua_data/engdic/a.dic";
			
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*
		 * startwith('a')
		 * regex
		 * token
		 * contains
		 * 
		 * compare
		 * equals
		 * similar
		 * 
		 */
		
		File file = new File(filePath);
		try {
			ReadFileByLine(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	private static void ReadFileByLine(File fin) throws IOException {
		FileInputStream fis = new FileInputStream(fin);
	 
		//Construct BufferedReader from InputStreamReader
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
	 
		String line = null;
		while ((line = br.readLine()) != null) {
			line = line.replace(":", ",");
			System.out.println(line);
		}
	 
		br.close();
	}
}
