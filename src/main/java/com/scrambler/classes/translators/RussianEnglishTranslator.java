package com.scrambler.classes.translators;

import com.scrambler.classes.translators.parser.Parser;

public class RussianEnglishTranslator extends Translator<RussianEnglishTranslationData>{


    public RussianEnglishTranslator(RussianEnglishTranslationData translationData, Parser parser) {
        super(translationData, parser);
    }

    public String translateFromEnglishToRussian(String text){
        return translate(translationData.getEnglishToRussianUrl(),text);
    }

    public String translateFromRussianToEnglish(String text){
        return translate(translationData.getRussianToEnglishUrl(),text);
    }
}
