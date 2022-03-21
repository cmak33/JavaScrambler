package com.scrambler.classes.application_info;

import com.scrambler.classes.file_operations.FileOperations;
import com.scrambler.classes.scramblers.CaesarAndTranslationScrambler;
import com.scrambler.classes.translators.RussianEnglishTranslationData;
import com.scrambler.classes.translators.RussianEnglishTranslator;
import com.scrambler.classes.translators.parser.Parser;
import com.scrambler.classes.translators.web_drivers.ChromeDriverCreatorWithOptions;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Properties;

public class ScramblerGetter {
    private static final String SCRAMBLER_PROPERTIES_PATH = "properties/scrambler.properties";
    private static CaesarAndTranslationScrambler scrambler;

    private ScramblerGetter(){}

    public static CaesarAndTranslationScrambler getScrambler(){
        if(scrambler==null){
            scrambler=createScrambler();
        }
        return scrambler;
    }

    private static CaesarAndTranslationScrambler createScrambler(){
        Properties properties = FileOperations.loadProperties(SCRAMBLER_PROPERTIES_PATH);
        RussianEnglishTranslator translator = createTranslator(properties);
        int shift = Integer.parseInt(properties.getProperty("shift"));
        return new CaesarAndTranslationScrambler(translator,shift);
    }

    private static RussianEnglishTranslator createTranslator(Properties properties){
        RussianEnglishTranslationData translationData = createTranslationData(properties);
        Parser parser = createParser(properties);
        return new RussianEnglishTranslator(translationData,parser);
    }

    private static RussianEnglishTranslationData createTranslationData(Properties properties){
        String russianToEnglishUrl = properties.getProperty("russianToEnglishUrl");
        String englishToRussianUrl= properties.getProperty("englishToRussianUrl");
        String translationXPath= properties.getProperty("translationXPath");
        int maxTranslationLength = Integer.parseInt(properties.getProperty("maxTranslationLength"));
        String urlSpaceReplacement= properties.getProperty("urlSpaceReplacement");
        return new RussianEnglishTranslationData(russianToEnglishUrl,englishToRussianUrl,translationXPath,maxTranslationLength,urlSpaceReplacement);
    }

    private static Parser createParser(Properties properties){
        ChromeOptions options =new ChromeOptions();
        options.setHeadless(true);
        String driverPath = properties.getProperty("chromeDriverPath");
        ChromeDriverCreatorWithOptions driverCreator = new ChromeDriverCreatorWithOptions(driverPath,options);
        int maxPageWaitTime = Integer.parseInt(properties.getProperty("maxPageWaitTime"));
        return new Parser(driverCreator,maxPageWaitTime);
    }


}
