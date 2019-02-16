import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final int trialCount;
    private final static double CONFIDENCE_95 = 1.96;
    private static double stdDeviation, mean;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {

        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("PercolationStats err");

        this.trialCount = trials;
        double[] siteCounts = new double[trialCount];

        for (int i = 0; i < trialCount; i++) {
            siteCounts[i] = runExp(n);
        }
        PercolationStats.mean = StdStats.mean(siteCounts);
        PercolationStats.stdDeviation = StdStats.stddev(siteCounts);
    }

    private double runExp(int n) {
        Percolation perc = new Percolation(n);
        boolean doesPercolate = false;
        int[] idxArr = StdRandom.permutation(n * n);
        int cnt = 0;
        while (!doesPercolate) {
            int row = idxArr[cnt] / n + 1;
            int col = idxArr[cnt] % n + 1;

            perc.open(row, col);
            doesPercolate = perc.percolates();
            cnt++;
        }
        return cnt * 1.0 / (n * n) ;
    }

    // sample mean of percolation threshold
    public double mean() {
        return PercolationStats.mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return PercolationStats.stdDeviation;
    }

    // low end point of 95% confidence interval
    public double confidenceLo() {
        return PercolationStats.mean - PercolationStats.CONFIDENCE_95 * PercolationStats.stdDeviation / Math.sqrt(trialCount);
    }

    // high end point of 95% confidence interval
    public double confidenceHi() {
        return PercolationStats.mean + PercolationStats.CONFIDENCE_95 * PercolationStats.stdDeviation / Math.sqrt(trialCount);
    }

    // test client (described below)
    public static void main(String[] args){
        int currN = StdIn.readInt();
        int currT = StdIn.readInt();

        PercolationStats percolationStats = new PercolationStats(currN, currT);
        StdOut.print(percolationStats.mean());
    }

}
