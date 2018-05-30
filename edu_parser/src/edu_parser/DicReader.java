package edu_parser;

import com.sun.tools.javac.comp.Check;
import org.apache.bcel.generic.ALOAD;

import java.io.*;
import java.util.StringTokenizer;

public class DicReader {

	static String ALPHABET_PATH = "b";
	static int staticCount = 0;
	static String filePath = "/Users/juseong-yun/IdeaProjects/git/edu_parser/EKDIC/"+ALPHABET_PATH+".dic";
	static String tFilePath = "/Users/juseong-yun/IdeaProjects/git/edu_parser/EKDIC/word_"+ALPHABET_PATH+".txt";
	static String [] alphabet =
			{
					"a",
					"b",
					"c",
					"d",
					"e",
					"f",
					"g",
					"h",
					"i",
					"j",
					"k",
					"l",
					"m",
					"n",
					"o",
					"p",
					"q",
					"r",
					"s",
					"t",
					"u",
					"v",
					"w",
					"x",
					"y",
					"z"
			};

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

		LoopAndLineCheck();
		//ReadFileAndInsertEKDIC();

	}


	private static void LoopAndLineCheck(){
		for(int i=0; i<alphabet.length; i++){
			CheckLine(alphabet[i]);
		}
	}

	private static void ReadFileAndInsertEKDIC(){
		SeleniumDB selDB = new SeleniumDB();

		for(int i=0; i<alphabet.length; i++){
			String alp = "a";
			alp = alphabet[i];
			String destPath = "/Users/juseong-yun/IdeaProjects/git/edu_parser/EKDIC/word_"+alp+".txt";
			selDB.OpenDB();
			selDB.FileReadAndInsertDB(destPath);
			selDB.CloseDB();
		}
	}



	private static void CheckLine(String alp){

		String srcPath = "/Users/juseong-yun/IdeaProjects/git/edu_parser/EKDIC/"+alp+".dic";
		String destPath = "/Users/juseong-yun/IdeaProjects/git/edu_parser/EKDIC/word_"+alp+".txt";

		int oriLineCount = 0;
		try {
			File file = new File(srcPath);
			FileInputStream fis = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));

			String line = null;
			while ((line = br.readLine()) != null) {
				++oriLineCount;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		int parseLineCount = 0;
		try {
			File tFile = new File(destPath);
			FileInputStream tFis = new FileInputStream(tFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(tFis));

			String line = null;
			while ((line = br.readLine()) != null) {
				++parseLineCount;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		staticCount += parseLineCount;

		System.out.println("PARSE "+alp+" LINE COMPARE : "+ oriLineCount + ", " + parseLineCount);
		System.out.println("TOTAL WORD COUNT : " + staticCount);
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

	private static void ReadFileByLineSP() {
		try {
			File file = new File(filePath);

			FileInputStream fis = new FileInputStream(file);

			//Construct BufferedReader from InputStreamReader
			BufferedReader br = new BufferedReader(new InputStreamReader(fis, "EUC-KR"));

			String line = null;

			String word = "";
			String wordType = "";
			String definition = "";

			String[] wordTypeList = {"n,", "a,", "vt,", "vi", "x,"};
			String[] wordTypeListKor = {"[N]", "[A]", "[V]", "[V]", "[P]"};

			String stringBuilder = "";

			boolean chk = false;
			while ((line = br.readLine()) != null) {
				word = "";
				wordType = "";
				definition = "";

				String[] splitedAry = line.split("\\:");
				//System.out.println(splitedAry[0]);

				word = splitedAry[0].trim();

				System.out.println(word);
				stringBuilder += word + "\n";

				String rightValue = "";
				for (int i = 1; i < splitedAry.length; i++) {
					rightValue += splitedAry[i];
				}
				rightValue = rightValue.trim();
				//System.out.println(rightValue);


				for (int j = 0; j < wordTypeList.length; j++) {
					if (rightValue.contains(wordTypeList[j])) {
						//System.out.println("###FINDED : " +wordTypeList[j]+ "#####");
						//replace
						rightValue = rightValue.replaceAll(wordTypeList[j], wordTypeListKor[j]);
						//System.out.println(rightValue);
					}
				}

				String[] splitedRV = rightValue.split("\\,");

				for (int k = 0; k < splitedRV.length; k++) {
					//System.out.println(""+splitedRV[k].trim());
				}

			}

			WriteFile(stringBuilder);

			br.close();

		}catch (IOException e){

		}

	}


	private static void WriteFile(String strBuffer) throws IOException {

		//file output
		String fileName = tFilePath;

		try{

			BufferedWriter fw = new BufferedWriter(new FileWriter(fileName, true));

			// 파일안에 문자열 쓰기
			fw.write(strBuffer);
			fw.flush();

			// 객체 닫기
			fw.close();


		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
