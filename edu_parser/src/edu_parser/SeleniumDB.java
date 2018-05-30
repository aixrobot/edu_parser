package edu_parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;




public class SeleniumDB {


    static final int MODE_DEF_NULL_UPDATE = 0;
    static final int MODE_FLAG_UPDATE = 1;
    static final int MODE_ALLREPLACE = 2;
    static final int MODE_DEF_MIS_UPDATE = 4;

    static final int MODE = MODE_ALLREPLACE;

    WebDriver driver;
    Connection c = null;

    public static void main(String[] args) {

        SeleniumDB selenium = new SeleniumDB();
        selenium.InitDriver();
        selenium.OpenDB();

        String filePath = "/Users/juseong-yun/IdeaProjects/git/edu_parser/kor_dic_b";

        HashMap<String, String> wordHashMap = new HashMap<String, String>();

        List<String> wordList = new ArrayList<String>();
        List<String> defList = new ArrayList<String>();

        try {
            //selenium.ParseDefinition();


            wordList = selenium.SelectWordList("a");
            defList = selenium.ListParse(wordList);
            selenium.UpdateDefLoopControl(wordList, defList);



            //selenium.FileReadAndInsertDB(filePath);
            //selenium.FileReadAndParse();
            //selenium.ChromeReader("absent");
        }catch(Exception e){
        }


        if(selenium.driver != null) {
            selenium.CloseDriver();
        }
        if(selenium.c != null) {
            selenium.CloseDB();
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


    public void OpenDB(){
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:/Users/juseong-yun/IdeaProjects/git/edu_parser/java_sqlite.db");
            c.setAutoCommit(true);
            System.out.println("Opened database successfully");
        }catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public void CloseDB(){
        if(c!= null){
            try {
                c.close();
                System.out.println("Closed database successfully");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void ParseDefinition() throws IOException {
        String def = "지원<def><pos>명사<kor>지원<syn>backup,<syn>desire<pos>형용사<kor>지원하는<syn>backup";
        String [] split1 = def.split("\\<def>");

        for(int i=0; i<split1.length; i++){
            System.out.println("-"+split1[i]);
            if(i == 1){
                String [] split2 = split1[1].split("\\<pos>");
                for(int j=0; j<split2.length; j++){
                    System.out.println("--"+split2[j]);
                    String [] split3 = split2[j].split("\\<kor>");
                    for(int k=0; k<split3.length; k++){
                        System.out.println("---"+split3[k]);
                    }
                }
            }
        }
    }



    public void UpdateDefLoopControl(List<String> wordList, List<String> defList){
        System.out.println("Find word count : " + wordList.size());
        System.out.println("Find def count : " + defList.size());

        for(int i=0; i<wordList.size(); i++){
            int resultCnt = UpdateDefinition(wordList.get(i), defList.get(i), MODE_ALLREPLACE);
            System.out.println("Update Complete : " + wordList.get(i) + " - Count : " + resultCnt);
        }
    }

    public void FileReadAndInsertDB(String filePath){
        System.out.println("File Path : "+filePath);
        File file = new File(filePath);

        try {
            FileInputStream fis = new FileInputStream(file);

            //Construct BufferedReader from InputStreamReader
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));

            String line = null;

            while ((line = br.readLine()) != null) {
                System.out.println(line);
                line = line.trim();
                InsertWord(line);
            }

        }catch(IOException e){
            e.printStackTrace();
        }
    }


    public List<String> ListParse(List<String> wordList){
        String result = "";
        List<String> defList = new ArrayList<String>();
        for(int i=0; i<wordList.size(); i++) {
            try {
                Thread.sleep(1000);
                try {

                    String defStr = ChromeWordReader(wordList.get(i));
                    defList.add(defStr);

                } catch (Exception e) {
                    //e.printStackTrace();
                } finally {
                    continue;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return defList;
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

    public String ChromeWordReader(String word){

        /*
        seperator

        newline : /n
        comma : ,
        bar : |
        colon : :
        semicolon : ;
        plus : +

         */



        String definition = "";
        String newline = "\n";
        if(driver != null) {
            driver.get("http://translate.google.co.kr/?um=1&ie=UTF-8&hl=ko&client=tw-ob#auto/ko/" + word); // URL로 접속하기

            //WebElement coolestWidgetEvah = driver.findElement(By.id("coolestWidgetEvah")); //id로 Element 가져오기

            WebElement resultBox = driver.findElement(By.id("result_box"));

            System.out.println("Captured success : " + word + " - " + resultBox.getText() + "<def>");
            definition += resultBox.getText() + "<def>";


            String src = driver.getPageSource();

            Document doc = Jsoup.parse(src);
            Element elemDoc = doc.select("div.gt-cc-r").first();
            //System.out.println(elemDoc);
            String partHtml = elemDoc.html();
            String [] splitedTxt = partHtml.split("\\\n");

            for(int i=0; i<splitedTxt.length; i++){

                //System.out.println(splitedTxt[i]);
                if(splitedTxt[i].contains("class=\"gt-cd-pos\"")){
                    System.out.println("<pos>"+Jsoup.parse(splitedTxt[i]).text());
                    definition += "<pos>" + Jsoup.parse(splitedTxt[i]).text();

                }

                if(splitedTxt[i].contains("gt-baf-word-clickable")){
                    System.out.println("<kor>"+Jsoup.parse(splitedTxt[i]).text());
                    definition += "<kor>" + Jsoup.parse(splitedTxt[i]).text();

                }

                if(splitedTxt[i].contains("gt-baf-back")){
                    System.out.println("<syn>"+Jsoup.parse(splitedTxt[i]).text());
                    definition += "<syn>" + Jsoup.parse(splitedTxt[i]).text();

                }
            }

            System.out.println("");
            System.out.println("");
            System.out.println("");
        }
        return definition;
    }



    //Databas section
    public void InsertWord(String word){
        String sql = "INSERT INTO TBL_CRAW_KORDIC('WORD') VALUES(?)";

        try {
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setString(1, word);
            pstmt.executeUpdate();
            c.commit();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

        }
    }


    public List<String> SelectWordList(String startChar){
        Statement stmt = null;
        List<String> wordList = new ArrayList<String>();

        try {

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM TBL_CRAW_KORDIC WHERE WORD LIKE '"+startChar+"%' and id between 114744 and 114844 AND CRAW_TEXT IS NULL  "  );
            while ( rs.next() ) {
                int id = rs.getInt("id");
                String  word = rs.getString("word");
                wordList.add(word);
                System.out.println( "ID = " + id );
                System.out.println( "WORD = " + word );
                System.out.println();
            }

            rs.close();
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");

        return wordList;
    }


    public int UpdateDefinition(String word, String def, int mode){
        int updateCnt = -1;

        def = def.trim();

        String sql = "UPDATE TBL_CRAW_KORDIC SET CRAW_TEXT = ?, C_FLAG = C_FLAG+1 WHERE UPPER(WORD) = UPPER(?) AND CRAW_TEXT is null";
/*
        switch(MODE) {
            case MODE_ALLREPLACE :
                sql = "UPDATE TBL_CRAW_KORDIC SET CRAW_TEXT = ?, C_FLAG = C_FLAG+1 WHERE UPPER(WORD) = UPPER(?) ";
                break;
            case MODE_DEF_NULL_UPDATE :
                sql = "UPDATE TBL_CRAW_KORDIC SET CRAW_TEXT = ?, C_FLAG = C_FLAG+1 WHERE UPPER(WORD) = UPPER(?) AND CRAW_TEXT is null ";
                break;
            case MODE_FLAG_UPDATE :
                sql = "UPDATE TBL_CRAW_KORDIC SET CRAW_TEXT = ?, C_FLAG = C_FLAG+1 WHERE UPPER(WORD) = UPPER(?) AND TRIM(CRAW_TEXT) = '' ";
                break;
            case MODE_DEF_MIS_UPDATE :
                sql = "UPDATE TBL_CRAW_KORDIC SET CRAW_TEXT = ?, C_FLAG = C_FLAG+1 WHERE UPPER(WORD) = UPPER(?) AND TRIM(CRAW_TEXT) = '' ";
                break;
            default:
                break;
        }
*/

        try {

            PreparedStatement pstmt = c.prepareStatement(sql);
            System.out.println("Define : " + def);
            pstmt.setString(1, def);
            pstmt.setString(2, word);
            updateCnt = pstmt.executeUpdate();
            //c.commit();
            pstmt.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");

        return updateCnt;
    }


    public int UpdateWordFlag(String word){
        int updateCnt = -1;

        String sql = "UPDATE TBL_CRAW_KORDIC SET CRAW_TEXT = ?, C_FLAG = C_FLAG+1 WHERE UPPER(WORD) = UPPER(?) ";

        try {

            PreparedStatement pstmt = c.prepareStatement(sql);
            //pstmt.setString(1, def);
            pstmt.setString(2, word);
            updateCnt = pstmt.executeUpdate();
            c.commit();
            pstmt.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");

        return updateCnt;
    }





    //Database format section

    public void InsertSql(){
        Statement stmt = null;

        try {
            stmt = c.createStatement();
            String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
                    "VALUES (1, 'Paul');";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
                    "VALUES (2, 'Allen');";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
                    "VALUES (3, 'Teddy');";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
                    "VALUES (4, 'Mark');";
            stmt.executeUpdate(sql);

            stmt.close();

            c.commit();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }

    public void InsertPreparedSql(){
        String sql = "INSERT INTO TBL_TEMP('name') VALUES(?)";

        try {
            PreparedStatement pstmt = c.prepareStatement(sql);
            pstmt.setString(1, "scott");
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

        }
    }

    public void UpdateSql(){

        Statement stmt = null;

        try {

            stmt = c.createStatement();
            String sql = "UPDATE TEMP_TEMP set NAME = 'observer' where ID=1;";
            stmt.executeUpdate(sql);
            c.commit();

            ResultSet rs = stmt.executeQuery( "SELECT * FROM TBL_TEMP;" );

            while ( rs.next() ) {
                int id = rs.getInt("id");
                String  name = rs.getString("name");

                System.out.println( "ID = " + id );
                System.out.println( "NAME = " + name );
                System.out.println();
            }
            rs.close();
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }



    public void DeleteSql(){
        Statement stmt = null;

        try {

            stmt = c.createStatement();
            String sql = "DELETE from TBL_TEMP where ID=2;";
            stmt.executeUpdate(sql);
            c.commit();

            ResultSet rs = stmt.executeQuery( "SELECT * FROM TBL_TEMP;" );

            while ( rs.next() ) {
                int id = rs.getInt("id");
                String  name = rs.getString("name");

                System.out.println( "ID = " + id );
                System.out.println( "NAME = " + name );
                System.out.println();
            }
            rs.close();
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }

    public void SelectSql(){

        Statement stmt = null;
        try {

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM TBL_TEST;" );

            while ( rs.next() ) {
                int id = rs.getInt("id");
                String  name = rs.getString("name");

                System.out.println( "ID = " + id );
                System.out.println( "NAME = " + name );
                System.out.println();
            }
            rs.close();
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }


}
