import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class BurrowsWheeler {

    // apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
    public static void encode() {
//        StringBuilder inputString = new StringBuilder("ABRACADABRA!");

        StringBuilder inputString = readFromInputString();

        int length = inputString.length();
        String str = inputString.toString();

        CircularSuffixArray cSuffixArray = new CircularSuffixArray(str);
        StringBuilder output = new StringBuilder();

        for(int z = 0; z < length; z++) {
            int offset = cSuffixArray.index(z);

            if (offset == 0) {
                output.append(inputString.charAt(inputString.length() - 1));
                output = new StringBuilder(String.valueOf(z).concat(output.toString()));
                continue;
            }

            output.append(inputString.charAt(offset - 1));
        }
        BinaryStdOut.write(output.toString());
        BinaryStdOut.flush();
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
    public static void decode() {
        StringBuilder inputData = readFromInputString();

        int start = Integer.parseInt(String.valueOf(inputData.charAt(0)));

        char[] t = new char[inputData.length() - 1];

        for(int i = 0; i < inputData.length() - 1; i++) {
            t[i] = inputData.charAt(i+1);
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
        BinaryIn binaryIn = new BinaryIn();
        StringBuilder inputData = new StringBuilder("3ARD!RCAAAABB");

        while(!binaryIn.isEmpty()) {
            inputData.append(binaryIn.readChar());
        }

        return inputData;
    }

    // if args[0] is '-', apply Burrows-Wheeler encoding
    // if args[0] is '+', apply Burrows-Wheeler decoding
    public static void main(String[] args) {
        BurrowsWheeler.decode();
    }
}
