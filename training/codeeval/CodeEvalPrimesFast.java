package gk.training.codeeval;

import java.util.*;

import static gk.training.codeeval.CodeEvalPrimes.sayln;
/**
 * Date: 7/2/15
 */
public class CodeEvalPrimesFast {
    public static void main(String[] args) {
        int max = 8;
        solve(max);
        //sayln(mchoosen(8, 3));
    }

    public static void solve(int max) {
        Integer[] primes = generatePrimes(max);
        //numbef of ways to arrange the prime sums in (max - 1) slots
        Map<Integer, List<int[]>> pairsums = new HashMap<Integer, List<int[]>>();
        for (int prime : primes) {
            if (prime == 3) continue;
            //find summand pairs
            sayln("Summands for prime " + prime);
            List<int[]> summands = new ArrayList<int[]>();

            for (int i = 1; i <= prime / 2; i++) {
                if (i <= max && prime - i <= max) {
                    int[] pair = new int[2];
                    pair[0] = i; pair[1] = prime - i;
                    sayln("\t" + Arrays.toString(pair));
                    summands.add(pair);
                }
            }
            pairsums.put(prime, summands);
        }
}

    public static long fastFactorial(int start, int m) {
        int result = start;
        for (int i = start + 1; i <= m; i++) {
            result *= i;
        }
        return result;
    }
    //m - total available slots, n - slots per prime sum
    public static long mchoosen(int m, int n) {
        int greater = n > (m - n) ? n : (m - n);
        long ffm = fastFactorial (greater+1, m);
        sayln(ffm);
        sayln(fastFactorial(1, m - greater));
          return ffm /  fastFactorial(1, m - greater);
    }

    public static Integer[] generatePrimes(int max) {
        List<Integer> result = new ArrayList<Integer>();
        int ceiling = max * 2 -1;
        for (int i = 3; i <= ceiling; i++) {
            if (isPrime(i)) result.add(i);
        }
        return result.toArray(new Integer[result.size()]);
    }

    private static boolean isPrime(int num) {
        if (num == 2) return true;
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) return false;
        }
        return true;
    }
}
