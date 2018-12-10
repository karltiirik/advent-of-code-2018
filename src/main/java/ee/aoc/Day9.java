package ee.aoc;

import java.util.ArrayDeque;
import java.util.Arrays;

import static ee.aoc.FileUtil.readLinesFromFile;

public class Day9 {
    private static final String fileName = "src/main/resources/9_day_input.txt";

    static class CircleDeque<T> extends ArrayDeque<T> {
        void rotate(int num) {
            if (num > 0) {
                for (int i = 0; i < num; i++) {
                    T t = this.removeLast();
                    this.addFirst(t);
                }
            }
            if (num < 0) {
                for (int i = 0; i < Math.abs(num) - 1; i++) {
                    T t = this.remove();
                    this.addLast(t);
                }
            }
        }
    }

    public static long game(int players, int lastMarble) {
        CircleDeque<Integer> circle = new CircleDeque<>();
        circle.addFirst(0);
        long[] scores = new long[players];
        for (int marble = 1; marble <= lastMarble; marble++) {
            if (marble % 23 == 0) {
                circle.rotate(-7);
                scores[marble % players] += marble + circle.pop();

            } else {
                circle.rotate(2);
                circle.addLast(marble);
            }
        }
        return Arrays.stream(scores).max().getAsLong();
    }

    public static void main(String[] args) {
        String[] splitInput= readLinesFromFile(fileName).get(0).split(" ");
        int players = Integer.parseInt(splitInput[0]);
        int lastMarble = Integer.parseInt(splitInput[6]);

        System.out.println("Answer 1: " + game(players, lastMarble) );
        System.out.println("Answer 2: " + game(players, lastMarble * 100));
    }
}
