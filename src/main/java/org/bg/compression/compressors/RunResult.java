package org.bg.compression.compressors;

public class RunResult {
    private int numberOfRuns;
    private int numberOfFails;
    private float avgRuntime;
    private float failsPercenteges;

    public RunResult() {
        this(0, 0, (float) 0.0, (float) 0.0);
    }

    public RunResult(int numberOfRuns, int numberOfFails, float avgRuntime, float failedPercenteges) {
        this.numberOfRuns = numberOfRuns;
        this.numberOfFails = numberOfFails;
        this.avgRuntime = avgRuntime;
        this.failsPercenteges = failedPercenteges;
    }

    @Override
    public String toString() {
        return "{ numberOfRuns=" + numberOfRuns +
                ", numberOfFails=" + numberOfFails +
                ", avgRuntime=" + avgRuntime +
                ", failsPercenteges=" + failsPercenteges + "% }";
    }

    public float getAvgRuntime() {
        return avgRuntime;
    }
}
