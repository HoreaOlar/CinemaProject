package Exceptions;

public class ExceededSitsException extends  Exception {
    public ExceededSitsException () {
        super(String.format("We dont have enought available sits"));
    }
}
