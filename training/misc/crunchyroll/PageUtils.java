package gk.training.misc.crunchyroll;

import static gk.training.misc.crunchyroll.Parser.say;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Utility class for I/O operations (connections and JSON output)
 */

public class PageUtils {
    static final String BASE_URL = "http://www.crunchyroll.com/tech-challenge/roaming-math/igoreksf@gmail.com/";

    private static String toJSONString(String header, long value) {
        String result =  '"' + header + "\":" + value;
        return result;
    }

    private static String toJSONString(String header, List<Long> values) {
         StringBuilder sb = new StringBuilder('"' + header + "\":[");
        for (long value : values) {
            sb.append(value + ",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }

    public static void writeJSON(Graph g) {
        StringBuilder sb = new StringBuilder("{\n");
        sb.append(toJSONString("goal", g.goalPage) + ",\n");
        sb.append(toJSONString("node_count", g.count) + ",\n");
        sb.append(toJSONString("shortest_path", g.shortestPath()) + ",\n");
        sb.append(toJSONString("directed_cycle_count", g.cycles) + "\n");
        sb.append("}");
        say(sb.toString());
    }

    public static List<String> getLinksAt(long value)
                throws IOException {
        List<String> links = new LinkedList<String>();
        URL url = new URL(BASE_URL + value);
        URLConnection connection = url.openConnection();
        Scanner sc = new Scanner(connection.getInputStream());
        while (sc.hasNextLine()) {
             links.add(sc.nextLine());
        }
        return links;
    }
}
