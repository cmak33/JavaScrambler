package com.scrambler;

import com.scrambler.classes.db.ApplicationInfo;
import com.scrambler.classes.db.models.Encryption;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
    private ListView encryptionsListView;


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


    @FXML
    private void initialize() {
        List<Encryption> encryptionList = ApplicationInfo.getEncryptionsDbOperations().getProfileEncryptionList(ApplicationInfo.getCurrentProfile().getId());
        encryptionList.forEach(x-> {
            encryptionsListView.getItems().add(x.getPublishDateTime().toString() + " -- "+x.getEncryptedText());
        });
    }
}
