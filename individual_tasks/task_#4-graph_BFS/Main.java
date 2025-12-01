import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader("input.in"));
             PrintWriter writer = new PrintWriter("output.out")) {

            int n = Integer.parseInt(reader.readLine().trim());
            int[][] acquaintance = new int[n][n];

            for (int i = 0; i < n; i++) {
                String[] parts = reader.readLine().trim().split("\\s+");
                for (int j = 0; j < n; j++) {
                    acquaintance[i][j] = Integer.parseInt(parts[j]);
                }
            }

            int[] color = new int[n];
            Arrays.fill(color, -1); // -1 = не посещён

            boolean isBipartite = true;

            for (int start = 0; start < n && isBipartite; start++) {
                if (color[start] != -1) continue;

                Queue<Integer> queue = new ArrayDeque<>();
                queue.add(start);
                color[start] = 0;

                while (!queue.isEmpty() && isBipartite) {
                    int person = queue.poll();
                    for (int other = 0; other < n; other++) {
                        if (person == other) continue;

                        if (acquaintance[person][other] == 1) { // знакомы → разные группы
                            if (color[other] == -1) {
                                color[other] = 1 - color[person];
                                queue.add(other);
                            } else if (color[other] == color[person]) {
                                isBipartite = false;
                                break;
                            }
                        }
                    }
                }
            }

            if (!isBipartite) {
                writer.println("NO");
            } else {
                writer.println("YES");

                List<Integer> groupA = new ArrayList<>();
                List<Integer> groupB = new ArrayList<>();

                for (int i = 0; i < n; i++) {
                    if (color[i] == 0) groupA.add(i + 1);
                    else groupB.add(i + 1);
                }

                if (groupA.isEmpty()) groupA.add(groupB.remove(0));
                if (groupB.isEmpty()) groupB.add(groupA.remove(0));

                for (int i = 0; i < groupA.size(); i++) {
                    if (i > 0) writer.print(" ");
                    writer.print(groupA.get(i));
                }
                writer.println();
            }
        }
    }
}