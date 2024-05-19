package seng201.team8.models;

/**
 * The enumerator Resource for the different resource types in the game
 * <p></p>
 * Each resource type has the attributes:
 * <p>
 *     One of the resource names: CORN, WOOD or IRON
 *     <p>
 *         Resource value: {@link Resource#resourceValue}
 *     </p>
 * </p>
 * Taken in as a parameter when constructing TowerStats and Cart.
 * {@link TowerStats#TowerStats(int, Resource, int)}
 * {@link Cart#Cart(int, Resource, int)}
 * @see TowerStats
 * @see Cart
 */

public enum Resource {
    /**
     * Corn resource type with resource value: 1
     */
    CORN(1),
    /**
     * Wood resource type with resource value: 4
     */
    WOOD(4),
    /**
     * Iron resource type with resource value: 10
     */
    IRON(10);

    /**
     * An integer representing the resource's respective
     * resource value.
     * <p></p>
     *     This value is used when trying to calculate the different
     *     resource amount as each resource type scales differently.
     * <p></p>
     */
    final private int resourceValue;

    /**
     * The constructor for a Resource enum
     * <p></p>
     * Takes in an integer to set the resource value of
     * the resource to
     * @param resourceValue An integer
     */
    Resource(int resourceValue) {this.resourceValue = resourceValue;}

    /**
     * Getter for the resource's resourceValue
     * @return an Integer
     */
    public int getResourceValue(){
        return resourceValue;
    }

}
