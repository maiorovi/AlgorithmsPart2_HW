import java.util.*;

public class BoggleSolver {
    private HashSet<String> dictionary;
    private int width;
    private int height;

    public BoggleSolver(String[] dictionary){
        this.dictionary = new HashSet<String>();
        Collections.addAll(this.dictionary, dictionary);
    }

    public Iterable<String> getAllValidWords(BoggleBoard board){
        height = board.rows();
        width = board.cols();
        ArrayList<String> validWords = new ArrayList<String>();

        Iterable<String> words = dfs(board);

        for(String word : words) {
            if (dictionary.contains(word)) {
                validWords.add(word);
            }
        }

        return validWords;
    }

    private Iterable<String> dfs(BoggleBoard board) {
        int start = 0;

        return null;
    }

    //x - row, y - column
    private Iterable<Character> adj(int x, int y,BoggleBoard board) {
        ArrayList list = new ArrayList();

        if (x != 0 && y != 0)
            list.add(board.getLetter(x-1, y-1));

        if(x != 0)
            list.add(board.getLetter(x-1, y));

        if(x != 0 && y!= width - 1)
            list.add(board.getLetter(x-1, y+1));

        if( y != 0)
            list.add(board.getLetter(x, y - 1));

        if (y != width - 1)
            list.add(board.getLetter(x, y + 1));


        if ( x != height - 1 && y != 0)
            list.add(board.getLetter(x+1, y-1));

        if (x != height -1)
            list.add(board.getLetter(x+1, y));

        if(x != height - 1 && y != width - 1)
            list.add(board.getLetter(x+1, y+1));

        return list;
    }

    public int scoreOf(String word) {
        return 0;
    }

    private class BoggleTrie<T> {
        private int R = 26;
        private Node root;

        private class Node<T> {
            private T val;
            private Node[] ref = new Node[R];
        }

        public T get(String key) {
            Node node = get(root, key, 0);
            if (node != null)
                return (T)node.val;

            return null;
        }

        private Node get(Node x, String key, int d) {
            if (x == null)
                return null;
            if (key.length() == d)
                return x;
            char c = key.charAt(d);
            return get(x, key, d + 1);
        }

        public void put(String key, T value) {

        }

        private Node put(Node x, String key, T value, int d) {
            if (x == null)
                x = new Node();

            if (d == key.length()) {
                x.val = value;
                return x;
            }

            char c = key.charAt(d);
            x.ref[c] = put(x.ref[c], key,value,d+1);
            return x;
        }



    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
//        BoggleBoard board = new BoggleBoard(args[1]);
//        int score = 0;
//        for (String word : solver.getAllValidWords(board))
//        {
//            StdOut.println(word);
//            score += solver.scoreOf(word);
//        }
//        StdOut.println("Score = " + score);
    }
}

