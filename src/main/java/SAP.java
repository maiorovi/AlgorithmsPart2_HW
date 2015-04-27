import java.util.Iterator;
import java.util.LinkedList;

public class SAP {

    private BFSCache vcache, wcache;
    private final Digraph graph;

    public SAP(Digraph graph) {
        this.graph = new Digraph(graph);
        vcache = new BFSCache(graph.V());
        wcache = new BFSCache(graph.V());
    }


    public int length(int v, int w) {
        precalc(v, w);

        return findDistance();
    }

    private int findDistance() {
        int result = -1;
        BFSCache[] caches = {vcache, wcache};

        for (BFSCache cache : caches) {
            for (int cur : cache) {
                if (vcache.canReach(cur) && wcache.canReach(cur)) {
                    int distance = vcache.distanceTo(cur) + wcache.distanceTo(cur);

                    if (result == -1 || distance < result) {
                        result = distance;
                    }
                }
            }
        }

        return result;
    }

    public int ancestor(int v, int w) {
        precalc(v, w);

        return findAncestor();
    }

    private void verifyArguments(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new NullPointerException();
        }
    }

    private int findAncestor() {
        int ancestor = -1;
        int result = -1;
        BFSCache[] caches = {vcache, wcache};

        for (BFSCache cache : caches) {
            for (int cur : cache) {
                if (vcache.canReach(cur) && wcache.canReach(cur)) {
                    int distance = vcache.distanceTo(cur) + wcache.distanceTo(cur);

                    if (result == -1 || distance < result) {
                        result = distance;
                        ancestor = cur;
                    }
                }
            }
        }
        return ancestor;
    }

    private void precalc(int v, int w) {
        if (v < 0 || w < 0 || v > graph.V() - 1 || w > graph.V() - 1) {
            throw new IndexOutOfBoundsException();
        }

        vcache.clear();
        wcache.clear();

        vcache.bfs(v);
        wcache.bfs(w);
    }

    private void precalc(Iterable<Integer> v, Iterable<Integer> w) {
        vcache.clear();
        wcache.clear();

        vcache.bfs(v);
        wcache.bfs(w);
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        verifyArguments(v,w);
        precalc(v,w);

        return findDistance();
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        verifyArguments(v,w);
        precalc(v,w);

        return findAncestor();
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }

    private class BFSCache implements Iterable<Integer> {
        private boolean[] marked;
        private int[] distanceTo;
        private LinkedList<Integer> path = new LinkedList<Integer>();

        public BFSCache(int V) {
            marked = new boolean[V];
            distanceTo = new int[V];

            for (int i = 0; i < V; i++) {
                distanceTo[i] = -1;
            }
        }

        public void bfs(int v) {
            LinkedList<Integer> queue = new LinkedList<Integer>();
            queue.addLast(v);
            marked[v] = true;
            distanceTo[v] = 0;
            path.addLast(v);

            while (!queue.isEmpty()) {
                Integer current = queue.removeFirst();
                for (int p : graph.adj(current)) {
                    if (!marked[p]) {
                        marked[p] = true;
                        distanceTo[p] = distanceTo[current] + 1;
                        path.addLast(p);
                        queue.addLast(p);
                    }
                }
            }
        }

        public void bfs(Iterable<Integer> vert) {
            LinkedList<Integer> queue = new LinkedList<Integer>();

            for (int v : vert) {
                if (v < 0 || v > graph.V()) {
                    throw new IndexOutOfBoundsException();
                }

                queue.addLast(v);
                marked[v] = true;
                distanceTo[v] = 0;
                path.addLast(v);
            }

            while (!queue.isEmpty()) {
                Integer current = queue.removeFirst();
                for (int p : graph.adj(current)) {
                    if (!marked[p]) {
                        marked[p] = true;
                        distanceTo[p] = distanceTo[current] + 1;
                        path.addLast(p);
                        queue.addLast(p);
                    }
                }
            }
        }

        public void clear() {
            while (!path.isEmpty()) {
                int ind = path.removeFirst();
                marked[ind] = false;
                distanceTo[ind] = -1;
            }
        }

        public int distanceTo(int v) {
            if (canReach(v))
                return distanceTo[v];

            return -1;
        }

        public boolean canReach(int v) {
            return marked[v];
        }

        @Override
        public Iterator<Integer> iterator() {
            return path.iterator();
        }
    }

}