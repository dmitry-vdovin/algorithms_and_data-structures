import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int sequencesLength = scanner.nextInt();

        int[] firstSequence = new int[sequencesLength];
        int[] secondSequence = new int[sequencesLength];

        for (int i = 0; i < sequencesLength; i++) {
            firstSequence[i] = scanner.nextInt();
        }
        for (int j = 0; j < sequencesLength; j++) {
            secondSequence[j] = scanner.nextInt();
        }

        int result = findMaxLength(firstSequence, secondSequence);
        System.out.println(result);

        restoreIndex(firstSequence, secondSequence);
    }

    public static int findMaxLength(int[] arr1, int[] arr2) {

        // n - строки справочника
        // m - столбцы
        
        int n = arr1.length, m = arr2.length;
        int[][] dp = new int[n + 1][m + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (arr1[i - 1] == arr2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[n][m];
    }

    public static void restoreIndex(int[] arr1, int[] arr2) {
        int n = arr1.length, m = arr2.length;
        int[][] dp = new int[n + 1][m + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (arr1[i - 1] == arr2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        // индексы одинаковых элементов
        List<Integer> firstIdx = new ArrayList<>();
        List<Integer> secondIdx = new ArrayList<>();

        int i = n, j = m;
        while (i > 0 && j > 0) {
            if (arr1[i - 1] == arr2[j - 1]) {
                firstIdx.add(i - 1);
                secondIdx.add(j - 1);
                i--;
                j--;
            } else if (dp[i - 1][j] >= dp[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        Collections.reverse(firstIdx);
        Collections.reverse(secondIdx);

        for (int idx : firstIdx) System.out.print(idx + " ");
        System.out.println();
        for (int idx : secondIdx) System.out.print(idx + " ");
        System.out.println();
    }
}