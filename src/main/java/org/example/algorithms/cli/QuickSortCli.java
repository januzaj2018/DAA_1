package org.example.algorithms.cli;

import org.example.algorithms.sorting.QuickSort;

import java.util.*;
import java.io.*;

public class QuickSortCli {
    public static void main(String[] args) throws Exception {
        if (args.length == 0 || Arrays.asList(args).contains("--help")) {
            printHelp();
            return;
        }
        int[] arr = null;
        int n = 0;
        String out = null;
        String metricsOut = null;
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--array":
                    String arrStr = args[++i].replaceAll("[\\[\\](){}\\s]", "");
                    arr = Arrays.stream(arrStr.split(",")).filter(s -> !s.isEmpty()).mapToInt(Integer::parseInt).toArray();
                    n = arr.length;
                    break;
                case "--n":
                    n = Integer.parseInt(args[++i]);
                    break;
                case "--out":
                    out = args[++i];
                    break;
                case "--metrics":
                    metricsOut = args[++i];
                    break;
            }
        }
        if (arr == null && n > 0) {
            arr = new Random().ints(n, 0, 10000).toArray();
        }
        if (arr == null) {
            printHelp();
            return;
        }
        int[] arrCopy = Arrays.copyOf(arr, arr.length);
        org.example.algorithms.util.Metrics metrics = new org.example.algorithms.util.Metrics();
        QuickSort.sort(arrCopy, metrics);
        String result = Arrays.toString(arrCopy);
        if (out != null) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(out))) {
                writer.println(result);
            }
            System.out.println("Sorted array written to " + out);
        } else {
            System.out.println("Sorted array: " + result);
        }
        if (metricsOut != null) {
            java.util.List<org.example.algorithms.util.Metrics> metricsList = new java.util.ArrayList<>();
            metricsList.add(metrics);
            org.example.algorithms.util.CsvWriter.writeMetrics(metricsOut, metricsList);
            System.out.println("Metrics written to " + metricsOut);
        }
    }

    private static void printHelp() {
        System.out.println("Usage: java -cp ... org.example.QuickSortCli --array <comma-separated> | --n <size> [--out <file>] [--metrics <csvfile>]");
    }
}
