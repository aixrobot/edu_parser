package edu_parser;
import java.io.*;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Translate {
    private String getText() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String string = null;

        try {
            string = br.readLine();
        } catch (IOException ioe) {
            System.err.println("I/O Error");
        }
        return string;
    }

    private String getTranslate(String lang, String englishText) {
        // Convert all ' ' to '+'
        englishText = englishText.replaceAll(" ", "+");

        // Scrape web page
        final WebClient webClient = new WebClient();
        String pageAsXml = "";
        try (final WebClient webClient1 = new WebClient()) {

            // Get the page
            final HtmlPage page = webClient.getPage("https://translate.google.co.kr/?um=1&ie=UTF-8&hl=ko&client=tw-ob#auto/ko/absent");
            webClient.waitForBackgroundJavaScript(1000); // Wait for Google to process AJAX request
            pageAsXml = page.asXml(); // Produce the HTML content of the page (after JS has worked it's magic)
        } catch (Exception e) {
            // catch
        }

        /*
         * Painfully process output
         */

        // Find area we are working in
        char[] pageChar = pageAsXml.toCharArray();
        String returnText = "";
        int i = pageAsXml.indexOf("result_box") + 101;
        do {
            returnText += pageChar[i];
            i++;
        } while (pageChar[i] != '<');

        // Decide if we have a split response
        String find = "<span class=\"hps\">";
        int last = 0;
        int j = 0;
        while (last != -1) {
            last = pageAsXml.indexOf(find, last);
            if (last != -1) {
                j++;
                last += find.length();
            }
        }
        if (j > 1) {
            i = pageAsXml.indexOf("<span class=\"hps\">", i + 1);
            do {
                returnText += pageChar[i];
                i++;
            } while (pageChar[i] != '<');
        }

        // Clean up output string
        returnText = returnText.replaceAll("<span class=\"hps\">", " "); // removes random HTML tag easily
        returnText = returnText.replaceAll("\n", " ").replace("\r", " "); // removes all line breaks from string
        for (i = 0; i < 10; i++) {
            returnText = returnText.replaceAll("  ", " "); // hopefully remove all double spaces, leaving spaces between words
        }

        // Return response
        return returnText;
    }

    public static void main(String[] args) {
        Translate t = new Translate();

        System.out.println("Enter English text to translate:");
        String englishText = t.getText();

        System.out.println("Enter language to translate to (e.g. \"fr\", \"es\", \"de\"):");
        String lang = t.getText();
        try {
            System.out.println(t.getTranslate(lang, englishText));
        } catch (Exception e) {
            //catch
        }
    }
}
