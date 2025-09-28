package org.example.algorithms.geometry;

import org.example.algorithms.util.Metrics;
import org.junit.jupiter.api.Test;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClosestPairTest {
    // Brute-force O(n^2) for validation
    private double bruteForce(ClosestPair.Point[] pts) {
        double minDist = Double.POSITIVE_INFINITY;
        for (int i = 0; i < pts.length; i++) {
            for (int j = i + 1; j < pts.length; j++) {
                double d = Math.hypot(pts[i].x - pts[j].x, pts[i].y - pts[j].y);
                if (d < minDist) minDist = d;
            }
        }
        return minDist;
    }

    @Test
    void testSmallRandom() {
        Random rand = new Random(42);
        for (int n = 2; n <= 200; n += 50) {
            ClosestPair.Point[] pts = new ClosestPair.Point[n];
            for (int i = 0; i < n; i++) {
                pts[i] = new ClosestPair.Point(rand.nextDouble() * 1000, rand.nextDouble() * 1000);
            }
            Metrics metrics = new Metrics();
            double fast = new ClosestPair().findClosest(pts, metrics).distance;
            double brute = bruteForce(pts);
            assertEquals(brute, fast, 1e-9);
            metrics.printMetrics("SmallRandom n=" + n);
        }
    }

    @Test
    void testMediumRandom() {
        Random rand = new Random(123);
        int n = 1000;
        ClosestPair.Point[] pts = new ClosestPair.Point[n];
        for (int i = 0; i < n; i++) {
            pts[i] = new ClosestPair.Point(rand.nextDouble() * 10000, rand.nextDouble() * 10000);
        }
        Metrics metrics = new Metrics();
        double fast = new ClosestPair().findClosest(pts, metrics).distance;
        double brute = bruteForce(pts);
        assertEquals(brute, fast, 1e-9);
        metrics.printMetrics("MediumRandom n=" + n);
    }

    @Test
    void testLargeRandom() {
        Random rand = new Random(321);
        int n = 5000;
        ClosestPair.Point[] pts = new ClosestPair.Point[n];
        for (int i = 0; i < n; i++) {
            pts[i] = new ClosestPair.Point(rand.nextDouble() * 1e6, rand.nextDouble() * 1e6);
        }
        Metrics metrics = new Metrics();
        double fast = new ClosestPair().findClosest(pts, metrics).distance;
        metrics.printMetrics("LargeRandom n=" + n);
    }

    @Test
    void testEdgeCases() {
        Metrics metrics = new Metrics();
        // Empty
        assertThrows(IllegalArgumentException.class, () -> new ClosestPair().findClosest(new ClosestPair.Point[]{}, metrics));
        // One point
        assertThrows(IllegalArgumentException.class, () -> new ClosestPair().findClosest(new ClosestPair.Point[]{new ClosestPair.Point(1,2)}, metrics));
        // Two points
        ClosestPair.Point[] pts2 = {new ClosestPair.Point(0,0), new ClosestPair.Point(3,4)};
        double d2 = new ClosestPair().findClosest(pts2, metrics).distance;
        assertEquals(5.0, d2, 1e-9);
        metrics.printMetrics("TwoPoints");
        // Collinear
        ClosestPair.Point[] ptsCol = {new ClosestPair.Point(0,0), new ClosestPair.Point(1,0), new ClosestPair.Point(2,0)};
        double dCol = new ClosestPair().findClosest(ptsCol, metrics).distance;
        assertEquals(1.0, dCol, 1e-9);
        metrics.printMetrics("Collinear");
        // Duplicate points
        ClosestPair.Point[] ptsDup = {new ClosestPair.Point(1,1), new ClosestPair.Point(1,1), new ClosestPair.Point(2,2)};
        double dDup = new ClosestPair().findClosest(ptsDup, metrics).distance;
        assertEquals(0.0, dDup, 1e-9);
        metrics.printMetrics("DuplicatePoints");
    }
}
