package seng201.team8.models.effects;

import seng201.team8.models.Rarity;
import seng201.team8.models.Tower;
import seng201.team8.models.Upgrade;

import java.util.List;

/**
 * Repairs a destroyed {@link Tower}.
 * <p></p>
 * One of the possible effects an {@link Upgrade} can have. The number of
 * towers targetable scales based on {@link Rarity}.
 */
public class RepairTower implements Effect, Cloneable{
    /**
     * Repairs each tower in towers. If the tower is already
     * repaired then it does nothing, but is still consumed.
     * @param towers a List of Towers to apply the effect to
     * @see Tower
     */

    @Override
    public void affects(List<Tower> towers){
        for (Tower tower: towers){
            tower.setBroken(false);
        }
    }
    /**
     * Creates and returns a deep copy of this effect.
     * @return {@link RepairTower}
     * @see Cloneable
     */
    @Override
    public RepairTower clone(){
        try{
            return (RepairTower) super.clone();
        }
        catch (CloneNotSupportedException e){
            return new RepairTower();
        }
    }

    /**
     * Getter for the effect name
     * @return The string "Repair Tower"
     */
    @Override
    public String getEffectName(){
        return "Repair Tower";
    }

    /**
     * Getter for the string description of the effect
     * @return The string "Repairs broken towers"
     */

    public String toString(){
        return "Repairs broken towers";
    }

}
