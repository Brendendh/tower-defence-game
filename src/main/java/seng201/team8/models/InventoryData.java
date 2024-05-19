package seng201.team8.models;

import seng201.team8.services.InventoryManager;

import java.util.ArrayList;

/**
 * A model that stores the player's inventory data which
 * includes the player's main towers, reserve towers and upgrades
 * <p></p>
 * Is created by, stored in and managed by InventoryManager
 * {@link InventoryManager#InventoryManager()}
 * @see seng201.team8.services.InventoryManager
 */
public class InventoryData {
    /**
     * An array of fixed size 5 that contains the player's
     * main towers Tower object.
     * <p></p>
     * If there is no tower as a specified slot, it is null
     * instead.
     * @see Tower
     */
    private Tower[] mainTowers;
    /**
     * An array of fixed size 5 that contains the player's
     * reserve towers Tower object.
     * <p></p>
     * If there is no tower as a specified slot, it is null
     * instead.
     * @see Tower
     */
    private Tower[] reserveTowers;
    /**
     * An ArrayList of the player's owned Upgrades.
     * @see Upgrade
     */
    private ArrayList<Upgrade> upgrades;

    /**
     * The constructor for InventoryData
     * <p></p>
     * Creates the mainTowers,reserveTowers arrays and
     * upgrades ArrayList when first constructed.
     */
    public InventoryData(){
        mainTowers = new Tower[5];
        reserveTowers = new Tower[5];
        upgrades = new ArrayList<>();
    }

    /**
     * Returns the array of reserve towers
     * @return Array of size 5
     */
    public Tower[] getReserveTowers() {
        return reserveTowers;
    }

    /**
     * Returns the array of main towers
     * @return Array of size 5
     */
    public Tower[] getMainTowers() {
        return mainTowers;
    }

    /**
     * Returns the ArrayList of upgrades
     * @return ArrayList containing upgrades
     */
    public ArrayList<Upgrade> getUpgrades() {
        return upgrades;
    }

    /**
     * Sets the reserveTowers to the input reserveTowers
     * @param reserveTowers an Array of Towers of size 5`
     * @see Tower
     */
    public void setReserveTowers(Tower[] reserveTowers) {
        this.reserveTowers = reserveTowers;
    }

    /**
     * Sets the mainTowers to the input mainTowers
     * @param mainTowers an Array of Towers of size 5
     * @see Tower
     */

    public void setMainTowers(Tower[] mainTowers) {
        this.mainTowers = mainTowers;
    }

    /**
     * Sets upgrades to the inputted ArrayList
     * @param upgrades an ArrayList of Upgrade
     * @see Upgrade
     */
    public void setUpgrades(ArrayList<Upgrade> upgrades) {
        this.upgrades = upgrades;
    }
}
