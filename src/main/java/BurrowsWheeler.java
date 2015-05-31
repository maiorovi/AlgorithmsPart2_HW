import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class BurrowsWheeler {

    private static final int CHAR_BITS = 8;
    // apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
    public static void encode() {
        StringBuilder inputString = readFromInputString();

        int length = inputString.length();
        String str = inputString.toString();
        int start = -1;

        CircularSuffixArray cSuffixArray = new CircularSuffixArray(str);
        StringBuilder output = new StringBuilder();

        for(int z = 0; z < length; z++) {
            int offset = cSuffixArray.index(z);

            if (offset == 0) {
                output.append(inputString.charAt(inputString.length() - 1));
                start = z;
                continue;
            }

            output.append(inputString.charAt(offset - 1));
        }
        BinaryStdOut.write(start, 32);
        BinaryStdOut.write(output.toString(), CHAR_BITS);
        BinaryStdOut.flush();
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
    public static void decode() {
        int start = BinaryStdIn.readInt();
        StringBuilder inputData = readFromInputString();
//        int start = Integer.parseInt(String.valueOf(inputData.charAt(0)));

        char[] t = new char[inputData.length()];

        for(int i = 0; i < inputData.length(); i++) {
            t[i] = inputData.charAt(i);
        }

        int next[] = new int[t.length];

        Map<Character, LinkedList<Integer>> positions = new HashMap<Character, LinkedList<Integer>>();
        for (int i = 0; i < t.length; i++) {
            if(!positions.containsKey(t[i]))
                positions.put(t[i], new LinkedList<Integer>());
            positions.get(t[i]).addLast(i);
        }

        // get first chars array
        Arrays.sort(t);
        // go consistently through sorted firstChars array
        for (int i = 0; i < t.length; i++) {
            next[i] = positions.get(t[i]).removeFirst();
        }
        // decode msg
        // for length of the msg
        for (int i = 0, curRow = start; i < t.length; i++, curRow = next[curRow])
            // go from first to next.
            BinaryStdOut.write(t[curRow]);
        BinaryStdOut.close();
    }

    private static StringBuilder readFromInputString() {
        StringBuilder inputData = new StringBuilder();

        while(!BinaryStdIn.isEmpty()) {
            inputData.append(BinaryStdIn.readChar());
        }

        return inputData;
    }

    // if args[0] is '-', apply Burrows-Wheeler encoding
    // if args[0] is '+', apply Burrows-Wheeler decoding
    public static void main(String[] args) {
        String action = args[0];
        if (action.equals("-")) {
            BurrowsWheeler.encode();
        } else if (action.equals("+")) {
            BurrowsWheeler.decode();
        }
    }
}
