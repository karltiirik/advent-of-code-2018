package day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Solution {
    private static final String fileName = "src/day1/frequency_changes.txt";


    public static List<String> readLinesFromFile (String fileName) {
        List<String> list = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
            list = br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

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
