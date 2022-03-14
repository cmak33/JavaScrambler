package com.example.javaproject;


import com.example.javaproject.classes.db.ApplicationInfo;
import com.example.javaproject.classes.fileOperations.FileReaderWriter;
import com.example.javaproject.classes.fileOperations.Pair;
import com.example.javaproject.classes.loginAndRegister.Encryption;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainController {
    private final String profileViewName = "ProfileView.fxml";
    private final ChromeDriverCreator driverCreator = new ChromeDriverCreator("C:\\Users\\User\\Desktop\\chromedriver.exe");
    private final RussianEnglishTranslationData translationData = new RussianEnglishTranslationData("https://translate.google.gr/?sl=ru&tl=en&text=","https://translate.google.gr/?sl=en&tl=ru&text=","html > body > c-wiz > div > div:eq(1) > c-wiz > div:eq(1) > c-wiz > div > div:eq(1) > div:eq(2) > c-wiz:eq(1) > div:eq(5) > div > div > span > span > span",5000,"%20");
    private final RussianEnglishTranslator translator = new RussianEnglishTranslator(translationData,driverCreator);
    private final CaesarAndTranslationScrambler scrambler = new CaesarAndTranslationScrambler(translator,15);
    private String inputFilePath = null;
    private String outputFilePath = null;

    @FXML
    private Button choseInputFileButton;

    @FXML
    private Button choseOutputFileButton;

    @FXML
    private Label inputFilePathLabel;

    @FXML
    private Label outputFilePathLabel;

    @FXML
    private Button encryptButton;

    @FXML
    private Button decryptButton;

    @FXML
    private Label profileLabel;

    @FXML
    private Label errorLabel;

    @FXML
    void onChoseInputButtonClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter fileExtensions =
                new FileChooser.ExtensionFilter(
                        "text files", "*.txt");

        fileChooser.getExtensionFilters().add(fileExtensions);
        fileChooser.setTitle("Input file");
        File chosenFile = fileChooser.showOpenDialog(getStage());
        if(chosenFile!=null){
            inputFilePath=chosenFile.getPath();
            inputFilePathLabel.setText(inputFilePath);
        }
    }

    @FXML
    void onChoseOutputButtonClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter fileExtensions =
                new FileChooser.ExtensionFilter(
                        "text files", "*.txt");

        fileChooser.getExtensionFilters().add(fileExtensions);
        fileChooser.setTitle("Output file");
        File chosenFile = fileChooser.showOpenDialog(getStage());
        if(chosenFile!=null){
            outputFilePath=chosenFile.getPath();
            outputFilePathLabel.setText(outputFilePath);
        }
    }

    @FXML
    void decrypt(ActionEvent event) {
        Pair<Boolean,String> result= FileReaderWriter.tryReadFile(inputFilePath);
        if(result.getFirst()){
            String decrypted = scrambler.decode(result.getSecond());
           if(!FileReaderWriter.tryWriteToFile(outputFilePath,decrypted)){
               errorLabel.setText("error writing to output file");
           }
        }
        else{
            errorLabel.setText("error reading from file");
        }
    }

    @FXML
    void encrypt(ActionEvent event) {
        Pair<Boolean,String> result= FileReaderWriter.tryReadFile(inputFilePath);
        if(result.getFirst()){
            String encrypted = scrambler.encode(result.getSecond());
            ApplicationInfo.getEncryptionsDbOperations().addEncryption(new Encryption(ApplicationInfo.getCurrentProfile().getId(), encrypted));
            if(!FileReaderWriter.tryWriteToFile(outputFilePath,encrypted)){
                errorLabel.setText("error writing to output file");
            }
        }
        else{
            errorLabel.setText("error reading from file");
        }
    }

    @FXML
    void switchToProfileView(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(profileViewName));
            Stage stage = getStage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch(IOException exception){
            System.err.println(exception.getMessage());
        }
    }

    private Stage getStage(){
        return  (Stage)choseInputFileButton.getScene().getWindow();
    }

}
