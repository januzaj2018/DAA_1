package org.example.algorithms.select;

import org.example.algorithms.util.Metrics;

public class DeterministicSelect {
    public static int select(int[] arr, int k, Metrics metrics) {
        if (arr == null || arr.length == 0 || k < 0 || k >= arr.length)
            throw new IllegalArgumentException();
        metrics.setArraySize(arr.length);
        metrics.start();
        int result = selectHelper(arr, 0, arr.length - 1, k, metrics);
        metrics.stop();
        return result;
    }

    private static int selectHelper(int[] arr, int low, int high, int k, Metrics metrics) {
        try {
            metrics.trackRecursionStart();
            while (low < high) {
                int pivot = medianOfMedians(arr, low, high, metrics);
                int[] bounds = threeWayPartition(arr, low, high, pivot, metrics);
                int lt = bounds[0];
                int gt = bounds[1];
                int lessSize = lt - low;
                int eqSize = gt - lt + 1;
                int lessEqSize = lessSize + eqSize;
                if (k < lessSize) {
                    int discardedSize = (high - low + 1) - lessSize;
                    if (lessSize <= discardedSize) {
                        return selectHelper(arr, low, lt - 1, k, metrics);
                    } else {
                        high = lt - 1;
                    }
                } else if (k < lessEqSize) {
                    return arr[lt];
                } else {
                    int greaterSize = high - gt;
                    int discardedSize = (high - low + 1) - greaterSize;
                    if (greaterSize <= discardedSize) {
                        return selectHelper(arr, gt + 1, high, k - lessEqSize, metrics);
                    } else {
                        low = gt + 1;
                        k -= lessEqSize;
                    }
                }
            }
            return arr[low];
        } finally {
            metrics.trackRecursionEnd();
        }
    }

    private static int medianOfMedians(int[] arr, int low, int high, Metrics metrics) {
        int n = high - low + 1;
        if (n <= 5) {
            insertionSort(arr, low, high, metrics);
            return arr[low + n / 2];
        }
        int numGroups = (n + 4) / 5;
        int[] medians = new int[numGroups];
        metrics.allocationIncrement(); // For medians array
        for (int i = 0; i < numGroups; i++) {
            int groupLow = low + i * 5;
            int groupHigh = Math.min(groupLow + 4, high);
            insertionSort(arr, groupLow, groupHigh, metrics);
            medians[i] = arr[(groupLow + groupHigh) / 2];
        }
        // Recurse to find median of medians
        return selectHelper(medians, 0, numGroups - 1, numGroups / 2, metrics);
    }

    private static int[] threeWayPartition(int[] arr, int low, int high, int pivot, Metrics metrics) {
        // Find and swap one occurrence of pivot to high
        boolean found = false;
        for (int j = low; j <= high; j++) {
            metrics.comparisonIncrement();
            if (arr[j] == pivot) {
                swap(arr, j, high);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new RuntimeException("Pivot not found in array");
        }
        int pivotVal = arr[high];
        int lt = low;
        int gt = high;
        int i = low;
        while (i <= gt) {
            metrics.comparisonIncrement();
            if (arr[i] < pivotVal) {
                swap(arr, lt, i);
                lt++;
                i++;
            } else if (arr[i] > pivotVal) {
                swap(arr, i, gt);
                gt--;
            } else {
                i++;
            }
        }
        return new int[]{lt, gt};
    }

    private static void insertionSort(int[] arr, int low, int high, Metrics metrics) {
        for (int i = low + 1; i <= high; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= low) {
                metrics.comparisonIncrement();
                if (arr[j] > key) {
                    arr[j + 1] = arr[j];
                    j--;
                } else {
                    break;
                }
            }
            arr[j + 1] = key;
        }
    }

    private static void swap(int[] arr, int i, int j) {
        if (i != j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }
}
