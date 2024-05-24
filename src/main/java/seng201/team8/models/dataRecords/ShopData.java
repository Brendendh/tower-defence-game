package seng201.team8.models.dataRecords;

import seng201.team8.models.Tower;
import seng201.team8.models.Upgrade;

/**
 * The model for the ShopData class.
 * <br><br>
 * Stores all the information relating to the shop. Such as:
 * <br><br>
 * The towers sold in the shop, {@link ShopData#towersSold}
 * <br><br>
 * The upgrades sold in the shop, {@link ShopData#upgradesSold}
 * <br><br>
 *
 * Created and managed by the ShopManager class and is stored in the GameManager.
 * @see seng201.team8.services.GameManager
 * @see seng201.team8.services.ShopManager
 */

public class ShopData {
    /**
     * An array of length 3 containing the {@link Tower} sold
     */
    private Tower[] towersSold;
    /**
     * An array of length 3 containing the {@link Upgrade} sold
     */
    private Upgrade[] upgradesSold;
    /**
     * An integer representing the round number in which the shop data
     * was created/last accessed.
     * <br><br>
     * The ShopData should remain the same when exiting and entering the
     * Shop screen during the same round. However, the shop should refresh
     * when it is accessed during the next round, hence this value will be compared
     * to the current round number stored in {@link GameData} as a means to check
     * if the current round is the next round and if it should refresh or not.
     * <br><br>
     * Once the shop is refreshed, this value updates to the current round number again.
     */

    private int initializedRoundNumber;

    /**
     * Takes in an Integer to set {@link ShopData#initializedRoundNumber} to
     * @param i An Integer
     */
    public void setInitializedRoundNumber(int i){
        this.initializedRoundNumber = i;
    }

    /**
     * The getter for {@link ShopData#initializedRoundNumber}
     * @return {@link ShopData#initializedRoundNumber}
     */
    public int getInitializedRoundNumber(){
        return initializedRoundNumber;
    }

    /**
     * Getter for the towers sold in the shop.
     * @return {@link ShopData#towersSold}
     */
    public Tower[] getTowersSold() {
        return towersSold;
    }

    /**
     * Getter for the upgrades sold in the shop
     * @return {@link ShopData#upgradesSold}
     */
    public Upgrade[] getUpgradesSold() {
        return upgradesSold;
    }

    /**
     *Takes in a size 3 array of towers to set the
     * {@link ShopData#towersSold} to.
     * @param towersSold A 3 sized array of {@link Tower}
     */
    public void setTowersSold(Tower[] towersSold) {
        this.towersSold = towersSold;
    }
    /**
     *Takes in a size 3 array of upgrades to set the
     * {@link ShopData#upgradesSold} to.
     * @param upgradesSold A 3 sized array of {@link Upgrade}
     */
    public void setUpgradesSold(Upgrade[] upgradesSold) {
        this.upgradesSold = upgradesSold;
    }
}
