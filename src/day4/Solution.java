package day4;

import java.util.*;
import java.util.stream.IntStream;

import static day1.Solution.readLinesFromFile;

public class Solution {
    private static final String fileName = "src/day4/sleep_records.txt";

    private static Integer keyWithMaxSum(Map<Integer, int[]> map) {
        Map.Entry<Integer, int[]> maxEntry = null;
        for (Map.Entry<Integer, int[]> entry : map.entrySet()) {
            if (maxEntry == null || IntStream.of(entry.getValue()).sum() > IntStream.of(maxEntry.getValue()).sum()) {
                maxEntry = entry;
            }
        }
        return maxEntry.getKey();
    }

    private static int mostPopularSleepMinute(int[] array) {
        int maxValue = -1;
        int maxValueMinute = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > maxValue) {
                maxValue = array[i];
                maxValueMinute = i;
            }
        }
        return maxValueMinute;
    }

    private static int idMostFreqASleepAtASingleMinute(Map<Integer, int[]> map) {
        int id = 0;
        int max = 0;
        for (Integer key : map.keySet()) {
            OptionalInt currentMax = IntStream.of(map.get(key)).max();
            if (currentMax.isPresent() && currentMax.getAsInt() > max) {
                max = currentMax.getAsInt();
                id = key;
            }
        }
        return id;
    }

    public static void main(String[] args) {
        List<String> sleepRecords = readLinesFromFile(fileName);
        Collections.sort(sleepRecords);

        int guardId = 0;
        int sleepStart = 0;
        Map<Integer, int[]> sleepData = new HashMap<>();


        for (String line : sleepRecords) {
            String[] splitLine = line.split("] ");
            int minute = Integer.parseInt(splitLine[0].substring(15));
            String action = splitLine[1];

            if (action.contains("begins shift")) {
                guardId = Integer.parseInt(action.split(" ")[1].substring(1));
                sleepData.putIfAbsent(guardId, new int [60]);
                sleepStart = 0;
            } else if (action.contains("falls asleep")) {
                sleepStart = minute;
            } else if (action.contains("wakes up")) {
                for (int m = sleepStart; m < minute; m++) {
                    sleepData.get(guardId)[m]++;
                }
            }
        }

        int mostSleptGuardId = keyWithMaxSum(sleepData);
        int mostPopularSleepMinute = mostPopularSleepMinute(sleepData.get(mostSleptGuardId));
        System.out.println(mostSleptGuardId);
        System.out.println(mostPopularSleepMinute);
        System.out.println("Answer 1: " + mostPopularSleepMinute * mostSleptGuardId);


        int mostFreqASleepAtASingleMinute = idMostFreqASleepAtASingleMinute(sleepData);
        int minute = mostPopularSleepMinute(sleepData.get(mostFreqASleepAtASingleMinute));
        System.out.println(mostFreqASleepAtASingleMinute);
        System.out.println(minute);
        System.out.println("Answer 2: " + mostFreqASleepAtASingleMinute * minute);
    }
}
