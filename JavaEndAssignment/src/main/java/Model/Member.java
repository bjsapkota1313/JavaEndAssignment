package Model;

import java.time.LocalDate;

public class Member extends Person{
    private final int identifier;
    // making id as read only
    private final LocalDate dateOfBirth;
    public Member(LocalDate dateOfBirth, String firstName, String lastName,int identifier) {
        super( firstName, lastName);
        this.identifier = identifier;
        this.dateOfBirth = dateOfBirth;
    }

    public int getIdentifier() {
        return identifier;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
}
