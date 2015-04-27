import java.util.ArrayList;
import java.util.HashMap;

public class Outcast {
    private WordNet wordNet;

    public Outcast(WordNet wordnet) {
        wordNet = wordnet;
    }
    public String outcast(String[] nouns) {
        HashMap<Integer, Integer> dist = new HashMap<Integer, Integer>();

        for (int i = 0; i < nouns.length; i++) {
            int current = 0;
            for (int j = 0; j < nouns.length; j++) {
                current += wordNet.distance(nouns[i], nouns[j]);
            }
            dist.put(current, i);
        }

        return nouns[max(dist)];
    }

    private int max(HashMap<Integer, Integer> dist) {
        int max = Integer.MIN_VALUE;

        for (int curr : dist.keySet()) {
            if (curr > max) {
                max = curr;
            }
        }

        return dist.get(max);
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}