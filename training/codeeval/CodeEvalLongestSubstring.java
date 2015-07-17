package gk.training.codeeval;

import java.io.InputStream;

public class CodeEvalLongestSubstring {

    public static void main(String[] args) throws Exception {
        String s = "thisisatest;testing123testing";


        String[] arr = s.split(";");
        System.out.println(lcs(arr[0], arr[1]));


    }

    public static String lcs(String s1, String s2) {
        int[][] table = new int[s1.length()+1][s2.length()+1];

        for (int i = 0; i < s1.length(); i++)
            for (int j = 0; j < s2.length(); j++)
                if (s1.charAt(i) == s2.charAt(j)) {
                    table[i+1][j+1] = table[i][j] + 1;
                } else {
                    table[i+1][j+1] = Math.max(table[i + 1][j], table[i][j+1]);
                }

        String result = "";
        int row = s1.length(), col = s2.length();
        while (row != 0 && col != 0) {
            if (table[row][col] == table[row-1][col]) {
                row--;
            } else {
                if (table[row][col] == table[row][col-1]) {
                    col--;
                } else {
                    result = s1.charAt(row-1) + result;
                    row--; col--;
                }
            }
        }
        return result;
    }


    public static void say(Object o) {
        System.out.println(o);
    }
}
