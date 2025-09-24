package org.example.algorithms.sorting;

import org.example.algorithms.util.Metrics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class QuickSortTest {
    private Metrics tracker;

    @BeforeEach
    public void setUp() {
        tracker = new Metrics();
    }

    @Test
    void testRandom() {
        int[] actual = generateArray(100);
        int[] expected = Arrays.copyOf(actual, actual.length);
        Arrays.sort(expected);
        QuickSort.sort(actual, tracker);
        assertArrayEquals(expected, actual);
        tracker.printMetrics("Random");
    }

    @Test
    void testSorted() {
        int[] actual = generateArray(100);
        Arrays.sort(actual);
        int[] expected = Arrays.copyOf(actual, actual.length);
        QuickSort.sort(actual, tracker);
        assertArrayEquals(expected, actual);
        tracker.printMetrics("Sorted");
    }

    @Test
    void testReverse() {
        int[] actual = generateArray(100);
        Arrays.sort(actual);
        for (int i = 0; i < actual.length / 2; i++) {
            int temp = actual[i];
            actual[i] = actual[actual.length - 1 - i];
            actual[actual.length - 1 - i] = temp;
        }
        int[] expected = Arrays.copyOf(actual, actual.length);
        Arrays.sort(expected);
        QuickSort.sort(actual, tracker);
        assertArrayEquals(expected, actual);
        tracker.printMetrics("Reverse");
    }

    @Test
    void testAdversarial() {
        int[] actual = new int[100];
        for (int i = 0; i < actual.length; i++) {
            actual[i] = i % 10;
        }
        int[] expected = Arrays.copyOf(actual, actual.length);
        Arrays.sort(expected);
        QuickSort.sort(actual, tracker);
        assertArrayEquals(expected, actual);
        tracker.printMetrics("Adversarial");
    }

    @Test
    void testEmpty() {
        int[] actual = new int[0];
        int[] expected = new int[0];
        QuickSort.sort(actual, tracker);
        assertArrayEquals(expected, actual);
        tracker.printMetrics("Empty");
    }

    @Test
    void testSingleElement() {
        int[] actual = {42};
        int[] expected = {42};
        QuickSort.sort(actual, tracker);
        assertArrayEquals(expected, actual);
        tracker.printMetrics("SingleElement");
    }

    @Test
    void testAllEqual() {
        int[] actual = new int[50];
        Arrays.fill(actual, 7);
        int[] expected = new int[50];
        Arrays.fill(expected, 7);
        QuickSort.sort(actual, tracker);
        assertArrayEquals(expected, actual);
        tracker.printMetrics("AllEqual");
    }

    @Test
    void testLargeArray() {
        int[] actual = generateArray(10_000);
        int[] expected = Arrays.copyOf(actual, actual.length);
        Arrays.sort(expected);
        QuickSort.sort(actual, tracker);
        assertArrayEquals(expected, actual);
        tracker.printMetrics("LargeArray");
    }

    private int[] generateArray(int size) {
        Random random = new Random(42); // fixed seed for reproducibility
        return random.ints(size, 0, 10000).toArray();
    }
}
