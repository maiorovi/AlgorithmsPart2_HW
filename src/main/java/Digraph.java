import java.util.ArrayList;
import java.util.HashMap;

public class Digraph {

    private final int V;
    private int E;
    private HashMap<Integer, ArrayList<Integer>> adjList;

    public Digraph(int V) {
        this.V = V;
        this.E = 0;
        adjList = new HashMap<Integer, ArrayList<Integer>>(V);
    }

    public void addEdge(int v, int w) {
        if (!adjList.containsKey(v)) {
            ArrayList<Integer> list = new ArrayList<Integer>();
            list.add(w);
            adjList.put(v, list);
            E++;
            return;
        }
        adjList.get(v).add(w);
        E++;
    }

    public int vertex() {
        return V;
    }

    public int edges() {
        return E;
    }

    public Iterable<Integer> adj(int v) {
        return adjList.get(v);
    }
}
