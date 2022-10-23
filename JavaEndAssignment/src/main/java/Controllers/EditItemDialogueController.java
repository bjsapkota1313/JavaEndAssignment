package Controllers;

import Model.Book;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class EditItemDialogueController implements Initializable {
    private Book selectedBook;
    @FXML
    private TextField txtFieldItemTitle,txtFieldAuthorFirstName,txtFieldAuthorLastName;

    public EditItemDialogueController(Book selectedBook) {
        this.selectedBook = selectedBook;
    }

    @FXML
    private void onBtnUpdateClicked(ActionEvent event) {
        updateBook();
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
    @FXML
    private void onBtnCancelClicked(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
    private void updateBook() {
      selectedBook.setName(txtFieldItemTitle.getText());
        selectedBook.getAuthor().setFirstName(txtFieldAuthorFirstName.getText());
        selectedBook.getAuthor().setLastName(txtFieldAuthorLastName.getText());
    }
    private void fillSelectedItemDetails(){
        // showing current details in Prompt Text
        txtFieldItemTitle.setPromptText(selectedBook.getName());
        txtFieldAuthorFirstName.setPromptText(selectedBook.getAuthor().getFirstName());
        txtFieldAuthorLastName.setPromptText(selectedBook.getAuthor().getLastName());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillSelectedItemDetails();
    }
}
