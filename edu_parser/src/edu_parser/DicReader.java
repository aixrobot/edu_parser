package edu_parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class DicReader {

	static String filePath = "D://edua_data/engdic/a.dic";
			
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// git version

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
		int i = 0;
		boolean chk = false;
		while ((line = br.readLine()) != null) {
			//line = line.replace(":", ",");
			//System.out.println(line);
			StringTokenizer st1 = new StringTokenizer(line, ":");
			i=0;
			chk = false;
			String bd = "";
			while (st1.hasMoreTokens()) {
				String tok = st1.nextToken();
				if(i==0){
					tok = tok.trim();
					System.out.print("INSERT INTO (word, definition) VALUES('"+tok+"',");
				}else if(i==2){
					bd+=" : "+tok;
				}else{
					tok = tok.trim();
					bd+=tok;
				}
				i++;
			}
			System.out.print("'" + bd +"');");
			System.out.println("");
		}
	 
		br.close();
	}
}
