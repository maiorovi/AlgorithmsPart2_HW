import java.util.Arrays;

public class CircularSuffixArray {

    private int lenght;
    private String s;
    private int[] index;

    public CircularSuffixArray(String s) {
        lenght = s.length();
        index = new int[lenght];
        this.s = s;

        for(int i = 0; i <lenght; i++) {

        }

    }

    private String rotate(String s) {
        StringBuilder builder = new StringBuilder();
        char first = s.charAt(0);
        String q = new String(s.toCharArray(), 1, s.length() - 1).concat(String.valueOf(first));

        return q;
    }



    private void printArray(String arr[]) {
        for(int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }


               // returns index of ith sorted suffix
    public static void main(String[] args) {
        CircularSuffixArray arr  = new CircularSuffixArray("ABRACADABRA!");
    }
}