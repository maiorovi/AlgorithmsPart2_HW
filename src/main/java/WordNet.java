import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class WordNet {

    private HashMap<String, Integer> wordToIdMap = new HashMap<String, Integer>();
    private HashMap<Integer, ArrayList<Integer>> hypernums = new HashMap<Integer, ArrayList<Integer>>();
    private HashMap<Integer, String> idToSynsetsMap = new HashMap<Integer, String>();
    private SAP sap;

    public WordNet(String synsets, String hypernyms) {
        int amount = readSynsets(synsets);
        Digraph graph = readHypernums(hypernyms, amount);
        sap = new SAP(graph);
    }

    private int readSynsets(String fileName) {
        In input = new In(fileName);
        int counter = 0;
        while(input.hasNextLine()) {
            String line = input.readLine();
            processLineSynset(line);
            counter++;
        }

        return counter;
    }

    private Digraph readHypernums(String fileName, int amount) {
        In input = new In(fileName);
        Digraph graph = new Digraph(amount);
        while(input.hasNextLine()) {
            String line = input.readLine();
            processHypernumLine(line, graph);
        }

        return graph;
    }

    private void processLineSynset(String line) {
        String[] result = line.split(",");
        String id = result[0];
        String[] words = result[1].split("\\s+");
        String description = result[2];

        idToSynsetsMap.put(Integer.parseInt(id), line);

        for (int i = 0; i < words.length; i++) {
            wordToIdMap.put(words[i], Integer.parseInt(id));
        }
    }

    private void processHypernumLine(String line, Digraph graph) {
        String[] result = line.split(",");
        ArrayList<Integer> ids = new ArrayList<Integer>();

        for (int i = 1; i < result.length; i++) {
            ids.add(Integer.parseInt(result[i]));
            graph.addEdge(Integer.parseInt(result[0]), Integer.parseInt(result[i]));
        }

        hypernums.put(Integer.parseInt(result[0]), ids);
    }

    public Iterable<String> nouns() {
        return wordToIdMap.keySet();
    }

    public boolean isNoun(String word) {
        return wordToIdMap.containsKey(word);
    }

    public int distance(String nounA, String nounB) {
        Integer firstId = wordToIdMap.get(nounA);
        Integer secondId = wordToIdMap.get(nounB);

        return sap.length(firstId, secondId);
    }

    public String sap(String nounA, String nounB) {
        Integer firstId = wordToIdMap.get(nounA);
        Integer secondId = wordToIdMap.get(nounB);

        int result = sap.ancestor(firstId, secondId);
        return idToSynsetsMap.get(result);
    }

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
