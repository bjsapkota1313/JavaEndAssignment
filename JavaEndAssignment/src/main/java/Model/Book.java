package Model;

public class Book extends LibraryItem{
    private final Author author;

    public Book(String name,int itemCode, boolean availability,Author author) {
        super(itemCode, availability,name);
        this.author = author;
    }

    public Author getAuthor() {
        return author;
    }
    public String GetYesNoAvailability() {
        String output="";
        if (this.getAvailability()) {
            output = "Yes";
        } else  {
            output = "No";
        }
        return output;
    }
}
