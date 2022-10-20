package Controllers;

import Database.LentItemsData;
import Database.LibraryItemsData;
import Database.MemberData;
import Model.*;
import com.exam.javaendassignment.AppLibrary;
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
    private final LibraryItemsData libraryItemDB;
    private final LentItemsData lentItemDB;
    private final MemberData memberDB;
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
    private Button btnReceiveItem, btnLendItem;
    @FXML
    private Label lblUserName, lblUserFeedBackLendingItem,lblUserFeedBackReceivingItem;
    @FXML
    private TextField txtFieldItemCode, txtFieldMemberId,  txtBoxReceiveItemCode;


    public MainWindowController() {
        libraryItemDB = new LibraryItemsData();
        lentItemDB = new LentItemsData();
        memberDB = new MemberData();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblUserName.setText("Welcome " + "Bijay"); //To do Username
        tabPane.getSelectionModel().select(tabLending);// when app runs making default tab selection on Lending
        // book list
        bookList=FXCollections.observableList(libraryItemDB.getLibraryBooks());
        libraryItemTableView.setItems(bookList);

        // members
        members = FXCollections.observableList(memberDB.getMembers());
        membersTableView.setItems(members);
    }

    @FXML
    protected void onBtnLendItemClicked() {
        LibraryItem lendingLibraryItem = libraryItemDB.getLibraryItemWithItemCode(Integer.parseInt(txtFieldItemCode.getText()));
        Member lendingMember = memberDB.getMemberById(Integer.parseInt(txtFieldMemberId.getText()));

        if (lendingLibraryItem == null | lendingMember == null) {
            lblUserFeedBackLendingItem.getStyleClass().add("errorMessageStyle");//changing text color to red
            lblUserFeedBackLendingItem.setText("Please check your Item code or Member Id and try again");
        } else {
            // when Some libraryItem is lent to the member new lentItem object is made
            lentItemDB.addLentItem(new LentItem(lendingLibraryItem, lendingMember, LocalDate.now()));
            lblUserFeedBackLendingItem.getStyleClass().add("successMessageStyle");
            lblUserFeedBackLendingItem.setText("The " + lendingLibraryItem.getName() + " has been lent to " + lendingMember);
            updateLibraryItemAvailability(lendingLibraryItem,false); // updating lending item availability status
            libraryItemTableView.refresh();
        }
    }

    @FXML
    protected void onBtnReceiveItemClicked() {
        LentItem receivingLentItem = lentItemDB.getLentItemWithLentItems(Integer.parseInt(txtBoxReceiveItemCode.getText()));
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
                lentItemDB.removeLentItem(receivingLentItem); // removing from LentItem list
                updateLibraryItemAvailability(receivingLentItem.getItem(),true);
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
    }
    private void updateLibraryItemAvailability(LibraryItem libraryItem,boolean availability) {
        libraryItem.setAvailability(availability);
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
    }
    @FXML
    private void onBtnEditItemClicked() {
        loadDialogueBox("EditLibraryItem",new EditItemDialogueController(libraryItemTableView.getSelectionModel().getSelectedItem()));
        libraryItemTableView.refresh(); // refreshing table view whenever it is updated in observable list
    }
    @FXML
    private void onBtnEditMemberClicked() {
        loadDialogueBox("EditMember",new EditMemberDialogueController(membersTableView.getSelectionModel().getSelectedItem()));
        membersTableView.refresh(); // refreshing table view whenever it is updated in
    }
}
