package org.example.algorithms.geometry;

import org.example.algorithms.util.Metrics;
import java.util.Arrays;
import java.util.Comparator;

public class ClosestPair {
    public static class Point implements Comparable<Point> {
        public double x;
        public double y;
        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
        @Override
        public int compareTo(Point other) {
            return Double.compare(this.x, other.x);
        }
    }

    public static class Result {
        public final Point p1, p2;
        public final double distance;
        public Result(Point p1, Point p2, double distance) {
            this.p1 = p1;
            this.p2 = p2;
            this.distance = distance;
        }
    }

    private double distance(Point p1, Point p2, Metrics metrics) {
        metrics.comparisonIncrement();
        return Math.hypot(p1.x - p2.x, p1.y - p2.y);
    }

    public Result findClosest(Point[] points, Metrics metrics) {
        if (points == null || points.length < 2) throw new IllegalArgumentException();
        metrics.setArraySize(points.length);
        metrics.start();
        Point[] pointsByX = points.clone();
        Arrays.sort(pointsByX, Comparator.comparingDouble(p -> p.x));
        metrics.allocationIncrement();
        Result result = closestPairRecursive(pointsByX, 0, pointsByX.length - 1, metrics);
        metrics.stop();
        return result;
    }

    private Result closestPairRecursive(Point[] pts, int left, int right, Metrics metrics) {
        metrics.trackRecursionStart();
        int n = right - left + 1;
        if (n <= 3) {
            double minDist = Double.POSITIVE_INFINITY;
            Point best1 = null, best2 = null;
            for (int i = left; i <= right; i++) {
                for (int j = i + 1; j <= right; j++) {
                    double d = distance(pts[i], pts[j], metrics);
                    if (d < minDist) {
                        minDist = d;
                        best1 = pts[i];
                        best2 = pts[j];
                    }
                }
            }
            metrics.trackRecursionEnd();
            return new Result(best1, best2, minDist);
        }
        int mid = left + (right - left) / 2;
        double midX = pts[mid].x;
        Result leftResult = closestPairRecursive(pts, left, mid, metrics);
        Result rightResult = closestPairRecursive(pts, mid + 1, right, metrics);
        Result best = leftResult.distance < rightResult.distance ? leftResult : rightResult;
        double d = best.distance;
        // Build strip
        Point[] strip = new Point[n];
        int stripSize = 0;
        for (int i = left; i <= right; i++) {
            if (Math.abs(pts[i].x - midX) < d) {
                strip[stripSize++] = pts[i];
            }
        }
        metrics.allocationIncrement();
        Arrays.sort(strip, 0, stripSize, Comparator.comparingDouble(p -> p.y));
        // Scan up to 7 neighbors
        for (int i = 0; i < stripSize; i++) {
            for (int j = i + 1; j < stripSize && (strip[j].y - strip[i].y) < d; j++) {
                double dist = distance(strip[i], strip[j], metrics);
                if (dist < best.distance) {
                    best = new Result(strip[i], strip[j], dist);
                    d = dist;
                }
            }
        }
        metrics.trackRecursionEnd();
        return best;
    }
}
