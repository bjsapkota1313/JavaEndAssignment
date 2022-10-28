package Controllers;

import Model.Exception.EmptyFieldException;
import Model.Member;
import com.exam.javaendassignment.CloserAndLoader.StageCloser;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
        return Collections.max(members, Comparator.comparing(Member::getIdentifier)).getIdentifier()+1; // getting the member having the highest id and then adding 1 into it
    }
    @FXML
    private void onBtnAddMemberClicked(ActionEvent event){
        try{
            members.add( new Member( dateOfBirthPicker.getValue() == null
                    ? LocalDate.parse(dateOfBirthPicker.getEditor().getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                    : dateOfBirthPicker.getValue()
                   ,getTextFieldText(txtFieldFirstName),getTextFieldText(txtFieldFirstName),getHighestIdentifier()));
            new StageCloser().closeStageByEvent(event);
        }
        catch(DateTimeParseException  | EmptyFieldException exp) {
            if(exp instanceof DateTimeParseException){
                lblError.setText("Error Parsing " + dateOfBirthPicker.getEditor().getText() + "Into Date");
                event.consume();
            } else {
                lblError.setText(exp.getMessage());
            }
        }
    }
    @FXML
    private void onBtnCancelClicked(ActionEvent event){
        new StageCloser().closeStageByEvent(event);
    }
    private String getTextFieldText(TextField textField){
        if(!textField.getText().isEmpty()){
            return textField.getText();
        }
       throw new EmptyFieldException( textField.getPromptText() + " Field  is Empty");
    }
}
