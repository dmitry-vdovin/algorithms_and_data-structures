import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main {
    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;

        FastScanner(String file) throws FileNotFoundException {
            this.in = new FileInputStream(file);
        }

        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }

        int nextInt() throws IOException {
            int c, sgn = 1, x = 0;
            do {
                c = read();
            } while (c <= ' ');
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            while (c > ' ') {
                x = x * 10 + (c - '0');
                c = read();
            }
            return x * sgn;
        }

        void close() throws IOException {
            in.close();
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner("report.in");
        int n = fs.nextInt();
        int[] h = new int[n + 1];
        for (int i = 1; i <= n; i++) h[i] = fs.nextInt();
        fs.close();

        int[] inc = new int[n + 1]; // длина возр. посл., заканчив. в i дне
        int[] prev = new int[n + 1]; // для восстановления левого плеча (до пика)

        Arrays.fill(inc, 1);
        Arrays.fill(prev, -1);

        for (int i = 1; i <= n; i++) {
            int best = 0, p = -1;
            for (int j = 1; j < i; j++) {
                if (h[j] < h[i] && inc[j] > best) {
                    best = inc[j];
                    p = j;
                }
            }
            inc[i] = best + 1;
            prev[i] = p;
        }

        int[] dec = new int[n + 1]; // длина убыв. посл, начиная с i
        int[] next = new int[n + 1]; // для восстановления правого плеча (после пика)
        Arrays.fill(dec, 1);
        Arrays.fill(next, -1);
        for (int i = n; i >= 1; i--) {
            int best = 0, q = -1;
            for (int j = i + 1; j <= n; j++) {
                if (h[j] < h[i] && dec[j] > best) {
                    best = dec[j];
                    q = j;
                }
            }
            dec[i] = best + 1;
            next[i] = q;
        }

        // выбираем пик

        int bestK = -1, peak = 1;
        for (int i = 1; i <= n; i++) {
            int k = Math.min(inc[i], dec[i]) - 1;
            if (k > bestK) {
                bestK = k;
                peak = i;
            }
        }
        int k = Math.max(0, bestK);

        // восстанавливаем левое плечо (возрастание до пика)
        ArrayList<Integer> leftFull = new ArrayList<>(); // индексы подъёма до пика (+сам пик)
        for (int x = peak; x != -1; x = prev[x]) leftFull.add(x);
        Collections.reverse(leftFull);
        ArrayList<Integer> left = new ArrayList<>();
        for (int i = Math.max(0, leftFull.size() - (k + 1)); i < leftFull.size(); i++)
            left.add(leftFull.get(i));

        // восстанавливаем правое плечо (убывание от пика)
        ArrayList<Integer> right = new ArrayList<>();
        int x = next[peak];
        for (int t = 0; t < k && x != -1; t++) {
            right.add(x);
            x = next[x];
        }

        // склеиваем
        ArrayList<Integer> ans = new ArrayList<>(left);
        ans.addAll(right);

        // вывод
        try (PrintWriter out = new PrintWriter("report.out")) {
            out.println(k);
            for (int i = 0; i < ans.size(); i++) {
                if (i > 0) out.print(' ');
                out.print(ans.get(i));
            }
            out.println();
        }


    }

}