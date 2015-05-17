
public class FlowEdge {
    private double capacity;
    private double flow;
    private int from;
    private int to;

    public FlowEdge(int from, int to, double capacity) {
        this.from = from;
        this.to = to;
        this.capacity = capacity;
    }

    public int from() {
        return from;
    }

    public int to() {
        return to;
    }

    public int other(int v) {
        if (v == from)  return  to;
        else if (v == to ) return from;
        else throw new IllegalArgumentException();
    }

    public double capacity() {
        return capacity;
    }

    public double flow() {
        return flow;
    }

    public double residualCapacityTo(int v) {
        if (v == from) return flow;
        else if (v == to) return capacity - flow;
        else throw new IllegalArgumentException();
    }

    public void addResidualFlowTo(int v, double delta) {
        if (v == from) flow -= delta;
        else if (v == to) flow += delta;
        else throw new IllegalArgumentException();
    }



}
