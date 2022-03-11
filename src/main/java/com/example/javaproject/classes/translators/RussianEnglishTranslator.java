package com.example.javaproject.classes.translators;

public class RussianEnglishTranslator extends Translator<RussianEnglishTranslationData>{

    public RussianEnglishTranslator(RussianEnglishTranslationData translationData,WebDriverCreator webDriverCreator) {
        super(translationData,webDriverCreator);
    }

    public String translateFromEnglishToRussian(String text){
        return translate(translationData.getEnglishToRussianUrl(),text);
    }

    public String translateFromRussianToEnglish(String text){
        return translate(translationData.getRussianToEnglishUrl(),text);
    }
}
