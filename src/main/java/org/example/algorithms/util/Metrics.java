package org.example.algorithms.util;

public class Metrics {
    private int arraySize;
    private long comparisons;
    private long allocations;
    private int MaxRecursionDepth;
    private int currentRecursionDepth;
    private long startTime;
    private long endTime;

    public void start() {
        this.startTime = System.nanoTime();
        this.comparisons = 0;
        this.allocations = 0;
        this.MaxRecursionDepth = 0;
        this.currentRecursionDepth = 0;
    }

    public void stop() {
        this.endTime = System.nanoTime();
    }

    public long getElapsedTime() {
        return this.endTime - this.startTime;
    }

    public void trackRecursionStart() {
        this.currentRecursionDepth++;
        if (this.currentRecursionDepth > this.MaxRecursionDepth) {
            this.MaxRecursionDepth = this.currentRecursionDepth;
        }
    }

    public void trackRecursionEnd() {
        this.currentRecursionDepth--;
    }

    public void comparisonIncrement() {
        this.comparisons++;
    }

    public void allocationIncrement() {
        this.allocations++;
    }

    public long getComparisons() {
        return comparisons;
    }

    public long getAllocations() {
        return allocations;
    }
    public int getMaxRecursionDepth() {
        return MaxRecursionDepth;
    }

    public int getArraySize() {
        return arraySize;
    }

    public void setArraySize(int arraySize) {
        this.arraySize = arraySize;
    }
    public int getCurrentRecursionDepth() {
        return currentRecursionDepth;
    }

    public void printMetrics(String testName) {
        System.out.printf(
            "%s: size=%d, comparisons=%d, allocations=%d, maxDepth=%d, time=%dns%n",
            testName,
            getArraySize(),
            getComparisons(),
            getAllocations(),
            getMaxRecursionDepth(),
            getElapsedTime()
        );
    }
}
