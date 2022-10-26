package com.exam.javaendassignment;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class SceneLoader {
    public void loadScene(String name, Object controller,Stage window ,boolean isDialogBox) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(AppLibrary.class.getResource(name+".fxml"));
            fxmlLoader.setController(controller);
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(getClass().getResource("/css/AppStyles.css").toExternalForm());
            window.setTitle(name);
            window.setScene(scene);
            if(isDialogBox){
                window.showAndWait();
            }
            else{
                window.show();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
