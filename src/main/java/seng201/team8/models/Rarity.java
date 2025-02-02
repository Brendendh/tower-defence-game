package seng201.team8.models;

/**
 * An enumerator class for an item's Rarity
 * <br><br>
 *     Has attributes:
 *     <br><br>
 *         -Rarity name : Common, Rare and Epic
 *         <br><br>
 *             -{@link Rarity#rarityStatMultiplier}
 *             <br><br>
 *                 -{@link Rarity#rarityTextColorHex}
 *
 *
 *
 *
 * Taken in as a parameter when constructing items.
 * @see Item
 */
public enum Rarity {
    /**
     * Common rarity of an item
     * <br><br>
     *     Rarity text color is black
     *     <br><br>
     *         Stat multiplier is 1
     *
     *
     */
    COMMON(1, "#000000"),
    /**
     * Rare rarity of an item
     * <br><br>
     *     Rarity text color is gold
     *     <br><br>
     *         Stat multiplier is 2
     *
     *
     */
    RARE(2, "#FFA500"),
    /**
     * Epic rarity of an item
     * <br><br>
     *     Rarity text color is Purple
     *     <br><br>
     *         Stat multiplier is 3
     *
     *
     */
    EPIC(3, "#A020F0");

    /**
     * An integer stat multiplier used when calculating item attributes
     * and buying/selling price.
     */
    private final int rarityStatMultiplier;
    /**
     * The color hex code of the text that will be displayed when an
     * item is of a specific rarity.
     */
    private final String rarityTextColorHex;

    /**
     * The constructor for Rarity
     * <br><br>
     *     Takes in an Integer rarityStatMultiplier and a
     *     Color rarityTextColor
     *
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
