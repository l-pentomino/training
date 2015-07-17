package gk.training.codeeval;

import dvakota.toys.Mangle;

import java.util.*;

/**
 * Date: 7/16/15
 */
public class CodeEvalStraightLines {
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
            String[] xy = line.split("\\s+");
            Point p = new Point(Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));
        }
        return points.toArray(new Point[points.size()]);
    }

    public static int countLines(Point[] points) {
        int result = 0;
        Map<Double, List<Point[]>> map = new HashMap<Double, List<Point[]>>();
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i+1; j < points.length; j++) {
                double slope = (double) (points[i].y - points[j].y) / (points[i].x - points[j].x);
                List<Point[]> clpoints = map.get(slope);
                if (clpoints == null) clpoints = new ArrayList<Point[]>();
                Point[] pair = new Point[2];
                pair[0] = points[i]; pair[1] = points[j];
                clpoints.add(pair);
                map.put(slope, clpoints);
            }
        }

        for (Double slope : map.keySet()) {
            List<Point[]> pairs = map.get(slope);
            if (pairs.size() < 2) continue;

            Collections.sort(pairs, new Comparator<Point[]>() {
                @Override
                public int compare(Point[] pair1, Point[] pair2) {
                    return pair1[0].x - pair2[0].x;
                }
            });

            Point origin = pairs.get(0)[0];
            say("Testing  origin " + origin);

            //eliminate parallel lines; only collinear pairs will have equal or complementary angle
            double theta = Math.atan2(origin.y, origin.x);
            int collinear = 0;
            for (int i = 1; i < pairs.size(); i++) {
                Point[] pair = pairs.get(i);
                //check the other pair's end point
                double theta2 = Math.atan2(pair[1].y, pair[1].x);
                if (theta == theta2 || Math.abs(theta - theta2) == Math.PI) {
                    say("Point " + pair[1] + " is collinear");
                    collinear++;
                }
            }

            if (collinear > 2) result++;

            }
        return result;
    }

    public static void say(Object o) {
        System.out.println(o);
    }
}

