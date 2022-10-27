package Controllers;

import Model.Book;
import Model.Exception.EmptyFieldException;
import com.exam.javaendassignment.CloserAndLoader.StageCloser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EditItemDialogueController implements Initializable {
    private final Book selectedBook;
    @FXML
    private TextField txtFieldItemTitle,txtFieldAuthorFirstName,txtFieldAuthorLastName;
    @FXML
    private Label lblError;

    public EditItemDialogueController(Book selectedBook) {

        this.selectedBook = selectedBook;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        fillSelectedItemDetails();
    }

    @FXML
    private void onBtnUpdateClicked(ActionEvent event) {
        try {
            selectedBook.setName(getTextFieldText(txtFieldItemTitle));
            selectedBook.getAuthor().setFirstName(getTextFieldText(txtFieldAuthorFirstName));
            selectedBook.getAuthor().setLastName(getTextFieldText(txtFieldAuthorLastName));
            new StageCloser().closeStageByEvent(event);
        }
        catch (EmptyFieldException exp ) {
            lblError.setText(exp.getMessage());
        }
    }
    @FXML
    private void onBtnCancelClicked(ActionEvent event) {
        new StageCloser().closeStageByEvent(event);
    }
    private void fillSelectedItemDetails(){
        // showing current details in Prompt Text
        txtFieldItemTitle.setPromptText(selectedBook.getName());
        txtFieldAuthorFirstName.setPromptText(selectedBook.getAuthor().getFirstName());
        txtFieldAuthorLastName.setPromptText(selectedBook.getAuthor().getLastName());
    }
    private String getTextFieldText(TextField textField){
        if(!textField.getText().isEmpty()){
            return textField.getText();
        }
        throw new EmptyFieldException("Field cannot be left empty while editing Item Details");
    }


}
