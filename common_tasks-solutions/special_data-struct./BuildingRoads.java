import java.io.*;
import java.util.*;

public class Main {
    static class DSU {
        int[] parent, size;
        DSU(int n) {
            parent = new int[n + 1];
            size = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }
        int find(int x) {
            if (parent[x] == x) return x;
            parent[x] = find(parent[x]);
            return parent[x];
        }
        boolean union(int a, int b) {
            a = find(a);
            b = find(b);
            if (a == b) return false;
            if (size[a] < size[b]) { int t = a; a = b; b = t; }
            parent[b] = a;
            size[a] += size[b];
            return true;
        }
    }

    static class FastScanner implements Closeable {
        private final BufferedInputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;
        FastScanner(InputStream is) { in = new BufferedInputStream(is); }
        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }
        int nextInt() throws IOException {
            int c, s = 1, x = 0;
            do { c = read(); } while (c <= 32);
            if (c == '-') { s = -1; c = read(); }
            while (c > 32) { x = x * 10 + (c - '0'); c = read(); }
            return x * s;
        }
        public void close() throws IOException { in.close(); }
    }

    public static void main(String[] args) throws Exception {
        try (FastScanner fs = new FastScanner(new FileInputStream("input.txt"));
             BufferedWriter out = new BufferedWriter(new FileWriter("output.txt"))) {
            int n = fs.nextInt();
            int q = fs.nextInt();
            DSU dsu = new DSU(n);
            int components = n;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < q; i++) {
                int u = fs.nextInt();
                int v = fs.nextInt();
                if (dsu.union(u, v)) components--;
                sb.append(components).append('\n');
            }
            out.write(sb.toString());
        }
    }
}