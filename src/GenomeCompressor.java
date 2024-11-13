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
 *  @author Sohum Berry
 */
public class GenomeCompressor {
    // I could set it to 13 because the length of virus log 2 rounds up to 13
    public static final int BIT_READ_LENGTH = 16;
    public static final int BIT_WRITE_LENGTH = 2;

    /**
     * Reads a sequence of 8-bit extended ASCII characters over the alphabet
     * { A, C, T, G } from standard input; compresses and writes the results to standard output.
     */
    public static void compress() {
        // The highest value in this alphabet is 'T', so no need to waste memory on a larger map
        int[] binaryMap = new int['T'+1];
        binaryMap['A'] = 0;
        binaryMap['C'] = 1;
        binaryMap['G'] = 2;
        binaryMap['T'] = 3;

        String s = BinaryStdIn.readString();
        int n = s.length();
        // Write out the length of the string using 16 bits. No need for all 32, only positive values are needed.
        BinaryStdOut.write(n, BIT_READ_LENGTH);
        // Write out each character/nucleotide as two bits
        for (int i = 0; i < n; i++) {
            BinaryStdOut.write(binaryMap[s.charAt(i)], BIT_WRITE_LENGTH);
        }
        BinaryStdOut.close();
    }

    /**
     * Reads a binary sequence from standard input; expands and writes the results to standard output.
     */
    public static void expand() {
        // Create a map to connect a number to each character/nucleotide
        char[] charMap = new char[4];
        charMap[0] = 'A';
        charMap[1] = 'C';
        charMap[2] = 'G';
        charMap[3] = 'T';

        // Read length of the bits that should be read to not read in the flushed bits at the end
        int length = BinaryStdIn.readShort();
        // For each bit in the length, read in a number from the BIT_WRITE_LENGTH number of bits
        for (int i = 0; i < length; i++) {
            int num = BinaryStdIn.readInt(BIT_WRITE_LENGTH);
            // Write the character from the map to the buffer
            BinaryStdOut.write(charMap[num]);
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