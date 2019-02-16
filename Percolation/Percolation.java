import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int size;
    private final WeightedQuickUnionUF unionFind, unionFind2;
    private boolean[][] grid;
    private int numOpen = 0;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("Percolation throws");

        if (n > 1) {
            // have 1 node at up, 1 node at bottom which virtually connects
            unionFind = new WeightedQuickUnionUF(n * n + 2);
            // only have 1 node at up because of backwash problem
            unionFind2 = new WeightedQuickUnionUF(n * n + 1);

            // unify top
            for (int i = 1; i < n + 1; i++) {
                unionFind.union(0, i);
                unionFind2.union(0, i);
            }

            // unify bottom
            for (int i = n * n; i > n * n - n; i--) {
                unionFind.union(n * n + 1, i);
            }
        } else {
            unionFind = new WeightedQuickUnionUF(1);
            unionFind2 = new WeightedQuickUnionUF(1);
        }

        size = n;
        grid = new boolean[n][n];
    }

    private void checkRowCalIdx(int row, int col, String methodName) {
        if (row < 1 || row > size || col < 1 || col > size)
            throw new IllegalArgumentException(methodName + " throws exception for row,col values");
    }

    // first and last indices are virtual to check percolation
    private int convert2idx(int row, int col) {
        if (this.size > 1)
            return (row - 1) * this.size + col;
        return (row - 1) * this.size + col - 1;
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {

        checkRowCalIdx(row, col, "open");

        if (grid[row - 1][col - 1]) {
            return;
        }

        numOpen++;
        grid[row - 1][col - 1] = true;

        int currIdx = convert2idx(row, col);
        int belowIdx = convert2idx(row + 1, col);
        int aboveIdx = convert2idx(row - 1, col);
        int rightIdx = convert2idx(row, col + 1);
        int leftIdx = convert2idx(row, col - 1);

        // check below
        if (row < size && grid[row][col - 1]) {
            unionFind.union(currIdx, belowIdx);
            unionFind2.union(currIdx, belowIdx);
        }
        // check above
        if (row > 1 && grid[row - 2][col - 1]) {
            unionFind.union(currIdx, aboveIdx);
            unionFind2.union(currIdx, aboveIdx);
        }
        // check right
        if (col < size && grid[row - 1][col]) {
            unionFind.union(currIdx, rightIdx);
            unionFind2.union(currIdx, rightIdx);
        }
        // check left
        if (col > 1 && grid[row - 1][col - 2]) {
            unionFind.union(currIdx, leftIdx);
            unionFind2.union(currIdx, leftIdx);
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkRowCalIdx(row, col, "isOpen");

        return grid[row - 1][col - 1];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {

        checkRowCalIdx(row, col, "isFull");

        if (!grid[row - 1][col - 1])
            return false;

        int idx = convert2idx(row, col);
        return unionFind2.connected(0, idx);
    }

    // number of open sites
    public     int numberOfOpenSites() {
        return numOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        if (this.size > 1)
            return unionFind.connected(0, this.size * this.size + 1);
        return this.grid[0][0];
    }

    // test client (optional)
//	public static void main(String[] args){
//
//	    Percolation p = new Percolation(1);
//        System.out.println(p.percolates());
//
//        p.open(1,1);
//
//        boolean b1 =  p.isFull(1,1);
//        System.out.println(b1);
//
//        System.out.println(p.percolates());
//	}

}
