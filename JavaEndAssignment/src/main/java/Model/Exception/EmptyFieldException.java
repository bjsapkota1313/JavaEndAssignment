package Model.Exception;

import java.util.InputMismatchException;

public class EmptyFieldException extends InputMismatchException {
    public EmptyFieldException(String s) {
        super(s);
    }
}
