package Exceptions;

public class WrongCardNumberException extends Exception{
    public WrongCardNumberException() {
        super(String.format("Wrong card Number"));
    }
}
