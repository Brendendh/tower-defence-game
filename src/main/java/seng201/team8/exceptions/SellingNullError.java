package seng201.team8.exceptions;

/**
 * An exception thrown when a player tries to sell nothing.
 * @see seng201.team8.services.ShopManager#sellMainTower(int) 
 * @see seng201.team8.services.ShopManager#sellReserveTower(int) 
 * @see seng201.team8.services.ShopManager#sellUpgrade(int)
 */

public class SellingNullError extends Exception{
    /**
     * Constructor for the error that sets the error message to the parameter error message {@link String}
     * @param errorMessage error message {@link String}
     */
    public SellingNullError(String errorMessage){
        super(errorMessage);
    }
}
