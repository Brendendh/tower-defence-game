package seng201.team8.exceptions;

/**
 * An exception thrown when the player tries to buy a tower without having an empty tower slot in their inventory.
 * @see seng201.team8.services.ShopManager#buyTower(int)
 */
public class NoSpaceException extends Exception{

    /**
     * Constructor for the error that sets the error message to the parameter error message {@link String}
     * @param message error message {@link String}
     */
    public NoSpaceException(String message){
        super(message);
    }
}
