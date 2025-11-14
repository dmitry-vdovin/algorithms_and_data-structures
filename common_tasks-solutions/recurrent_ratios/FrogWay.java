import java.util.*;

public class Main {
    private static final int UNREACHABLE = -1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int[] mosquitoes = new int[n];
        for (int i = 0; i < n; i++) mosquitoes[i] = scanner.nextInt();

        int[] maxScore = new int[n];
        int[] prevIndex = new int[n];
        Arrays.fill(maxScore, UNREACHABLE);
        Arrays.fill(prevIndex, -1);

        maxScore[0] = mosquitoes[0];
        if (n >= 3 && maxScore[0] != UNREACHABLE) {
            maxScore[2] = maxScore[0] + mosquitoes[2];
            prevIndex[2] = 0;
        }
        for (int i = 3; i < n; i++) {
            int fromTwo = maxScore[i - 2];
            int fromThree = maxScore[i - 3];
            if (fromTwo == UNREACHABLE && fromThree == UNREACHABLE) {
                maxScore[i] = UNREACHABLE;
            } else if (fromTwo >= fromThree) {
                maxScore[i] = fromTwo + mosquitoes[i];
                prevIndex[i] = i - 2;
            } else {
                maxScore[i] = fromThree + mosquitoes[i];
                prevIndex[i] = i - 3;
            }
        }

        if (maxScore[n - 1] == UNREACHABLE) {
            System.out.println(-1);
            return;
        }

        System.out.println(maxScore[n - 1]);

        int[] path = new int[n];
        int len = 0, cur = n - 1;
        while (cur != -1) {
            path[len++] = cur + 1;
            cur = prevIndex[cur];
        }

        for (int i = len - 1; i >= 0; i--) {
            System.out.print(path[i]);
            if (i > 0) System.out.print(" ");
        }
    }
}