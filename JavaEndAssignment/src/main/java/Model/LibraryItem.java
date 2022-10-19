package Model;

public abstract class LibraryItem {
    private final int itemCode;
    private boolean availability ;
    private final String name ;

    public LibraryItem( int itemCode, boolean availability,String name ) {
        this.itemCode = itemCode;
        this.availability = availability;
        this.name = name;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }


    public int getItemCode() {
        return itemCode;
    }

    public boolean getAvailability() {
        return availability;
    }


    public String getName() {
        return name;
    }
}
