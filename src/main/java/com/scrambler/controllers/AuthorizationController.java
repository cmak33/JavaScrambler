package com.scrambler.controllers;

import com.scrambler.classes.application_info.DbClassesGetter;
import com.scrambler.classes.db.ProfileDbOperations;
import com.scrambler.classes.db.models.Profile;
import com.scrambler.classes.scene_loader.SceneLoader;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



public class AuthorizationController {
    private static final ProfileDbOperations profileDbOperations = DbClassesGetter.getInstance().getProfileOperations();
    private boolean isAuthorizing = false;
    private final Task<Boolean> loginTask = new Task<>() {
        @Override
        protected Boolean call() {
            return tryLogin(nicknameEdit.getText(), passwordEdit.getText());
        }
        private boolean tryLogin(String nickname,String password){
            boolean isProfileExisting = profileDbOperations.isProfileExisting(nickname,password);
            if(isProfileExisting) {
                int id = profileDbOperations.findIdByNickname(nickname);
                Profile profile = new Profile(nickname, password, id);
                DbClassesGetter.getInstance().setCurrentProfile(profile);
            }
            return isProfileExisting;
        }
    };
    private final Task<Boolean> registerTask = new Task<>() {
        @Override
        protected Boolean call() {
            return tryRegister(nicknameEdit.getText(), passwordEdit.getText());
        }

        private boolean tryRegister(String nickname,String password){
            boolean isNicknameAvailable = profileDbOperations.isNicknameAvailable(nickname);
            if(isNicknameAvailable){
                Profile profile=profileDbOperations.createProfile(nickname,password);
                DbClassesGetter.getInstance().setCurrentProfile(profile);
            }
            return isNicknameAvailable;
        }
    };

    @FXML
    private TextField nicknameEdit;

    @FXML
    private TextField passwordEdit;

    @FXML
    private Label errorLabel;


    private void startAuthorizationTask(Task<?> task){
        if(!isAuthorizing){
            isAuthorizing = true;
            new Thread(task).start();
        }
    }

    @SuppressWarnings("unused")
    @FXML
    void onLoginButtonClick(ActionEvent event) {
        startAuthorizationTask(loginTask);
    }




    private void afterTryLogin(boolean isLoggedSuccessfully){
        if(isLoggedSuccessfully){
            switchToMainView();
        }
        else{
            errorLabel.setText("no such profile");
        }
        isAuthorizing = false;
    }

    @SuppressWarnings("unused")
    @FXML
    void onRegisterButtonClick(ActionEvent event) {
        startAuthorizationTask(registerTask);
    }

    private void afterTryRegister(boolean isRegisteredSuccessfully){
        if(isRegisteredSuccessfully){
            switchToMainView();
        }
        else{
            errorLabel.setText("nickname is not available");
        }
        isAuthorizing = false;
    }

    private void switchToMainView(){
        Stage stage = SceneLoader.findControlStage(nicknameEdit);
        SceneLoader.loadMainView(stage);
    }

    @FXML
    public void initialize(){
        loginTask.setOnSucceeded(stateEvent -> afterTryLogin(loginTask.getValue()));
        registerTask.setOnSucceeded(stateEvent -> afterTryRegister(registerTask.getValue()));
    }


}
