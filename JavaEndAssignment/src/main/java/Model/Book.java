package Model;
public class Book extends LibraryItem{
    private final Author author;

    public Book(String name,int itemCode, Availability availability,Author author) {
        super(itemCode, availability,name);
        this.author = author;
    }

    public Author getAuthor() {
        return author;
    }

}
