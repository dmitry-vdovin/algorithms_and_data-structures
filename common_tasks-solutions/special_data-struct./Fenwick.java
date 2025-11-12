import java.io.*;
import java.util.*;

public class Main {
    static class Fenwick {
        long[] bit;
        int n;

        Fenwick(int n) {
            this.n = n;
            bit = new long[n + 1];
        }

        void add(int i, long x) {
            for (i++; i <= n; i += i & -i)
                bit[i] += x;
        }

        long sum(int i) {
            long s = 0;
            for (; i > 0; i -= i & -i)
                s += bit[i];
            return s;
        }

        long rangeSum(int l, int r) {
            return sum(r) - sum(l);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        Fenwick f = new Fenwick(n);
        for (int i = 0; i < n; i++) {
            long val = Long.parseLong(st.nextToken());
            f.add(i, val);
        }

        int q = Integer.parseInt(br.readLine());
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            String cmd = st.nextToken();
            if (cmd.equals("Add")) {
                int idx = Integer.parseInt(st.nextToken());
                long x = Long.parseLong(st.nextToken());
                f.add(idx, x);
            } else { // FindSum
                int l = Integer.parseInt(st.nextToken());
                int r = Integer.parseInt(st.nextToken());
                out.println(f.rangeSum(l, r));
            }
        }
        out.flush();
    }
}