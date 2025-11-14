import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;

public class Main {
    static class Node {
        int key;
        Node left, right;
        Node(int k) { key = k; }
    }

    static class BST {
        Node root;

        void insert(int k) {
            if (root == null) { root = new Node(k); return; }
            Node v = root, p = null;
            while (v != null) {
                p = v;
                if (k < v.key) v = v.left;
                else if (k > v.key) v = v.right;
                else return;
            }
            if (k < p.key) p.left = new Node(k);
            else p.right = new Node(k);
        }

        void replaceChild(Node parent, Node oldChild, Node newChild) {
            if (parent == null) { root = newChild; return; }
            if (parent.left == oldChild) parent.left = newChild;
            else if (parent.right == oldChild) parent.right = newChild;
        }

        void deleteIter(int x) {
            Node parent = null, v = root;
            while (v != null) {
                if (x < v.key) { parent = v; v = v.left; }
                else if (x > v.key) { parent = v; v = v.right; }
                else break;
            }
            if (v == null) return;

            if (v.left == null || v.right == null) {
                Node child = (v.left != null) ? v.left : v.right;
                replaceChild(parent, v, child);
                return;
            }

            Node minParent = v, min = v.right;
            while (min.left != null) { minParent = min; min = min.left; }
            v.key = min.key;
            Node minChild = min.right;
            replaceChild(minParent, min, minChild);
        }

        void preorderIter(BufferedWriter bw) throws IOException {
            if (root == null) return;
            Deque<Node> st = new ArrayDeque<>();
            st.push(root);
            while (!st.isEmpty()) {
                Node v = st.pop();
                bw.write(Integer.toString(v.key));
                bw.write('\n');
                if (v.right != null) st.push(v.right);
                if (v.left != null) st.push(v.left);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"));
             BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt"))) {

            String s = br.readLine();
            if (s == null) return;
            int del = Integer.parseInt(s.trim());

            BST bst = new BST();
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                bst.insert(Integer.parseInt(line));
            }

            bst.deleteIter(del);
            bst.preorderIter(bw);
        }
    }
}