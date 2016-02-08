
import java.io.*;
import java.util.Scanner;

/**
 * This class will multiply two matrices together
 *
 * @author Dan Herold
 * @version 2012.10.11
 */
public class MatrixMultiplication {

    private static int[][] A;
    private static int[][] B;
    private static int[][] C;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            A = read(args[0]);
            B = read(args[1]);

            if (A[0].length != B.length) {
                System.out.println("These matrices cannot be multiplied!!");
                return;
            } else {
                C = new int[A.length][B[0].length];
                for (int i = 0; i < C.length; i++) {
                    for (int j = 0; j < C[i].length; j++) {
                        Thread thd = new Thread(new WorkerThread(i, j, A, B, C));
                        thd.start();
                    }
                }
                write(args[2]);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Reads matrices from files into the fields
     *
     * @param aName the file to read the matrix from
     */
    protected static int[][] read(String aName) throws FileNotFoundException {
        //creates scanners based on file names
        Scanner a = new Scanner(new File(aName));
        a.useDelimiter("\\Z");
        String str = a.next();
        str = str.replace("\r", "");
        //split the entire file string into an array of the rows
        String[] list = str.split("\n");
        //find out how many columns A needs by splitting the first row
        int cols = list[0].split("\t").length;
        String[] ints;
        int[][] damn = new int[list.length][cols];
        //fill the rest of A
        for (int i = 0; i < list.length; i++) {
            ints = list[i].split("\t");
            if (ints.length != cols) {
                System.err.println("Not a valid matrix.");
                return null;
            }
            for (int j = 0; j < cols; j++) {
                try {
                    damn[i][j] = Integer.parseInt(ints[j]);
                } catch (NumberFormatException ne) {
                    System.err.println("Error parsing ints. :(");
                    return null;
                }
            }
        }
        a.close();
        return damn;
    }

    /**
     * Will write the new matrix to the specified file
     *
     * @param fName the name of the file to write to
     */
    protected static void write(String fName) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fName));
            String str = "";
            for (int i = 0; i < C.length - 1; i++) {
                for (int j = 0; j < C[i].length - 1; j++) {
                    str += (C[i][j] + "\t");
                }
                str += C[i][C[i].length - 1];
                str += System.getProperty("line.separator");
            }
            int i = C.length - 1;
            for (int j = 0; j < C[i].length - 1; j++) {
                str += C[i][j] + "\t";
            }
            str += C[i][C[i].length - 1];
            str += System.getProperty("line.separator");
            out.write(str);
            out.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * This method was used for testing purposes
     *
     * @param derp
     */
    protected static void printMatrix(int[][] derp) {
        for (int i = 0; i < derp.length - 1; i++) {
            for (int j = 0; j < derp[i].length - 1; j++) {
                System.out.print(derp[i][j] + "\t");
            }
            System.out.println(derp[i][derp[i].length - 1]);
        }
        int i = derp.length - 1;
        for (int j = 0; j < derp[i].length - 1; j++) {
            System.out.print(derp[i][j] + "\t");
        }
        System.out.print(derp[i][derp[i].length - 1]);
    }

    /**
     * This method was used for testing purposes
     *
     * @param derp
     */
    protected static void printMatrix(String[][] derp) {
        String str = "";
        for (int i = 0; i < derp.length - 1; i++) {
            for (int j = 0; j < derp[i].length - 1; j++) {
                str += (derp[i][j] + "\t");
            }
            str += (derp[i][derp[i].length - 1] + "\n");
        }
        int i = derp.length - 1;
        for (int j = 0; j < derp[i].length - 1; j++) {
            str += (derp[i][j] + "\t");
        }
        str += (derp[i][derp[i].length - 1]);
    }
}