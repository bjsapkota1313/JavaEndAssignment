package com.exam.javaendassignment;

import Controllers.MainWindowController;
import Database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;



public class AppLibrary extends Application {
    public static void main(String[] args) {
    launch();
}
/*
  @Override
    public void start(Stage stage) throws IOException {
       FXMLLoader fxmlLoader = new FXMLLoader(AppLibrary.class.getResource("LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

*/
   // To Work with MainWindow

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppLibrary.class.getResource("MainWindow.fxml"));
       fxmlLoader.setController(new MainWindowController(new Database()));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("/css/AppStyles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();

    }
    @Override
    public void stop(){
        System.out.println("Stage is closing");
        // Save file
       
    }



}
