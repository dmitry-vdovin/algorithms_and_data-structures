import java.io.*;

public class Main {
    static class FastIO implements Closeable {
        private final InputStream in;
        private final byte[] buf = new byte[1 << 16];
        private int ptr = 0, len = 0;
        FastIO(InputStream in) { this.in = in; }
        private int read() throws IOException {
            if (ptr >= len) { len = in.read(buf); ptr = 0; if (len <= 0) return -1; }
            return buf[ptr++];
        }
        String next() throws IOException {
            StringBuilder sb = new StringBuilder();
            int c;
            do { c = read(); } while (c <= 32 && c != -1);
            while (c > 32 && c != -1) { sb.append((char)c); c = read(); }
            return sb.length() == 0 ? null : sb.toString();
        }
        public void close() throws IOException { in.close(); }
    }

    public static void main(String[] args) throws Exception {
        try (FastIO io = new FastIO(new FileInputStream("input.txt"));
             BufferedWriter out = new BufferedWriter(new FileWriter("output.txt"))) {

            int m = Integer.parseInt(io.next());
            int c = Integer.parseInt(io.next());
            int n = Integer.parseInt(io.next());

            int[] slot = new int[m];
            for (int i = 0; i < m; i++) slot[i] = -1;

            for (int k = 0; k < n; k++) {
                long x = Long.parseLong(io.next());
                int base = (int)(x % m);
                int i = 0;
                while (true) {
                    int pos = (int)((base + (long)c * i) % m);
                    int cur = slot[pos];
                    if (cur == -1) { slot[pos] = (int)x; break; }
                    if (cur == (int)x) break;
                    i++;
                }
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < m; i++) {
                if (i > 0) sb.append(' ');
                sb.append(slot[i]);
            }
            out.write(sb.toString());
        }
    }
}