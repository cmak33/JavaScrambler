package com.example.javaproject.Classes.Translator;

public class RussianEnglishTranslationData extends TranslationData{
    private final String russianToEnglishUrl;
    private final String englishToRussianUrl;

    public RussianEnglishTranslationData(String russianToEnglishUrl,String englishToRussianUrl,String translationXpath, int maxTranslationLength, String urlSpaceReplacement) {
        super(translationXpath, maxTranslationLength, urlSpaceReplacement);
        this.russianToEnglishUrl=russianToEnglishUrl;
        this.englishToRussianUrl=englishToRussianUrl;
    }

    public String getRussianToEnglishUrl() {
        return russianToEnglishUrl;
    }

    public String getEnglishToRussianUrl() {
        return englishToRussianUrl;
    }
}
