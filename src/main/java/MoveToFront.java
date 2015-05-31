import java.util.Iterator;
import java.util.LinkedList;

public class MoveToFront {
    private static final int ALBPHABET_SIZE = 255;
    private static final int CHAR_BITS = 8;
    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        LinkedList<Character> alphabet = constructAlphabet();

        while(!BinaryStdIn.isEmpty()) {
            char currentChar = BinaryStdIn.readChar();
            Iterator<Character> it =  alphabet.listIterator();
            int alphabetPos = 0;

            while(it.hasNext()) {
                if (it.next().equals(Character.valueOf(currentChar))) {
                 BinaryStdOut.write(alphabetPos, CHAR_BITS);
                 Character toFront = alphabet.remove(alphabetPos);
                 alphabet.add(0, toFront);
                 break;
                }
                alphabetPos++;
            }
        }
        BinaryStdOut.flush();
        BinaryStdOut.close();
    }

    private static LinkedList<Character> constructAlphabet() {
        LinkedList<Character> alphabet = new LinkedList();

        for (int i = 0; i <= ALBPHABET_SIZE; i++) {
            alphabet.add((char) i);
        }

        return alphabet;
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        LinkedList<Character> alphabet = constructAlphabet();

        while(!BinaryStdIn.isEmpty()) {
            int currentChar = BinaryStdIn.readChar();
                    BinaryStdOut.write(alphabet.get(currentChar), CHAR_BITS);
                BinaryStdOut.flush();
                    Character toFront = alphabet.remove(currentChar);
                    alphabet.addFirst(toFront);
        }
        BinaryStdOut.flush();
        BinaryStdOut.close();
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        String action = args[0];
        if (action.equals("-")) {
            MoveToFront.encode();
        } else if (action.equals("+")) {
            MoveToFront.decode();
        }
    }
}
