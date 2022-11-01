package Database;

import Model.*;
import Model.Exception.ResultNotFoundException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Database {
    private  List<LentItem> lentItems;
    private  List<Book> books;
    private  List<Member> members;
    private final List<User> users;

    public Database() {
        // lent items Records for lent item
        lentItems= new ArrayList<>();

        // library Items
        books = new ArrayList<>();

        // members
        members = new ArrayList<>();

        // users
        users = new ArrayList<>();
        users.add(new User("John","Sapkota","Admin@123"));
        users.add(new User("Durga","Jackson","Admin@123"));

    }

    public void createAndAddMembersToTheList() {
        Member memberPiet= new Member(LocalDate.of(2000,06,21),"Piet"," de Vries",32);
        Member memberBijay= new Member(LocalDate.of(2001,10,26),"Bijay","Sapkota",33);
        Member memberDaniel= new Member(LocalDate.of(1990,10,5),"Daniel","de Vries",34);
        members.add(memberPiet);
        members.add(memberDaniel);
        members.add(memberBijay);
    }

    public void createAndAddBookToList() {
        Author wilco = new Author("Wilco","Dekker");
        Author vries = new Author("E. de ","Vries");
        Author whitaker = new Author("RB","Whitaker");
        Book bookJava= new Book("Java forDummies,13 th edition",233,Availability.Yes,vries);
        Book bookCHash = new Book("C#,14th edition",234,Availability.Yes,whitaker);
        Book bookMountEverest = new Book("Mount Everest,15th edition",235,Availability.Yes,wilco);
        books.add(bookJava);
        books.add(bookCHash);
        books.add(bookMountEverest);
    }

    public void addLentItem(LentItem lentItem) {
        lentItems.add(lentItem);
    }
    public void removeLentItem(LentItem lentItem){
        lentItems.remove(lentItem);
    }
    public LentItem getLentItemWithItemCode(int libraryItemCode){
        for (LentItem lentItem:lentItems
        ) {
            // looking in lending Item if the library Item is there or not
            if(lentItem.item().getItemCode() == libraryItemCode){
                return lentItem;
            }
        }
        throw  new ResultNotFoundException("No Item was lent with this item Code " + libraryItemCode);
    }
    public  List<Book> getLibraryBooks(){
        return books;
    }
    public void setMembersFromSerializedFile(File file) {
        members= (List<Member>)(List<?>)readSerializableList(file);
    }
    public void setBooksFromSerializedFile(File file) {
        books= (List<Book>)(List<?>)readSerializableList(file);
    }
    public void setLentItemsFromSerializedFile(File file) {
        lentItems= (List<LentItem>)(List<?>)readSerializableList(file);
    }
    public LibraryItem getLibraryItemWithItemCode(int libraryItemCode){
        for (LibraryItem libraryItem : books
        ) {
            if(libraryItem.getItemCode() == libraryItemCode){
              return libraryItem;
            }
        }
       throw  new ResultNotFoundException("No Item was found with code " + libraryItemCode);
    }
    public List<Member> getMembers() {
        return members;
    }
    public Member getMemberById(int identifier) {
        for (Member member : members) {
            if (member.getIdentifier() == identifier) {
                return member;
            }
        }
        throw new ResultNotFoundException("No Member have been found with Id "+identifier);
    }
    public User loginWithCredentials(String username, String password) {
        for (User user : users
        ) {
            if (user.getUserName().equals(username) && user.getPassword().equals(password))
            {
                 return user;
            }
        }
        throw  new ResultNotFoundException("Invalid username or password combination");
    }
    public void writeSerializable(File file,List<Serializable> list) throws IOException {
        OutputStream outputStream  = new FileOutputStream(file);
        ObjectOutputStream objectStream=new ObjectOutputStream(outputStream);
        objectStream.writeObject(list);
        objectStream.flush(); // flushing
        //closing the stream
        objectStream.close();
    }
    private List<Serializable> readSerializableList(File file){
        List<Serializable> readingList ;
        try {
            ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream(file));
            readingList=(List<Serializable>) objectInputStream.readObject();
            objectInputStream.close();

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return readingList;
    }
    public List<LentItem> getLentItems(){
        return lentItems;
    }
    public void updateLibraryItem(int itemCode,Availability availability,LocalDate date){
        for (LibraryItem item :books
             ) {
            if(item.getItemCode() == itemCode){
                item.setAvailability(availability);
                item.setExpectedReturnDate(date);
            }
        }
    }
    public void addReadBooksFromFile(List<Book> books){
        for (Book book : books
             ) {
            books.add(book);

        }

    }

    public List<Book> readBooksFromFile(File file){
        List<Book> list = new ArrayList<>();
        try {
            Files.readAllLines(Paths.get(file.getPath()))
                    .forEach(line -> list.add(new Book(line.split(";")[1], Integer.parseInt(line.split(";")[0]), Availability.No, new Author(line.split(";")[2], ""))));


        }
     catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }}
