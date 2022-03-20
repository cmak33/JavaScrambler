package com.scrambler.classes.scramblers;

import com.scrambler.classes.translators.RussianEnglishTranslator;

public class CaesarAndTranslationScrambler extends CaesarScrambler{
    private  final RussianEnglishTranslator translator;

    public CaesarAndTranslationScrambler(RussianEnglishTranslator translator,int shift) {
        super(shift);
        this.translator = translator;
    }

    @Override
    public String encode(String str){
        String translation = translator.translateFromRussianToEnglish(str);
        return super.encode(translation);
    }

    @Override
    public String decode(String str){
        String decodedString =super.decode(str);
        return translator.translateFromEnglishToRussian(decodedString);
    }
}
