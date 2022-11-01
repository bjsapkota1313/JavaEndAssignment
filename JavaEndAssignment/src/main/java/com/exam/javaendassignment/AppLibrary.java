package com.exam.javaendassignment;

import Controllers.LoginViewController;
import Database.Database;
import com.exam.javaendassignment.CloserAndLoader.SceneLoader;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;


public class AppLibrary extends Application {
    private final Database database;
    private final File membersFile;
    private final File booksFile;
    private final File lentItemsFile;
    public static void main(String[] args) {
    launch();
}
    public AppLibrary() {
        database=new Database();
        membersFile = new File("src//Members.txt");
        booksFile= new File("src//Books.txt");
        lentItemsFile= new File("src//LentItems.txt");
        insertDataOnDatabase();
    }
    @Override
    public void start(Stage stage)  {
        new SceneLoader().loadScene("LoginView",new LoginViewController(database),stage,false);
    }
    @Override
    public void stop() throws IOException {
        // writing everything when application is stopped
        database.writeSerializable(membersFile,(List<Serializable>)(List<?>) database.getMembers());
        database.writeSerializable(booksFile,(List<Serializable>)(List<?>) database.getLibraryBooks());
        database.writeSerializable(lentItemsFile,(List<Serializable>)(List<?>) database.getLentItems());

    }
    private void insertDataOnDatabase() {
        ///as file is not found HardCode data will be created if not created it will throw error while getting HighestI d from List
        // members file
       if(membersFile.exists() ) {
            database.setMembersFromSerializedFile(membersFile);
        }
        else {
           database.createAndAddMembersToTheList();
        }
        // books file
        if(booksFile.exists()) {
            database.setBooksFromSerializedFile(booksFile);
        }
        else{
            database.createAndAddBookToList();
        }
        // lentItem records
        if (lentItemsFile.exists()){
            database.setLentItemsFromSerializedFile(lentItemsFile);
        }
        // no files will be lent as default

    }

}




