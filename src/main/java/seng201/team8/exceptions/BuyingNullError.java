package seng201.team8.exceptions;

/**
 * An error thrown when trying to buy an already sold item in the shop.
 * @see seng201.team8.services.ShopManager#buyTower(int)
 * @see seng201.team8.services.ShopManager#buyUpgrade(int)
 */
public class BuyingNullError extends Exception{

    /**
     * Constructor for the error that sets the error message to the parameter error message {@link String}
     * @param errorMessage error message {@link String}
     */
    public BuyingNullError(String errorMessage){super(errorMessage);}
}
