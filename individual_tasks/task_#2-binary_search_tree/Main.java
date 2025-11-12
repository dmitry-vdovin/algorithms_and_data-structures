package ind_task2_final;

import java.io.*;

public class Main {

    static class Node {
        int key;
        Node left, right;
        int h = Integer.MIN_VALUE; // высота поддерева (кэш)

        Node(int k) {
            key = k;
        }
    }

    static Node insert(Node r, int x) {
        if (r == null) return new Node(x);
        if (x < r.key) r.left = insert(r.left, x);
        else if (x > r.key) r.right = insert(r.right, x); // игнорируем дубликаты
        return r;
    }

    // высота в рёбрах
    // вернёт -1 если null
    static int height(Node r) {
        if (r == null) return -1;
        if (r.h != Integer.MIN_VALUE) return r.h;
        int hl = height(r.left);
        int hr = height(r.right);
        return r.h = 1 + Math.max(hl, hr);
    }

    // подсчёт числа вершин-кандидатов (|hL - hR| == 2)
    static int countCandidates(Node r) {
        if (r == null) return 0;
        int cnt = countCandidates(r.left) + countCandidates(r.right);
        int hl = (r.left == null ? -1 : r.left.h);
        int hr = (r.right == null ? -1 : r.right.h);
        if (Math.abs(hl - hr) == 2) cnt++;
        return cnt;
    }

    // k-я по значению вершина среди кандидатов (внутренний обход)
    static Node kthCandidate(Node r, int[] k) {
        if (r == null) return null;
        Node res = kthCandidate(r.left, k);
        if (res != null) return res;
        int hl = (r.left == null ? -1 : r.left.h);
        int hr = (r.right == null ? -1 : r.right.h);
        if (Math.abs(hl - hr) == 2) {
            if (--k[0] == 0) return r;
        }
        return kthCandidate(r.right, k);
    }

    // левое удаление ключа x
    static Node deleteLeft(Node r, int x) {
        if (r == null) return null;
        if (x < r.key) {
            r.left = deleteLeft(r.left, x);
            return r;
        }
        if (x > r.key) {
            r.right = deleteLeft(r.right, x);
            return r;
        }

        // нашли вершину r
        if (r.left == null) return r.right; // если нет предшественника
        if (r.right == null) return r.left;

        // ищем максимум в левом поддереве
        Node p = r, q = r.left;
        while (q.right != null) {
            p = q;
            q = q.right;
        }
        r.key = q.key;                // переносим ключ предшеств.
        // удаляем q из левого поддерева
        if (p == r) p.left = q.left;  // предшеств. был прямым левым
        else p.right = q.left;
        return r;
    }

    // прямой (левый) обход
    static void preorder(Node r, PrintWriter out) {
        if (r == null) return;
        out.println(r.key);
        preorder(r.left, out);
        preorder(r.right, out);
    }

    public static void main(String[] args) throws Exception {
        Node root = null;

        // чтение из файла
        try (InputStream is = new FileInputStream("tst.in")) {
            FastScanner fs = new FastScanner(is);
            Integer v;
            while ((v = fs.nextInt()) != null) root = insert(root, v);
        }

        if (root != null) {
            // 1) посчитать высоты
            height(root);

            // 2) найти среднюю по знач. среди вершин, где |hL - hR| = 2
            int m = countCandidates(root);

            // удаляем вершину ТОЛЬКО если число подходящих нечётно (иначе средней не существует)
            if ((m & 1) == 1) {
                int k = m / 2 + 1; // порядковый номер нужной вершины
                Node t = kthCandidate(root, new int[]{k});
                if (t != null) root = deleteLeft(root, t.key);
            }
        }

        // вывод в tst.out
        try (PrintWriter out = new PrintWriter("tst.out")) {
            preorder(root, out);
        }
    }

    static final class FastScanner {
        private final InputStream in;
        private final byte[] buf = new byte[1 << 16];
        private int ptr = 0, len = 0;

        FastScanner(InputStream in) {
            this.in = in;
        }

        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buf);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buf[ptr++];
        }

        Integer nextInt() throws IOException {
            int c;
            do {
                c = read();
                if (c == -1) return null;
            } while (c <= 32);
            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }
            int val = 0;
            while (c > 32) {
                val = val * 10 + (c - '0');
                c = read();
            }
            return val * sign;
        }
    }
}