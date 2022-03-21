package com.scrambler;


import com.scrambler.classes.application_info.DbClassesGetter;
import com.scrambler.classes.application_info.ScramblerGetter;
import com.scrambler.classes.db.EncryptionDbOperations;
import com.scrambler.classes.file_operations.FileOperations;
import com.scrambler.classes.file_operations.Pair;
import com.scrambler.classes.db.models.Encryption;
import com.scrambler.classes.scene_loader.SceneLoader;
import com.scrambler.classes.scramblers.CaesarAndTranslationScrambler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MainController {
    private final CaesarAndTranslationScrambler scrambler = ScramblerGetter.getScrambler();
    private final EncryptionDbOperations encryptionDbOperations = DbClassesGetter.getInstance().getEncryptionOperations();
    private String inputFilePath = null;
    private String outputFilePath = null;

    @FXML
    private Label inputFilePathLabel;

    @FXML
    private Label outputFilePathLabel;

    @FXML
    private Label errorLabel;

    @SuppressWarnings("unused")
    @FXML
    void onChoseInputButtonClick(ActionEvent event) {
        File chosenFile = receiveDialogFile("Input file");
        if(chosenFile!=null){
            inputFilePath=chosenFile.getPath();
            inputFilePathLabel.setText(inputFilePath);
        }
    }

    @SuppressWarnings("unused")
    @FXML
    void onChoseOutputButtonClick(ActionEvent event) {
        File chosenFile = receiveDialogFile("Output file");
        if(chosenFile!=null){
            outputFilePath=chosenFile.getPath();
            outputFilePathLabel.setText(outputFilePath);
        }
    }


    private File receiveDialogFile(String dialogTitle){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter fileExtensions = new FileChooser.ExtensionFilter(
                        "text files", "*.txt");
        fileChooser.getExtensionFilters().add(fileExtensions);
        fileChooser.setTitle(dialogTitle);
        Stage stage = SceneLoader.findControlStage(errorLabel);
        return fileChooser.showOpenDialog(stage);
    }

    @FXML
    @SuppressWarnings("unused")
    void decrypt(ActionEvent event) {
        Pair<Boolean,String> result= FileOperations.tryReadFile(inputFilePath);
        boolean isReadCorrectly = result.getFirst();
        if(isReadCorrectly){
            String decrypted = scrambler.decode(result.getSecond());
           if(FileOperations.tryWriteToFile(outputFilePath, decrypted)){
               errorLabel.setText("error writing to output file");
           }
        }
        else{
            errorLabel.setText("error reading from file");
        }
    }

    @SuppressWarnings("unused")
    @FXML
    void encrypt(ActionEvent event) {
        Pair<Boolean,String> result= FileOperations.tryReadFile(inputFilePath);
        boolean isReadCorrectly = result.getFirst();
        if(isReadCorrectly){
            String encrypted = scrambler.encode(result.getSecond());
            Encryption encryption = new Encryption(DbClassesGetter.getInstance().getCurrentProfile().getId(), encrypted);
            encryptionDbOperations.addEncryption(encryption);
            if(FileOperations.tryWriteToFile(outputFilePath, encrypted)){
                errorLabel.setText("error writing to output file");
            }
        }
        else{
            errorLabel.setText("error reading from file");
        }
    }

    @SuppressWarnings("unused")
    @FXML
    void switchToProfileView(MouseEvent event) {
        Stage stage = SceneLoader.findControlStage(errorLabel);
        SceneLoader.loadProfileView(stage);
    }


}
