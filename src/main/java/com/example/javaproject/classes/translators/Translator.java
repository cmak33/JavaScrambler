package com.example.javaproject.classes.translators;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import java.util.Arrays;

public abstract class Translator<T extends TranslationData> {
    protected final T translationData;
    private final WebDriverCreator webDriverCreator;

    protected Translator(T translationData,WebDriverCreator webDriverCreator){
        this.translationData=translationData;
        this.webDriverCreator = webDriverCreator;
    }

    protected String translate(String initialUrl,String text){
        String[] textParts = splitTextByMaxSize(text);
        String[] translationParts= Arrays.stream(textParts).map(part->
            translateTextWithPermissibleLength(initialUrl,part)
        ).toArray(String[]::new);
        return String.join(" ",translationParts);
    }

    private String translateTextWithPermissibleLength(String initialUrl,String text){
        String url = getTranslationUrl(initialUrl,text);
        Document document = getDocumentByUrl(url);
        return findTranslationInDocument(document);
    }

    private  String getTranslationUrl(String initialUrl,String text){
        return initialUrl+text.replace(" ", translationData.getUrlSpaceReplacement());
    }

    private Document getDocumentByUrl(String url){
        WebDriver driver = webDriverCreator.createWebDriver();
        driver.get(url);
        Document document = Jsoup.parse(driver.getPageSource());
        driver.quit();
        return document;
    }

    private String[] splitTextByMaxSize(String text){
        return text.split(translationData.getTextPartsRegex());
    }

    private String findTranslationInDocument(Document document){
        String translation;
        try {
            translation =document.selectXpath(translationData.getTranslationXpath()).get(0).text();
        }catch (Exception exception) {
            translation = "";
        }
        return translation;
    }
    
}
