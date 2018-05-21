package edu_parser;

import java.io.*;
import java.util.StringTokenizer;

public class DicReader2 {

    static String filePath = "/Users/juseong-yun/IdeaProjects/git/edu_parser/a_dic";

    public static void main(String[] args) {
        // TODO Auto-generated method stub

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


    private static void ReadFileByLineTK(File fin) throws IOException {
        FileInputStream fis = new FileInputStream(fin);

        //Construct BufferedReader from InputStreamReader
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        String line = null;

        while ((line = br.readLine()) != null) {

            StringTokenizer st1 = new StringTokenizer(line, "(");


            String leftPart = "";
            String rightPart = "";
            int i = 0;
            while (st1.hasMoreTokens()) {
                String splitedStr = st1.nextToken();

                if(i == 0) {
                    //get first token
                    leftPart = splitedStr;
                    System.out.println(leftPart);
                }else{

                    //get second token
                    rightPart += splitedStr;
                }
                ++i;
            }


            System.out.println(rightPart);
            StringTokenizer st2 = new StringTokenizer(rightPart, ")");

            String leftPart2 = "";
            String rightPart2 = "";
            int j = 0;
            while (st2.hasMoreTokens()) {
                String splitedStr = st2.nextToken();

                if(j == 0) {
                    //get first token
                    leftPart2 = splitedStr;
                    //System.out.println(leftPart2);
                }else{

                    //get second token
                    rightPart2 += splitedStr;

                }
                ++j;
            }
            //System.out.println(rightPart2);

        }

        br.close();
    }


    private static void ReadFileByLineSP(File fin) throws IOException {



        FileInputStream fis = new FileInputStream(fin);

        //Construct BufferedReader from InputStreamReader
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        String outputStr = "";
        String line = null;

        String wordToken = "";
        String typeToken = "";
        String defineToken = "";

        while ((line = br.readLine()) != null) {
            wordToken = "";
            typeToken = "";
            defineToken = "";

            if(line.trim().equals("")){

            }else {
                String[] splitedAry = line.split("\\(");
                //System.out.println(splitedAry[0]);
                String rightValue = "";

                wordToken = splitedAry[0].trim();
                wordToken = wordToken.replaceAll("\'", "''");

                for (int i = 1; i < splitedAry.length; i++) {
                    rightValue += splitedAry[i];
                }
                //System.out.println(rightValue);

                String[] splitedRightValue = rightValue.split("\\)");
                String rrightValue = "";
                //System.out.println(splitedRightValue[0]);

                typeToken = splitedRightValue[0].trim();

                for (int i = 1; i < splitedRightValue.length; i++) {
                    rrightValue += splitedRightValue[1];
                }
                rrightValue = rrightValue.trim();

                rrightValue = rrightValue.replaceAll("\'", "''");
                //System.out.println(rrightValue);

                defineToken = rrightValue;

                //System.out.println("|" + wordToken + "|" + typeToken + "|" + defineToken);

                //Create SQL
                //INSERT INTO TBL_DIC(WORD, WORD_TYPE, DEFINITION) VALUES ('','','')
                String insertSQL = "INSERT INTO TBL_DIC(WORD, WORD_TYPE, DEFINITION) VALUES (";

                insertSQL += "'" + wordToken + "'";
                insertSQL += ",";
                insertSQL += "'" + typeToken + "'";
                insertSQL += ",";
                insertSQL += "'" + defineToken + "'";

                insertSQL += "); \n";
                System.out.println(insertSQL);
                outputStr += insertSQL;

            }

        }

        WriteFile(outputStr);

        br.close();
    }


    private static void WriteFile(String strBuffer) throws IOException {

        //file output
        String fileName = "/Users/juseong-yun/IdeaProjects/git/edu_parser/dic_a_db.txt" ;

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
