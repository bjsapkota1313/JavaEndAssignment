package Controllers;

import Model.Member;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class EditMemberDialogueController implements Initializable {
    @FXML
    private TextField txtFieldFirstName,txtFieldLastName;
    @FXML
    private DatePicker dateOfBirthPicker;
    private Member selectedMember;

    public EditMemberDialogueController(Member selectedMember) {
        this.selectedMember = selectedMember;
    }

    @FXML
    private void onBtnUpdateMemberClicked(ActionEvent event) {
        updateMemberDetails();
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
    @FXML
    private void onBtnCancelClicked(ActionEvent event) {
    Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
    stage.close();
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
    private void updateMemberDetails() {
        selectedMember.setFirstName(txtFieldFirstName.getText());
        selectedMember.setLastName(txtFieldLastName.getText());
        selectedMember.setDateOfBirth(dateOfBirthPicker.getValue() == null
                ? LocalDate.parse(dateOfBirthPicker.getEditor().getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                : dateOfBirthPicker.getValue());

    }
}
