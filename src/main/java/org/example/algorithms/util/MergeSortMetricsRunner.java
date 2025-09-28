package org.example.algorithms.util;

import org.example.algorithms.sorting.MergeSort;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MergeSortMetricsRunner {
    public static void main(String[] args) {
        List<Metrics> metricsList = new ArrayList<>();
        Random random = new Random();

        for (int n = 20; n <= 10000; n += 10) {
            int[] array = random.ints(n, 0, 10000).toArray();
            Metrics metrics = new Metrics();
            MergeSort.sort(array, metrics);
            metricsList.add(metrics);
        }
        CsvWriter.writeMetrics("mergesort_metrics.csv", metricsList);
    }
}
