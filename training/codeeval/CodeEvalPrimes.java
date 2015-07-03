package gk.training.codeeval;

import java.util.*;

public class CodeEvalPrimes {
    static int maxDepth;
    static Map<Integer, Chain> solved = new HashMap<Integer, Chain>();

    static class Chain {
        int chains;
        int maxN;
        Integer[] primes;

        public Chain(int max) {
            maxN = max;
            primes = generatePrimes(max);
        }

        public int combinations() {
            chains = 0;
            recurse(1, 0);
            return chains;
        }

        public int combinations(Chain other) {
            if (other.maxN >= this.maxN) return combinations();
            chains = other.chains;
            int partial = 0;
            for (int i = 0; i < other.maxN; i++) {
                partial = addBead(partial, i);
            }
            for (int i = 2; i <= other.maxN; i++)
            if (isPrime(i + 1)) recurse(i, partial);
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
        private void recurse(int nthBead, int beads){
            List<Integer> toExplore = new LinkedList<Integer>();

            //append current bead to the chain
            int nextBeads = addBead(beads, nthBead - 1);

            //if the last bead was added, check if it sums to a prime with the first bead (1)
            //before incrementing counter:
            if (totalUsed(nextBeads, maxN) == maxN && isPrime(nthBead + 1)) {
                chains++;
                return;
            }

            int pindex = 0; int diff = primes[pindex] - nthBead;

            //advance within the primes table until the first prime is found
            //that is large enough to contain current bead's value
            while (diff <= 1) {
                pindex++;
                diff = primes[pindex] - nthBead;
            }

            //find all possible prime sums that contain the current bead,
            //making the difference to be the beads to explore on next iterations
            while (pindex < primes.length) {
                diff = primes[pindex] - nthBead;
                if (diff <= maxN && !containsBead(beads, diff - 1)) {
                    toExplore.add(diff);
                }
                pindex++;
            }

            for (int next : toExplore) {
                maxDepth++;
                recurse(next, nextBeads);
            }
        }
    }

    public static void sayln(Object o) {
        System.out.println(o);
    }

    public static void say(Object o) {
        System.out.print(o + " ");
    }

    public static void main(String[] args) {
        for (int i = 8; i <= 18; i += 10) {
            Chain ch = new Chain(i);
            sayln(ch.combinations());
        }
        sayln("Max recursion depth " + maxDepth);
    }
}
