/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * worker thread that calculates an int value for a position in the new matrix
 *
 * @author Dan Herold
 * @version 2012.10.15
 */
public class WorkerThread implements Runnable {

    //the first matrix to mult the row with the column of B
    private int[][] A;
    //the matrix that will mult the A with a column
    private int[][] B;
    //the resulting matrix holding the sum of the products of A and B
    private int[][] C;
    private int row, col;

    public WorkerThread(int row, int col, int[][] A, int[][] B, int[][] C) {
        this.row = row;
        this.col = col;
        this.A = A;
        this.B = B;
        this.C = C;
    }

    /**
     * This will mult the row in A by the column in B and store the resulting
     * sum in C[row][col]
     */
    @Override
    public void run() {
        int prod = 0, sum = 0;
        for (int i = row; i < A[row].length; i++) {
            for (int j = 0; j < B.length; j++) {
                prod = A[row][j] * B[j][col];
                sum += prod;
            }
            C[row][col] = sum;
            break;
        }
    }
}
