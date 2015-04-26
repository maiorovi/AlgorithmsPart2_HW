import java.util.ArrayList;

public class Outcast {
    private WordNet wordNet;

    public Outcast(WordNet wordnet) {
        wordNet = wordnet;
    }
    public String outcast(String[] nouns) {
        ArrayList<Integer> dist = new ArrayList<Integer>();

        for (int i = 0; i < nouns.length; i++) {
            int current = 0;
            for (int j = 0; j < nouns.length; j++) {
                current += wordNet.distance(nouns[i], nouns[j]);
            }
            dist.add(current);
        }

        return nouns[max(dist)];
    }

    private int max(ArrayList<Integer> dist) {
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < dist.size(); i++) {
            if (dist.get(i) > max) {
                max = dist.get(i);
            }
        }

        return max;
    }

    public static void main(String[] args) {

    }
}