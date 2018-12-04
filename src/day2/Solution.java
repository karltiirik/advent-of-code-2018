package day2;

import java.util.List;

import static day1.Solution.readLinesFromFile;

public class Solution {
    private static final String fileName = "src/day2/box_ids.txt";

    public static int checkSum(List<String> boxIds) {
        int numIdsWith2Letters = 0;
        int numIdsWith3Letters = 0;

        for (String id : boxIds) {
            char[] idChars = id.toCharArray();
            boolean found2Letters = false;
            boolean found3Letters = false;

            for (char idChar : idChars) {
                long count = id.chars().filter(ch -> ch == idChar).count();
                if (!found2Letters && count ==2) {
                    numIdsWith2Letters ++;
                    found2Letters = true;
                }
                if (!found3Letters && count == 3) {
                    numIdsWith3Letters ++;
                    found3Letters = true;
                }
                if (found2Letters && found3Letters) {
                    break;
                }
            }
        }
        return numIdsWith2Letters * numIdsWith3Letters;
    }

    public static String commonLetters(List<String> boxIds) {
        for (String id1 : boxIds) {
            for (String id2 : boxIds) {
                int matchinCharsCnt = 0;
                int diffIndex = 0;
                if (!id1.equals(id2)) {
                    char[] idChars = id1.toCharArray();
                    for (int i = 0; i < idChars.length; i++) {
                        if (id1.charAt(i) == id2.charAt(i)){
                            matchinCharsCnt ++;
                        } else {
                            diffIndex = i;
                        }
                    }
                    if (matchinCharsCnt == id1.length()-1) {
                        return id1.substring(0, diffIndex) + id2.substring(diffIndex + 1);
                     }
                }
            }

        }
        return "";
    }

    public static void main(String[] args) {
        List<String> boxIds = readLinesFromFile(fileName);
        System.out.println(checkSum(boxIds));
        System.out.println(commonLetters(boxIds));
    }

}
