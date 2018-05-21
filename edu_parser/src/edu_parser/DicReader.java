package edu_parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class DicReader {

	static String filePath = "/Users/juseong-yun/IdeaProjects/edu_parser/edu_parser/a.dic";

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
				ReadFileByLineSP(file);
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

	private static void ReadFileByLineSP(File fin) throws IOException {
		FileInputStream fis = new FileInputStream(fin);

		//Construct BufferedReader from InputStreamReader
		BufferedReader br = new BufferedReader(new InputStreamReader(fis, "EUC-KR"));

		String line = null;

		String word = "";
		String wordType = "";
		String definition = "";

		String [] wordTypeList = {"n,", "a,", "vt,", "vi", "x,"};
		String [] wordTypeListKor = {"[N]", "[A]", "[V]", "[V]", "[P]"};

		boolean chk = false;
		while ((line = br.readLine()) != null) {
			word = "";
			wordType = "";
			definition = "";

			String[] splitedAry = line.split("\\:");
			System.out.println(splitedAry[0]);

			word = splitedAry[0].trim();
			String rightValue = "";
			for(int i=1; i<splitedAry.length; i++){
				rightValue += splitedAry[i];
			}
			rightValue = rightValue.trim();
			System.out.println(rightValue);


			for(int j=0; j<wordTypeList.length; j++) {
				if (rightValue.contains(wordTypeList[j])) {
					System.out.println("###FINDED : " +wordTypeList[j]+ "#####");
					//replace
					rightValue = rightValue.replaceAll(wordTypeList[j], wordTypeListKor[j]);
					System.out.println(rightValue);
				}
			}

			String [] splitedRV = rightValue.split("\\,");

			for(int k=0; k<splitedRV.length; k++){
				System.out.println(""+splitedRV[k].trim());
			}

		}

		br.close();
	}
}
