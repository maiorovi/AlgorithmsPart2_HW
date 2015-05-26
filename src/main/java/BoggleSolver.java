import java.util.*;

public class BoggleSolver {
//    private HashSet<String> dictionary;
    BoggleTrie<Integer> dictionary;
    private int width;
    private int height;

    public BoggleSolver(String[] dictionary) {
//        this.dictionary = new HashSet<String>();
        this.dictionary = new BoggleTrie<>();

        for (int i = 0; i < dictionary.length;i++) {
            this.dictionary.put(dictionary[i], 1);
        }
    }

    public Iterable<String> getAllValidWords(BoggleBoard board) {
        height = board.rows();
        width = board.cols();
        TreeSet<String> validWords = new TreeSet<String>();

        for (int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                searchWords(board, i, j, validWords);
            }
        }

        return validWords;
    }

    private void searchWords(BoggleBoard board, int i, int j, TreeSet<String> words) {
        boolean[][] visited = new boolean[board.rows()][board.cols()];
        dfs(board, i, j, words, visited, "");

    }

    private void dfs(BoggleBoard board, int i, int j, TreeSet<String> words,boolean[][] visited, String prefix) {
        if(visited[i][j])
            return;

        char letter = board.getLetter(i,j);

        prefix += (letter == 'Q' ? "Qu" : String.valueOf(letter));

        if (prefix.length() > 2 && dictionary.contains(prefix))
            words.add(prefix);

        if (!dictionary.isPrefix(prefix))
            return;

        visited[i][j] = true;

        if (i > 0) {
            dfs(board, i - 1, j, words, visited, prefix);
            if (j > 0) {
                dfs(board, i - 1, j - 1, words, visited, prefix);
            }
            if (j < board.cols() - 1) {
                dfs(board, i - 1, j + 1, words, visited, prefix);
            }
        }
        if (j > 0) {
            dfs(board, i, j - 1, words, visited, prefix);
        }
        if (j < board.cols() - 1) {
            dfs(board, i, j + 1, words, visited, prefix);
        }
        if (i < board.rows() - 1) {
            if (j > 0) {
                dfs(board, i + 1, j - 1, words, visited, prefix);
            }
            if (j < board.cols() - 1) {
                dfs(board, i + 1, j + 1, words, visited, prefix);
            }
            dfs(board, i + 1, j, words, visited, prefix);
        }
        visited[i][j] = false;
    }

    public int scoreOf(String word) {
        if (word.length() <= 2) {
            return 0;
        } else
        if (word.length() < 4) {
            return 1;
        } else
        if (word.length() == 6) {
            return 2;
        } else
        if(word.length() == 7) {
            return 5;
        } else
        if(word.length() >= 8) {
            return 11;
        }

        return 0;
    }

    private class BoggleTrie<T> {
        private int R = 26;
        private static final int OFFSET = 65;
        private Node root = new Node();

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
            return get(x.ref[c-OFFSET], key, d + 1);
        }

        public void put(String key, T value) {
            put(root, key, value, 0);
        }

        private Node put(Node x, String key, T value, int d) {
            if (x == null)
                x = new Node();

            if (d == key.length()) {
                x.val = value;
                return x;
            }

            char c = key.charAt(d);
            x.ref[c-OFFSET] = put(x.ref[c-OFFSET], key,value,d+1);
            return x;
        }

        public boolean contains(String key) {
            T val =  get(key);

            if (val != null)
                return true;

            return false;
        }

        public boolean isPrefix(String prefix) {
            return get(root, prefix, 0) != null;
        }
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board))
        {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}

