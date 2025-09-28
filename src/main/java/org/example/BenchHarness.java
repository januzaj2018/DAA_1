package org.example;

import org.example.algorithms.sorting.MergeSort;
import org.example.algorithms.select.DeterministicSelect;
import org.example.algorithms.util.Metrics;

import java.util.Arrays;
import java.util.Random;

public class BenchHarness {
    public static void main(String[] args) {
        int maxN = 10000;
        int step = 1000;
        int trials = 5;
        Random random = new Random();
        System.out.println("n,select_ns,sort_ns,select_cmp,sort_cmp,select_depth,sort_depth");
        for (int n = step; n <= maxN; n += step) {
            long selectTime = 0, sortTime = 0;
            long selectCmp = 0, sortCmp = 0;
            long selectDepth = 0, sortDepth = 0;
            for (int t = 0; t < trials; t++) {
                int[] arr = random.ints(n, 0, 100000).toArray();
                int[] arrCopy = Arrays.copyOf(arr, arr.length);
                int k = n / 2;
                Metrics mSel = new Metrics();
                DeterministicSelect.select(arr, k, mSel);
                selectTime += mSel.getElapsedTime();
                selectCmp += mSel.getComparisons();
                selectDepth += mSel.getMaxRecursionDepth();
                Metrics mSort = new Metrics();
                MergeSort.sort(arrCopy, mSort);
                sortTime += mSort.getElapsedTime();
                sortCmp += mSort.getComparisons();
                sortDepth += mSort.getMaxRecursionDepth();
            }
            System.out.printf("%d,%d,%d,%d,%d,%d,%d\n",
                    n,
                    selectTime / trials,
                    sortTime / trials,
                    selectCmp / trials,
                    sortCmp / trials,
                    selectDepth / trials,
                    sortDepth / trials);
        }
    }
}

