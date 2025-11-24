import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"));
             PrintWriter out = new PrintWriter(new FileWriter("output.txt"))) {

            int n = Integer.parseInt(br.readLine());
            int[] parent = new int[n + 1];
            Arrays.fill(parent, 0);

            for (int i = 0; i < n - 1; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int u = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                parent[v] = u;
            }

            for (int i = 1; i <= n; i++) {
                out.print(parent[i]);
                if (i < n) out.print(' ');
            }
        }
    }
}