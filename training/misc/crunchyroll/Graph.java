package gk.training.misc.crunchyroll;

import gk.training.misc.crunchyroll.Parser.Token;
import java.io.IOException;
import java.util.*;

/**
 * Graph represinting the link topology
 */
public class Graph {

    int cycles;
    long goalPage;
    int count;
    Node root;

    class Node {
        long id;
        String url;
        Node parent;
        List<Node> adjacent;

        public Node(long id, Node parent) {
            this.id = id;
            this.url = id + "";
            this.adjacent = new LinkedList<Node>();
            this.parent = parent;
        }

        public Node(long id, Node parent, String url) {
            this(id, parent);
            this.url = url;
        }
    }

    /**
     * Constructs a topology graph by traversing the encoded links
     * starting with a given expression
     *
     * @param parser - Parser instance to decode the URLs
     * @param init - initial expression
     */
    public Graph(Parser parser, String init) {
        Set<Long> visited = new HashSet<Long>();
        long n = parser.evaluate(init);
        root = new Node(n, null);
        traverse(root, parser, visited);
    }

    private void traverse(Node node, Parser parser, Set<Long> visited) {

        if (node.url == Token.GOAL || node.url == Token.DEAD_END) return;
        try {
            for (String expr : PageUtils.getLinksAt(node.id)) {
                if (expr.equals(Token.DEAD_END)) {
                    node.adjacent.add(new Node(-1, node, Token.DEAD_END));
                    continue;
                }
                if (expr.equals(Token.GOAL)) {
                    goalPage = node.id;
                    node.adjacent.add(new Node(-2, node, Token.GOAL));
                    continue;
                }

                long id = parser.evaluate(expr);
                Node n = new Node(id, node);
                node.adjacent.add(n);
            }

            visited.add(node.id);
            for (Node n : node.adjacent) {
                if (n.url == Token.GOAL || n.url == Token.DEAD_END) continue;
                if (!visited.contains(n.id)) {
                    this.count++;
                    traverse(n, parser, visited);
                } else {
                    if (isDirectedCycle(n)) this.cycles++;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Connection error at: " + node.url);
        }
    }

    //A cycle is directed only if the branch contains a duplicate node ID.
    //It can be the case such that the node is marked visited, but is not a part of the
    //same cycle: when the node in question has already been traversed from another parent
    private boolean isDirectedCycle(Node n) {
        Set<Long> duplicates = new HashSet<Long>();
        while (n != null) {
            if (!duplicates.add(n.id)) return true;
            n = n.parent;
        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("");
        toString(root, sb);
        return sb.toString();
    }

    private void vertices(Node current, Queue<Node> que, Map<Node, Integer> distances) {
        if (current.url == Token.GOAL || current.url == Token.DEAD_END)
            return;
        for (Node n : current.adjacent) {
            que.offer(n);
            distances.put(n, Integer.MAX_VALUE);
            vertices(n, que, distances);
        }
    }

    /**
     * Breadth-first traversal to find the shortest path
     *
     * @return  List of node IDs representing the shortest path from
     * the source to the GOAL page
     */
    public List<Long> shortestPath() {
        Set<Node> visited = new HashSet<Node>();
        Queue<Node> que = new LinkedList<Node>();
        que.offer(root);

        int pathLength = Integer.MAX_VALUE;
        List<Long> shortestPath = null;

        while (!que.isEmpty()) {
            Node current = que.poll();
            visited.add(current);
            if (current.id == goalPage) {
                List<Long> path = buildPath(current);
                if (path.size() < pathLength) {
                    pathLength = path.size();
                    shortestPath = path;
                }
            }
            for (Node n : current.adjacent) {
                if (n.url == Token.GOAL || visited.contains(n)) continue;
                que.offer(n);
            }
        }
        return shortestPath;
    }

    private List<Long> buildPath(Node node) {
        Stack<Node> path = new Stack<Node>();
        path.push(node);
        while (node.parent != null) {
            node = node.parent;
            path.push(node);
        }
        List<Long> result = new LinkedList<Long>();
        while (!path.isEmpty()) result.add(path.pop().id);
        return result;
    }

    private void toString(Node node, StringBuilder sb) {
        sb.append("[" + node.url + "]\n");
        for (Node n : node.adjacent) {
            sb.append("\t" + n.url + ",");
        }
        sb.append("\n\n");
        for (Node n : node.adjacent)
            toString(n, sb);
    }
}
