import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(new File("input.txt"));
        PrintWriter out = new PrintWriter(new File("output.txt"));

        int n = in.nextInt();
        long[] a = new long[n + 1]; 
        for (int i = 1; i <= n; i++) a[i] = in.nextLong();

        boolean isHeap = true;
        for (int i = 1; i <= n / 2; i++) {
            int left = 2 * i;
            int right = 2 * i + 1;
            if (left <= n && a[i] > a[left]) { isHeap = false; break; }
            if (right <= n && a[i] > a[right]) { isHeap = false; break; }
        }

        out.println(isHeap ? "Yes" : "No");
        out.close();
    }
}