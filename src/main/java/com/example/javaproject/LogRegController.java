package com.example.javaproject;

import com.example.javaproject.Classes.Translator.Translator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class LogRegController {

    @FXML
    private Button b;

    @FXML
    protected void OnButtonClick(ActionEvent event){
        String str = Translator.translateFromEnglishToRussian("we lost gg");
        b.setText(str);
    }
}
