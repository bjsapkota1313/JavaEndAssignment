package Controllers;

import Database.Database;
import Model.*;
import com.exam.javaendassignment.SceneLoader;
import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    private TextField txtFieldItemCode, txtFieldMemberId,  txtBoxReceiveItemCode,txtFieldSearchName;
    private User currentLoggedUser;


    public MainWindowController( Database database,User currentLoggedUser) {
        this.currentLoggedUser = currentLoggedUser;
        this.database = database;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblUserName.setText("Welcome " + currentLoggedUser); //To do Username
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
            if (ChronoUnit.DAYS.between( receivingLentItem.lendingDate(),LocalDate.now())>=21 ){
                lblUserFeedBackReceivingItem.getStyleClass().add("errorMessageStyle");//changing text color to red
                lblUserFeedBackReceivingItem.setText("This item is returned " + (ChronoUnit.DAYS.between( receivingLentItem.lendingDate(),LocalDate.now()) - 21) + " days Late");
                // for now Not receiving a book whenever it later than 21 days  implementing fine can be adopted later on
            } else {
                lblUserFeedBackReceivingItem.getStyleClass().add("successMessageStyle");
                database.removeLentItem(receivingLentItem); // removing from LentItem list
                database.updateLibraryItemAvailability(receivingLentItem.item().getItemCode(),Availability.Yes);
                libraryItemTableView.refresh();
                // updates the library item to available again
                lblUserFeedBackReceivingItem.setText(receivingLentItem.item().getName() +" is available again to Lend");
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
    private void onBtnAddAddMemberClicked(ActionEvent event) throws IOException {
        new SceneLoader().loadScene("AddMember",new AddMemberDialogueController(members),new Stage(),true);
        membersTableView.getSelectionModel().clearSelection();
    }
    @FXML
    private void  onBtnAddItemClicked() throws IOException {
        new SceneLoader().loadScene("AddLibraryItem",new AddItemDialogueController(bookList),new Stage(),true);
        libraryItemTableView.getSelectionModel().clearSelection();
    }
    @FXML
    private void onBtnEditItemClicked() throws IOException {
        new SceneLoader().loadScene("EditLibraryItem",new EditItemDialogueController(libraryItemTableView.getSelectionModel().getSelectedItem()),new Stage(),true);
        libraryItemTableView.refresh(); // refreshing table view whenever it is updated in observable list
        libraryItemTableView.getSelectionModel().clearSelection();
    }
    @FXML
    private void onBtnEditMemberClicked() throws IOException {
        new SceneLoader().loadScene("EditMember",new EditMemberDialogueController(membersTableView.getSelectionModel().getSelectedItem()),new Stage(),true);
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
    @FXML
    private void onSearchMemberTxtFieldChange(){
       // members.sort(txtFieldSearchName.getText().compareTo());
        //FXCollections.sort(members,new MyComparator(txtFieldSearchName.getText()));
    }

}
