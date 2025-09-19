package org.example.algorithms.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CsvWriter {
    public static void writeMetrics(String filename, List<Metrics> metricsList) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("n, time_ns, comparisons, allocations, max_recursion_depth");
            for (Metrics metrics : metricsList) {
                writer.printf("%d, %d, %d, %d, %d%n",
                        metrics.getArraySize(),
                        metrics.getElapsedTime(),
                        metrics.getComparisons(),
                        metrics.getAllocations(),
                        metrics.getMaxRecursionDepth());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
