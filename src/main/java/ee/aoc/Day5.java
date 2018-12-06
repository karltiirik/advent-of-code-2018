package ee.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ee.aoc.FileUtil.readLinesFromFile;

public class Day5 {
    private static final String fileName = "src/main/resources/5_day_input.txt";

    public static boolean sameCharDifferentCase(char a, char b) {
        boolean sameChar = Character.toLowerCase(a) == Character.toLowerCase(b);
        return sameChar && !(a == b);
    }

    public static int removeOppositesSize(List<Character> list) {
        boolean elRemoved = true;
        while (elRemoved) {
            for (int i = 0; i < list.size() - 1; i++) {
                if (sameCharDifferentCase(list.get(i), list.get(i + 1))) {
                    list.remove(i);
                    list.remove(i);
                    elRemoved = true;
                    break;
                }
                elRemoved = false;
            }
        }
        return list.size();
    }

    public static int findMinSize(List<Character> list) {
        int minSize = Integer.MAX_VALUE;
        List<Character> removedList;
        for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
            removedList = new ArrayList(list);
            removedList.removeAll(Arrays.asList(alphabet, Character.toLowerCase(alphabet)));
            int size = removeOppositesSize(removedList);
            if (size < minSize ) {
                minSize = size;
            }
        }
        return minSize;

    }

    public static void main(String[] args) {
        String sleepRecords = readLinesFromFile(fileName).get(0);
        List<Character> chars = sleepRecords.chars().mapToObj(e -> (char) e).collect(Collectors.toList());

        System.out.println("Answer 1: " + removeOppositesSize(chars));
        System.out.println("Answer 2: " + findMinSize(chars));
    }
}
