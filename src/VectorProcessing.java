import java.util.Arrays;

public class VectorProcessing {
    public static void main(String[] args) {
        int[] vector = new int[100000];
        Arrays.fill(vector, 1);

        int n = vector.length;
        int m = 10;

        long startTime = System.currentTimeMillis();
        PosObrabotka(vector, n, 2);
        long endTime = System.currentTimeMillis();
        long sequentialTime = endTime - startTime;

        System.out.println("Последовательная обработка занимает: " + sequentialTime + " мс");

        startTime = System.currentTimeMillis();
        ParallelObrabotka(vector, n, 2, m);
        endTime = System.currentTimeMillis();
        long parallelTime = endTime - startTime;

        System.out.println("Многопоточная обработка: " + parallelTime + " мс");
        System.out.println("Ускорение: " + (double) sequentialTime / parallelTime);
    }

    static void PosObrabotka(int[] vector, int n, int multiplier) {
        for (int i = 0; i < n; i++) {
            vector[i] *= multiplier;
        }
    }

    static void ParallelObrabotka(int[] vector, int n, int multiplier, int m) {
        int blockSize = n / m;
        Thread[] threads = new Thread[m];

        for (int i = 0; i < m; i++) {
            int start = i * blockSize;
            int end = (i == m - 1) ? n : (i + 1) * blockSize;

            threads[i] = new Thread(() -> {
                for (int j = start; j < end; j++) {
                    vector[j] *= multiplier;
                }
            });

            threads[i].start();
        }

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}