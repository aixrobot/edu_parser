package edu_parser;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * This class parses the pdf file. i.e this class returns the text from the pdf
 * file.
 * 
 * @author Mubin Shrestha
 */

public class PdfFileTokenParser {

	public String PdfFileParser(String pdffilePath) throws FileNotFoundException, IOException {
		String content;
		FileInputStream fi = new FileInputStream(new File(pdffilePath));
		PDFParser parser = new PDFParser(fi);
		parser.parse();
		COSDocument cd = parser.getDocument();
		PDFTextStripper stripper = new PDFTextStripper();
		content = stripper.getText(new PDDocument(cd));
		cd.close();
		return content;
	}

	public static void main(String args[]) throws FileNotFoundException, IOException {
		String filepath = "/Users/juseong-yun/IdeaProjects/git/edu_parser/toeic.pdf";
		String contents = new PdfFileTokenParser().PdfFileParser(filepath);
		//System.out.println(contents);

		//StringTokenizer st1 = new StringTokenizer(contents, " ");
		contents = contents.replaceAll("\n", " ");
		String [] splitedTxt = contents.split("\\ ");
		System.out.println("Splited Size : "+splitedTxt.length);

		String [] trimToken = {"?", "!", ";", ".", ","};

		String [] words = new String[100000];
		HashMap<String, Integer> hashWordList = new HashMap<String, Integer>();

		String keyWord = "";
		for(int i=0; i<splitedTxt.length; i++) {
			keyWord = "";
			keyWord = splitedTxt[i];
			keyWord = keyWord.trim();
			keyWord = keyWord.toLowerCase();


			for(int k=0; k<trimToken.length; k++){
				if(keyWord.endsWith(trimToken[k])){
					keyWord = keyWord.replace(trimToken[k], "");
				}
			}
			if (keyWord.matches("[a-zA-Z]+") && keyWord.length() != 1) {
				int dupCnt = 1;
				for (HashMap.Entry<String, Integer> elem : hashWordList.entrySet()) {
					if (elem.getKey().equals(keyWord)) {
						dupCnt = elem.getValue() + 1;
					}
				}

				hashWordList.put(keyWord, dupCnt);
			}
		}

		for(HashMap.Entry<String, Integer> elem : hashWordList.entrySet()){

			String key = elem.getKey();
			int value = elem.getValue();

			System.out.println(key + " : " + value);

		}

		System.out.println("Total Count : " + splitedTxt.length);
		System.out.println("Total Count Grouop : " + hashWordList.size());


	}
}




