package ee.aoc;

import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static ee.aoc.FileUtil.readLinesFromFile;

public class Day6 {
    private static final String fileName = "src/main/resources/6_day_input.txt";

    private static Point parsePoint(String input) {
        String[] splitLine = input.split(", ");
        int x = Integer.parseInt(splitLine[0]);
        int y = Integer.parseInt(splitLine[1]);
        return new Point(x, y);
    }

    private static int closestPointIndex(int x, int y, HashMap<Integer, Point> points) {
        int minDist = Integer.MAX_VALUE;
        int index = -1;

        for (int i = 0; i < points.size(); i++) {
            int dist = distToPoint(x, y, points.get(i));
            if (dist < minDist) {
                minDist = dist;
                index = i;
            } else if (dist == minDist) {
                index = -1;
            }
        }
        return index;
    }

    public static int distToPoint(int x, int y, Point p) {
        return Math.abs(x - p.x) + Math.abs(y - p.y);
    }

    public static HashMap<Integer, Integer> removeInfiniteAreas(HashMap<Integer, Integer> areas, int[][] grid) {
        int maxX = grid.length - 1;
        int maxY = grid[0].length - 1;
        for (int x = 0; x <= maxX; x++) {
            areas.remove(grid[x][0]);
            areas.remove(grid[x][maxY]);
        }
        for (int y = 0; y <= maxY; y++) {
            areas.remove(grid[0][y]);
            areas.remove(grid[maxX][y]);
        }
        return areas;
    }

    public static int findBiggestArea(HashMap<Integer, Integer> areas) {
        return Collections.max(areas.values());
    }

    public static boolean distToAllPointsUnder(HashMap<Integer, Point> points, int x, int y, int maxSize) {
        int size = 0;
        for (Point p : points.values()) {
            int dist = distToPoint(x, y, p);
            size += dist;
            if (size >= maxSize) {
                return false;
            }

        }
        return true;
    }

    public static void main(String[] args) {
        List<String> input = readLinesFromFile(fileName);
        HashMap<Integer, Point> points = new HashMap<>();
        int maxX = 0;
        int maxY = 0;

        for (int i = 0; i < input.size(); i++) {
            Point point = parsePoint(input.get(i));
            points.put(i, point);
            maxX = point.x > maxX ? point.x : maxX;
            maxY = point.y > maxY ? point.y : maxY;
        }

        int[][] grid = new int[maxX + 1][maxY + 1];
        HashMap<Integer, Integer> areas = new HashMap<>();

        for (int x = 0; x <= maxX; x++) {
            for (int y = 0; y <= maxY; y++) {
                int pointIndex = closestPointIndex(x, y, points);
                grid[x][y] = pointIndex;
                areas.merge(pointIndex, 1, Integer::sum);
            }
        }

        areas = removeInfiniteAreas(areas, grid);
        System.out.println("Answer 1: " + findBiggestArea(areas));

        int distUnder10k = 0;
        for (int x = 0; x <= maxX; x++) {
            for (int y = 0; y <= maxY; y++) {
                distUnder10k = distToAllPointsUnder(points, x, y, 10_000) ? distUnder10k + 1 : distUnder10k;
            }
        }
        System.out.println("Answer 2: " + distUnder10k);
    }
}
