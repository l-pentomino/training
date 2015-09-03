package gk.training.codeeval;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 8/9/15
 * Finds maximal sum of consecutive integers within a sequence
 * https://www.codeeval.com/open_challenges/17/
 */
public class CodeEval17 {

    public static int maxSum(int[] nums) {
        int max = Integer.MIN_VALUE;
        for (int start = 0; start < nums.length; start++) {
            for (int end = nums.length - 1; end >= start; end--) {
                int sum = sum(nums, start, end);
                if (max < sum) max = sum;
            }
        }
        return max;
    }

    public static int sum(int[] nums, int start, int end) {
        int sum = 0;
        for (int i = start; i <= end; i++) {
            sum += nums[i];
        }
        return sum;
    }


    private static void say(Object o) {
        System.out.println(o);
    }

    public static void main(String[] args) {
        int[] nums = {-10,2,3,-2,0,5,-15};
        int[] nums2 = {2,3,-2,-1,10};
        say(maxSum(nums));
    }
}
