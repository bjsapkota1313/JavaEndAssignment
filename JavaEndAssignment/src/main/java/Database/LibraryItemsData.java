package Database;

import Model.Author;
import Model.Book;
import Model.LibraryItem;
import java.util.ArrayList;
import java.util.List;

public class LibraryItemsData {
  private final List<Book> items;
  private final  Book bookJava;
  private  final Book bookCHash;
  private final  Book bookMountEverest;
  private final  Author wilco;
  private final  Author vries;
  private final Author whitaker;

    public LibraryItemsData() {
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
    }
    public void AddLibraryBook( Book libraryItem){

      items.add(libraryItem);
    }
    public void removeLibraryBook(Book libraryItem){

      items.remove(libraryItem);
    }
    public void updateLibraryItemAvailability(LibraryItem libraryItem,boolean availability){
      libraryItem.setAvailability(availability);
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


 }
