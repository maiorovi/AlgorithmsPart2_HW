import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Digraph {

    private final int V;
    private int E;
    private ArrayList<Integer>[] adjList;

    public Digraph(int V) {
        this.V = V;
        this.E = 0;
        adjList = new ArrayList[V];

        for (int i = 0; i < adjList.length; i++) {
            adjList[i] = new ArrayList<Integer>();
        }
    }

    public Digraph(In in) {
        this(in.readInt());
        this.E = in.readInt();

        for (int i = 0; i < E; i++) {
            addEdge(in.readInt(), in.readInt());
        }
    }

    public Digraph(Digraph digraph) {
        this.V = digraph.V();
        for (int i = 0; i < digraph.E; i++) {
            for (int q : digraph.adj(i))
                adjList[i].add(q);
        }
    }

    public void addEdge(int v, int w) {
        adjList[v].add(w);
    }

    public int V() {
        return V;
    }

    public int edges() {
        return E;
    }

    public Iterable<Integer> adj(int v) {
        return adjList[v];
    }
}
