package Model;

import java.time.LocalDate;
// Thinking that one Member  can  lend more than one book and one book can be lent by many member
// This make many-to-many relationships so, new Lent item calls is made
public class LentItem {
    private LibraryItem item;
    private  Person member;
    private LocalDate lendingDate;

    public LentItem(LibraryItem item, Member member, LocalDate lendingDate) {
        this.item = item;// when the library item is Lent it is available
        this.member = member;
        this.lendingDate = lendingDate;
    }

    public LibraryItem getItem() {
        return item;
    }

    public Person getMember() {
        return member;
    }

    public LocalDate getLendingDate() {
        return lendingDate;
    }
}
