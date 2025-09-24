package org.example.algorithms.sorting;

public class MergeSort {
    private static final int CUTOFF = 7;

    public static void sort(int[] array) {
        int[] buffer = new int[array.length];
        sort(array, buffer, 0, array.length - 1);
    }


    private static void sort(int[] arrayOfNumbers, int[] buffer, int low, int high) {
        if (high <= low + CUTOFF) {
            insertionSort(arrayOfNumbers, low, high);
            return;
        }

        int mid = low + (high - low) / 2;

//        Divide step
        sort(arrayOfNumbers, buffer, low, mid);
        sort(arrayOfNumbers, buffer, mid + 1, high);

//        Merge step
        merge(arrayOfNumbers, buffer, low, mid, high);
    }

    private static void merge(int[] arrayOfNumbers, int[] buffer, int low, int mid, int high) {
        for (int k = low; k <= high; k++) {
            buffer[k] = arrayOfNumbers[k];
        }
        int i = low;
        int j = mid + 1;
        for (int k = low; k <= high; k++) {
            if (i > mid) {
                arrayOfNumbers[k] = buffer[j++];
            } else if (j > high) {
                arrayOfNumbers[k] = buffer[i++];
            } else if (buffer[j] < buffer[i]) {
                arrayOfNumbers[k] = buffer[j++];
            } else {
                arrayOfNumbers[k] = buffer[i++];
            }
        }
    }

    private static void insertionSort(int[] arrayOfNumbers, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            int key = arrayOfNumbers[i];
            int j = i - 1;
            while (j >= low && arrayOfNumbers[j] > key) {
                arrayOfNumbers[j + 1] = arrayOfNumbers[j];
                j--;
            }
            arrayOfNumbers[j + 1] = key;
        }
    }
}

