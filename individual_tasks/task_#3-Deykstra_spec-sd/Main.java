import java.io.*;
import java.util.Arrays;

public class Main {

    static final class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int len = 0, ptr = 0;

        FastScanner(InputStream is) {
            in = is;
        }

        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }

        long nextLong() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= 32);
            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }
            long v = 0;
            while (c > 32) {
                v = v * 10 + (c - '0');
                c = read();
            }
            return v * sign;
        }

        int nextInt() throws IOException {
            return (int) nextLong();
        }
    }

    // мин-куча для (dist, node) 
    static final class MinHeap {
        long[] keyDistance;   // ключ — расстояние
        int[] nodeId;         // идентификатор клетки
        int size = 0;

        MinHeap(int capacity) {
            keyDistance = new long[capacity];
            nodeId = new int[capacity];
        }

        void push(long dist, int id) {
            if (size == keyDistance.length) grow();
            int i = size++;
            keyDistance[i] = dist;
            nodeId[i] = id;
            siftUp(i);
        }

        boolean isEmpty() {
            return size == 0;
        }

        long topDistance() {
            return keyDistance[0];
        }

        int topId() {
            return nodeId[0];
        }

        void pop() {
            int last = --size;
            keyDistance[0] = keyDistance[last];
            nodeId[0] = nodeId[last];
            siftDown(0);
        }

        private void siftUp(int i) {
            while (i > 0) {
                int p = (i - 1) >>> 1;
                if (keyDistance[p] <= keyDistance[i]) break;
                swap(i, p);
                i = p;
            }
        }

        private void siftDown(int i) {
            while (true) {
                int l = (i << 1) + 1, r = l + 1, m = i;
                if (l < size && keyDistance[l] < keyDistance[m]) m = l;
                if (r < size && keyDistance[r] < keyDistance[m]) m = r;
                if (m == i) break;
                swap(i, m);
                i = m;
            }
        }

        private void swap(int i, int j) {
            long td = keyDistance[i];
            keyDistance[i] = keyDistance[j];
            keyDistance[j] = td;
            int tv = nodeId[i];
            nodeId[i] = nodeId[j];
            nodeId[j] = tv;
        }

        private void grow() {
            int n = keyDistance.length * 2 + 1;
            keyDistance = Arrays.copyOf(keyDistance, n);
            nodeId = Arrays.copyOf(nodeId, n);
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner in = new FastScanner(new FileInputStream("in.txt"));

        int rows = in.nextInt();
        int cols = in.nextInt();
        int cellsCount = rows * cols;

        int[] heightByCell = new int[cellsCount];
        for (int r = 0, id = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++, id++) {
                heightByCell[id] = in.nextInt();
            }
        }

        long stepCost = in.nextLong();

        int startRow = in.nextInt() - 1;
        int startCol = in.nextInt() - 1;
        int goalRow = in.nextInt() - 1;
        int goalCol = in.nextInt() - 1;

        int startId = startRow * cols + startCol;
        int goalId = goalRow * cols + goalCol;

        long[] minDistance = new long[cellsCount];
        Arrays.fill(minDistance, Long.MAX_VALUE);
        boolean[] settled = new boolean[cellsCount];

        MinHeap pq = new MinHeap(Math.max(4, cellsCount / 4));
        minDistance[startId] = 0;
        pq.push(0L, startId);

        final int[] dRow = {-1, 1, 0, 0};
        final int[] dCol = {0, 0, -1, 1};

        while (!pq.isEmpty()) {
            long curDist = pq.topDistance();
            int curId = pq.topId();
            pq.pop();

            if (settled[curId]) continue;
            settled[curId] = true;
            if (curId == goalId) break;

            int r = curId / cols, c = curId % cols;
            int hHere = heightByCell[curId];

            for (int k = 0; k < 4; k++) {
                int nr = r + dRow[k], nc = c + dCol[k];
                if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) continue;

                int nextId = nr * cols + nc;
                long edgeWeight = stepCost + Math.abs((long) hHere - (long) heightByCell[nextId]);
                long cand = curDist + edgeWeight;

                if (cand < minDistance[nextId]) {
                    minDistance[nextId] = cand;
                    pq.push(cand, nextId);
                }
            }
        }

        try (PrintWriter out = new PrintWriter("out.txt")) {
            out.println(minDistance[goalId]);
        }
    }
}