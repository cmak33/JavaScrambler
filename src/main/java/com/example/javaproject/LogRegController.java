package com.example.javaproject;

import com.example.javaproject.Classes.FileOperations.FileReaderWriter;
import com.example.javaproject.Classes.FileOperations.Pair;
import com.example.javaproject.Classes.Translator.ChromeDriverCreator;
import com.example.javaproject.Classes.Translator.RussianEnglishTranslationData;
import com.example.javaproject.Classes.Translator.RussianEnglishTranslator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

import java.io.File;

public class LogRegController {

    private String fileToEncodePath ="";
    private String decodeFilePath = "C:\\Users\\User\\Desktop\\Новый текстовый документ.txt";

    @FXML
    private Button translateButton;

    @FXML
    protected void OnTranslateButtonClick(ActionEvent event){
        Pair<Boolean,String> textToEncode = FileReaderWriter.tryReadFile(fileToEncodePath);
        if(textToEncode.getFirst()) {
            RussianEnglishTranslationData translationData =new RussianEnglishTranslationData("https://translate.google.gr/?sl=ru&tl=en&text=", "https://translate.google.gr/?sl=en&tl=ru&text=", "//*[@id=\"yDmH0d\"]/c-wiz/div/div[2]/c-wiz/div[2]/c-wiz/div[1]/div[2]/div[3]/c-wiz[2]/div[6]/div/div[1]/span[1]/span/span", 2000, "%20");
            ChromeDriverCreator driverCreator = new ChromeDriverCreator("C:\\Users\\User\\Desktop\\chromedriver.exe");
            RussianEnglishTranslator translator = new RussianEnglishTranslator(translationData,driverCreator);
            String translation = translator.translateFromRussianToEnglish(textToEncode.getSecond());
            FileReaderWriter.tryWriteToFile(decodeFilePath,translation);
        }
    }

    @FXML
    protected void OnChooseFileButtonClick(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(translateButton.getScene().getWindow());
        if(file!=null){
            fileToEncodePath = file.getPath();
        }
    }
}
