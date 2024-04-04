package seng201.team8.models;

/**
 * Simple counter class to keep track of current count
 * @author seng201 teaching team
 */
public class Counter {
    private int count;

    /**
     * Constructor
     */
    public Counter() {
        count = 0;
    }

    /**
     * Get current counter count
     * @return Current count
     */
    public int getCount() {
        return count;
    }

    /**
     * Set current counter count
     * @param value Value of counter
     */
    public void setCount(int value) {
        count = value;
    }
}
