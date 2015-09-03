package gk.training.codeeval;

/**
 * Date: 6/30/15
 * Count the number of set bits in an integer
 * https://www.codeeval.com/open_challenges/16/
 */
public class CodeEval16 {

    public static void main(String[] args) {
        String input = "25, 453, 12, 35, 4, 2, 3, 0, -1, -2";
        String[] arr = input.split(",\\s*");
        for (String str : arr) {
            int num = Integer.parseInt(str);
            System.out.println(countBits(num) + "\n");
        }
        System.out.println("-1 in binary:" + Integer.toBinaryString(-1));
        System.out.println(countBits(-1));
    }

    public static int countBits(int num) {
        int result = 0;
        while (num != 0) {
            result += (num & 0x1);
            num >>>= 1; //takes care of negative numbers (regular right shift preserves the MSB)
        }
        return result;
    }
}
