package gk.training.codeeval;

/**
 * Date: 6/30/15
 */
public class CodeEvalSetBits {
    public static void main(String[] args) {
        String input = "25, 453, 12, 35, 4, 2, 3, 0, -1, -2";
        String[] arr = input.split(",\\s*");
        for (String str : arr) {
            int num = Integer.parseInt(str);
            System.out.println(Integer.toBinaryString(num));
            System.out.println(countBits(num) + "\n");
        }
        System.out.println(Integer.toBinaryString(Integer.MIN_VALUE + 6420000));
        System.out.println(countBits(Integer.MIN_VALUE + 6420000));
    }

    public static int countBits(int num) {
        int result = 0;
        while (num != 0) {
            result += (num & 0x1); //takes care of the negative numbers
            num >>>= 1;
        }
        return result;
    }
}
