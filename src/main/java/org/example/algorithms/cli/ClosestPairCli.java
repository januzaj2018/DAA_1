package org.example.algorithms.cli;

import org.example.algorithms.geometry.ClosestPair;
import org.example.algorithms.geometry.ClosestPair.Point;

import java.util.*;
import java.io.*;

public class ClosestPairCli {
    public static void main(String[] args) throws Exception {
        if (args.length == 0 || Arrays.asList(args).contains("--help")) {
            printHelp();
            return;
        }
        Point[] points = null;
        int n = 0;
        String out = null;
        String metricsOut = null;
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--points":
                    String[] pairs = args[++i].split(";");
                    points = new Point[pairs.length];
                    for (int j = 0; j < pairs.length; j++) {
                        String[] xy = pairs[j].split(",");
                        points[j] = new Point(Double.parseDouble(xy[0]), Double.parseDouble(xy[1]));
                    }
                    n = points.length;
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
        if (points == null && n > 0) {
            points = new Point[n];
            Random random = new Random();
            for (int i = 0; i < n; i++) {
                points[i] = new Point(random.nextDouble() * 10000, random.nextDouble() * 10000);
            }
        }
        if (points == null) {
            printHelp();
            return;
        }
        // Print input points
        org.example.algorithms.util.Metrics metrics = new org.example.algorithms.util.Metrics();
        org.example.algorithms.geometry.ClosestPair.Result result = new org.example.algorithms.geometry.ClosestPair().findClosest(points, metrics);
        String output = String.format("Closest pair: (%.4f, %.4f) and (%.4f, %.4f)\nClosest pair distance: %.6f",
                result.p1.x, result.p1.y, result.p2.x, result.p2.y, result.distance);
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
        System.out.println("Usage: java -cp ... org.example.ClosestPairCli --points <x1,y1;x2,y2;...> | --n <size> [--out <file>] [--metrics <csvfile>]");
    }
}
