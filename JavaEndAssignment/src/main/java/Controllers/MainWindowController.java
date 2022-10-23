package Controllers;

import Database.Database;
import Model.*;
import com.exam.javaendassignment.AppLibrary;
import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;


public class MainWindowController implements Initializable {

    private final Database database;
    private ObservableList<Member> members;
    @FXML
    private TableView<Book> libraryItemTableView;
    @FXML
    private TableView<Member> membersTableView;
    @FXML
    private ObservableList<Book> bookList;
    @FXML
    public TabPane tabPane;
    @FXML
    public Tab tabLending;
    @FXML
    private Button btnReceiveItem, btnLendItem,btnEditMember,btnDeleteMember,btnEditItem,btnDeleteItem;
    @FXML
    private Label lblUserName, lblUserFeedBackLendingItem,lblUserFeedBackReceivingItem;
    @FXML
    private TextField txtFieldItemCode, txtFieldMemberId,  txtBoxReceiveItemCode;


    public MainWindowController( Database database) {
        this.database = database;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblUserName.setText("Welcome " + "Bijay"); //To do Username
        tabPane.getSelectionModel().select(tabLending);// when app runs making default tab selection on Lending
        // book list
        bookList=FXCollections.observableList(database.getLibraryBooks());
        libraryItemTableView.setItems(bookList);
        libraryItemTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // members
        members = FXCollections.observableList(database.getMembers());
        membersTableView.setItems(members);
        membersTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        btnEditMember.disableProperty().bind(Bindings.isEmpty(membersTableView.getSelectionModel().getSelectedItems()));

        disableButtons();
    }

    @FXML
    protected void onBtnLendItemClicked() {
        LibraryItem lendingLibraryItem = database.getLibraryItemWithItemCode(Integer.parseInt(txtFieldItemCode.getText()));
        Member lendingMember = database.getMemberById(Integer.parseInt(txtFieldMemberId.getText()));

        if (lendingLibraryItem == null | lendingMember == null) {
            lblUserFeedBackLendingItem.getStyleClass().add("errorMessageStyle");//changing text color to red
            lblUserFeedBackLendingItem.setText("Please check your Item code or Member Id and try again");
        } else {
            // when Some libraryItem is lent to the member new lentItem object is made
            database.addLentItem(new LentItem(lendingLibraryItem, lendingMember, LocalDate.now()));
            lblUserFeedBackLendingItem.getStyleClass().add("successMessageStyle");
            lblUserFeedBackLendingItem.setText("The " + lendingLibraryItem.getName() + " has been lent to " + lendingMember);
           database.updateLibraryItemAvailability(lendingLibraryItem.getItemCode(),Availability.No); // updating lending item availability status
            libraryItemTableView.refresh();
        }
    }

    @FXML
    protected void onBtnReceiveItemClicked() {
        LentItem receivingLentItem = database.getLentItemWithItemCode(Integer.parseInt(txtBoxReceiveItemCode.getText()));
        if (receivingLentItem == null) {
            lblUserFeedBackReceivingItem.getStyleClass().add("errorMessageStyle");//changing text color to red
            lblUserFeedBackReceivingItem.setText("The entered Item code is not lent or invalid item code");
        }else{
            if (Period.between(receivingLentItem.getLendingDate(), LocalDate.now()).getDays() >= 21) {
                lblUserFeedBackReceivingItem.getStyleClass().add("errorMessageStyle");//changing text color to red
                lblUserFeedBackReceivingItem.setText("This item is returned " + (Period.between(receivingLentItem.getLendingDate(), LocalDate.now()).getDays() - 21) + " days");
                // for now Not receiving a book whenever it later than 21 days fine can be adopted later on
            } else {
                lblUserFeedBackReceivingItem.getStyleClass().add("successMessageStyle");
                database.removeLentItem(receivingLentItem); // removing from LentItem list
                database.updateLibraryItemAvailability(receivingLentItem.getItem().getItemCode(),Availability.Yes);
                libraryItemTableView.refresh();
                // updates the library item to available again
                lblUserFeedBackReceivingItem.setText(receivingLentItem.getItem().getName() +" is available again to Lend");
            }
        }
    }

    @FXML
    public void onItemCodeChange(StringProperty observable, String oldValue, String newValue) {
        lblUserFeedBackLendingItem.getStyleClass().clear();
        lblUserFeedBackLendingItem.setText(""); // making sure that the old feedback does not interfere with the new entered value
    }

    @FXML
    public void onMemberIDChange(StringProperty observable, String oldValue, String newValue) {
        lblUserFeedBackLendingItem.getStyleClass().clear();
        lblUserFeedBackLendingItem.setText("");
    }
    @FXML
    public void onReceiveItemCodeChange(StringProperty observable, String oldValue, String newValue) {
        lblUserFeedBackReceivingItem.getStyleClass().clear();
        lblUserFeedBackReceivingItem.setText("");
    }
    @FXML
    private void onBtnAddAddMemberClicked(ActionEvent event) {
      loadDialogueBox("AddMember",new AddMemberDialogueController(members));
        membersTableView.getSelectionModel().clearSelection();
    }

    private void loadDialogueBox(String viewName,Object controller){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(AppLibrary.class.getResource(viewName+".fxml"));
            fxmlLoader.setController(controller);
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(getClass().getResource("/css/AppStyles.css").toExternalForm());
            Stage dialog = new Stage();
            dialog.setScene(scene);
            dialog.setTitle(viewName);
            dialog.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void  onBtnAddItemClicked() {
        loadDialogueBox("AddLibraryItem",new AddItemDialogueController(bookList));
        libraryItemTableView.getSelectionModel().clearSelection();
    }
    @FXML
    private void onBtnEditItemClicked() {
        loadDialogueBox("EditLibraryItem",new EditItemDialogueController(libraryItemTableView.getSelectionModel().getSelectedItem()));
        libraryItemTableView.refresh(); // refreshing table view whenever it is updated in observable list
        libraryItemTableView.getSelectionModel().clearSelection();
    }
    @FXML
    private void onBtnEditMemberClicked() {
        loadDialogueBox("EditMember",new EditMemberDialogueController(membersTableView.getSelectionModel().getSelectedItem()));
        membersTableView.refresh(); // refreshing table view whenever it is updated in
        membersTableView.getSelectionModel().clearSelection(); // clearing selection
    }
    @FXML
    private void onBtnDeleteItemClicked() {
        bookList.removeAll(libraryItemTableView.getSelectionModel().getSelectedItems());
        libraryItemTableView.getSelectionModel().clearSelection();
        libraryItemTableView.getSelectionModel().clearSelection(); // clearing selection
    }
    @FXML
    private void onBtnDeleteMemberClicked(){
        members.removeAll(membersTableView.getSelectionModel().getSelectedItems());
        membersTableView.getSelectionModel().clearSelection(); // clearing selection
    }
    private void disableButtons(){
        // preventing the null point expectation when user does not select in Tableview and try to Edit or Delete Item
        btnDeleteItem.disableProperty().bind(Bindings.isEmpty(libraryItemTableView.getSelectionModel().getSelectedItems()));
        btnEditItem.disableProperty().bind(Bindings.isEmpty(libraryItemTableView.getSelectionModel().getSelectedItems()));
        btnDeleteMember.disableProperty().bind(Bindings.isEmpty(membersTableView.getSelectionModel().getSelectedItems()));
        btnEditMember.disableProperty().bind(Bindings.isEmpty(membersTableView.getSelectionModel().getSelectedItems()));
    }

}
