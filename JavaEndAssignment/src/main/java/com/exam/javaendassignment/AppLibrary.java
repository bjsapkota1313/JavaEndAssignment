package com.exam.javaendassignment;

import Controllers.MainWindowController;
import Database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.*;
import java.util.List;


public class AppLibrary extends Application {
    private Database database;
    private final File membersFile;
    private final File booksFile;
    private final File lentItemsFile;
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

    public AppLibrary() throws FileNotFoundException {
        database=new Database();
         membersFile = new File("src//Members.txt");
         booksFile= new File("src//Books.txt");
         lentItemsFile= new File("src//LentItems.txt");
        insertDataOnDatabase();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppLibrary.class.getResource("MainWindow.fxml"));
       fxmlLoader.setController(new MainWindowController(database));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("/css/AppStyles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();

    }
    @Override
    public void stop() throws IOException {
        // writing everything when application is stopped
        if(!database.getMembers().isEmpty()){
            database.writeSerializable(membersFile,(List<Serializable>)(List<?>) database.getMembers());
        }
        if(!database.getLibraryBooks().isEmpty()) {
            database.writeSerializable(booksFile,(List<Serializable>)(List<?>) database.getLibraryBooks());
        }
        if(!database.getLentItems().isEmpty()) {
            database.writeSerializable(lentItemsFile,(List<Serializable>)(List<?>) database.getLentItems());
        }
    }
    private void insertDataOnDatabase(){
        // members file
        if(membersFile.exists() ) {
            database.setMembersFromSerializedFile(membersFile);
        }
        else {
            database.createAndAddMembersToTheList();
        }
        // books file
        if(booksFile.exists() ) {
            database.setBooksFromSerializedFile(booksFile);
        }
        else{
            database.createAndAddBookToList();
        }
        // lentItem records
        if (lentItemsFile.exists()){
            database.setLentItemsFromSerializedFile(lentItemsFile);
        }
            // no files will be lent
    }

}




