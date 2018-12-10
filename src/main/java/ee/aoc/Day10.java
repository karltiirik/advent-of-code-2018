package ee.aoc;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ee.aoc.FileUtil.readLinesFromFile;

public class Day10 {
    private static final String fileName = "src/main/resources/10_day_input.txt";

    public static List<List<Integer>> parse(List<String> input) {
        List<List<Integer>> parsedInputList = new ArrayList<>();
        for (String line : input) {
            Pattern intsOnly = Pattern.compile("-?\\d+");
            Matcher makeMatch = intsOnly.matcher(line);
            List<Integer> parsedInput= new ArrayList<>();
            while (makeMatch.find()) {
                int inputInt = Integer.parseInt(makeMatch.group());
                parsedInput.add(inputInt);
            }
            parsedInputList.add(parsedInput);
        }
        return parsedInputList;
    }

    public static int[] findMinSize(List<List<Integer>> list) {
        // best time, min size, maxX, minX, maxY, minY
        int[] answer = new int[6];
        int bestT = -1;
        int minSize = Integer.MAX_VALUE;

        for (int t = 0; t < 20000; t++) {
            int minX = Integer.MAX_VALUE;
            int maxX = Integer.MIN_VALUE;
            int minY = Integer.MAX_VALUE;
            int maxY = Integer.MIN_VALUE;
            for (List<Integer> line : list) {
                int currentX = line.get(0) + (line.get(2) * t);
                int currentY = line.get(1) + (line.get(3) * t);
                maxX = currentX > maxX ? currentX : maxX;
                minX = currentX < minX ? currentX : minX;
                maxY = currentY > maxY ? currentY : maxY;
                minY = currentY < minY ? currentY : minY;
            }
            int currentSize = maxX - minX + maxY - minY;
            if (currentSize < minSize) {
                minSize = currentSize;
                bestT = t;
                answer = new int[] {bestT, minSize, maxX, minX, maxY, minY};
            }
        }
        return answer;
    }

    public static List<Point> getPointsAtTime(List<List<Integer>> list, int time){
        List<Point> points = new ArrayList<>();
        for (List<Integer> line : list) {
            int x = line.get(0) + (line.get(2) * time);
            int y = line.get(1) + (line.get(3) * time);
            points.add(new Point(x, y));
        }
        return points;
    }

    public static void printPoints(List<Point> points, int[] info) {
        for (int y = info[5]; y <= info[4]; y++) {
            for (int x = info[3]; x <= info[2]; x++) {
                String symbol = points.contains(new Point(x, y)) ? "#" : " ";
                System.out.print(symbol);
            }
            System.out.println();
        }

    }

    public static void main(String[] args) {
        List<String> input = readLinesFromFile(fileName);
        List<List<Integer>> list = parse(input);
        int[] minSize = findMinSize(list);
        int smallestSizeTime = minSize[0];

        List<Point> points = getPointsAtTime(list, smallestSizeTime);
        System.out.println("Answer 1: " );
        printPoints(points, minSize);
        System.out.println("Answer 2: " + smallestSizeTime);
    }
}
