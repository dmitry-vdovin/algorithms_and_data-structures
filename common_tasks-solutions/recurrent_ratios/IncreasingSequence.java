import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

public class Main {

        static final class FastScanner implements Closeable {
            private final InputStream in;
            private final byte[] buffer = new byte[1 << 16];
            private int ptr = 0, len = 0;

            FastScanner(InputStream is) {this.in = is; }

            private int read() throws IOException {
                if (ptr >= len) {
                    len = in.read(buffer);
                    ptr = 0;
                    if (len <= 0) return -1;
                }
                return buffer[ptr++];
            }

            int nextInt() throws IOException {
                int c, sign = 1, x = 0;

                do c = read(); while (c <= 32);

                if (c == '-') { sign = -1; c = read(); }

                while (c > 32) {
                    x = x * 10 + (c - '0');
                    c = read();
                }
                return x * sign;
            }

            @Override public void close() throws IOException { in.close(); }
        }


    public static void main(String[] args) throws Exception {
        // читаем из input.txt
        try (FastScanner fs = new FastScanner(new FileInputStream("input.txt"));
             PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("output.txt")))) {

            int n = fs.nextInt();
            int[] tails = new int[n]; // tails[len] — минимально возможный хвост длины len+1
            int size = 0;

            for (int i = 0; i < n; i++) {
                int x = fs.nextInt();

                // бинарный поиск
                int l = 0, r = size;
                while (l < r) {
                    int m = (l + r) >>> 1;
                    if (tails[m] >= x) r = m;
                    else l = m + 1;
                }

                tails[l] = x;
                if (l == size) size++;
            }

            out.println(size);
        }


        }
}