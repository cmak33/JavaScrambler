package com.example.javaproject.Classes.Translator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Arrays;

public abstract class Translator<T extends TranslationData> {

    protected T translationData;

    public Translator(T translationData){
        this.translationData=translationData;
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
        Document document;
        try{
            document = Jsoup.connect(url).get();
        }catch(IOException exception){
            document = null;
        }
        return document;
    }

    private String[] splitTextByMaxSize(String text){
        return text.split(translationData.getTextPartsRegex());
    }

    private String findTranslationInDocument(Document document){
        String translation;
        try {
            translation =document.selectXpath(translationData.getTranslationPath()).get(0).text();
        }catch (Exception exception) {
            translation = "";
        }
        return translation;
    }
    
}
