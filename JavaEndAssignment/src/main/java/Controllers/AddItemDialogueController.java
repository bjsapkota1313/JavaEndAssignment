package Controllers;

import Model.Author;
import Model.Book;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Collections;
import java.util.Comparator;

public class AddItemDialogueController {
    @FXML
    private TextField txtFieldItemTitle,txtFieldAuthorFirstName,txtFieldAuthorLastName;
    private ObservableList<Book> bookList;

    public AddItemDialogueController(ObservableList<Book> bookList) {
        this.bookList = bookList;
    }
    @FXML
    public void onBtnAddItemClicked(ActionEvent event) {
        addItem();
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
    @FXML
    public void onBtnCancelClicked(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
    private int getHighestItemCode(){
        return Collections.max(bookList, Comparator.comparing(s -> s.getItemCode())).getItemCode()+1; // getting the member having highest item code in booklist and then adding 1 into it
    }
    private void addItem(){
        // when ever new book is added , it is available to lend
        bookList.add(new Book(txtFieldItemTitle.getText(),getHighestItemCode(),true,new Author(txtFieldAuthorFirstName.getText(),txtFieldAuthorLastName.getText())));
    }
}
