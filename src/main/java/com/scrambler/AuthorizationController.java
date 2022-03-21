package com.scrambler;

import com.scrambler.classes.application_info.DbClassesGetter;
import com.scrambler.classes.db.ProfileDbOperations;
import com.scrambler.classes.db.models.Profile;
import com.scrambler.classes.scene_loader.SceneLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class AuthorizationController {
    private static final ProfileDbOperations profileDbOperations = DbClassesGetter.getInstance().getProfileOperations();

    @FXML
    private TextField nicknameEdit;

    @FXML
    private TextField passwordEdit;

    @FXML
    private Label errorLabel;

    @SuppressWarnings("unused")
    @FXML
    void onLoginButtonClick(ActionEvent event) {
        if(profileDbOperations.isProfileExisting(nicknameEdit.getText(),passwordEdit.getText())){
            int id = profileDbOperations.findIdByNickname(nicknameEdit.getText());
            Profile profile = new Profile(nicknameEdit.getText(),passwordEdit.getText(),id);
            DbClassesGetter.getInstance().setCurrentProfile(profile);
            switchToMainView();
        }
        else{
            errorLabel.setText("no such profile");
        }
    }

    @SuppressWarnings("unused")
    @FXML
    void onRegisterButtonClick(ActionEvent event) {
        if(profileDbOperations.isNicknameAvailable(nicknameEdit.getText())){
            Profile profile=profileDbOperations.createProfile(nicknameEdit.getText(),passwordEdit.getText());
            DbClassesGetter.getInstance().setCurrentProfile(profile);
            switchToMainView();
        }
        else{
            errorLabel.setText("nickname is not available");
        }
    }

    private void switchToMainView(){
        Stage stage = SceneLoader.findControlStage(nicknameEdit);
        SceneLoader.loadMainView(stage);
    }


}
