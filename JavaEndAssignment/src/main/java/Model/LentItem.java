package Model;

import java.io.Serializable;
import java.time.LocalDate;

// Thinking that one Member  can  lend more than one book and one book can be lent by many member
// This make many-to-many relationships so, new Lent item calls is made
public record LentItem(LibraryItem item, Member member, LocalDate lendingDate) implements Serializable {
}
