package seng201.team8.models.dataRecords;

import seng201.team8.models.Item;
import seng201.team8.models.Rarity;

/**
 * A model class for RarityData.
 * <p></p>
 * Responsible for holding an Array of different {@link Rarity} that determines
 * the probability of an {@link Item}'s generated rarity at different stages of the game.
 * Accessed by the ShopManager when generating the items sold in the shop.
 * @see seng201.team8.services.ShopManager
 * @see RarityData#earlyGameRarity
 * @see RarityData#midGameRarity
 * @see RarityData#lateGameRarity
 */

public class RarityData {
    /**
     * A sized 10 Array of {@link Rarity} which represent
     * the probability of rarities during rounds 1-5
     * <p>
     *     70% for Common, 30% for Rare
     * </p>
     */
    final private Rarity[] earlyGameRarity;
    /**
     * A sized 10 Array of {@link Rarity} which represent
     * the probability of rarities during rounds 6-10
     * <p>
     *     50% Common, 30% Rare, 20% Epic
     * </p>
     */
    final private Rarity[] midGameRarity;
    /**
     * A sized 10 Array of {@link Rarity} which represent
     * the probability of rarities during rounds 11-15
     * <p>
     *     30% Common,40% Rare, 30% Epic
     * </p>
     */
    final private Rarity[] lateGameRarity;

    public RarityData(){
        this.earlyGameRarity = new Rarity[]{Rarity.COMMON,Rarity.COMMON,Rarity.COMMON,Rarity.COMMON,Rarity.COMMON,Rarity.COMMON,Rarity.COMMON,Rarity.RARE,Rarity.RARE, Rarity.RARE};
        this.midGameRarity = new Rarity[]{Rarity.COMMON,Rarity.COMMON,Rarity.COMMON,Rarity.COMMON,Rarity.COMMON,Rarity.RARE,Rarity.RARE,Rarity.RARE,Rarity.EPIC,Rarity.EPIC};
        this.lateGameRarity = new Rarity[]{Rarity.COMMON, Rarity.COMMON,Rarity.COMMON,Rarity.RARE,Rarity.RARE,Rarity.RARE,Rarity.RARE,Rarity.EPIC,Rarity.EPIC,Rarity.EPIC};
    }

    /**
     * Returns the rarity chance for rounds 1-5
     * @return {@link RarityData#earlyGameRarity}
     */
    public Rarity[] getEarlyGameRarity() {
        return earlyGameRarity;
    }

    /**
     * Returns the rarity chance for rounds 6-10
     * @return {@link RarityData#midGameRarity}
     */
    public Rarity[] getMidGameRarity() {
        return midGameRarity;
    }

    /**
     * Returns the rarity chance for rounds 11-15
     * @return {@link RarityData#lateGameRarity}
     */
    public Rarity[] getLateGameRarity() {
        return lateGameRarity;
    }


}
