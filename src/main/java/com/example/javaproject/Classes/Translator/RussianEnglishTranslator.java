package com.example.javaproject.Classes.Translator;

public class RussianEnglishTranslator extends Translator<RussianEnglishTranslationData>{

    public RussianEnglishTranslator(RussianEnglishTranslationData translationData) {
        super(translationData);
    }

    public String translateFromEnglishToRussian(String text){
        return translate(translationData.getEnglishToRussianUrl(),text);
    }

    public String translateFromRussianToEnglish(String text){
        return translate(translationData.getRussianToEnglishUrl(),text);
    }
}
