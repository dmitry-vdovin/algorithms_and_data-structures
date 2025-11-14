import java.io.*;

public class Main {
    static final class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;
        FastScanner(InputStream is) { in = is; }
        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }
        long nextLong() throws IOException {
            int c;
            do { c = read(); } while (c <= 32);
            int sign = 1;
            if (c == '-') { sign = -1; c = read(); }
            long val = 0;
            while (c > 32) {
                val = val * 10 + (c - '0');
                c = read();
            }
            return val * sign;
        }
        char nextChar() throws IOException {
            int c;
            do { c = read(); } while (c <= 32);
            return (char)c;
        }
        int nextInt() throws IOException { return (int)nextLong(); }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(new FileInputStream("bst.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("bst.out")));

        int n = fs.nextInt();
        if (n == 0) { out.println("YES"); out.close(); return; }

        long[] val = new long[n + 2];
        int[] L = new int[n + 2];
        int[] R = new int[n + 2];

        val[1] = fs.nextLong();
        for (int line = 2; line <= n; line++) {
            long m = fs.nextLong();
            int p = fs.nextInt();
            char c = fs.nextChar();
            val[line] = m;
            if (c == 'L') L[p] = line; else R[p] = line;
        }

        int[] stackNode = new int[n];
        long[] stackLo = new long[n];
        long[] stackHi = new long[n];
        int top = 0;

        stackNode[top] = 1;
        stackLo[top] = Long.MIN_VALUE;
        stackHi[top] = Long.MAX_VALUE;
        top++;

        boolean ok = true;
        while (top > 0 && ok) {
            top--;
            int v = stackNode[top];
            long lo = stackLo[top], hi = stackHi[top];
            long x = val[v];
            if (x < lo || x >= hi) { ok = false; break; }

            int r = R[v];
            if (r != 0) {
                stackNode[top] = r;
                stackLo[top] = x;
                stackHi[top] = hi;
                top++;
            }
            int l = L[v];
            if (l != 0) {
                stackNode[top] = l;
                stackLo[top] = lo;
                stackHi[top] = x;
                top++;
            }
        }

        out.println(ok ? "YES" : "NO");
        out.close();
    }
}