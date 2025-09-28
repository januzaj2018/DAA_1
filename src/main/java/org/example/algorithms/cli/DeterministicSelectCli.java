package org.example.algorithms.cli;

import org.example.algorithms.select.DeterministicSelect;

import java.util.*;
import java.io.*;

public class DeterministicSelectCli {
    public static void main(String[] args) throws Exception {
        if (args.length == 0 || Arrays.asList(args).contains("--help")) {
            printHelp();
            return;
        }
        int[] arr = null;
        int n = 0;
        int k = -1;
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
                case "--k":
                    k = Integer.parseInt(args[++i]);
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
        if (k < 0) k = n + k;
        int[] arrCopy = Arrays.copyOf(arr, arr.length);
        org.example.algorithms.util.Metrics metrics = new org.example.algorithms.util.Metrics();
        int result = org.example.algorithms.select.DeterministicSelect.select(arrCopy, k, metrics);
        String output = "k-th smallest (k=" + k + "): " + result;
        if (out != null) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(out))) {
                writer.println(output);
            }
            System.out.println("Result written to " + out);
        } else {
            System.out.println(output);
        }
        if (metricsOut != null) {
            java.util.List<org.example.algorithms.util.Metrics> metricsList = new java.util.ArrayList<>();
            metricsList.add(metrics);
            org.example.algorithms.util.CsvWriter.writeMetrics(metricsOut, metricsList);
            System.out.println("Metrics written to " + metricsOut);
        }
    }

    private static void printHelp() {
        System.out.println("Usage: java -cp ... org.example.DeterministicSelectCli --array <comma-separated> | --n <size> [--k <order>] [--out <file>] [--metrics <csvfile>]");
        System.out.println("  --k <order>: 0 = smallest, n-1 = largest, -1 = largest, -2 = 2nd largest, etc.");
    }
}
