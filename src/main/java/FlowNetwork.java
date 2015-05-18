import java.util.ArrayList;
import java.util.Iterator;

public class FlowNetwork {

    public ArrayList<FlowEdge>[] adjList;
    private int vertex;
    private int edges;

    public FlowNetwork(int v) {
        adjList = new ArrayList[v];
        vertex = v;

        for (int i = 0; i < v; i++) {
            adjList[i] = new ArrayList<FlowEdge>();
        }
    }

    public void addEdge(FlowEdge e) {
        adjList[e.from()].add(e);
        adjList[e.to()].add(e);
        edges++;
    }

    public Iterable<FlowEdge> adj(int v) {
        return adjList[v];
    }

    public int vertex() {
        return vertex;
    }

    public int edges() {
        return edges;
    }


}
