import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"));
             PrintWriter out = new PrintWriter(new FileWriter("output.txt"))) {

            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            List<List<Integer>> adj = new ArrayList<>(n + 1);
            for (int i = 0; i <= n; i++) adj.add(new ArrayList<>());

            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                int u = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                adj.get(u).add(v);
                adj.get(v).add(u);
            }

            for (int i = 1; i <= n; i++) {
                List<Integer> list = adj.get(i);
                if (list.isEmpty()) {
                    out.println(0);
                } else {
                    out.print(list.size());
                    for (int x : list) out.print(" " + x);
                    out.println();
                }
            }
        }
    }
}