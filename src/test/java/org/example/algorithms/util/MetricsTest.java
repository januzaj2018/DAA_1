package org.example.algorithms.util;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MetricsTest {

    private Metrics metrics;

    @BeforeEach
    void setUp() {
        // Initialize a new Metrics object before each test
        metrics = new Metrics();
    }

    @Test
    void testStartAndStopTimer() throws InterruptedException {
        metrics.start();
        // Simulate some work being done for 10 milliseconds
        Thread.sleep(10);
        metrics.stop();
        // The time should be at least 10 milliseconds (10,000,000 nanoseconds)
        assertTrue(metrics.getElapsedTime() >= 10_000_000);
    }

    @Test
    void testComparisonsCounter() {
        assertEquals(0, metrics.getComparisons(), "Comparisons should start at 0");
        metrics.comparisonIncrement();
        assertEquals(1, metrics.getComparisons(), "Comparisons should be 1 after one increment");
        metrics.comparisonIncrement();
        metrics.comparisonIncrement();
        assertEquals(3, metrics.getComparisons(), "Comparisons should be 3 after three increments");
    }

    @Test
    void testAllocationsCounter() {
        assertEquals(0, metrics.getAllocations(), "Allocations should start at 0");
        metrics.allocationIncrement();
        assertEquals(1, metrics.getAllocations(), "Allocations should be 1 after one increment");
    }

    @Test
    void testRecursionDepthTracking() {
        assertEquals(0, metrics.getMaxRecursionDepth(), "Max depth should start at 0");

        metrics.trackRecursionStart(); // Depth 1
        assertEquals(1, metrics.getMaxRecursionDepth());
        assertEquals(1, metrics.getCurrentRecursionDepth());

        metrics.trackRecursionStart(); // Depth 2
        assertEquals(2, metrics.getMaxRecursionDepth());
        assertEquals(2, metrics.getCurrentRecursionDepth());

        metrics.trackRecursionEnd(); // Depth 1
        assertEquals(2, metrics.getMaxRecursionDepth(), "Max depth should not decrease");
        assertEquals(1, metrics.getCurrentRecursionDepth());
    }

    @Test
    void testArraySizeSetterAndGetter() {
        assertEquals(0, metrics.getArraySize(), "Array size should be 0 by default");

        metrics.setArraySize(100);
        assertEquals(100, metrics.getArraySize(), "Array size should be 100");

        metrics.setArraySize(10000);
        assertEquals(10000, metrics.getArraySize(), "Array size should be 10000");
    }
}
