package com.example.javaproject.classes.translators;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

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
        try{
            WebDriverWait wait = new WebDriverWait(driver,30);
            wait.until(d -> d.findElement(By.xpath(translationData.getTranslationXpath())));
        }catch (Exception e){

        }
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
            translation =String.join(" ",document.selectXpath(translationData.getTranslationXpath()).stream().map(x->x.text()).toArray(String[]::new));
        }catch (Exception exception) {
            translation = "";
        }
        return translation;
    }
    
}
