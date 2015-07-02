package gk.training.codeeval;

/**
 * Date: 7/1/15
 */
public class CodeEvalInterruptedBubbleSort {

    public static void main(String[] args) {
        String input = "54 46 0 34 15 48 47 53 25 18 50 5 21 76 62 48 74 1 43 74 78 29 | 1";
        String[] arr = input.split("\\|");
        long iterations = Long.parseLong(arr[1].trim());
        String numbers = arr[0].trim();
        long[] result = bubbleSort(numbers, iterations);
        for (long i : result) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public static long[] bubbleSort(String nums, long iterations) {
        String[] arr = nums.split("\\s+");
        long[] result = new long[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = Long.parseLong(arr[i]);
        }
        boolean isSorted = false;
        long realIterations = iterations >= result.length ? result.length : iterations;
        long times = 0;
        while (!isSorted && times++ < realIterations) {
            isSorted = true;
            for (int i = 0; i < result.length -1; i++) {
                if (result[i] > result[i+1]) {
                    long temp = result[i];
                    result[i] = result[i + 1];
                    result[i + 1] = temp;
                    isSorted = false;
                }
            }
        }
        return result;
    }
}
