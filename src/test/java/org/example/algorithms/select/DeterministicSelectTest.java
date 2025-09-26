package org.example.algorithms.select;

import org.example.algorithms.util.Metrics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeterministicSelectTest {
    private Metrics tracker;

    @BeforeEach
    public void setUp() {
        tracker = new Metrics();
    }

    @Test
    void testSortedArray() {
        int[] arr = new int[100];
        for (int i = 0; i < arr.length; i++) arr[i] = i;
        for (int k = 0; k < arr.length; k++) {
            tracker.start();
            int result = DeterministicSelect.select(arr.clone(), k, tracker);
            tracker.stop();
            assertEquals(k, result);
            tracker.printMetrics("SortedArray k=" + k);
        }
    }

    @Test
    void testRandomArray() {
        int[] arr = new int[100];
        Random rand = new Random(42);
        for (int i = 0; i < arr.length; i++) arr[i] = rand.nextInt(1000);
        int[] sorted = arr.clone();
        Arrays.sort(sorted);
        for (int k = 0; k < arr.length; k++) {
            tracker.start();
            int result = DeterministicSelect.select(arr.clone(), k, tracker);
            tracker.stop();
            assertEquals(sorted[k], result);
            tracker.printMetrics("RandomArray k=" + k);
        }
    }

    @Test
    void testDuplicates() {
        int[] arr = new int[50];
        Arrays.fill(arr, 7);
        for (int k = 0; k < arr.length; k++) {
            tracker.start();
            int result = DeterministicSelect.select(arr.clone(), k, tracker);
            tracker.stop();
            assertEquals(7, result);
            tracker.printMetrics("Duplicates k=" + k);
        }
    }

    @Test
    void testSingleElement() {
        int[] arr = {42};
        tracker.start();
        int result = DeterministicSelect.select(arr.clone(), 0, tracker);
        tracker.stop();
        assertEquals(42, result);
        tracker.printMetrics("SingleElement");
    }

    @Test
    void testAllEqual() {
        int[] arr = new int[20];
        Arrays.fill(arr, 5);
        for (int k = 0; k < arr.length; k++) {
            tracker.start();
            int result = DeterministicSelect.select(arr.clone(), k, tracker);
            tracker.stop();
            assertEquals(5, result);
            tracker.printMetrics("AllEqual k=" + k);
        }
    }

    @Test
    void testMinMax() {
        int[] arr = {9, 2, 7, 4, 5};
        tracker.start();
        int min = DeterministicSelect.select(arr.clone(), 0, tracker);
        tracker.stop();
        assertEquals(2, min);
        tracker.printMetrics("Min");
        tracker.start();
        int max = DeterministicSelect.select(arr.clone(), arr.length - 1, tracker);
        tracker.stop();
        assertEquals(9, max);

        tracker.printMetrics("Max");
    }

}
