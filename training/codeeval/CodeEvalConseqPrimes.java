package gk.training.codeeval;

import java.util.*;

public class CodeEvalConseqPrimes {
    public static void main(String[] args) {
        int test = 12;

        int[][] array = new int[test][test];
        for (int i = 0; i < test ; i++) {
            for (int j = 0; j <  test; j++) {
                if (i == j) continue;
                int sum = i + j + 2;
                if (isPrime(sum)) {
                    array[i][j] = 1;
                }
            }
        }

        for (int[] row : array) {
            for (int col : row) {
                System.out.print(col);
            }
            System.out.println();
        }

        Set<List<Integer>> chains = new HashSet<List<Integer>>();
        traverse(array, 0,  new VisitMap(array[0].length), new ArrayList<Integer>(), chains);

        System.out.println();

        System.out.println(Arrays.toString(chains.toArray()));
        System.out.println(chains.size());

    }

    static class Chain {
        int[] links;

        public Chain(Object[] arr) {
            links = new int[arr.length];
            for (int i = 0; i < links.length; i++) {
                links[i] = (Integer) arr[i];
            }
        }

        public String toString() {
            String result = "";
            for (int link : links) {
                result += link+"->";
            }
            return result.substring(0, result.length() -2);
        }

        public int indexOf(int link) {
            for (int i = 0; i < links.length; i++) {
                if (links[i] == link) return i;
            }
            return -1;
        }

        /* Determine if two chains are
        right-shift transformations of each other */
        public boolean equals(Object o) {
            Chain other = (Chain) o;
            int thisFirst = links[0];
            int offset = other.indexOf(thisFirst);
            if (offset < 0) return false;
            for (int i = 0; i < links.length; i++) {
                if (links[i] != other.links[(i + offset) % links.length])
                    return false;
            }
            return true;
        }

        public boolean isValid() {
            return isPrime(links[0] + links[links.length - 1]);
        }
    }

    static class VisitMap {
        int[] map;

        public VisitMap(int size) {
            map = new int[size];
        }

        //copy constructor
        public VisitMap(VisitMap mp) {
            map = new int[mp.map.length];
            for (int i = 0; i < map.length; i++) {
                map[i] = mp.map[i];
            }
        }

        public boolean visited(int row, int col) {
            return (map[row] & (0x1 << col)) != 0;
        }

        public void visit(int row, int col) {
            map[row] |= (0x1 << col);
            map[col] |= (0x1 << row);
        }
    }

    private static int addLink(int links, int value) {
        return links | (0x1 << value);
    }

    private static boolean containsLink(int links, int value) {
        return (links & (0x1 << value)) != 0;
    }

    private static boolean isFull(int links, int size) {
        int setBits = 0;
        for (int i = 0; i < size; i++) {
            setBits += (links & (0x1 << i)) >>> i;
        }
        return setBits == size;
    }


    public static void traverse(int[][] graph, int row,
                                VisitMap visitMap, List<Integer> links, Set<List<Integer>> linkChains) {
        int size = graph[row].length;
        if (links.size() == size && isPrime(links.get(0) + links.get(size-1)))
        {
            linkChains.add(links);
            System.out.println();
            return;
        }
        for (int i = 0; i < size; i++) {
            if (graph[row][i] == 1 && !visitMap.visited(row, i)
                            && !links.contains(row+1)) {
                //System.out.print(row + ", " + i + " ->");
                //System.out.println(row+1);
                VisitMap newMap = new VisitMap(visitMap);
                newMap.visit(row, i);
                List<Integer> newLinks = new ArrayList<Integer>();
                for (int n : links) newLinks.add(n);
                newLinks.add(row+1);
                traverse(graph, i, newMap, newLinks, linkChains);
            }
        }
    }

    public static boolean isPrime(int num) {
        if (num == 2) return true;
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) return false;
        }
        return true;
    }

}
