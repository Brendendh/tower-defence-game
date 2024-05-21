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
 *                 -{@link Rarity#rarityTextColorHex}
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
    COMMON(1, "#000000"),
    /**
     * Rare rarity of an item
     * <p>
     *     Rarity text color is gold
     *     <p>
     *         Stat multiplier is 2
     *     </p>
     * </p>
     */
    RARE(2, "#FFA500"),
    /**
     * Epic rarity of an item
     * <p>
     *     Rarity text color is Purple
     *     <p>
     *         Stat multiplier is 3
     *     </p>
     * </p>
     */
    EPIC(3, "#A020F0");

    /**
     * An integer stat multiplier used when calculating item attributes
     * and buying/selling price.
     */
    private final int rarityStatMultiplier;
    /**
     * The color hexcode of the text that will be displayed when an
     * item is of a specific rarity.
     */
    private final String rarityTextColorHex;

    /**
     * The constructor for Rarity
     * <p>
     *     Takes in an Integer rarityStatMultiplier and a
     *     Color rarityTextColor
     * </p>
     * @param rarityStatMultiplier An integer
     * @param rarityTextColor A Color Hex Code
     */
    Rarity(int rarityStatMultiplier, String rarityTextColor){
        this.rarityStatMultiplier = rarityStatMultiplier;
        this.rarityTextColorHex = rarityTextColor;
    }

    /**
     * Getter for the rarity's text color hex code
     * @return Color object
     */

    public String getRarityTextColor() {
        return rarityTextColorHex;
    }

    /**
     * Getter for the rarity's stat multiplier
     * @return An Integer
     */

    public int getRarityStatMultiplier() {
        return rarityStatMultiplier;
    }
}
