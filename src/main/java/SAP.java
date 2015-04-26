import java.util.Iterator;
import java.util.LinkedList;

public class SAP {

    private BFSCache vcache, wcache;
    private Digraph graph;

    public SAP(Digraph graph) {
        this.graph = graph;
        vcache = new BFSCache(graph.vertex());
        wcache = new BFSCache(graph.vertex());
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
        precalc(v,w);

        return findDistance();
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        precalc(v,w);

        return findAncestor();
    }

    public static void main(String[] args) {

    }

    private class BFSCache implements Iterable<Integer> {
        private boolean[] marked;
        private int[] distanceTo;
        private LinkedList<Integer> path = new LinkedList<Integer>();
        private int V;

        public BFSCache(int V) {
            this.V = V;
            marked = new boolean[V];
            distanceTo = new int[V];
        }

        public void bfs(int v) {
            LinkedList<Integer> queue = new LinkedList<Integer>();
            queue.addFirst(v);
            marked[v] = true;
            distanceTo[v] = 0;
            path.addLast(v);

            while (queue.isEmpty()) {
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
                queue.addFirst(v);
                marked[v] = true;
                distanceTo[v] = 0;
                path.addLast(v);
            }

            while (queue.isEmpty()) {
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
            while (path.isEmpty()) {
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
            return distanceTo[v] != 0;
        }

        @Override
        public Iterator<Integer> iterator() {
            return path.iterator();
        }
    }

}