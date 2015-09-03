package gk.training.codeeval;

import java.util.Stack;

/**
 * Date: 7/2/15
 * Check if the expression is correctly parenthesized
 */
public class CodeEval68 {
    public static void main(String[] args) {
        String[] tests = {"", "{[]}", "({}[()})", "[{}[]", "{([]aa kh})", "{[aaa(hkh)]}", "((((}))))))"};
        for (String test : tests) {
            System.out.println(isValid(test) ? "True" : "False");
        }
        System.out.println(Integer.toBinaryString(15 & 10));
    }

    public static boolean isValid(String str) {

        Stack<Character> stack = new Stack<Character>();
        for (char c : str.toCharArray()) {
           if ("{[(".contains(c + "")) stack.push(c);
           else {
               if (!"]})".contains(c + "")) continue;
               if (stack.isEmpty()) return false;
               char popped = stack.pop();
               if (popped != pairof(c)) return false;
           }
        }
        if (!stack.isEmpty()) return false;
        return true;
    }

    public static char pairof(char c) {
        switch (c) {
            case ']': return '[';
            case '}': return '{';
            case ')': return '(';
        }
        return ' ';
    }
}
