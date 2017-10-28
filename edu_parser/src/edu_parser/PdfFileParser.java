package edu_parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

/**
 * This class parses the pdf file. i.e this class returns the text from the pdf
 * file.
 * 
 * @author Mubin Shrestha
 */

public class PdfFileParser {

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
		String filepath = "D:\\word2.pdf";
		String contents = new PdfFileParser().PdfFileParser(filepath);
		System.out.println(new PdfFileParser().PdfFileParser(filepath));

		int [] flag = new int[100000];
		String [] words = new String[100000];
		
		int i=0;
		
		StringTokenizer st1 = new StringTokenizer(contents, "\n");
		while (st1.hasMoreTokens()) {
			StringTokenizer st2 = new StringTokenizer(st1.nextToken(), " ");
			while (st2.hasMoreTokens()) {
				String tok = st2.nextToken();
				//tok = tok.replace(",", " ");
				//tok = tok.replace(";", " ");
				tok = tok.trim();
				if (tok != null && !tok.equals("")) {
					System.out.print(tok);
					if (Pattern.matches("^[°¡-ÆR]*$", tok)) {
						//System.out.print(">>>>ÇÑ±Û");
						//System.out.print(", ");
					} else if (Pattern.matches("^[a-zA-Z]*$", tok)) {
						//System.out.print(">>>>¿µ¹®");
						System.out.print(", ");
					} else {
						//System.out.print(">>>>±âÅ¸");
						//System.out.print(", ");
					}
					words[i] += tok;
				}
				
				i++;
			}
			//words[i] += "\n";
			System.out.println("");
		}
		
		for(int j=0; j<=i; j++){
			//System.out.print(words[j]);
		}
		
		
		/*
		//´Ü¾îÀå 1 ÆÄ½Ì
		int i = 0;
		
		StringTokenizer st1 = new StringTokenizer(contents, "\n");
		
		while (st1.hasMoreTokens()) {
			
			StringTokenizer st2 = new StringTokenizer(st1.nextToken(), " ");
			while (st2.hasMoreTokens()) {
				String tok = st2.nextToken();
				tok = tok.replace(",", "");
				tok = tok.replace(":", "");
				tok = tok.trim();
				if (!tok.equals("")) {
					System.out.print("#TOK:" + tok);
					if (Pattern.matches("^[°¡-ÆR]*$", tok)) {
						System.out.print(">>>>ÇÑ±Û");
						flag[i] = 1;
						words[i] = tok;
					} else if (Pattern.matches("^[a-zA-Z]*$", tok)) {
						System.out.print(">>>>¿µ¹®");
						flag[i] = 2;
						words[i] = tok;
					} else {
						System.out.print(">>>>±âÅ¸");
						flag[i] = 3;
					}
					System.out.println("");
				}
				if(i > 0){
					if(flag[i] == 2 && flag[i-1] == 1){ //ÇöÀç°ªÀÌ ¿µ¹®ÀÌ°í ÀÌÀü°ªÀÌ ÇÑ±ÛÀÏ¶§ ÇÑ°³ÀÇ ±×·ì
						words[i-1] = words[i-1]+"\n";
						System.out.println("#####GROUPED#####");
					}
				}
				i++;
			}
		}
		
		for(int j=0; j<i; j++){
			if(words[j]!=null && !words[j].equals("")){
				System.out.print(words[j]);
				if(!words[j].contains("\n")){
					System.out.print(", ");
				}
			}
		}
		*/
		
	}
}