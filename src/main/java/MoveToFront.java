import java.util.Iterator;
import java.util.LinkedList;

public class MoveToFront {
    private static final int ALBPHABET_SIZE = 255;
    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        LinkedList<Character> alphabet = constructAlphabet();

        while(!BinaryStdIn.isEmpty()) {
            int currentChar = (int) BinaryStdIn.readChar();
            Iterator<Character> it =  alphabet.iterator();
            int alphabetPos = 0;

            while(it.hasNext()) {
                if (it.next().equals(currentChar)) {
                 BinaryStdOut.write(alphabetPos);
                 Character toFront = alphabet.remove(alphabetPos);
                 alphabet.addFirst(toFront);
                 break;
                }
                alphabetPos++;
            }
            BinaryStdOut.close();
        }
    }

    private static LinkedList<Character> constructAlphabet() {
        LinkedList<Character> alphabet = new LinkedList();

        for (int i = 0; i < ALBPHABET_SIZE; i++) {
            alphabet.add((char) i);
        }

        return alphabet;
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        LinkedList<Character> alphabet = constructAlphabet();

        while(!BinaryStdIn.isEmpty()) {
            int currentChar = BinaryStdIn.readInt();
            Iterator<Character> it = alphabet.iterator();
//            int alphabetPosition = 0;
            while(it.hasNext()) {
                if (it.next().equals((int)currentChar)) {
                    BinaryStdOut.write(alphabet.get(currentChar));
                    Character toFront = alphabet.remove(currentChar);
                    alphabet.addFirst(toFront);
                }
//                alphabetPosition++;
            }
            BinaryStdOut.close();
        }
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        MoveToFront mtf = new MoveToFront();
        mtf.encode();
    }
}
