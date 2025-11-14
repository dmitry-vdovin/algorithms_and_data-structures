import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final int MOD = 1_000_000_007;
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt(); // длина строки
        int k = scanner.nextInt(); // кол-во единиц

        if (k > n) {
            System.out.println(0);
            return;
        }

        long result = 1;

        for (int i = 1; i <= k; i++) {
            result = result * (n - i + 1) % MOD;

            // вычисляем обратный элемент для i по модулю MOD
            long inverse = 1;
            long base = i % MOD;
            long exponent = MOD - 2;

            while (exponent > 0) {
                if ((exponent & 1) == 1) {
                    inverse = inverse * base % MOD;
                }
                base = base * base % MOD;
                exponent >>= 1;
            }

            // эквивалент деления на i
            result = result * inverse % MOD;
        }

        System.out.println(result);
    }
}