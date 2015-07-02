package gk.training.codeeval;

import java.io.File;
import java.util.*;

/**
 * Date: 6/30/15
 */
public class CodeEvalSumOfSquares {

    public static void main(String[] args) throws Exception {
        Random rnd = new Random();
        int[] nums = new int[1000];
        for (int i = 0; i < nums.length; i++) {
           nums[i] = rnd.nextInt(Integer.MAX_VALUE);
   //         System.out.println(nums[i]);
        }
        //int[] nums = {50, 0, 9 + 9, 1 + 0, 4 + 4, 4 + 9, 5, 4 + 2, 9 + 18, 64 + 81, 7, 64 + 64, 25, -25};
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
            //System.out.println("Number: " + num + "\tSums:" + sumsOfSquares(num) + "\n");
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
