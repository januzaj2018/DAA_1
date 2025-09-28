package org.example.algorithms.util;

import org.example.algorithms.geometry.ClosestPair;
import org.example.algorithms.geometry.ClosestPair.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClosestPairMetricsRunner {
    public static void main(String[] args) {
        List<Metrics> metricsList = new ArrayList<>();
        Random random = new Random();

        for (int n = 20; n <= 10000; n += 10) {
            Point[] points = new Point[n];
            for (int i = 0; i < n; i++) {
                points[i] = new Point(random.nextDouble() * 10000, random.nextDouble() * 10000);
            }
            Metrics metrics = new Metrics();
            new ClosestPair().findClosest(points, metrics);
            metricsList.add(metrics);
        }
        CsvWriter.writeMetrics("closestpair_metrics.csv", metricsList);
    }
}
