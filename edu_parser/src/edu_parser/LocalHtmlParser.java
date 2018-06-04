package edu_parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;

public class LocalHtmlParser {

    static String filePath = "/Users/juseong-yun/IdeaProjects/git/edu_parser/EEDIC/wb1913_a.html";
    public static void main(String[] args) {
        System.out.println("[LOCAL HTML PARSER]");

        LocalHtmlParser lhp = new LocalHtmlParser();

        String alp = "a";
        String aFilePath = "/Users/juseong-yun/IdeaProjects/git/edu_parser/EEDIC/wb1913_"+alp+".html";
        String strFile = lhp.FileStringReader(aFilePath);


        lhp.EngHtmlParser(strFile);


    }

    public void EngHtmlParser(String strHtml){
        Document htmlDoc = Jsoup.parse(strHtml);

        Elements elems = htmlDoc.select("P");

        for (Element elem: elems) {
            //System.out.println(elem.ownText());
            String word = elem.child(0).text();
            String pos = elem.child(1).text();
            String ownText = elem.ownText();
            ownText = ownText.replaceAll("\\(\\)", "");
            ownText = ownText.trim();
            System.out.println(word+"---"+pos+"---"+ownText);

        }

    }


    public String FileStringReader(String filePath){
        String stringBuilder = "";
        StringBuilder strB = new StringBuilder();


        try {
            File file = new File(filePath);

            FileInputStream fis = new FileInputStream(file);

            BufferedReader br = new BufferedReader(new InputStreamReader(fis, "EUC-KR"));

            String line = "";
            while ((line = br.readLine()) != null) {
                strB.append(line);
                //stringBuilder += line + "\n";
                //System.out.println(line);


            }

            br.close();

            return strB.toString();

        }catch (IOException e){

        }

        return stringBuilder;
    }


}
