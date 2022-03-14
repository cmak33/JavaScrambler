package com.example.javaproject;

import com.example.javaproject.classes.db.ApplicationInfo;
import com.example.javaproject.classes.db.ProfileDbOperations;
import com.example.javaproject.classes.fileOperations.FileReaderWriter;
import com.example.javaproject.classes.loginAndRegister.Profile;
import com.example.javaproject.classes.scramblers.CaesarAndTranslationScrambler;
import com.example.javaproject.classes.translators.ChromeDriverCreator;
import com.example.javaproject.classes.translators.RussianEnglishTranslationData;
import com.example.javaproject.classes.translators.RussianEnglishTranslator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class LogRegController {
    private final String mainViewName = "MainView.fxml";
    private final ChromeDriverCreator driverCreator = new ChromeDriverCreator("C:\\Users\\User\\Desktop\\chromedriver.exe");
    private final RussianEnglishTranslationData translationData = new RussianEnglishTranslationData("https://translate.google.gr/?sl=ru&tl=en&text=","https://translate.google.gr/?sl=en&tl=ru&text=","//*[@id=\"yDmH0d\"]/c-wiz/div/div[2]/c-wiz/div[2]/c-wiz/div[1]/div[2]/div[3]/c-wiz[2]/div[6]/div/div[1]/span[1]/span/span",5000,"%20");
    private final RussianEnglishTranslator translator = new RussianEnglishTranslator(translationData,driverCreator);
    private final CaesarAndTranslationScrambler scrambler = new CaesarAndTranslationScrambler(translator,10);

    @FXML
    private TextField nicknameEdit;

    @FXML
    private TextField passwordEdit;

    @FXML
    private Label errorLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    void onLoginButtonClick(ActionEvent event) {
        if(ApplicationInfo.getProfileDbOperations().profileExists(nicknameEdit.getText(),passwordEdit.getText())){
            int id = ApplicationInfo.getProfileDbOperations().getIdByNickname(nicknameEdit.getText());
            Profile profile = new Profile(nicknameEdit.getText(),passwordEdit.getText(),id);
            ApplicationInfo.setCurrentProfile(profile);
            switchToMainView();
        }
        else{
            errorLabel.setText("no such profile");
        }
    }

    @FXML
    void onRegisterButtonClick(ActionEvent event) {
        if(ApplicationInfo.getProfileDbOperations().isNicknameAvailable(nicknameEdit.getText())){
            Profile profile=ApplicationInfo.getProfileDbOperations().createProfile(nicknameEdit.getText(),passwordEdit.getText());
            ApplicationInfo.setCurrentProfile(profile);
            switchToMainView();
        }
        else{
            errorLabel.setText("nickname is not available");
        }
    }

    private void switchToMainView(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource(mainViewName));
            Stage stage = getStage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch(IOException exception){
            System.err.println(exception.getMessage());
        }
    }


    private Stage getStage(){
        return  (Stage)nicknameEdit.getScene().getWindow();
    }

}
