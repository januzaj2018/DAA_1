package org.example.algorithms.util;

import org.example.algorithms.select.DeterministicSelect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DeterministicSelectMetricsRunner {
    public static void main(String[] args) {
        List<Metrics> metricsList = new ArrayList<>();
        Random random = new Random();

        for (int n = 20; n <= 10000; n += 10) {
            int[] arr = random.ints(n, 0, 10000).toArray();
            int k = n / 2; // median
            Metrics metrics = new Metrics();
            DeterministicSelect.select(arr.clone(), k, metrics);
            metricsList.add(metrics);
        }
        CsvWriter.writeMetrics("deterministicselect_metrics.csv", metricsList);
    }
}
