package seng201.team8.exceptions;

public class NotEnoughCurrencyException extends Exception{

    public NotEnoughCurrencyException(){

    }

    public NotEnoughCurrencyException(String errorMessage){
        super(errorMessage);
    }
}
