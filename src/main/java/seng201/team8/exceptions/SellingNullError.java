package seng201.team8.exceptions;

public class SellingNullError extends Exception{
    public SellingNullError(){};

    public SellingNullError(String errorMessage){
        super(errorMessage);
    }
}
