package seng201.team8.models;

/**
 * A model for the different items in the game such as Tower and Upgrade
 * <p></p>
 * Makes sure that all items have the same required attributes and methods.
 * Attributes such as:
 * <p>
 *     -Buying price
 *     <p>
 *     -Selling price
 *     <p>
 *     -Rarity
 * </p>
 * @see Upgrade
 * @see Tower
 */
public class Item {
    /**
     * The integer buying price of the item.
     * <p>
     *     Can mean either points or money depending
     *     on the item type.
     *     <p>
     *         If its a Tower, it means money
     *     </p>
     *      If its an Upgrade, it means points
     * </p>
     * @see Tower
     * @see Upgrade
     */
    private int buyingPrice;
    /**
     * The integer selling price of the item.
     * <p>
     *     Can mean either points or money depending
     *     on the item type.
     *     <p>
     *         If its a Tower, it means money
     *     </p>
     *      If its an Upgrade, it means points
     * </p>
     * @see Tower
     * @see Upgrade
     */
    private int sellingPrice;
    /**
     * The rarity of the item.
     * @see Rarity
     */
    private Rarity rarity;

    /**
     * The constructor for an Item.
     * <p>
     *     Called by the constructors of Upgrade and Tower to
     *     set their respective Rarity, buying price and selling price.
     *     <p></p>
     *     {@link Tower#Tower(String, TowerStats, int, Rarity)}
     *     {@link Upgrade#Upgrade(Effect, Rarity, int, int)}
     * </p>
     * </p>
     * @param buyingPrice an Integer to set the buying price to
     * @param sellingPrice an Integer to set the selling price to
     * @param rarity a Rarity enum to set the rarity to
     */
    public Item(int buyingPrice, int sellingPrice, Rarity rarity){
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
        this.rarity = rarity;
    }

    /**
     * Takes in an Integer buyingPrice to set the buying price to
     * @param buyingPrice An Integer
     */
    public void setBuyingPrice(int buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    /**
     * Takes in an Integer sellingPrice to set the selling price to
     * @param sellingPrice An Integer
     */
    public void setSellingPrice(int sellingPrice){
        this.sellingPrice = sellingPrice;
    }

    /**
     * The getter for the item's buying price
     * @return An Integer
     */
    public int getBuyingPrice() {
        return buyingPrice;
    }

    /**
     * The getter for the item's selling price
     * @return An Integer
     */
    public int getSellingPrice() {
        return sellingPrice;
    }

    /**
     * The getter for the item's Rarity
     * @return Rarity Enum
     */
    public Rarity getRarity(){ return rarity;}
}