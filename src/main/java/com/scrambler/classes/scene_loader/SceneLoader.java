package com.scrambler.classes.scene_loader;

import com.scrambler.ScramblerApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SceneLoader {
    private static final Logger LOGGER = Logger.getLogger(SceneLoader.class.getName());
    private static final Class<?> APPLICATION_CLASS = ScramblerApplication.class;
    private static final String MAIN_VIEW_NAME = "MainView.fxml";
    private static final String PROFILE_VIEW_NAME = "ProfileView.fxml";

    private SceneLoader(){}

    public static void loadMainView(Stage stage){
        loadScene(stage,MAIN_VIEW_NAME);
    }

    public static void loadProfileView(Stage stage){
        loadScene(stage,PROFILE_VIEW_NAME);
    }

    private static void loadScene(Stage stage, String sceneViewName){
        try {
            URL url = Objects.requireNonNull(APPLICATION_CLASS.getResource(sceneViewName));
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch(IOException exception){
            LOGGER.log(Level.SEVERE,"could not load page",exception);
        }
    }

    public static Stage findControlStage(Control control){
        return (Stage)control.getScene().getWindow();
    }
}
