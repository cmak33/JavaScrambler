package com.scrambler.classes.translators;

import com.scrambler.classes.translators.parser.Parser;


import java.util.Arrays;
import java.util.List;


public abstract class Translator<T extends TranslationData> {
    protected final T translationData;
    private final Parser parser;

    protected Translator(T translationData,Parser parser){
        this.translationData=translationData;
        this.parser=parser;
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
        List<String> translation = parser.findElementsTextByXPath(url,translationData.getTranslationXpath());
        return String.join(" ",translation);
    }

    private  String getTranslationUrl(String initialUrl,String text){
        return initialUrl+text.replace(" ", translationData.getUrlSpaceReplacement());
    }


    private String[] splitTextByMaxSize(String text){
        return text.split(translationData.getTextPartsRegex());
    }

    
}
