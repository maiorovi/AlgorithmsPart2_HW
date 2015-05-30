import java.util.Arrays;
import java.util.Comparator;

public class CircularSuffixArray {

    private int lenght;
    private String s;
    private Integer[] index;

    public CircularSuffixArray(final String s) {
        lenght = s.length();
        index = new Integer[lenght];
        this.s = s;

        for(int i = 0; i <lenght; i++) {
            index[i] = i;
        }

        Arrays.sort(index, new Comparator<Integer>(){

            @Override
            public int compare(Integer firstChar, Integer secondChar) {
                int fChar = firstChar; int sChar = secondChar;
                for(int i = 0; i < s.length(); i++) {
                    if (fChar > s.length() - 1)
                        fChar = 0;
                    if (sChar > s.length() - 1)
                        sChar = 0;

                    if (s.charAt(fChar) - s.charAt(sChar) < 0) {
                        return -1;
                    }
                    if (s.charAt(fChar) - s.charAt(sChar) > 0) {
                        return 1;
                    }

                    fChar++;
                    sChar++;
                }

                return 0;
            }
        });
    }

    public int length() {
        return s.length();
    }

    public int index(int i) {
        return index[i];
    }

    public static void main(String[] args) {
        CircularSuffixArray arr  = new CircularSuffixArray("ABRACADABRA!");
    }
}