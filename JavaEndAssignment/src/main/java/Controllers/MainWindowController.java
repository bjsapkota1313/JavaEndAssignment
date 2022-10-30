package Controllers;
import Database.Database;
import Model.*;
import Model.Exception.EmptyFieldException;
import Model.Exception.ResultNotFoundException;
import com.exam.javaendassignment.CloserAndLoader.SceneLoader;
import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;


public class MainWindowController implements Initializable {

    private final Database database;
    private ObservableList<Member> members;
    @FXML
    private TableView<Book> collectionTableView;
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
    private final User currentLoggedUser;

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
        collectionTableView.setItems(bookList);
        collectionTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // members
        members = FXCollections.observableList(database.getMembers());
        membersTableView.setItems(members);
        membersTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        btnEditMember.disableProperty().bind(Bindings.isEmpty(membersTableView.getSelectionModel().getSelectedItems()));

        disableButtons(); // disabling button when nothing is selecting in index
    }

    @FXML
    protected void onBtnLendItemClicked() {
        try {
            LibraryItem lendingLibraryItem = database.getLibraryItemWithItemCode(Integer.parseInt(getTextFieldText(txtFieldItemCode)));
            Member lendingMember = database.getMemberById(Integer.parseInt( getTextFieldText(txtFieldMemberId)));
            // when Some libraryItem is lent to the member new lentItem object is made
            database.addLentItem(new LentItem(lendingLibraryItem, lendingMember, LocalDate.now()));
            lblUserFeedBackLendingItem.getStyleClass().add("successMessageStyle");
            lblUserFeedBackLendingItem.setText("The " + lendingLibraryItem.getName() + " has been lent to " + lendingMember);
            database.updateLibraryItemAvailability(lendingLibraryItem.getItemCode(),Availability.No); // updating lending item availability status
            collectionTableView.refresh();
        }catch (ResultNotFoundException | NumberFormatException | EmptyFieldException exp) {
            lblUserFeedBackLendingItem.getStyleClass().add("errorMessageStyle");//changing text color to red
           if(exp instanceof NumberFormatException){
               lblUserFeedBackLendingItem.setText("Cannot Parse entered value into Id");
           } else  {
               lblUserFeedBackLendingItem.setText(exp.getMessage());
           }
        }
    }

    @FXML
    protected void onBtnReceiveItemClicked() {
        try {
            LentItem receivingLentItem = database.getLentItemWithItemCode(Integer.parseInt( getTextFieldText(txtBoxReceiveItemCode)));
            if (ChronoUnit.DAYS.between( receivingLentItem.lendingDate(),LocalDate.now())>=21 ){
                lblUserFeedBackReceivingItem.getStyleClass().add("errorMessageStyle");//changing text color to red
                lblUserFeedBackReceivingItem.setText("This item is returned " + (ChronoUnit.DAYS.between( receivingLentItem.lendingDate(),LocalDate.now()) - 21) + " days Late");
                // for now Not receiving a book whenever it later than 21 days  implementing fine can be adopted later on
            } else {
                lblUserFeedBackReceivingItem.getStyleClass().add("successMessageStyle");
                database.removeLentItem(receivingLentItem); // removing from LentItem list
                database.updateLibraryItemAvailability(receivingLentItem.item().getItemCode(),Availability.Yes);
                collectionTableView.refresh();
                // updates the library item to available again
                lblUserFeedBackReceivingItem.setText(receivingLentItem.item().getName() +" is available again to Lend");
            }
        }
        catch (ResultNotFoundException |NumberFormatException | EmptyFieldException exp) {
            lblUserFeedBackReceivingItem.getStyleClass().add("errorMessageStyle");//changing text color to red
            if(exp instanceof NumberFormatException){
                lblUserFeedBackReceivingItem.setText("Cannot Parse entered value into Item code");
            } else{
                lblUserFeedBackReceivingItem.setText(exp.getMessage());
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
    private void onBtnAddAddMemberClicked() {
        new SceneLoader().loadScene("AddMember",new AddMemberDialogueController(members),new Stage(),true);
        membersTableView.getSelectionModel().clearSelection();
    }
    @FXML
    private void  onBtnAddItemClicked()  {
        new SceneLoader().loadScene("AddLibraryItem",new AddItemDialogueController(bookList),new Stage(),true);
        collectionTableView.getSelectionModel().clearSelection();
    }
    @FXML
    private void onBtnEditItemClicked()  {
        new SceneLoader().loadScene("EditSelectedItem",new EditItemDialogueController(collectionTableView.getSelectionModel().getSelectedItem()),new Stage(),true);
        collectionTableView.refresh(); // refreshing table view whenever it is updated in observable list
        collectionTableView.getSelectionModel().clearSelection();
    }
    @FXML
    private void onBtnEditMemberClicked()  {
        new SceneLoader().loadScene("EditMember",new EditMemberDialogueController(membersTableView.getSelectionModel().getSelectedItem()),new Stage(),true);
        membersTableView.refresh(); // refreshing table view whenever it is updated in
        membersTableView.getSelectionModel().clearSelection(); // clearing selection
    }
    @FXML
    private void onBtnDeleteItemClicked() {
        bookList.removeAll(collectionTableView.getSelectionModel().getSelectedItems());
        collectionTableView.getSelectionModel().clearSelection();
        collectionTableView.getSelectionModel().clearSelection(); // clearing selection
    }
    @FXML
    private void onBtnDeleteMemberClicked(){
        members.removeAll(membersTableView.getSelectionModel().getSelectedItems());
        membersTableView.getSelectionModel().clearSelection(); // clearing selection
    }
    private void disableButtons(){
        // preventing the null point expectation when user does not select in Tableview and try to Edit or Delete Item
        btnDeleteItem.disableProperty().bind(Bindings.isEmpty(collectionTableView.getSelectionModel().getSelectedItems()));
        btnEditItem.disableProperty().bind(Bindings.isEmpty(collectionTableView.getSelectionModel().getSelectedItems()));
        btnDeleteMember.disableProperty().bind(Bindings.isEmpty(membersTableView.getSelectionModel().getSelectedItems()));
        btnEditMember.disableProperty().bind(Bindings.isEmpty(membersTableView.getSelectionModel().getSelectedItems()));
    }
    @FXML
    private void onSearchMemberTxtFieldChange(StringProperty observable, String oldValue, String newValue){
        FilteredList<Member> filteredMembers = new FilteredList<>(members, b->true);
        filteredMembers.setPredicate(member->{
            if (newValue.isBlank() || newValue.isEmpty())
            {
                return true;
            }
            String searchingKey = newValue.toUpperCase();
            return member.getFirstName().toUpperCase().contains(searchingKey) || member.getLastName().toUpperCase().contains(searchingKey);
        });
        //Sorting the filtered list
        SortedList<Member> sortedMembers = new SortedList<>(filteredMembers);
        // binding the sorted members with table view
        sortedMembers.comparatorProperty().bind(membersTableView.comparatorProperty());
        membersTableView.setItems(sortedMembers);
    }
    @FXML
    private void onSearchItemTxtFieldChange(StringProperty observable, String oldValue, String newValue){
        FilteredList<Book> filteredBooks = new FilteredList<>(bookList, b->true);
        filteredBooks.setPredicate(book->{
            if (newValue.isBlank() || newValue.isEmpty())
            {
                return true;
            }
            String searchingKey = newValue.toUpperCase();
            return book.getName().toUpperCase().contains(searchingKey) || book.getAuthor().getLastName().toUpperCase().contains(searchingKey) || book.getAuthor().getFirstName().toUpperCase().contains(searchingKey);
        });
        //Sorting the filtered list
        SortedList<Book> sortedBooks = new SortedList<>(filteredBooks);
        // binding the sorted Books with table view
        sortedBooks.comparatorProperty().bind(collectionTableView.comparatorProperty());
        collectionTableView.setItems(sortedBooks);
    }
    private String getTextFieldText(TextField textField){
        if(!textField.getText().isEmpty()){
            return textField.getText();
        }
        throw new EmptyFieldException(textField.getPromptText() + " cannot be empty");
    }
}
