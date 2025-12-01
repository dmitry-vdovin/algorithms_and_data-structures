import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"));
             PrintWriter out = new PrintWriter(new FileWriter("output.txt"))) {

            int n = Integer.parseInt(br.readLine());
            int[][] g = new int[n][n];
            for (int i = 0; i < n; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int j = 0; j < n; j++) {
                    g[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            int[] parent = new int[n];
            Arrays.fill(parent, 0);

            for (int u = 0; u < n; u++) {
                for (int v = 0; v < n; v++) {
                    if (g[u][v] == 1) {
                        parent[v] = u + 1;
                    }
                }
            }

            for (int i = 0; i < n; i++) {
                out.print(parent[i]);
                if (i + 1 < n) out.print(" ");
            }
        }
    }
}