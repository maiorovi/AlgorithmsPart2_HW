import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class WordNet {

    private HashMap<String, Integer> wordToIdMap = new HashMap<String, Integer>();
    private HashMap<Integer, ArrayList<Integer>> hypernums = new HashMap<Integer, ArrayList<Integer>>();

    public WordNet(String synsets, String hypernyms) {
        readSynsets(synsets);
        readHypernums(hypernyms);
    }

    private void readSynsets(String fileName) {
        In input = new In(fileName);
        while(input.hasNextLine()) {
            String line = input.readLine();
            processLineSynset(line);
        }
    }

    private void readHypernums(String fileName) {
        In input = new In(fileName);
        while(input.hasNextLine()) {
            String line = input.readLine();
            processHypernumLine(line);
        }
    }

    private void processLineSynset(String line) {
        String[] result = line.split(",");
        String id = result[0];
        String[] words = result[1].split("\\s+");
        String description = result[2];

        for (int i = 0; i < words.length; i++) {
            wordToIdMap.put(words[i], Integer.parseInt(id));
        }
    }

    private void processHypernumLine(String line) {
        String[] result = line.split(",");
        ArrayList<Integer> ids = new ArrayList<Integer>();

        for (int i = 1; i < result.length; i++) {
            ids.add(Integer.parseInt(result[i]));
        }

        hypernums.put(Integer.parseInt(result[0]), ids);
    }

    public Iterable<String> nouns() {
        return wordToIdMap.keySet();
    }

    public boolean isNoun(String word) {
        return wordToIdMap.containsKey(word);
    }
//
//    public int distance(String nounA, String nounB) {
//
//    }
//
//    public String sap(String nounA, String nounB) {
//
//    }
//
    public static void main(String[] args) {
        WordNet wn = new WordNet("synsets.txt","hypernyms.txt");

        Iterator it =wn.hypernums.get(164).iterator();

        while(it.hasNext()) {
            System.out.print(it.next() + " ");
        }

        System.out.println();
        System.out.println(wn.wordToIdMap.get("AND_circuit"));
        System.out.println(wn.wordToIdMap.get("AND_gate"));

        System.out.println(wn.isNoun("AND_circuit"));
    }
}
