package com.example.javaproject.Classes.ScramblerClasses;

import com.example.javaproject.Classes.Translator.RussianEnglishTranslator;

public class CaesarAndTranslationScrambler extends CaesarScrambler{
    private  final RussianEnglishTranslator translator;

    public CaesarAndTranslationScrambler(RussianEnglishTranslator translator,int shift) {
        super(shift);
        this.translator = translator;
    }

    @Override
    public String encode(String str){
        return translator.translateFromRussianToEnglish(super.encode(str));
    }

    @Override
    public String decode(String str){
        return super.decode(translator.translateFromEnglishToRussian(str));
    }
}
