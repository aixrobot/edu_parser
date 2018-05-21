package edu_parser;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class JsoupParser {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        System.out.println("Jsoup First");


        //FindDefinition();
        try {
            JsoupParse("native");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }


    public static void JsoupParse(String word) throws IOException {
        //Document google1 = Jsoup.connect("http://www.google.com").get();
        //Document google2 = Jsoup.connect("http://www.google.com").post();

        // Response로부터 Document 얻어오기
        Connection.Response response = Jsoup.connect("http://www.google.co.kr/search?q="+word+"%20의미").method(Connection.Method.GET).execute();
        Document google3 = response.parse();

        String html = google3.html();
        String text = google3.text();

        System.out.println(html);


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


    public static void JsoupParseTrans(String word) throws IOException {
        //Document google1 = Jsoup.connect("http://www.google.com").get();
        //Document google2 = Jsoup.connect("http://www.google.com").post();

        // Response로부터 Document 얻어오기
        Connection.Response response = Jsoup.connect("http://translate.google.co.kr/?hl=ko&q="+word+"#en/ko/"+"native").method(Connection.Method.GET).execute();
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

}
