package Model;

import java.io.Serializable;

public abstract class LibraryItem implements Serializable {
    private final int itemCode;
    private Availability availability ;
    private  String name ;

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
    }


    public int getItemCode() {
        return itemCode;
    }

    public Availability getAvailability() {
        return availability;
    }


    public String getName() {
        return name;
    }

}
