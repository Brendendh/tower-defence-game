package seng201.team8.services;

import seng201.team8.exceptions.NoSpaceException;
import seng201.team8.models.InventoryData;
import seng201.team8.models.Tower;

import java.util.ArrayList;

public class InventoryManager {
    private InventoryData inventoryData;

    public InventoryManager(){
        inventoryData = new InventoryData();
    }

    public InventoryManager(InventoryData inventoryData){
        this.inventoryData = inventoryData;
    }

    public InventoryData getInventoryData() {
        return inventoryData;
    }

    public void removeFromMain(int towerIndex){
        inventoryData.getMainTowers()[towerIndex] = null;
    }

    public void removeFromReserve(int towerIndex){
        inventoryData.getReserveTowers()[towerIndex] = null;
    }

    public void swapTowers(int tower1Index, int tower2Index){
        Tower[] tower1Array = inventoryData.getMainTowers();
        Tower[] tower2Array = inventoryData.getMainTowers();
        if (tower1Index > 4){
            tower1Array = inventoryData.getReserveTowers();
            tower1Index -= 4;
        }
        if (tower2Index > 4){
            tower2Array= inventoryData.getReserveTowers();
            tower2Index -=4;
        }
        Tower tower1 = tower1Array[tower1Index];
        Tower tower2 = tower2Array[tower2Index];
        tower1Array[tower1Index] = tower2;
        tower2Array[tower2Index] = tower1;
    }

    public void applyUpgradeTo(int upgradeIndex, ArrayList<Tower> towers){
        inventoryData.getUpgrades().get(upgradeIndex).getEffect().affects(towers);
        inventoryData.getUpgrades().remove(upgradeIndex);
    }

    public void removeUpgrade(int upgradeIndex){
        inventoryData.getUpgrades().remove(upgradeIndex);
    }

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
}
