package Controllers;

import Model.*;
import Model.Exception.EmptyFieldException;
import com.exam.javaendassignment.CloserAndLoader.StageCloser;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.Collections;
import java.util.Comparator;

public class AddItemDialogueController {
    @FXML
    private TextField txtFieldItemTitle,txtFieldAuthorFirstName,txtFieldAuthorLastName;
    @FXML
    private Label lblError;
    private final ObservableList<Book> bookList;

    public AddItemDialogueController(ObservableList<Book> bookList) {
        this.bookList = bookList;
    }
    @FXML
    public void onBtnAddItemClicked(ActionEvent event) {
        try {
            // when ever new book is added , it is available to lend
            bookList.add(new Book(getTextFieldText( txtFieldItemTitle),getHighestItemCode(), Availability.Yes,new Author(getTextFieldText(txtFieldAuthorFirstName) , getTextFieldText(txtFieldAuthorLastName))));
            new StageCloser().closeStageByEvent(event);
        }
        catch (EmptyFieldException exp) {
            lblError.setText(exp.getMessage());
        }
    }
    @FXML
    public void onBtnCancelClicked(ActionEvent event) {
        new StageCloser().closeStageByEvent(event);
    }
    private int getHighestItemCode(){
        return Collections.max(bookList, Comparator.comparing(LibraryItem::getItemCode)).getItemCode()+1; // getting the member having the highest item code in booklist and then adding 1 into it
    }

    private String getTextFieldText(TextField textField){
        if(!textField.getText().isEmpty()){
            return textField.getText();
        }
        throw new EmptyFieldException( textField.getPromptText() + " Field  is empty");
    }


}
