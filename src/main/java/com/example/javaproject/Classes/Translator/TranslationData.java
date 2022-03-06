package com.example.javaproject.Classes.Translator;


public abstract class TranslationData {
    private  final String translationXpath;
    private  final String textPartsRegex;
    private  final String urlSpaceReplacement;

    protected TranslationData(String translationXpath,int maxTranslationLength,String urlSpaceReplacement){
        this.translationXpath = translationXpath;
        this.textPartsRegex = String.format(".{%d}", maxTranslationLength);
        this.urlSpaceReplacement = urlSpaceReplacement;
    }

    public String getTranslationXpath() {
        return translationXpath;
    }

    public String getTextPartsRegex() {
        return textPartsRegex;
    }

    public String getUrlSpaceReplacement() {
        return urlSpaceReplacement;
    }




}
