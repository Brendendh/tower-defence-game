package seng201.team8.models;

/**
 * The enumerator Resource for the different resource types in the game
 * <br><br>
 * Each resource type has the attributes:
 * <br><br>
 *     One of the resource names: CORN, WOOD or IRON
 *     <br><br>
 *         Resource value: {@link Resource#resourceValue}
 *
 *
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
     * <br><br>
     *     This value is used when trying to calculate the different
     *     resource amount as each resource type scales differently.
     * <br><br>
     */
    final private int resourceValue;

    /**
     * The constructor for a Resource enum
     * <br><br>
     * Takes in an integer to set the resource value of
     * the resource to
     * @param resourceValue An integer
     */
    Resource(int resourceValue) {this.resourceValue = resourceValue;}

    /**
     * Getter for the resource's resourceValue
     * @return {@link Resource#resourceValue}
     */
    public int getResourceValue(){
        return resourceValue;
    }

}
