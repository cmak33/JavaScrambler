package com.example.javaproject;

import com.example.javaproject.Classes.Translator.RussianEnglishTranslationData;
import com.example.javaproject.Classes.Translator.RussianEnglishTranslator;
import com.example.javaproject.Classes.Translator.Translator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import us.codecraft.xsoup.Xsoup;

import java.util.List;

public class LogRegController {

    @FXML
    private Button b;

    @FXML
    protected void OnButtonClick(ActionEvent event){
        RussianEnglishTranslationData translationData = new RussianEnglishTranslationData("https://www.reverso.net/%D0%BF%D0%B5%D1%80%D0%B5%D0%B2%D0%BE%D0%B4-%D1%82%D0%B5%D0%BA%D1%81%D1%82%D0%B0#sl=rus&tl=eng&text=","https://www.reverso.net/%D0%BF%D0%B5%D1%80%D0%B5%D0%B2%D0%BE%D0%B4-%D1%82%D0%B5%D0%BA%D1%81%D1%82%D0%B0#sl=eng&tl=rus&text=","/html/body/app-root/app-translation/div/app-translation-box/div/div/div/div/div/app-context-box/div/div/app-context-item/div/div/div/div/span",2000,"%2520");
        RussianEnglishTranslator translator = new RussianEnglishTranslator(translationData);
        String translation = translator.translateFromRussianToEnglish("реально работает ");

    }
}
