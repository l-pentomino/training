package gk.training.codeeval;

import java.io.File;
import java.util.*;

/**
 * Date: 6/30/15
 * Check if an integer is the sum of two perfect squares
 * https://www.codeeval.com/open_challenges/33/
 */
public class CodeEval33 {

    public static void main(String[] args) throws Exception {
        Random rnd = new Random();
        int[] nums = new int[1000];
        for (int i = 0; i < nums.length; i++) {
           nums[i] = rnd.nextInt(Integer.MAX_VALUE);
        }
        Scanner sc = new Scanner(new File(System.getProperty("user.home") + "/numberstest.txt"));
        int count = 0;
        while (sc.hasNextLine()) {
            String str = sc.nextLine();
            String[] arr = str.split("\\s+");
            int num = Integer.parseInt(arr[0]);
            int control = Integer.parseInt(arr[1]);

            int mine = sumsOfSquares(num);
            if (control > 1) {
            System.out.println("Number " + num + "\tControl: " + control
                                           + "\tMine: " + mine + ":" + (control == mine ? "PASSED" : "FAILED"));
            }
            if (mine != control) count++;
        }
        System.out.println("Failed cases: " + count);
    }

    public static int sumsOfSquares(int num) {
        if (num < 0) return 0;
        Set<Integer> squares = new HashSet<Integer>();
        for (int i = 0; i * i <= num; i++) {
            squares.add(i * i);
        }
        int result = 0;
        for (int n : squares) {
            int second = num - n;
            if (squares.contains(second)) {
                if (n <= second) result++;
            }
        }
        return result;
    }
}
