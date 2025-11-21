import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"));
             PrintWriter out = new PrintWriter(new FileWriter("output.txt"))) {

            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            int[][] adj = new int[n][n];
            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                int u = Integer.parseInt(st.nextToken()) - 1;
                int v = Integer.parseInt(st.nextToken()) - 1;
                adj[u][v] = 1;
                adj[v][u] = 1;
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    sb.append(adj[i][j]);
                    if (j + 1 < n) sb.append(' ');
                }
                sb.append('\n');
            }
            out.print(sb);
        }
    }
}