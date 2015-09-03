package gk.training.misc.crunchyroll;

import static gk.training.misc.crunchyroll.Parser.say;

/**
 * Date: 9/1/15
 */
public class TestDriver {

    public static void main(String[] args) throws Exception {
        String s = "abs(add(add(73,add(91,53354)),subtract(15,subtract(135,38815))))\n";

        String grammar = "add:2\nsubtract:2\nmultiply:2\nabs:1";

        Parser p = Parser.fromGrammar(grammar);
        Graph g = new Graph(p, s);
        g.shortestPath();
        PageUtils.writeJSON(g);
    }
}
