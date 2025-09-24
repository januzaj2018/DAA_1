package org.example.algorithms.sorting;

import org.example.algorithms.util.Metrics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class MergeSortTest {
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
        MergeSort.sort(actual, tracker);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testSorted(){
        int[] actual = generateArray(100);
        int[] expected = Arrays.copyOf(actual, actual.length);
        Arrays.sort(actual);
        Arrays.sort(expected);
        MergeSort.sort(actual, tracker);
        assertArrayEquals(expected, actual);
    }
    @Test
    void testReverse(){
        int[] actual = generateArray(100);
        int[] expected = Arrays.copyOf(actual, actual.length);
        Arrays.sort(actual);
        for(int i=0; i<actual.length/2; i++){
            int temp = actual[i];
            actual[i] = actual[actual.length - 1 - i];
            actual[actual.length - 1 - i] = temp;
        }
        Arrays.sort(expected);
        MergeSort.sort(actual, tracker);
        assertArrayEquals(expected, actual);
    }
    @Test
    void testAdversarial(){
        int[] actual = new int[100];
        for(int i=0; i<actual.length; i++){
            actual[i] = i%10;
        }
        int[] expected = Arrays.copyOf(actual, actual.length);
        Arrays.sort(expected);
        MergeSort.sort(actual, tracker);
        assertArrayEquals(expected, actual);
    }
    private int[] generateArray(int size) {
        Random rand = new Random();
        return rand.ints(size, 0, 1000).toArray();
    }
}
