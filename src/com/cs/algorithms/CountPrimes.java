package com.cs.algorithms;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CountPrimes {

    public static final long NUMBER_OF_PRIMES_SMALLER_THAN_THIRTY = 10L;

    public static void main(String[] args) {
        CountPrimes countPrimes = new CountPrimes();
        final Long end = 1000L * 1000 * 1000;
        System.out.println("Without Stamp");
        countPrimes.countPrimesWithoutStamp(end);
        System.out.println("With Stamp");
        countPrimes.countPrimesWithStamp(end);
        try {
            System.out.println("Multithreading");
            countPrimes.countPrimesWithStampAndMultithreading(end);
        } catch (InterruptedException ex) {}
    }

    private void countPrimesWithoutStamp(final Long end) {
        long timeStart = System.nanoTime();

        final Double endsqrt = Math.sqrt(end);
        final boolean[] result = new boolean[end.intValue() + 1];
        result[1] = true;
        result[2] = true;

        for (int i = 1; i <= endsqrt; i++) {
            filterPrimes(end, result, i);
        }
        long count = 1L;
        for (int i = 3; i <= end; i += 2) {
            if (!result[i]) {
                count++;
            }
        }

        System.out.println("count " + count);

        long timeEnd = System.nanoTime();
        System.out.println(TimeUnit.NANOSECONDS.toMillis(timeEnd-timeStart));
    }

    private void countPrimesWithStamp(final Long end) {
        long timeStart = System.nanoTime();

        final Double endsqrt = Math.sqrt(end);
        final boolean[] result = new boolean[end.intValue() + 1];
        // 1,7,11,13,17,19,23,29

        for (int i = 7; i <= endsqrt; i+=6) {
            filterPrimes(end, result, i);
            i += 4;
            filterPrimes(end, result, i);
            i += 2;
            filterPrimes(end, result, i);
            i += 4;
            filterPrimes(end, result, i);
            i += 2;
            filterPrimes(end, result, i);
            i += 4;
            filterPrimes(end, result, i);
            i += 6;
            filterPrimes(end, result, i);
            i += 2;
            filterPrimes(end, result, i);
        }

        long count = NUMBER_OF_PRIMES_SMALLER_THAN_THIRTY;
        long end30 = end - 30;
        for (int i = 30; i <= end30; i += 30) {
            if (!result[i + 1]) {
                count++;
            }
            if (!result[i + 7]) {
                count++;
            }
            if (!result[i + 11]) {
                count++;
            }
            if (!result[i + 13]) {
                count++;
            }
            if (!result[i + 17]) {
                count++;
            }
            if (!result[i + 19]) {
                count++;
            }
            if (!result[i + 23]) {
                count++;
            }
            if (!result[i + 29]) {
                count++;
            }
        }

        System.out.println("count " + count);

        long timeEnd = System.nanoTime();
        System.out.println(TimeUnit.NANOSECONDS.toMillis(timeEnd-timeStart));
    }

    private void filterPrimes(Long end, boolean[] result, int i) {
        if (!result[i]) {
            for (int j = 3 * i; j <= end; j += 2 * i) {
                result[j] = true;
            }
        }
    }

    private void countPrimesWithStampAndMultithreading(final Long end) throws InterruptedException {
        long timeStart = System.nanoTime();
        final Double endsqrt = Math.sqrt(end);
        final boolean[] result = new boolean[end.intValue() + 1];
        // 1,7,11,13,17,19,23,29
        for (int i = 7; i <= endsqrt; i+=6) {
            final ExecutorService executor = Executors.newFixedThreadPool(8);
            executor.execute(new FilterPrimeThread(end, result, i));
            i += 4;
            executor.execute(new FilterPrimeThread(end, result, i));
            i += 2;
            executor.execute(new FilterPrimeThread(end, result, i));
            i += 4;
            executor.execute(new FilterPrimeThread(end, result, i));
            i += 2;
            executor.execute(new FilterPrimeThread(end, result, i));
            i += 4;
            executor.execute(new FilterPrimeThread(end, result, i));
            i += 6;
            executor.execute(new FilterPrimeThread(end, result, i));
            i += 2;
            executor.execute(new FilterPrimeThread(end, result, i));
            executor.shutdown();
            boolean finished = executor.awaitTermination(1, TimeUnit.MINUTES);
        }
        long count = NUMBER_OF_PRIMES_SMALLER_THAN_THIRTY;
        long end30 = end - 30;
        for (int i = 30; i <= end30; i += 30) {
            if (!result[i + 1]) {
                count++;
            }
            if (!result[i + 7]) {
                count++;
            }
            if (!result[i + 11]) {
                count++;
            }
            if (!result[i + 13]) {
                count++;
            }
            if (!result[i + 17]) {
                count++;
            }
            if (!result[i + 19]) {
                count++;
            }
            if (!result[i + 23]) {
                count++;
            }
            if (!result[i + 29]) {
                count++;
            }
        }

        System.out.println("count " + count);

        long timeEnd = System.nanoTime();
        System.out.println(TimeUnit.NANOSECONDS.toMillis(timeEnd-timeStart));
    }

    class FilterPrimeThread implements Runnable {
        private Long end;
        private boolean[] result;
        int i;

        public FilterPrimeThread(final Long end, final boolean[] result, final int i){
            this.end = end;
            this.result = result;
            this.i = i;
        }
        public void run() {
            if (!result[i]) {
                for (int j = 3 * i; j <= end; j += 2 * i) {
                    result[j] = true;
                }
            }
        }
    }
}
