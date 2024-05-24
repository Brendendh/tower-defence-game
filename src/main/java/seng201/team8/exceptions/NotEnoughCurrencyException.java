package seng201.team8.exceptions;

/**
 * An exception thrown when the player tries to buy an Item but does not have enough currency for it.
 * @see seng201.team8.services.ShopManager#buyTower(int)
 * @see seng201.team8.services.ShopManager#buyUpgrade(int)
 * @see seng201.team8.services.ShopManager#refresh()
 */
public class NotEnoughCurrencyException extends Exception{

    /**
     * Constructor for the error that sets the error message to the parameter error message {@link String}
     * @param errorMessage error message {@link String}
     */
    public NotEnoughCurrencyException(String errorMessage){
        super(errorMessage);
    }
}
