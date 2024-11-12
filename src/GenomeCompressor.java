/******************************************************************************
 *  Compilation:  javac GenomeCompressor.java
 *  Execution:    java GenomeCompressor - < input.txt   (compress)
 *  Execution:    java GenomeCompressor + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *  Data files:   genomeTest.txt
 *                virus.txt
 *
 *  Compress or expand a genomic sequence using a 2-bit code.
 ******************************************************************************/

/**
 *  The {@code GenomeCompressor} class provides static methods for compressing
 *  and expanding a genomic sequence using a 2-bit code.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  @author Zach Blick
 */
public class GenomeCompressor {

    /**
     * Reads a sequence of 8-bit extended ASCII characters over the alphabet
     * { A, C, T, G } from standard input; compresses and writes the results to standard output.
     */
    public static void compress() {
        // 85 because the highest value 'T', is ascii value 84
        boolean[][] binMap = new boolean[85][2];
        binMap['A'][0] = false;
        binMap['A'][1] = false;

        binMap['C'][0] = false;
        binMap['C'][1] = true;

        binMap['G'][0] = true;
        binMap['G'][1] = false;

        binMap['T'][0] = true;
        binMap['T'][1] = true;

        String s = BinaryStdIn.readString();
        int n = s.length();

        // Write out each character
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 2; j++) {
                BinaryStdOut.write(binMap[s.charAt(i)][j]);
            }
        }
        BinaryStdOut.close();
    }


    /**
     * Reads a binary sequence from standard input; expands and writes the results to standard output.
     */
    public static void expand() {
        while (!BinaryStdIn.isEmpty()) {
            boolean[] binArray = new boolean[2];
            for (int i = 0; i < 2; i++) {
                binArray[i] = BinaryStdIn.readBoolean();
            }
            if (binArray[0]) {
                if (binArray[1]) {
                    BinaryStdOut.write('T');
                } else {
                    BinaryStdOut.write('G');
                }
            } else {
                if (binArray[1]) {
                    BinaryStdOut.write('C');
                } else {
                    BinaryStdOut.write('A');
                }
            }
        }
        BinaryStdOut.close();
    }


    /**
     * Main, when invoked at the command line, calls {@code compress()} if the command-line
     * argument is "-" an {@code expand()} if it is "+".
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        if (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}