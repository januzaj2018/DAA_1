package org.example.algorithms.sorting;

import org.example.algorithms.util.Metrics;

public class MergeSort {
    private static final int CUTOFF = 7;

    public static void sort(int[] array, Metrics metrics) {
        if (array == null || array.length < 2) {
            return;
        }
        metrics.setArraySize(array.length);
        metrics.start();
        metrics.allocationIncrement();
        int[] buffer = new int[array.length];
        sort(array, buffer, 0, array.length - 1, metrics);
        metrics.stop();
    }


    private static void sort(int[] arrayOfNumbers, int[] buffer, int low, int high, Metrics metrics) {
        metrics.trackRecursionStart();
        try {
            if (high <= low + CUTOFF) {
                insertionSort(arrayOfNumbers, low, high,metrics);
                return;
            }

            int mid = low + (high - low) / 2;

//        Divide step
            sort(arrayOfNumbers, buffer, low, mid, metrics);
            sort(arrayOfNumbers, buffer, mid + 1, high, metrics);
            metrics.comparisonIncrement();
            if (arrayOfNumbers[mid] <= arrayOfNumbers[mid + 1]) {
                return;
            }
//        Merge step
            merge(arrayOfNumbers, buffer, low, mid, high,metrics);
        } finally {
            metrics.trackRecursionEnd();
        }
    }

    private static void merge(int[] arrayOfNumbers, int[] buffer, int low, int mid, int high, Metrics metrics) {
        if (high + 1 - low >= 0) {
            System.arraycopy(arrayOfNumbers, low, buffer, low, high + 1 - low);
        }
        metrics.allocationIncrement();
        int i = low;
        int j = mid + 1;
        for (int k = low; k <= high; k++) {
            if (i > mid) {
                arrayOfNumbers[k] = buffer[j++];
            } else if (j > high) {
                arrayOfNumbers[k] = buffer[i++];
            } else {
                metrics.comparisonIncrement();
                if (buffer[j] < buffer[i]) {
                    arrayOfNumbers[k] = buffer[j++];
                } else {
                    arrayOfNumbers[k] = buffer[i++];
                }
            }
        }
    }

    private static void insertionSort(int[] arrayOfNumbers, int low, int high,Metrics metrics) {
        for (int i = low + 1; i <= high; i++) {
            int key = arrayOfNumbers[i];
            int j = i - 1;
            while (j >= low && arrayOfNumbers[j] > key) {
                metrics.comparisonIncrement();
                arrayOfNumbers[j + 1] = arrayOfNumbers[j];
                j--;
            }
            arrayOfNumbers[j + 1] = key;
        }
    }
}

