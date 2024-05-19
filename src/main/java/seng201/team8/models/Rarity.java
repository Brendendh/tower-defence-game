package seng201.team8.models;

import javafx.scene.paint.Color;

/**
 * An enumerator class for an item's Rarity
 * <p>
 *     Has attributes:
 *     <p>
 *         -Rarity name : Common, Rare and Epic
 *         <p>
 *             -{@link Rarity#rarityStatMultiplier}
 *             <p>
 *                 -{@link Rarity#rarityTextColor}
 *             </p>
 *         </p>
 *     </p>
 * </p>
 * Taken in as a parameter when constructing items.
 * @see Item
 */
public enum Rarity {
    /**
     * Common rarity of an item
     * <p>
     *     Rarity text color is black
     *     <p>
     *         Stat multiplier is 1
     *     </p>
     * </p>
     */
    COMMON(1, Color.color(0,0,0)),
    /**
     * Rare rarity of an item
     * <p>
     *     Rarity text color is gold
     *     <p>
     *         Stat multiplier is 2
     *     </p>
     * </p>
     */
    RARE(2, Color.color(1,0.6470588235,0)),
    /**
     * Epic rarity of an item
     * <p>
     *     Rarity text color is Purple
     *     <p>
     *         Stat multiplier is 3
     *     </p>
     * </p>
     */
    EPIC(3, Color.color(0.6274509804,0.1254901961,0.9411764706));

    /**
     * An integer stat multiplier used when calculating item attributes
     * and buying/selling price.
     */
    private final int rarityStatMultiplier;
    /**
     * The color of the text that will be displayed when an
     * item is of a specific rarity.
     */
    private final Color rarityTextColor;

    /**
     * The constructor for Rarity
     * <p>
     *     Takes in an Integer rarityStatMultiplier and a
     *     Color rarityTextColor
     * </p>
     * @param rarityStatMultiplier An integer
     * @param rarityTextColor A Color
     */
    Rarity(int rarityStatMultiplier, Color rarityTextColor){
        this.rarityStatMultiplier = rarityStatMultiplier;
        this.rarityTextColor = rarityTextColor;
    }

    /**
     * Getter for the rarity's text color
     * @return Color object
     */

    public Color getRarityTextColor() {
        return rarityTextColor;
    }

    /**
     * Getter for the rarity's stat multiplier
     * @return An Integer
     */

    public int getRarityStatMultiplier() {
        return rarityStatMultiplier;
    }
}
