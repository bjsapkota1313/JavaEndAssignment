package Controllers;

import Model.Member;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.Comparator;


public class AddMemberDialogueController {
    @FXML
    private TextField txtFieldFirstName,txtFieldLastName;
    @FXML
    private DatePicker dateOfBirthPicker;
    @FXML
    private Label lblError;
    private final ObservableList<Member> members;

    public AddMemberDialogueController(ObservableList<Member> members) {
        this.members = members;
    }
    private int getHighestIdentifier(){
        return Collections.max(members, Comparator.comparing(s -> s.getIdentifier())).getIdentifier()+1; // getting the member having the highest id and then adding 1 into it
    }
    private  void addMember (){
       try{
           members.add( new Member(dateOfBirthPicker.getValue() == null
                   ? LocalDate.parse(dateOfBirthPicker.getEditor().getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                   : dateOfBirthPicker.getValue(),txtFieldFirstName.getText(),txtFieldLastName.getText(),getHighestIdentifier()));
       }
       catch(DateTimeParseException dateTimeParseException) {
           lblError.setText("Invalid Date ");
       }
    }
    @FXML
    private void onBtnAddMemberClicked(ActionEvent event){
        addMember();
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();


    }
    @FXML
    private void onBtnAddMemberCancelClicked(ActionEvent event){
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
