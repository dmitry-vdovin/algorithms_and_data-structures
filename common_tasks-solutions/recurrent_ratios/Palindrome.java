import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            // чтение из файла
            BufferedReader br = new BufferedReader(new FileReader("input.txt"));
            String s = br.readLine().trim();
            br.close();

            String palindrome = longestPalindromicSubsequence(s);

            // запись в файл
            PrintWriter out = new PrintWriter(new FileWriter("output.txt"));
            out.println(palindrome.length());
            out.println(palindrome);
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String longestPalindromicSubsequence(String s) {
        int n = s.length();
        char[] c = s.toCharArray();
        int[][] dp = new int[n][n];

        // одиночные символы
        for (int i = 0; i < n; i++) {
            dp[i][i] = 1;
        }

        // заполняем таблицу по длине подстроки
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;
                if (c[i] == c[j]) {
                    dp[i][j] = (len == 2) ? 2 : dp[i + 1][j - 1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }

        // восстановление палиндрома
        char[] res = new char[dp[0][n - 1]];
        int L = 0, R = res.length - 1;
        int i = 0, j = n - 1;

        while (i <= j) {
            if (c[i] == c[j]) {
                if (i == j) {
                    res[L] = c[i];
                } else {
                    res[L] = c[i];
                    res[R] = c[j];
                }
                i++;
                j--;
                L++;
                R--;
            } else if (dp[i + 1][j] >= dp[i][j - 1]) {
                i++;
            } else {
                j--;
            }
        }

        return new String(res);
    }
}