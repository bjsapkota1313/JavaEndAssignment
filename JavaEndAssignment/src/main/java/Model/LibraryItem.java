package Model;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class LibraryItem implements Serializable {
    private final int itemCode;
    private Availability availability ;
    private  String name ;

    public LocalDate getExpectedReturnDate() {
        return expectedReturnDate;
    }

    private LocalDate expectedReturnDate;
    public void setName(String name) {
        this.name = name;
    }

    public LibraryItem( int itemCode, Availability availability,String name ) {
        this.itemCode = itemCode;
        this.availability = availability;
        this.name = name;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
        if (availability != Availability.No){

        }
    }
    public int getItemCode() {
        return itemCode;
    }

    public void setExpectedReturnDate(LocalDate expectedReturnDate) {
        this.expectedReturnDate = expectedReturnDate;
    }

    public Availability getAvailability() {
        return availability;
    } // required this getter method to get availability in Tableview collections

    public String getName() {
        return name;
    }

}
