package com.example.javaproject.Classes.Translator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import us.codecraft.xsoup.Xsoup;

import java.io.IOException;
import java.util.Arrays;

public class Translator {
    private static final String ENGLISH_TO_RUSSIAN_URL = "https://www.reverso.net/%D0%BF%D0%B5%D1%80%D0%B5%D0%B2%D0%BE%D0%B4-%D1%82%D0%B5%D0%BA%D1%81%D1%82%D0%B0#sl=eng&tl=rus&text=";
    private static final String RUSSIAN_TO_ENGLISH_URL = "https://www.reverso.net/%D0%BF%D0%B5%D1%80%D0%B5%D0%B2%D0%BE%D0%B4-%D1%82%D0%B5%D0%BA%D1%81%D1%82%D0%B0#sl=rus&tl=eng&text=";
    private static final String TRANSLATION_XPATH = "//html/body/app-root/app-translation/div/app-translation-box/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/span/text()";
    private static final int MAX_TRANSLATION_LENGTH = 2000;
    private static final String TEXT_PARTS_REGEX = String.format(".{%d}", MAX_TRANSLATION_LENGTH);
    private static final String SPACE_REPLACEMENT = "%2520";

    private Translator(){}

    public static String translateFromRussianToEnglish(String text){
        return translate(RUSSIAN_TO_ENGLISH_URL,text);
    }

    public static String translateFromEnglishToRussian(String text){
        return translate(ENGLISH_TO_RUSSIAN_URL,text);
    }

    private static String translate(String initialUrl,String text){
        String[] textParts = splitTextByMaxSize(text);
        String[] translationParts= Arrays.stream(textParts).map(part-> {
            String url = getTranslationUrl(initialUrl,part);
            Document document = getDocument(url);
            return findTranslationInDocument(document);
        }).toArray(String[]::new);
        return String.join(" ",translationParts);
    }

    private static String getTranslationUrl(String initialUrl,String text){
        return initialUrl+text.replace(" ", SPACE_REPLACEMENT);
    }

    private static Document getDocument(String url){
        Document document;
        try{
            document = Jsoup.connect(url).get();
        }catch(IOException exception){
            document = null;
        }
        return document;
    }

    private static String[] splitTextByMaxSize(String text){
        return text.split(TEXT_PARTS_REGEX);
    }

    private static String findTranslationInDocument(Document document){
        String translation;
        try {
            translation = Xsoup.compile(TRANSLATION_XPATH).evaluate(document).list().get(0);
        }catch (Exception exception) {
            translation = "";
        }
        return translation;
    }





    
}
