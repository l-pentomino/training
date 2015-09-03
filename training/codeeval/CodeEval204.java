package gk.training.codeeval;

import java.util.*;

/**
 * Date: 7/16/15
 * Find the number of straight lines that cross two or more points
 * https://www.codeeval.com/open_challenges/204/
 */
public class CodeEval204 {

    static class Point {
        int x; int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public String toString() {
            return x + "," + y;
        }
    }

    public static Point[] doInput(String input) {
        String[] lines = input.split("\\n");
        List<Point> points = new ArrayList<Point>();
        for (String line : lines) {
            String[] xy = line.trim().split("\\s+");
            Point p = new Point(Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));
            points.add(p);
        }
        return points.toArray(new Point[points.size()]);
    }

    public static int countLines(Point[] points) {
        final double INFINITY = Double.MAX_VALUE;
        int result = 0;

        Map<Double, List<Set<Point>>> allSlopes = new HashMap<Double, List<Set<Point>>>();

        for (int i = 0; i < points.length; i++) {
            Point origin = points[i];

            Map<Double, Set<Point>> map = new HashMap<Double, Set<Point>>();
            double slope;

            say("Point " + points[i] + ":");

            for (int j =  i + 1; j < points.length; j++) {
                Point other = points[j];
                if (other.x == origin.x) slope = INFINITY;
                else slope = ((double) (other.y - origin.y) / (other.x - origin.x));

                Set<Point>  candidates = map.get(slope);

                if (candidates == null) {
                    candidates = new HashSet<Point>();
                    candidates.add(points[i]);
                }

                say("\tAngle with point " + points[j] + " = " +
                                (slope == INFINITY ? "INFINITY" : slope));
                candidates.add(points[j]);
                map.put(slope, candidates);
            }

            for (double slp : map.keySet()) {
                if (map.get(slp).size() < 3) continue;
                List<Set<Point>> list = allSlopes.get(slp);
                if (list == null) list = new ArrayList<Set<Point>>();
                list.add(map.get(slp));
                allSlopes.put(slp, list);
            }
        }

        //count the final number of straight lines
        for (double slope : allSlopes.keySet()) {
            List<Set<Point>> throughPoints = allSlopes.get(slope);

            //assume each point set that belongs to the same slope lays on a parallel line
            int lineCount = throughPoints.size();

            say("Slope " + slope + " currently has " + lineCount + " lines ");

            //check overlap between point sets (i.e. whether they belong to the same line)
            Set<Point> check = new HashSet<Point>();
            for (Set<Point> set : throughPoints) {
                say("Exlporing pointset " + Arrays.toString(set.toArray()));
                pointset:for (Point p : set) {
                    if (!check.add(p)) {
                        say("\tPoint " + p + " overlaps with previous set");
                        lineCount--;
                        say("Current linecount: " + lineCount);
                        break pointset;
                    }
                }
            }

            result += lineCount;
        }

        return result;
    }

    public static void say(Object o) {
        System.out.println(o);
    }

    public static void main(String[] args) {
        String input = "1 2\n2 4\n3 6\n-5 5\n-3 1\n-2 3\n3 2\n4 1\n4 2\n4 3\n0 5\n0 4\n0 0\n1 0\n2 0";
        Point[] points = doInput(input);
        say("Result: " + countLines(points));
        double theta1 = Math.atan(4.0/2)*2;
        double theta2 = Math.atan(-4 / -2);
        say(theta1);
        say(theta2);
    }
}

