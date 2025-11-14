import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {

    static class Tree {
        static class Node {
            int key;
            Node left, right;
            Node(int k) { key = k; }
        }

        private Node root;

        // вставка элемента (без рекурсии)
        
        public void insert(int x) {
            if (root == null) { root = new Node(x); return; }
            Node cur = root;
            while (true) {
                if (x < cur.key) {
                    if (cur.left == null) { cur.left = new Node(x); return; }
                    cur = cur.left;
                } else if (x > cur.key) {
                    if (cur.right == null) { cur.right = new Node(x); return; }
                    cur = cur.right;
                } else {
                    return; 
                }
            }
        }

        // прямой левый обход
        public void preorder(Appendable out) throws IOException {
            if (root == null) return;
            Deque<Node> st = new ArrayDeque<>();
            st.push(root);
            while (!st.isEmpty()) {
                Node n = st.pop();
                out.append(Integer.toString(n.key)).append('\n');
                if (n.right != null) st.push(n.right);
                if (n.left  != null) st.push(n.left);
            }
        }
    }

    public static void main(String[] args) {
        Path inPath = Paths.get("input.txt");
        Path outPath = Paths.get("output.txt");

        Tree tree = new Tree();
        
        try (BufferedReader br = Files.newBufferedReader(inPath)) {
            String s;
            while ((s = br.readLine()) != null) {
                s = s.trim();
                if (s.isEmpty()) continue;
                tree.insert(Integer.parseInt(s));
            }
        } catch (NoSuchFileException e) {
            try { Files.write(outPath, new byte[0]); } catch (IOException ignored) {}
            return;
        } catch (IOException e) {
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        try {
            tree.preorder(sb);
            try (BufferedWriter bw = Files.newBufferedWriter(outPath)) {
                bw.write(sb.toString());
            }
        } catch (IOException ignored) {}
    }
}