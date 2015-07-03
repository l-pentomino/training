package gk.training.codeeval;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 7/2/15
 */
public class CodeEvalLongestSubstr {


    public static String longestSubstr(String s1, String s2) {

        int[][] table = new int[s1.length()][s2.length()];
        int rows = s1.length(); int columns = s2.length();

        String result = "";
        int maxLength = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (s1.charAt(row) == s2.charAt(col)) {
                    if (row == 0 || col == 0)
                        table[row][col] = 1;
                    else {
                        table[row][col] = table[row - 1][col - 1] + 1;
                    }
                    if (table[row][col] > maxLength) {
                        maxLength = table[row][col];
                        result = s1.substring(row - maxLength + 1, row + 1);
                    } else {
                        if (table[row][col] == maxLength) {
                            result += s1.substring(row - maxLength + 1, row + 1);
                        }
                    }
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String s1 = "abracadabra";
        String s2 = "abracadabrac";
        System.out.println(longestSubstr(s1, s2));
    }
}
