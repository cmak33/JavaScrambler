package com.example.javaproject.Classes.Translator;

public class TranslationData {
    private  final String translationPath;
    private  final String textPartsRegex;
    private  final String urlSpaceReplacement;

    public TranslationData(String translationXpath,int maxTranslationLength,String urlSpaceReplacement){
        this.translationPath = translationXpath;
        this.textPartsRegex = String.format(".{%d}", maxTranslationLength);
        this.urlSpaceReplacement = urlSpaceReplacement;
    }

    public String getTranslationPath() {
        return translationPath;
    }

    public String getTextPartsRegex() {
        return textPartsRegex;
    }

    public String getUrlSpaceReplacement() {
        return urlSpaceReplacement;
    }


}
