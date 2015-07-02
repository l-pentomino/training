package gk.training.codeeval;

import java.util.*;

public class CodeEvalPrimes {

    static class Chain {
        int chains;
        int maxN;
        Integer[] primes;


        public Chain(int max) {
            maxN = max;
            primes = generatePrimes(max);
        }

        public int combinations() {
            List<Integer> sequence = new ArrayList<Integer>();
            recurse(1, 0, sequence, 0);
            return chains;
        }

        //generates all possible primes in range of [3..(maxN-1 + maxN)]
        private Integer[] generatePrimes(int maxN) {
            int ceiling = maxN * 2 - 1;
            List<Integer> result = new ArrayList<Integer>();
            for (int i = 3; i <= ceiling; i++) {
                if (isPrime(i)) result.add(i);
            }
            return result.toArray(new Integer[result.size()]);
        }

        private  boolean isPrime(int num) {
            if (num == 2) return true;
            for (int i = 2; i <= Math.sqrt(num); i++) {
                if (num % i == 0) return false;
            }
            return true;
        }

        //add a single 'bead' to a partially constructed chain
        private  int addBead(int chain, int value) {
            return chain | (0x1 << value);
        }

        //check if bead 'value' is already used
        private  boolean containsBead(int chain, int value) {
            return (chain & (0x1 << value)) != 0;
        }

        //total number of beads used
        private int totalUsed(int chain, int maxN) {
            int result = 0;
            for (int i = 0; i < maxN; i++) {
                result += ((chain & (0x1 << i)) >>> i);
            }
            return result;
        }

        //constructs the prime-sum "chains" recursively:
        //for a chain with n currently connected beads,
        //chain(n+1) = chain(n) + (prime_sums[i] - n-th bead) for i = 0..sizeof(prime_sums)
        private void recurse(int i, int beads,
                             List<Integer> sequence, int level) {
            List<Integer> next = new ArrayList<Integer>();

            char[] spaces = new char[level];
            Arrays.fill(spaces, ' ');

            int nextBeads = addBead(beads, i - 1);
            if (totalUsed(nextBeads, maxN) == maxN && isPrime(i + 1)) chains++;
            List<Integer> newSequence = new ArrayList<Integer>();
            for (Integer n : sequence) {
                newSequence.add(n);
            }
            newSequence.add(i);


            say("\n"+String.valueOf(spaces) + "Exploring "+ i + ":");
            sayln(String.valueOf(spaces) + Arrays.toString(newSequence.toArray()));

            int pindex = 0;
            int diff = primes[pindex] - i;
            while (diff <= 1) {
                pindex++;
                diff = primes[pindex] - i;
            }
            while (pindex < primes.length) {
                diff = primes[pindex] - i;
                if (diff <= maxN && !containsBead(beads, diff - 1)) {
                    next.add(diff);
                    sayln(String.valueOf(spaces) + "Adding " + diff);
                }
                pindex++;
            }

            for (int val : next) recurse(val,nextBeads, newSequence, level + 1);
            if (newSequence.size() == maxN) {
                sayln("--------");
                sayln(Arrays.toString(newSequence.toArray()));
            }
        }



    }

    private static void sayln(Object o) {
        System.out.println(o);
    }

    private static void say(Object o) {
        System.out.print(o + " ");
    }

    public static void main(String[] args) {
        for (int i = 2; i < 18; i += 2) {
            Chain ch = new Chain(i);
            sayln(ch.combinations());
        }
    }
}
