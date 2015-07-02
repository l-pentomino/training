package gk.training.codeeval;

import java.io.File;
import java.util.*;

/**
 * Date: 6/30/15
 */
public class CodeEvalPathTriangle {

    //implement triangle as a pseudo-binary tree where a parent can share
    //a child with another parent (one parent's left child is next parent's right)
    static class Triangle {

        class Node {
            int value;
            Node left;
            Node right;

            public Node(String s) {
                value = Integer.parseInt(s);
                left = null; right = null;
            }

            @Override
            public String toString() {
                return value + "";
            }
        }

        //remember state for traversal
        class State {
            Node node;
            long localMax;
            public State(Node n, long lm) {
                node = n;
                localMax = lm;
            }
        }

        Node root;

        public Triangle(List<String> input) {
            Node[] current = new Node[1];
            current[0] = new Node(input.get(0));
            root = current[0];
            for (int i = 1; i < input.size(); i++) {
                String[] arr = input.get(i).split("\\s+");
                Node[] next = new Node[arr.length];
                int childIndex = 0;
                for (Node parent : current) {
                    if (next[childIndex] == null) next[childIndex] = new Node(arr[childIndex]);
                    parent.left = next[childIndex];
                    next[childIndex + 1] = new Node(arr[childIndex + 1]);
                    parent.right = next[childIndex + 1];
                    childIndex++;
                    //         System.out.println("Parent " + parent + " children " + parent.left + ", " + parent.right);
                }
                current = next;
            }
        }


        public long findMaxPath() {
            Stack<State> stack = new Stack<State>();
            long max = 0;
            stack.push(new State(root, root.value));
            while (!stack.isEmpty()) {
                State currentState = stack.pop();
                Node node = currentState.node;
                System.out.println("\nCurrent node " + node);
                //System.out.println("Local max " + currentState.localMax);
                //System.out.println("Children: " + node.left + ", " + node.right);
                if (node.left == null && node.right == null) {
                    if (max < currentState.localMax) max = currentState.localMax;
       //             System.out.println();
                    continue;
                }
                stack.push(new State(node.left, currentState.localMax + node.left.value));
                stack.push(new State(node.right, currentState.localMax + node.right.value));
            }
            return max;
        }
    }


    public static void main(String[] args) throws Exception {
        String[] test = {"5", "9 6", "4 6 8", "0 7 1 5", "10 10 2 4 8"};
        Triangle t = new Triangle(Arrays.asList(test));
        System.out.println("Max: " + t.findMaxPath());

        /*Scanner sc = new Scanner(new File(System.getProperty("user.home") + "/triangle.txt"));
        List<String> lst = new ArrayList<String>();
        while (sc.hasNextLine()) {
            lst.add(sc.nextLine().trim());
        }
        Triangle ttr = new Triangle(lst);
        System.out.println(ttr.findMaxPath());*/

    }
}
