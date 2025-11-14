import java.io.*;

public class Main {
    static final class FastScanner {
        private final InputStream in;
        private final byte[] buf = new byte[1 << 16];
        private int len = 0, ptr = 0;
        FastScanner(String file) throws IOException { in = new FileInputStream(file); }
        private int read() throws IOException {
            if (ptr >= len) { len = in.read(buf); ptr = 0; if (len <= 0) return -1; }
            return buf[ptr++];
        }
        long nextLong() throws IOException {
            int c; do { c = read(); } while (c <= 32);
            int sign = 1; if (c == '-') { sign = -1; c = read(); }
            long x = 0;
            while (c > 32) { x = x * 10 + (c - '0'); c = read(); }
            return x * sign;
        }
        int nextInt() throws IOException { return (int) nextLong(); }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner("huffman.in");
        int n = fs.nextInt();
        long[] a = new long[n]; 
        for (int i = 0; i < n; i++) a[i] = fs.nextLong();

        if (n == 1) {
            try (PrintWriter out = new PrintWriter("huffman.out")) {
                out.println(0);
            }
            return;
        }

        long[] b = new long[n];
        int l1 = 0, r1 = n;     
        int l2 = 0, r2 = 0;     
        long ans = 0;

        while ((r1 - l1) + (r2 - l2) > 1) {
            // первый минимум
            long x;
            if (l1 < r1 && (l2 == r2 || a[l1] <= b[l2])) x = a[l1++];
            else x = b[l2++];

            // второй минимум
            long y;
            if (l1 < r1 && (l2 == r2 || a[l1] <= b[l2])) y = a[l1++];
            else y = b[l2++];

            long s = x + y;
            ans += s;
            b[r2++] = s;
        }

        try (PrintWriter out = new PrintWriter("huffman.out")) {
            out.println(ans);
        }
    }
}