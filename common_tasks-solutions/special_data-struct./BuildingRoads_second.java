import java.io.*;

public class Main {
    static class DSU {
        int[] p, sz;
        DSU(int n) {
            p = new int[n + 1];
            sz = new int[n + 1];
            for (int i = 1; i <= n; i++) { p[i] = i; sz[i] = 1; }
        }
        int find(int x) { return p[x] == x ? x : (p[x] = find(p[x])); }
        boolean union(int a, int b) {
            a = find(a); b = find(b);
            if (a == b) return false;
            if (sz[a] < sz[b]) { int t = a; a = b; b = t; }
            p[b] = a; sz[a] += sz[b];
            return true;
        }
    }

    static class FastScanner implements Closeable {
        private final BufferedInputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;
        FastScanner(InputStream is) { in = new BufferedInputStream(is); }
        private int read() throws IOException {
            if (ptr >= len) { len = in.read(buffer); ptr = 0; if (len <= 0) return -1; }
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
            int n = fs.nextInt(), m = fs.nextInt(), q = fs.nextInt();
            int[] u = new int[m + 1], v = new int[m + 1];
            for (int i = 1; i <= m; i++) { u[i] = fs.nextInt(); v[i] = fs.nextInt(); }
            int[] x = new int[q];
            boolean[] del = new boolean[m + 1];
            for (int i = 0; i < q; i++) { x[i] = fs.nextInt(); del[x[i]] = true; }

            DSU dsu = new DSU(n);
            int comp = n;
            for (int i = 1; i <= m; i++) if (!del[i]) if (dsu.union(u[i], v[i])) comp--;

            char[] ans = new char[q];
            for (int i = q - 1; i >= 0; i--) {
                ans[i] = (comp == 1) ? '1' : '0';
                if (dsu.union(u[x[i]], v[x[i]])) comp--;
            }
            out.write(new String(ans));
        }
    }
}