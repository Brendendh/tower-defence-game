package seng201.team8.models.effects;

import seng201.team8.models.*;

import java.util.ArrayList;

/**
 * An interface for the different effects used in the construction of Upgrade class objects.
 * @see Upgrade
 * @see CooldownReduction
 * @see ExpBoost
 * @see RepairTower
 * @see ResourceAmountBoost
 */
public interface Effect {
    /**
     * Applies the effect to each tower in an ArrayList of towers
     * @param towers an ArrayList of towers to be affected
     */
    void affects(ArrayList<Tower> towers);

    /**
     * Returns the name of the effect
     * @return name String of the effect
     */
    String getEffectName();
}
