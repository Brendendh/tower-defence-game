package seng201.team8.exceptions;

public class NoSpaceException extends Exception{

    public NoSpaceException(){

    }

    public NoSpaceException(String message){
        super(message);
    }
}
