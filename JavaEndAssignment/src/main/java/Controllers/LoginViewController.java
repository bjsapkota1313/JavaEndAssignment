package Controllers;

import Database.Database;
import Model.User;
import com.exam.javaendassignment.AppLibrary;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginViewController {
    @FXML
    public Label lblDisplayError;
    @FXML
    public AnchorPane anchorPane;
    private  final Database database;
    private  User loggedUser;

    private  final int SET_DIGIT_BIT = 0b100;
    private  final int SET_LETTER_BIT = 0b010;
    private  final int SET_SPECIAL_BIT = 0b001;
    @FXML
    private PasswordField pswdFieldPassword;
    @FXML
    private TextField txtFieldUserName;
    @FXML
    private Button btnLogin;

    public LoginViewController(Database database) {
        this.database = database;
    }

    @FXML
    protected void onLoginButtonClicked(){
         loggedUser=database.loginWithCredentials(txtFieldUserName.getText(), pswdFieldPassword.getText());
        if(loggedUser==null){
            lblDisplayError.setText("Invalid username or password combination");}
        else {
            loadScene("MainWindow.fxml", new MainWindowController(database,loggedUser));
        }

    }
    protected boolean isPasswordValid(String password) {
        byte values = 0b000;

        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                values = (byte) (values | SET_DIGIT_BIT);
            } else if (Character.isLetter(c)) {
                values = (byte) (values | SET_LETTER_BIT);
            } else {
                values = (byte) (values | SET_SPECIAL_BIT);
            }
        }
        return values == 7;
    }
    @FXML
    public void onPasswordTextChange(StringProperty observable, String oldValue, String newValue) {
        lblDisplayError.setText("");
        btnLogin.setDisable(!isPasswordValid(newValue));
    }
    public void loadScene(String name, Object controller) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(AppLibrary.class.getResource(name));
            fxmlLoader.setController(controller);
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(getClass().getResource("/css/AppStyles.css").toExternalForm());
            Stage window = (Stage) anchorPane.getScene().getWindow();
            window.setTitle(name.replace(".fxml", ""));
            window.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void onTextFieldUsernameChanged(StringProperty observable, String oldValue, String newValue){
        lblDisplayError.setText("");
    }


}