package Database;

import Model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private final List<LentItem> lentItems;
    private final List<Book> items;
    private final  Book bookJava;
    private  final Book bookCHash;
    private final  Book bookMountEverest;
    private final Author wilco;
    private final  Author vries;
    private final Author whitaker;
    private final List<Member> members;
    private final List<User> users;

    public Database() {
        // lent items Records for lent item
        lentItems= new ArrayList<LentItem>();

        // library Items
        wilco = new Author("Wilco","Dekker");
        vries = new Author("E. de ","Vries");
        whitaker = new Author("RB","Whitaker");
        bookJava= new Book("Java forDummies,13 th edition",233,true,vries);
        bookCHash = new Book("C#,14th edition",234,true,whitaker);
        bookMountEverest = new Book("Mount Everest,15th edition",235,true,wilco);
        items = new ArrayList<Book>();
        items.add(bookJava);
        items.add(bookCHash);
        items.add(bookMountEverest);

        // members
        Member memberPiet= new Member(LocalDate.of(2000,06,21),"Piet"," de Vries",32);
        Member memberBijay= new Member(LocalDate.of(2001,10,26),"Bijay","Sapkota",33);
        Member memberDaniel= new Member(LocalDate.of(1990,10,05),"Daniel","de Vries",34);
        members = new ArrayList<Member>();
        members.add(memberPiet);
        members.add(memberDaniel);
        members.add(memberBijay);

        // users
        users = new ArrayList<User>();
        users.add(new User("John","Sapkota","Admin@123"));
        users.add(new User("Amy","Jackson","Admin@1234"));

    }
    public void addLentItem(LentItem lentItem) {

        lentItems.add(lentItem);
    }
    public void removeLentItem(LentItem lentItem){
        lentItems.remove(lentItem);
    }
    public LentItem getLentItemWithItemCode(int libraryItemCode){
        LentItem returningLentItem=null;
        for (LentItem lentItem:lentItems
        ) {
            // looking in lending Item if the library Item is there or not
            if(lentItem.getItem().getItemCode() == libraryItemCode){
                returningLentItem=lentItem;
            }
        }
        return returningLentItem;
    }
    public  List<Book> getLibraryBooks(){

        return items;
    }
    public LibraryItem getLibraryItemWithItemCode(int libraryItemCode){
        LibraryItem returningLibraryItem=null;
        for (LibraryItem libraryItem : items
        ) {
            if(libraryItem.getItemCode() == libraryItemCode){
                returningLibraryItem=libraryItem;
            }
        }
        return returningLibraryItem;
    }
    public List<Member> getMembers() {

        return members;
    }
    public Member getMemberById(int identifier) {
        Member returningMember = null;
        for (Member member : members) {
            if (member.getIdentifier() == identifier) {
                returningMember= member;
            }
        }
        return returningMember;
    }
    public User loginWithCredentials(String username, String password){
        User loggingUser=null;
        for (User user : users
        ) {
            if (user.getUserName().equals(username) && user.getPassword().equals(password))
            {
                loggingUser = user;
            }

        }
        return loggingUser;
    }
}
