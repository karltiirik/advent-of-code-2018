package ee.aoc;

import java.util.HashSet;
import java.util.List;

import static ee.aoc.FileUtil.readLinesFromFile;

public class Day1 {
    private static final String fileName = "src/main/resources/1_day_input.txt";

    public static int sumFreqChanges(List<String> freqChangesList) {
        return freqChangesList.stream().mapToInt(Integer::parseInt).sum();
    }

    public static int firstFreqReachedTwice(List<String> freqChangesList) {
        HashSet<Integer> freqsList = new HashSet<>();
        freqsList.add(0);
        int currentFreq = 0;
        while (true) {
            for (String freqChange : freqChangesList) {
                currentFreq += Integer.parseInt(freqChange);
                if (freqsList.contains(currentFreq)){
                    return currentFreq;
                } else {
                    freqsList.add(currentFreq);
                }
            }
        }
    }

    public static void main(String args[]) {
        List<String> freqChangesList = readLinesFromFile(fileName);
        System.out.println(sumFreqChanges(freqChangesList));
        System.out.println(firstFreqReachedTwice(freqChangesList));
    }
}
