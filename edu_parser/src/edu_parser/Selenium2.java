package edu_parser;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.util.List;

public class Selenium2 {


    public static void main(String[] args) {
        try {
            ParseByChrome();
        }catch(Exception e){

        }
    }


    public static void ParseByChrome() throws IOException {


        WebDriver driver;
        System.setProperty("webdriver.chrome.driver", "/Users/juseong-yun/IdeaProjects/git/edu_parser/chromedriver");
        driver = new ChromeDriver();



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
                Thread.sleep(2000);
                try {



                    driver.get("http://translate.google.co.kr/?um=1&ie=UTF-8&hl=ko&client=tw-ob#auto/ko/"+line); // URL로 접속하기

                    //WebElement coolestWidgetEvah = driver.findElement(By.id("coolestWidgetEvah")); //id로 Element 가져오기
                    List<WebElement> elemsList = driver.findElements(By.className("gt-baf-word-clickable")); //id로 Element 가져오기

                    for(int i=0; i<elemsList.size(); i++){
                        System.out.println(elemsList.get(i).getText());
                    }

                    List<WebElement> elemsListSyn = driver.findElements(By.className("gt-baf-cell")); //id로 Element 가져오기
                    for(int i=0; i<elemsListSyn.size(); i++){
                        System.out.println(elemsListSyn.get(i).getText());
                    }

                    String src = driver.getPageSource();



                } catch (Exception e) {
                    //e.printStackTrace();
                } finally{
                    continue;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }




        //System.out.println(src);
    }

    public void ChromeReader(){}


}
