package edu_parser;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;

public class HtmlUnitParser {


    public static void main(String[] args) {
        try {
            final WebClient webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER);
            //HtmlPage page = webClient.getPage("http://www.google.co.kr/search?q=absent");

            webClient.setAjaxController(new NicelyResynchronizingAjaxController());
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setRedirectEnabled(true);
            webClient.getCookieManager().setCookiesEnabled(true);
            webClient.getOptions().setCssEnabled(true);
            webClient.waitForBackgroundJavaScript(1000);


            final HtmlPage page = webClient.getPage("https://translate.google.co.kr/?um=1&ie=UTF-8&hl=ko&client=tw-ob#auto/ko/absent");

            //System.out.println(page.asXml());
            String pageAsXml = page.asXml();
            System.out.println(pageAsXml);

//            String pageAsText = page.asText();
//            System.out.println(pageAsText);
        }catch (IOException e){
            //e.printStackTrace();
        }
    }
}
