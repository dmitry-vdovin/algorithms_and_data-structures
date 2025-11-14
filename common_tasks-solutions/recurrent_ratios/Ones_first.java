import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final int MOD = 1_000_000_007;

        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt(); // длина строки
        int k = scanner.nextInt(); // кол-во единиц

        int[][] memoryMap = new int[n + 1][k + 1];
        for (int i = 0; i <= n; i++) {
            for (int l = 0; l <= k; l++) {
                if (i == l) {
                    memoryMap[i][l] = 1;
                    continue;
                }
                if (l == 0) {
                    memoryMap[i][l] = 1;
                    continue;
                }

                if (i > l) {
                    memoryMap[i][l] = (int)(((long)memoryMap[i - 1][l - 1] + memoryMap[i - 1][l]) % MOD);
                }
            }
        }

        System.out.println(memoryMap[n][k]);
    }
}