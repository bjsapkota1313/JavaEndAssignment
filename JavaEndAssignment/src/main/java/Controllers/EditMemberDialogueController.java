package Controllers;

import Model.Exception.EmptyFieldException;
import Model.Member;
import com.exam.javaendassignment.CloserAndLoader.StageCloser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

public class EditMemberDialogueController implements Initializable {
    @FXML
    private TextField txtFieldFirstName,txtFieldLastName;
    @FXML
    private DatePicker dateOfBirthPicker;
    private final Member selectedMember;
    @FXML
    private Label lblError;

    public EditMemberDialogueController(Member selectedMember) {
        this.selectedMember = selectedMember;
    }

    @FXML
    private void onBtnUpdateMemberClicked(ActionEvent event) {
        try{
            selectedMember.setFirstName(getTextFieldText(txtFieldFirstName));
            selectedMember.setLastName(getTextFieldText(txtFieldLastName));
            LocalDate dateOfBirth=dateOfBirthPicker.getValue() == null
                    ? LocalDate.parse(dateOfBirthPicker.getEditor().getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                    : dateOfBirthPicker.getValue();
            selectedMember.setDateOfBirth(dateOfBirth);
            // closing the dialogue
            new StageCloser().closeStageByEvent(event);
        }
        catch(DateTimeParseException  | EmptyFieldException exp) {
            if(exp instanceof DateTimeParseException){
                lblError.setText("Error Parsing " + dateOfBirthPicker.getEditor().getText() + "Into Date");
            } else {
                lblError.setText(exp.getMessage());
            }
            event.consume();
        }
    }
    @FXML
    private void onBtnCancelClicked(ActionEvent event) {
        new StageCloser().closeStageByEvent(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDetailsOfSelectedMember();
    }
    private void setDetailsOfSelectedMember(){
        txtFieldFirstName.setPromptText(selectedMember.getFirstName());
        txtFieldLastName.setPromptText(selectedMember.getLastName());
        dateOfBirthPicker.setPromptText(selectedMember.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
    }
    private String getTextFieldText(TextField textField){
        if(!textField.getText().isEmpty()){
            return textField.getText();
        }
        throw new EmptyFieldException(" Field cannot be left empty while editing Member Details");
    }


}
