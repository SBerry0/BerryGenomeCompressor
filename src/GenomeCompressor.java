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
    // I could set it to 13 because the length of virus log 2 rounds up to 13
    public static final int BIT_READ_LENGTH = 16;
    /**
     * Reads a sequence of 8-bit extended ASCII characters over the alphabet
     * { A, C, T, G } from standard input; compresses and writes the results to standard output.
     */
    public static void compress() {
        // Size of 85 because the highest value 'T', is ascii value 84
        boolean[][] binMap = new boolean[85][2];
        // Initialize what each pair of bits represent for each letter
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
        // Write out the length of the string using 16 bits. No need for all 32, only positive values are needed.
        BinaryStdOut.write(n, BIT_READ_LENGTH);
        // Write out each character as two bits
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
        // Read length of the bits that should be read to not read in the flushed bits at the end
        int length = BinaryStdIn.readShort();

        // For each bit in the length...
        for (int i = 0; i < length; i++) {
            // Read in the next two bits to find what the next letter is
            boolean[] binArray = new boolean[2];
            for (int j = 0; j < 2; j++) {
                binArray[j] = BinaryStdIn.readBoolean();
            }
            // Check the values of each bit to decide which letter to write out
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
        // Close the stream
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