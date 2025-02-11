package com.scrambler.controllers;

import com.scrambler.classes.application_info.DbClassesGetter;
import com.scrambler.classes.db.EncryptionDbOperations;
import com.scrambler.classes.db.ProfileDbOperations;
import com.scrambler.classes.db.models.Encryption;
import com.scrambler.classes.db.models.Profile;
import com.scrambler.classes.scene_loader.SceneLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public class ProfileController {
    private static final ProfileDbOperations profileDbOperations = DbClassesGetter.getInstance().getProfileOperations();
    private static final EncryptionDbOperations encryptionDbOperations = DbClassesGetter.getInstance().getEncryptionOperations();
    private static final Profile currentProfile = DbClassesGetter.getInstance().getCurrentProfile();

    @FXML
    private TextField nicknameEdit;

    @FXML
    private TextField passwordEdit;

    @FXML
    private ListView<String> encryptionsListView;

    @SuppressWarnings("unused")
    @FXML
    void onAlterProfileButtonClick(ActionEvent event) {
        if(isPossibleToAlterProfile()){
            CompletableFuture.runAsync(()->updateProfile(nicknameEdit.getText(),passwordEdit.getText()));
        }
    }

    private void updateProfile(String nickname,String password){
        profileDbOperations.updateProfile(currentProfile.getId(),nickname,password);
        currentProfile.setNickname(nickname);
        currentProfile.setPassword(password);
    }

    private boolean isPossibleToAlterProfile(){
        return currentProfile.getNickname().equals(nicknameEdit.getText())
                || profileDbOperations.isNicknameAvailable(nicknameEdit.getText());
    }

    @SuppressWarnings("unused")
    @FXML
    void switchToMainView(MouseEvent event) {
        Stage stage = SceneLoader.findControlStage(nicknameEdit);
        SceneLoader.loadMainView(stage);
    }


    @FXML
    private void initialize() {
        List<Encryption> encryptionList =encryptionDbOperations.getProfileEncryptionList(currentProfile.getId());
        encryptionList.forEach(encryption-> encryptionsListView.getItems().add(encryptionToString(encryption)));
    }

    private String encryptionToString(Encryption encryption){
        return encryption.getPublishDateTime().toString() + " -- "+encryption.getEncryptedText();
    }
}
