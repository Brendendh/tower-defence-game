package seng201.team8.services;

import seng201.team8.exceptions.NoSpaceException;
import seng201.team8.models.dataRecords.InventoryData;
import seng201.team8.models.Tower;
import seng201.team8.models.Upgrade;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The service class for InventoryManager. Created during the Game Setup Screen after the
 * player has chosen starting towers.
 * <p></p>
 * Serves as a manager class which provides methods for the {@link InventoryData} object.
 * These include transferring {@link Tower}s and {@link Upgrade}s,
 * applying {@link Upgrade}s and checking {@link Tower} names. Frequently accessed by services which
 * needs to modify the {@link InventoryData}. Stored in the GameManager.
 * <p></p>
 * @see GameManager
 */
public class InventoryManager {
    /**
     * The {@link InventoryData} which stores the
     * players towers and upgrades.
     */
    private final InventoryData inventoryData;

    /**
     * The constructor for {@link InventoryManager} for testing purposes.
     * <p></p>
     * Creates an empty {@link InventoryData} and stores it inside the InventoryManager.
     */
    public InventoryManager(){
        inventoryData = new InventoryData();
    }

    /**
     * The constructor for {@link InventoryManager}.
     * <p></p>
     * Takes in an {@link InventoryData} as a parameter and stores it inside the InventoryManager;
     * @param inventoryData {@link InventoryData}
     */
    public InventoryManager(InventoryData inventoryData){
        this.inventoryData = inventoryData;
    }

    /**
     * Getter for {@link InventoryManager#inventoryData}
     * @return {@link InventoryData}
     */
    public InventoryData getInventoryData() {
        return inventoryData;
    }

    /**
     * Removes a {@link Tower} from the main towers by its index.
     * This is done by setting the {@link Tower} object on the index to null.
     * @param towerIndex {@link Integer}
     */
    public void removeFromMain(int towerIndex){
        inventoryData.getMainTowers()[towerIndex] = null;
    }

    /**
     * Removes a {@link Tower} from the reserve towers by its index.
     * This is done by setting the {@link Tower} object on the index to null.
     * @param towerIndex {@link Integer}
     */
    public void removeFromReserve(int towerIndex){
        inventoryData.getReserveTowers()[towerIndex] = null;
    }

    /**
     * Swaps the {@link Tower}s between two given {@link Integer} indexes.
     * <p></p>
     * Indexes which are bigger or equal to the size of main towers are converted to
     * reserve tower indexes. This is done by taking away the size of the main towers from the index.
     * @param tower1Index {@link Integer}
     * @param tower2Index {@link Integer}
     */
    public void swapTowers(int tower1Index, int tower2Index){
        Tower[] tower1Array = inventoryData.getMainTowers();
        Tower[] tower2Array = inventoryData.getMainTowers();
        if (tower1Index >= inventoryData.getMainTowers().length){
            tower1Array = inventoryData.getReserveTowers();
            tower1Index -= inventoryData.getMainTowers().length;
        }
        if (tower2Index >= inventoryData.getMainTowers().length){
            tower2Array= inventoryData.getReserveTowers();
            tower2Index -= inventoryData.getMainTowers().length;
        }
        Tower tower1 = tower1Array[tower1Index];
        Tower tower2 = tower2Array[tower2Index];
        tower1Array[tower1Index] = tower2;
        tower2Array[tower2Index] = tower1;
    }

    /**
     * Applies an {@link Upgrade} given by an {@link Integer} index to a
     * given {@link List} of {@link Tower}s.
     * <p></p>
     * After the upgrade is used, the upgrade is removed.
     * @param upgradeIndex {@link Integer}
     * @param towers An {@link List} of {@link Tower}s
     */
    public void applyUpgradeTo(int upgradeIndex, List<Tower> towers){
        inventoryData.getUpgrades().get(upgradeIndex).getEffect().affects(towers);
        inventoryData.getUpgrades().remove(upgradeIndex);
    }

    /**
     *Removes a {@link Upgrade} from the stored upgrades by its index.
     *This is done by removing the {@link Upgrade} in the {@link List}.
     * @param upgradeIndex {@link Integer}
     */
    public void removeUpgrade(int upgradeIndex){
        inventoryData.getUpgrades().remove(upgradeIndex);
    }

    /**
     * Moves a given {@link Tower} to the reserve towers.
     * <p></p>
     * Checks for a null element which represents an empty spot in the reserve tower array.
     * @param tower {@link Tower}
     * @throws NoSpaceException {@link NoSpaceException} if there is no space left in the reserve tower array.
     */
    public void moveToReserve(Tower tower) throws NoSpaceException {
        Tower[] reserveTowers = inventoryData.getReserveTowers();

        for(int i=0; i < reserveTowers.length; i++) {
            if (reserveTowers[i] == null) {
                reserveTowers[i] = tower;
                return;
            }
        }

        throw new NoSpaceException("There are no spaces left in the reserve towers");
    }

    /**
     * Moves a given {@link Tower} to the main towers.
     * <p></p>
     * Checks for a null element which represents an empty spot in the main tower array.
     * @param tower {@link Tower}
     * @throws NoSpaceException {@link NoSpaceException} if there is no space left in the main tower array.
     */
    public void moveToMain(Tower tower) throws NoSpaceException {
        Tower[] mainTowers = inventoryData.getMainTowers();

        for(int i=0; i < mainTowers.length; i++) {
            if (mainTowers[i] == null) {
                mainTowers[i] = tower;
                return;
            }
        }

        throw new NoSpaceException("There are no spaces left in the main towers");
    }

    /**
     * Checks if the {@link String} name matches the following conditions:
     *   - No special characters except for " " and "_".
     *   - Between 3 and 15 characters.
     * returns True if the conditions are matched
     * @param name {@link String}
     * @return {@link Boolean}
     */
    public boolean checkName(String name){
        Pattern pattern = Pattern.compile("\\w[\\w ]{2,14}");
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    /**
     * Adds an {@link Upgrade} to the {@link List} in the {@link InventoryData}.
     * @param upgrade {@link Upgrade}
     */
    public void addUpgrade(Upgrade upgrade){
        inventoryData.getUpgrades().add(upgrade);
    }
}
