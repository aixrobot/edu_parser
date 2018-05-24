package edu_parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.util.List;

public class Selenium {
    WebDriver driver;

    public static void main(String[] args) {

        Selenium selenium = new Selenium();
        selenium.InitDriver();

        try {
            selenium.FileReadAndParse();
            //selenium.ChromeReader("absent");
        }catch(Exception e){
        }

        if(selenium.driver != null) {
            selenium.CloseDriver();
        }
    }

    public void InitDriver(){
        System.setProperty("webdriver.chrome.driver", "/Users/juseong-yun/IdeaProjects/git/edu_parser/chromedriver");
        driver = new ChromeDriver();
    }

    public void CloseDriver(){

        driver.close();
        driver.quit();
    }

    public void FileReadAndParse() throws IOException {



        String filePath = "/Users/juseong-yun/IdeaProjects/git/edu_parser/kor_dic_b";

        File file = new File(filePath);

        FileInputStream fis = new FileInputStream(file);

        //Construct BufferedReader from InputStreamReader
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        String line = null;

        while ((line = br.readLine()) != null) {
            System.out.println(line);
            line = line.trim();

            try {
                Thread.sleep(1000);
                try {

                    ChromeReader(line);

                } catch (Exception e) {
                    //e.printStackTrace();
                } finally{
                    continue;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }


    public void ChromeReader(String word){
        if(driver != null) {
            driver.get("http://translate.google.co.kr/?um=1&ie=UTF-8&hl=ko&client=tw-ob#auto/ko/" + word); // URL로 접속하기

            //WebElement coolestWidgetEvah = driver.findElement(By.id("coolestWidgetEvah")); //id로 Element 가져오기

            WebElement resultBox = driver.findElement(By.id("result_box"));

            System.out.println("기본의미 : "+resultBox.getText());




            String src = driver.getPageSource();

            Document doc = Jsoup.parse(src);
            Element elemDoc = doc.select("div.gt-cc-r").first();
            //System.out.println(elemDoc);
            String partHtml = elemDoc.html();
            String [] splitedTxt = partHtml.split("\\\n");

            for(int i=0; i<splitedTxt.length; i++){

                //System.out.println(splitedTxt[i]);
                if(splitedTxt[i].contains("class=\"gt-cd-pos\"")){
                    System.out.println("순서 전치사 : "+Jsoup.parse(splitedTxt[i]).text());
                }
                if(splitedTxt[i].contains("gt-baf-word-clickable")){
                    System.out.println("한글 의미 : "+Jsoup.parse(splitedTxt[i]).text());
                }
                if(splitedTxt[i].contains("gt-baf-back")){
                    System.out.println("동의어 의미 : "+Jsoup.parse(splitedTxt[i]).text());
                }
            }

            System.out.println("");
            System.out.println("");
            System.out.println("");
        }

    }



    public void ChromeReader4(String word){
        if(driver != null) {
            driver.get("http://translate.google.co.kr/?um=1&ie=UTF-8&hl=ko&client=tw-ob#auto/ko/" + word); // URL로 접속하기

            //WebElement coolestWidgetEvah = driver.findElement(By.id("coolestWidgetEvah")); //id로 Element 가져오기

            WebElement resultBox = driver.findElement(By.id("result_box"));

            System.out.println("기본의미 : "+resultBox.getText());


            //List<WebElement> elemsList = driver.findElements(By.className("gt-baf-word-clickable")); //id로 Element 가져오기
            List<WebElement> elemsList = driver.findElements(By.className("gt-cc-r"));

            for (int i = 0; i < elemsList.size(); i++) {
                List<WebElement> elemPos = elemsList.get(i).findElements(By.className("gt-cd-pos"));

                for(int j=0; j<elemPos.size(); j++){
                    System.out.println("POS : "+elemPos.get(j).getText());
                }

                List<WebElement> elemKor = elemsList.get(i).findElements(By.className("gt-baf-word-clickable"));

                for(int j=0; j<elemKor.size(); j++){
                    System.out.println("KOR : "+elemKor.get(j).getText());
                }

                //System.out.println(elemsList.get(i).getText());
            }
            System.out.println("");
            System.out.println("");
            System.out.println("");



            String src = driver.getPageSource();

            Document doc = Jsoup.parse(src);
            Element elemDoc = doc.select("div.gt-cc-r").first();
            //System.out.println(elemDoc);
            String partHtml = elemDoc.html();
            String [] splitedTxt = partHtml.split("\\\n");
            for(int i=0; i<splitedTxt.length; i++){
                //System.out.println(splitedTxt[i]);
                if(splitedTxt[i].contains("class=\"gt-cd-pos\"")){
                    System.out.println("순서 전치사 : "+Jsoup.parse(splitedTxt[i]).text());
                }
                if(splitedTxt[i].contains("gt-baf-word-clickable")){
                    System.out.println("한글 의미 : "+Jsoup.parse(splitedTxt[i]).text());
                }
                if(splitedTxt[i].contains("gt-baf-back")){
                    System.out.println("동의어 의미 : "+Jsoup.parse(splitedTxt[i]).text());
                }
            }
        }

    }

    public void ChromeReader3(String word){
        if(driver != null) {
            driver.get("http://translate.google.co.kr/?um=1&ie=UTF-8&hl=ko&client=tw-ob#auto/ko/" + word); // URL로 접속하기

            //WebElement coolestWidgetEvah = driver.findElement(By.id("coolestWidgetEvah")); //id로 Element 가져오기

            WebElement resultBox = driver.findElement(By.id("result_box"));

            System.out.println("기본의미 : "+resultBox.getText());


            //List<WebElement> elemsList = driver.findElements(By.className("gt-baf-word-clickable")); //id로 Element 가져오기
            List<WebElement> elemsList = driver.findElements(By.className("gt-cc-r"));

            for (int i = 0; i < elemsList.size(); i++) {
                System.out.println(i+" : "+elemsList.get(i).getText());

//                List<WebElement> elemPos = elemsList.get(i).findElements(By.className("gt-cd-pos"));
//
//                for(int j=0; j<elemPos.size(); j++){
//                    System.out.println("POS : "+elemPos.get(j).getText());
//                }
//
//                List<WebElement> elemKor = elemsList.get(i).findElements(By.className("gt-baf-word-clickable"));
//
//                for(int j=0; j<elemKor.size(); j++){
//                    System.out.println("KOR : "+elemKor.get(j).getText());
//                }


                //System.out.println(elemsList.get(i).getText());
            }
            System.out.println("");
            System.out.println("");
            System.out.println("");


//            List<WebElement> elemsListSyn = driver.findElements(By.className("gt-baf-cell")); //id로 Element 가져오기
//            for (int i = 0; i < elemsListSyn.size(); i++) {
//                System.out.println(elemsListSyn.get(i).getText());
//            }

            String src = driver.getPageSource();
        }
        //driver.close();
        //driver.quit();
    }

    public void ChromeReader2(String word){
        if(driver != null) {
            driver.get("http://translate.google.co.kr/?um=1&ie=UTF-8&hl=ko&client=tw-ob#auto/ko/" + word); // URL로 접속하기

            //WebElement coolestWidgetEvah = driver.findElement(By.id("coolestWidgetEvah")); //id로 Element 가져오기
            List<WebElement> elemsList = driver.findElements(By.className("gt-baf-word-clickable")); //id로 Element 가져오기

            for (int i = 0; i < elemsList.size(); i++) {
                System.out.println(elemsList.get(i).getText());
            }
            System.out.println("");
            System.out.println("");
            System.out.println("");


//            List<WebElement> elemsListSyn = driver.findElements(By.className("gt-baf-cell")); //id로 Element 가져오기
//            for (int i = 0; i < elemsListSyn.size(); i++) {
//                System.out.println(elemsListSyn.get(i).getText());
//            }

            String src = driver.getPageSource();
        }
    }

}
