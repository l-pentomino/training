package gk.training.misc.crunchyroll;

import java.util.*;

/**
 * Date: 9/1/15
 */
public class Parser {

    static class Token {
            static final int NUMBER = 0;
            static final int OPEN_PARENTH = 1;
            static final int CLOSE_PARENTH = 2;
            static final int FUNCTION = 3;
            static final int COMMA = 4;
            static final String DEAD_END = "DEADEND";
            static final String GOAL = "GOAL";

            int type; String value;

            public Token(int type, String val) {
                this.type = type;
                this.value = val;
            }

            @Override
            public String toString() {
                return value;
            }
        }

    private String str;
    private List<Token> tokens;

    abstract static class Function {
        static final String ADD = "add";
        static final String SUBTRACT = "subtract";
        static final String MULTIPLY = "multiply";
        static final String ABS = "abs";

        String name;
        int parity;

        String repr;

        public Function(String name, int parity) {
            this.name = name;
            this.parity = parity;
        }

        public abstract long apply(long...operands);
    }


    Map<String, Function> functions = new HashMap<String, Function>();

    public static Parser fromGrammar(String grm) {
        Parser result = new Parser();
        String[] rules = grm.split("\\n");
        for (final String s : rules) {
            final String[] arr = s.toLowerCase().split(":\\s*");
            Function f;
            if (arr[0].equals(Function.ADD) || arr[0].equals(Function.SUBTRACT)
                            || arr[0].equals(Function.MULTIPLY)) {
                f = new Function(arr[0].toLowerCase(), 2) {
                    @Override
                    public long apply(long...operands) {
                        if(name.equals(Function.ADD)) {
                            long result = operands[0] + operands[1];
                            repr = operands[0] +   " + " + operands[1] + " = " + result;
                            return result;
                        }
                        if (name.equals(Function. SUBTRACT)) {
                            long result = operands[1] - operands[0];
                            repr = operands[0] +   " - " + operands[1] + " = " + result;
                            return result;
                        }
                        else {
                            long result = operands[0] * operands[1];
                            repr = operands[0] +   " * " + operands[1] + " = " + result;
                            return result;
                        }
                    }
                };
                result.functions.put(f.name, f);
                continue;
            }

            if (arr[0].equals(Function.ABS)) {
                f = new Function(arr[0].toLowerCase(), Integer.parseInt(arr[1])) {

                    @Override
                    public long apply(long...operands) {
                        if (operands[0] < 0) return 0 - operands[0];
                        repr = "abs(" + operands[0] + ")";
                        return operands[0];
                    }
                };
                result.functions.put(f.name, f);
                continue;
            }
        }
        return result;
    }

    //Transformms a character sequence 'str' to a list of expression tokens
    //of type FUNCTION, NUMBER, PAREHTNESIS and COMMA
    private List<Token> tokenize(String str) {
        List<Token> tokens = new LinkedList<Token>();

        if (str.equals(Token.DEAD_END)) return tokens;
        String alphanumeric = "";
        Token t = null;

        for (char c : str.toCharArray()) {
            if (Character.isWhitespace(c)) continue;

            //alhanumeric sequence represents a function or its argument
            if (Character.isDigit(c) || c == '-' || Character.isLetter(c)) {
                alphanumeric += c;

            } else {
                if (c == ')' || c == '(' || c == ',') {
                    if (!alphanumeric.isEmpty()) {
                        try {
                            Integer.parseInt(alphanumeric);
                            t = new Token(Token.NUMBER, alphanumeric);
                        } catch (NumberFormatException x) {
                            t = new Token(Token.FUNCTION, alphanumeric);
                        }
                        alphanumeric = "";
                        tokens.add(t);
                    } else {
                        //open parenth always follows a funciton call;
                        //if alphanumeric holder 'val' is empty, the parenth is out of place
                        if (c == '(')
                            throw new RuntimeException("Syntax error : symbol " + c + " was not expected" +
                                                                   " at this position");
                    }
                    if (c == ')') t = new Token(Token.CLOSE_PARENTH, c+ "");
                    if (c == '(') t = new Token(Token.OPEN_PARENTH, c + "");
                    if (c == ',') t = new Token(Token.COMMA, c + "");
                    if (t != null) tokens.add(t);
                } else {
                    throw new RuntimeException("Syntax error : undefined symbol ' " + c + "' in " + str);
                }
            }
        }
        return tokens;
    }

    //Consumes next token from the sequence
    private Token nextToken() {
        if (!tokens.isEmpty())
            return tokens.remove(0);
        return null;
    }

    //Consumes next token expecting a given type
    //if the type does not match, exception is thrown
    private void expect(int type) {
        Token t = nextToken();
        if (t.type != type)
            throw new RuntimeException("Parse error : invalid token " + t);
    }

    //helper method to generalize function application. Consumes
    //a number of operands from operand stack respective of function's parity
    //throws Parse Error when parity does not match
    private void applyFunction(Function f, Stack<Long> intStack) {
        if (intStack.size() < f.parity)
            throw new RuntimeException("Parse error : mismatched parity");

        long[] operands = new long[f.parity];
        for (int i = 0; i < f.parity; i++) {
            operands[i] = intStack.pop();
        }
        intStack.push(f.apply(operands));
    }

    /**
     * Evaluates a String expression in prefix notation
     * @param expr
     * @return
     */
    public long evaluate(String expr) {
        this.tokens = tokenize(expr);

        //operand stack
        Stack<Long> intStack = new Stack<Long>();
        //function stack
        Stack<Function> funcStack = new Stack<Function>();
        Token t;

        while ((t = nextToken()) != null) {
            switch (t.type) {
                case Token.FUNCTION :
                    Function f = functions.get(t.value);
                    if (f == null)
                        throw new RuntimeException("Parse error : " +
                                                               "undefined function " + t.value);
                    funcStack.push(f);
                    expect(Token.OPEN_PARENTH);
                    break;

                case Token.NUMBER :
                    intStack.push(Long.parseLong(t.value));
                    break;

                case Token.CLOSE_PARENTH :
                    f = funcStack.pop();
                    applyFunction(f, intStack);
                    break;
            }
        }

        while (!funcStack.isEmpty()) {
            Function f = funcStack.pop();
            applyFunction(f, intStack);
        }

        if (intStack.size() != 1)
            throw new RuntimeException("Parse error : invalid input string :\n" +
                                                                         str);
        return intStack.pop();
    }

    static void say(Object o) {
        System.out.println(o);
    }
}
