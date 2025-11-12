import java.util.Scanner;

public class Main {

    static int lowerBound(int[] nums, int x) {
        int l = 0, r = nums.length;
        while (l < r) {
            int m = (l + r) >>> 1;
            if (nums[m] < x) l = m + 1;
            else r = m;
        }
        return l;
    }

    static int upperBound(int[] nums, int x) {
        int l = 0, r = nums.length;
        while (l < r) {
            int m = (l + r) >>> 1;
            if (nums[m] <= x) l = m + 1;
            else r = m;
        }
        return l;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        int[] array = new int[n];
        for (int i = 0; i < array.length; i++) {
            int value = scanner.nextInt();
            array[i] = value;
        }

        int k = scanner.nextInt();

        for (int j = 0; j < k; j++) {

            int x = scanner.nextInt();

            int l = lowerBound(array, x);
            int r = upperBound(array, x);
            int b = (l < n && array[l] == x) ? 1 : 0;

            System.out.println(b + " " + l + " " + r);

        }

    }
}