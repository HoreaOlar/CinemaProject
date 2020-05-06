package Exceptions;

public class EmptyFieldException extends Exception {
    public EmptyFieldException() {
        super(String.format("Empty Field, try again!"));
    }
}
