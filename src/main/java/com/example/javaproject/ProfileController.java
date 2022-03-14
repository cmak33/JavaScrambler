package com.example.javaproject;

import com.example.javaproject.classes.db.ApplicationInfo;
import com.example.javaproject.classes.loginAndRegister.Profile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfileController {
    private final String mainViewName = "MainView.fxml";

    @FXML
    private TextField nicknameEdit;

    @FXML
    private TextField passwordEdit;

    @FXML
    private Button alterProfileButton;

    @FXML
    private Label goBackLabel;


    @FXML
    void onAlterProfileButtonClick(ActionEvent event) {
        if(ApplicationInfo.getCurrentProfile().getNickname().equals(nicknameEdit.getText()) || ApplicationInfo.getProfileDbOperations().isNicknameAvailable(nicknameEdit.getText())){
            ApplicationInfo.getProfileDbOperations().updateProfile(ApplicationInfo.getCurrentProfile().getId(),nicknameEdit.getText(),passwordEdit.getText());
        }
    }

    @FXML
    void switchToMainView(MouseEvent event) {
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
