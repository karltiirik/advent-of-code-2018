package ee.aoc;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ee.aoc.FileUtil.readLinesFromFile;

public class Day12 {
    private static final String fileName = "src/main/resources/12_day_input.txt";

    static class PotsRow {
        String state;
        int potsSum;

        public PotsRow(String state, int sum) {
            this.state = state;
            this.potsSum = sum;
        }
    }

    public static void main(String[] args) {
        List<String> input = readLinesFromFile(fileName);
        String initialState = input.get(0).split(": ")[1];
        List<String> rules = parseRules(input.subList(2, input.size()));

        int sum = getPotsSum(initialState, rules, 20);
        System.out.println("Answer 1: " + sum);
        long longSum = getPotsSumStatistically(initialState, rules, 50_000_000_000L);
        System.out.println("Answer 2: " + longSum);
    }

    public static List<String> parseRules(List<String> input) {
        List<String> rules = new ArrayList<>();
        for (String line : input) {
            String[] splitLine = line.split(" => ");
            char result = splitLine[1].charAt(0);
            if (result == '#') {
                rules.add(splitLine[0]);
            }
        }
        return rules;
    }

    public static int getPotsSum(String initState, List<String> rules, int gens) {
        int offSet = 25;
        String padding = String.join("", Collections.nCopies(offSet, "."));
        String state = padding + initState + padding;
        PotsRow potsRow = new PotsRow(state, 0);
        for (int g = 1; g <= gens; g++) {
            if (g == 1) {
                // wtf?? the -1 should not be needed
                potsRow = growGen(potsRow, rules, offSet - 1, offSet);
            } else {
                potsRow = growGen(potsRow, rules, 2, offSet);
            }
        }

        return potsRow.potsSum;
    }

    public static long getPotsSumStatistically(String initState, List<String> rules, long gens) {
        int offSet = 250;
        String padding = String.join("", Collections.nCopies(offSet, "."));
        String state = padding + initState + padding;
        PotsRow potsRow = new PotsRow(state, 0);


        int stablizedLimit = 3;
        int lastSum = 0;
        int lastDiff = 0;
        int sameDiffCnt = 0;

        for (int g = 1; g <= gens; g++) {
            if (g == 1) {
                // wtf?? the -1 should not be needed
                potsRow = growGen(potsRow, rules, offSet - 1, offSet);
            } else {
                potsRow = growGen(potsRow, rules, 2, offSet);
            }

            int diff = Math.abs(lastSum - potsRow.potsSum);
            lastSum = potsRow.potsSum;
            if (diff == lastDiff) {
                sameDiffCnt ++;
            } else {
                sameDiffCnt = 0;
            }
            lastDiff = diff;

            if (sameDiffCnt >= stablizedLimit) {
                return potsRow.potsSum + (gens - g) * lastDiff;
            }

        }

        return potsRow.potsSum;
    }

    public static PotsRow growGen(PotsRow potsRow, List<String> rules, int start, int offSet) {
        String state = potsRow.state;
        StringBuilder result = new StringBuilder(state);
        int sum = 0;
        for (int i = start; i < state.length() - 2; i++) {
            result.setCharAt(i, '.');
            for (String rule : rules) {
                String substring = state.substring(i - 2, i + 3);
                if (substring.equals(rule)) {
                    result.setCharAt(i, '#');
                    sum += i - offSet;
                }
            }
        }
        return new PotsRow(String.valueOf(result), sum);
    }
}
