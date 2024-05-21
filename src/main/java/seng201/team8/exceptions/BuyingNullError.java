package seng201.team8.exceptions;

public class BuyingNullError extends Exception{
    public BuyingNullError(){};
    public BuyingNullError(String errorMessage){super(errorMessage);}
}
