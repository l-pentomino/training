package gk.training.codeeval;

/**
 * Date: 6/30/15
 * Swaps letter case in a string
 * https://www.codeeval.com/open_challenges/96/
 */
public class CodeEval96 {
    public static void main(String[] args) {
        String input = "' ',Hello woRld, I am HerE; Not ON youR LIFE!";
        String[] inputs = input.split(",\\s*");
        for (String s : inputs) {
            System.out.println(s);
            System.out.println(switchCase(s));
        }
    }

    public static String switchCase(String s) {
        char[] src = s.toCharArray();
        char[] result = new char[src.length];
        int offset = 'a' - 'A';
        for (int i = 0; i < src.length; i++) {
            char c = src[i];
            result[i] = (char) (c + offset * multiplier(c));
        }
        return String.valueOf(result);
    }

    public static int multiplier(final char c) {
        //multiplier will have the opposite sign of the offset between 'a' and 'A'
        //to determine whether to add or subtract the offset to convert cases
        if (c  >= 'a' && c <= 'z') return -1;
        if (c >= 'A' && c <= 'Z') return 1;
        return 0;
    }
}