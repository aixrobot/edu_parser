package edu_parser;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;

public class JsoupParser {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        System.out.println("Starting Jsoup Parsing...");


        //FindDefinition();
        try {
            JsoupParse("absent");
        } catch (IOException e) {
            e.printStackTrace();
        }

//        try {
//            ReadLineAndParse();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static void FindDefinition(){
        String [] wordList = {"abreast","absent","attrition","abroad", "as"};

        for(int i=0; i<wordList.length; i++) {
            try {
                Thread.sleep(1000);
                try {
                    JsoupParse(wordList[i]);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally{
                    System.out.println("Finally Process");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    public static void ReadLineAndParse() throws IOException {

        String filePath = "/Users/juseong-yun/IdeaProjects/git/edu_parser/kor_dic_b";

        File file = new File(filePath);

        FileInputStream fis = new FileInputStream(file);

        //Construct BufferedReader from InputStreamReader
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        String line = null;
        int i = 0;
        boolean chk = false;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
            line = line.trim();

            try {
                Thread.sleep(1000);
                try {
                    JsoupParse(line);
                } catch (IOException e) {
                    //e.printStackTrace();
                    System.out.println("***** Exception : "+line);
                } finally{
                    continue;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

//        for(int i=0; i<wordList.length; i++) {
//            try {
//                Thread.sleep(1000);
//                try {
//                    JsoupParse(wordList[i]);
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                } finally{
//                    System.out.println("Finally Process");
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }

    }



    public static void JsoupParse(String word) throws IOException {
        Connection.Response response = Jsoup.connect("http://www.google.co.kr/search?q="+word+"%20구글번역").method(Connection.Method.GET).execute();
        Document document = response.parse();

        String html = document.html();
        String text = document.text();



        //main definition
        Element selDiv = document.select("div.oSioSc pre.tw-data-text span").first();
        System.out.println(selDiv.text());


        //additional definition
        Elements addSelElems = document.select("div.tw-bilingual-dictionary");


        for(Element elem: addSelElems){
            //System.out.println(elem);

            //System.out.println(elem);

            Elements selPos1 = elem.select("div.tw-bilingual-pos");
            for(Element eSelPos1: selPos1) {
                System.out.println(eSelPos1.text() + "|");
            }
            //System.out.println(selPos1.text());

            Elements selPos2 = elem.select("span.tw-bilingual-translation");
            for(Element eSelPos2: selPos2) {
                System.out.println(eSelPos2.text() + "|");
            }

            //Elements selPos3 = elem.select("div.tw-bilingual-entries div.tw-bilingual-marked");
            Elements selPos3 = elem.select("div.tw-bilingual-entries div.tw-bilingual-entry");
            for(Element eSelPos3: selPos3) {
                System.out.println(eSelPos3.text() + "|");
            }

//            Elements selPos3 = elem.select("div");
//            for(Element eSelPos3: selPos3) {
//                System.out.println(eSelPos3.text() + "|");
//            }


//            Element selPos = elem.select("div").first();
//            System.out.print(selPos.text());
        }

    }


    public static void JsoupParseTrans(String word) throws IOException {
        //Document google1 = Jsoup.connect("http://www.google.com").get();
        //Document google2 = Jsoup.connect("http://www.google.com").post();

        // Response로부터 Document 얻어오기
        Connection.Response response = Jsoup.connect("http://translate.google.co.kr/?ie=UTF-8&hl=ko&client=tw-ob#en/ko/"+word).method(Connection.Method.POST).followRedirects(true).execute();
        Document google3 = response.parse();

        String html = google3.html();
        String text = google3.text();

        System.out.println(html);



//        //main definition
//        Element selDiv = google3.select("div.oSioSc pre.tw-data-text span").first();
//        System.out.println(selDiv.text());
//
//
//        //additional definition
//        Elements addSelElems = google3.select("div.tw-bilingual-dictionary");
//
//        System.out.println("#################\n");
//        //System.out.println(addSelElems);
//
//        for(Element elem: addSelElems){
//            System.out.print(elem);
//        }




//        String [] splitedHtml = html.split("\\\n");
//        for(int i=0; i<splitedHtml.length; i++){
//            if(splitedHtml[i].contains("data-placeholder=\"번역\"")){
//                System.out.println(splitedHtml[i]);
//
//            }
//            //System.out.println(splitedHtml[i]);
//        }

    }


    public static void JsoupParseBK(String word) throws IOException {
        //Document google1 = Jsoup.connect("http://www.google.com").get();
        //Document google2 = Jsoup.connect("http://www.google.com").post();

        // Response로부터 Document 얻어오기
        Connection.Response response = Jsoup.connect("http://www.google.co.kr/search?q="+word+"%20구글번역").method(Connection.Method.GET).execute();
        Document google3 = response.parse();

        String html = google3.html();
        String text = google3.text();

        //System.out.println(html);


        Element btnK = google3.select("input[name=btnK]").first();
        String btnKValue = btnK.attr("value");
        //Element korDef = google3.select(div.)
        //System.out.println(btnKValue);



        //main definition
        Element selDiv = google3.select("div.oSioSc pre.tw-data-text span").first();
        System.out.println(selDiv.text());


        //additional definition
        Elements addSelElems = google3.select("div.tw-bilingual-dictionary");

        System.out.println("#################\n");
        //System.out.println(addSelElems);

        for(Element elem: addSelElems){
            Element selPos = elem.select("div").first();
            System.out.print(selPos.text());
        }




//        String [] splitedHtml = html.split("\\\n");
//        for(int i=0; i<splitedHtml.length; i++){
//            if(splitedHtml[i].contains("data-placeholder=\"번역\"")){
//                System.out.println(splitedHtml[i]);
//
//            }
//            //System.out.println(splitedHtml[i]);
//        }

    }

}
