package org.example.algorithms.sorting;

import org.example.algorithms.util.Metrics;
import java.util.Random;

public class QuickSort {
    private static final Random RANDOM = new Random();

    public static void sort(int[] array, Metrics metrics) {
        if (array == null || array.length < 2) {
            return;
        }
        metrics.setArraySize(array.length);
        metrics.start();
        quickSort(array, 0, array.length - 1, metrics);
        metrics.stop();
    }

    private static void quickSort(int[] array, int low, int high, Metrics metrics) {
        while (low < high) {
            metrics.trackRecursionStart();
            int pivotIndex = randomizedPartition(array, low, high, metrics);
            // Recurse on smaller partition, iterate on larger
            if (pivotIndex - low < high - pivotIndex) {
                quickSort(array, low, pivotIndex - 1, metrics);
                low = pivotIndex + 1;
            } else {
                quickSort(array, pivotIndex + 1, high, metrics);
                high = pivotIndex - 1;
            }
            metrics.trackRecursionEnd();
        }
    }

    private static int randomizedPartition(int[] array, int low, int high, Metrics metrics) {
        int pivotIdx = low + RANDOM.nextInt(high - low + 1);
        swap(array, pivotIdx, high);
        metrics.allocationIncrement();
        return partition(array, low, high, metrics);
    }

    private static int partition(int[] array, int low, int high, Metrics metrics) {
        int pivot = array[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            metrics.comparisonIncrement();
            if (array[j] <= pivot) {
                i++;
                swap(array, i, j);
                metrics.allocationIncrement();
            }
        }
        swap(array, i + 1, high);
        metrics.allocationIncrement();
        return i + 1;
    }

    private static void swap(int[] array, int i, int j) {
        if (i != j) {
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
}
