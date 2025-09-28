package org.example.algorithms.util;

import org.example.algorithms.sorting.QuickSort;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuickSortMetricsRunner {
    public static void main(String[] args) {
        // Implementation would be similar to MergeSortMetricsRunner
        // but using QuickSort and collecting metrics accordingly.
        List<Metrics> metricsList = new ArrayList<>();
        Random random = new Random();

        for (int n =20; n <= 10000; n += 10) {
            int[] array = random.ints(n, 0, 10000).toArray();
            Metrics metrics = new Metrics();
            QuickSort.sort(array, metrics); // Assuming QuickSort has a similar method
            metricsList.add(metrics);
        }
        CsvWriter.writeMetrics("quicksort_metrics.csv", metricsList);
    }
}
