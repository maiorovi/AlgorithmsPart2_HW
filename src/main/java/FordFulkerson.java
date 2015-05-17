import java.util.LinkedList;

public class FordFulkerson {
    private boolean[] marked;
    private FlowEdge[] edgeTo;
    private double value;

    public FordFulkerson(FlowNetwork flowNetwork, int start, int target) {
        value = 0.0;

        while(hasAugmentingPath(flowNetwork, start, target)) {
            double bottle = Double.POSITIVE_INFINITY;
            for(int v = target; v != start; v = edgeTo[v].other(v) )
                bottle = Math.min(bottle, edgeTo[v].residualCapacityTo(v));

            for(int v = target; v != start; v = edgeTo[v].other(v)) {
                edgeTo[v].addResidualFlowTo(v, bottle);
            }

            value += bottle;
        }
    }

    public double value() {
        return value;
    }

    public boolean inCut(int v) {
        return marked[v];
    }

    private boolean hasAugmentingPath(FlowNetwork flowNetwork, int start, int target) {
        edgeTo = new FlowEdge[flowNetwork.vertex()];
        marked = new boolean[flowNetwork.vertex()];

        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(start);
        marked[start] = true;

        while(!queue.isEmpty()) {

            int v = queue.getFirst();

            for (FlowEdge edge : flowNetwork.adj(v)) {
                int w = edge.other(v);
                if (edge.residualCapacityTo(w) > 0 && !marked[w]) {
                    edgeTo[w] = edge;
                    marked[w] = true;
                    queue.addLast(w);
                }
            }
        }

        return marked[target];
    }

}
