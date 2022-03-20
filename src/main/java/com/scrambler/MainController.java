package com.scrambler;


import com.scrambler.classes.db.ApplicationInfo;
import com.scrambler.classes.file_operations.FileOperations;
import com.scrambler.classes.file_operations.Pair;
import com.scrambler.classes.db.models.Encryption;
import com.scrambler.classes.scramblers.CaesarAndTranslationScrambler;
import com.scrambler.classes.translators.parser.Parser;
import com.scrambler.classes.translators.web_drivers.ChromeDriverCreatorWithOptions;
import com.scrambler.classes.translators.RussianEnglishTranslationData;
import com.scrambler.classes.translators.RussianEnglishTranslator;
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
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;

public class MainController {
    private final String profileViewName = "ProfileView.fxml";
    private final ChromeOptions options =new ChromeOptions();
    private final ChromeDriverCreatorWithOptions driverCreator = new ChromeDriverCreatorWithOptions("C:\\Users\\User\\Desktop\\chromedriver.executor",options);
    private final Parser parser = new Parser(driverCreator,30);
    private final RussianEnglishTranslationData translationData = new RussianEnglishTranslationData("https://translate.google.gr/?sl=ru&tl=en&text=","https://translate.google.gr/?sl=en&tl=ru&text=","//*[@id='yDmH0d']/c-wiz/div/div[2]/c-wiz/div[2]/c-wiz/div[1]/div[2]/div[3]/c-wiz[2]/div[6]/div/div[1]/span[1]/span/span",5000,"%20");
    private final RussianEnglishTranslator translator = new RussianEnglishTranslator(translationData,parser);
    private final CaesarAndTranslationScrambler scrambler = new CaesarAndTranslationScrambler(translator,15);
    private String inputFilePath = null;
    private String outputFilePath = null;


    public MainController(){
        options.setHeadless(true);
    }

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
        Pair<Boolean,String> result= FileOperations.tryReadFile(inputFilePath);
        if(result.getFirst()){
            String decrypted = scrambler.decode(result.getSecond());
           if(FileOperations.tryWriteToFile(outputFilePath, decrypted)){
               errorLabel.setText("error writing to output file");
           }
        }
        else{
            errorLabel.setText("error reading from file");
        }
    }

    @FXML
    void encrypt(ActionEvent event) {
        Pair<Boolean,String> result= FileOperations.tryReadFile(inputFilePath);
        if(result.getFirst()){
            String encrypted = scrambler.encode(result.getSecond());
            ApplicationInfo.getEncryptionsDbOperations().addEncryption(new Encryption(ApplicationInfo.getCurrentProfile().getId(), encrypted));
            if(FileOperations.tryWriteToFile(outputFilePath, encrypted)){
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
