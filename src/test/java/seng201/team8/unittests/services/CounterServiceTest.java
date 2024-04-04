package seng201.team8.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team8.services.CounterService;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test Counter implementation
 * @author seng201 teaching team
 */
class CounterServiceTest {
    private CounterService testCounterService;

    /**
     * Setup before each test, we create two objects, one an actual
     * instance of our CounterService class, and another a mocked version
     * that has overridden methods.
     */
    @BeforeEach
    public void setupTest() {
        // Use CounterService directly
        testCounterService = new CounterService();
    }

    /**
     * Test incrementing the counter by one, normal JUnit test
     */
    @Test
    void testIncrement() {
        assertEquals(0, testCounterService.getCurrentCount());
        testCounterService.incrementCounter();
        assertEquals(1, testCounterService.getCurrentCount());
    }

}
