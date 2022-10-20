package Model;

import java.time.LocalDate;

public class Member extends Person{
    private final int identifier;
    // making id as read only
    private  LocalDate dateOfBirth;
    public Member(LocalDate dateOfBirth, String firstName, String lastName,int identifier) {
        super( firstName, lastName);
        this.identifier = identifier;
        this.dateOfBirth = dateOfBirth;
    }

    public int getIdentifier() {
        return identifier;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
}
