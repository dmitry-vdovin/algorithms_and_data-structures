import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) {
        String in = "input.txt";
        String out = "output.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(in));
             PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(out)))) {

            String line = nextNonEmpty(br);
            if (line == null) {
                pw.println(0);
                return;
            }

            int s = Integer.parseInt(line.trim());

            int[] p = new int[s+1];
            for (int i = 0; i < s; i++) {
                String[] parts = nextNonEmpty(br).trim().split("\\s+");
                int n = Integer.parseInt(parts[0]);
                int m = Integer.parseInt(parts[1]);
                if (i == 0) p[0] = n;
                p[i+1] = m;
            }

            long ans = minMultiplications(p);
            pw.println(ans);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String nextNonEmpty(BufferedReader br) throws IOException {
        String s;
        while((s = br.readLine()) != null) {
            if(!s.trim().isEmpty()) return s;
        }
        return null;
    }

    private static long minMultiplications(int[] p) {
        int n = p.length - 1;
        long[][] dp = new long[n][n];

        for (int len = 2; len <= n; len++) {
            for (int i = 0; i + len -1 < n; i++) {
                int j = i + len - 1;
                long best = Long.MAX_VALUE;
                for (int k = i; k < j; k++) {
                    long cost = dp[i][k] + dp[k+1][j] + 1L * p[i] *p[k+1] * p[j+1];
                    if(cost<best) best = cost;
                }
                dp[i][j] = best;
            }
        }
        return dp[0][n-1];
    }

}