package day3;

import java.util.List;

import static day1.Solution.readLinesFromFile;

public class Solution {
    private static final String fileName = "src/day3/claims.txt";
    private static int[][] fabric = new int[1001][1001];

    private static void claim(int x, int y, int width, int height) {
        for (int dx = 0; dx < width; dx++) {
            for (int dy = 0; dy < height; dy++) {
                fabric[x + dx][y + dy] ++;
            }
        }
    }

    private static int countOverlaps() {
        int numOverlaps = 0;

        for (int[] line : fabric) {
            for (int el : line) {
                if (el > 1) {
                    numOverlaps++;
                }
            }
        }
        return numOverlaps;
    }

    private static boolean intact(int x, int y, int width, int height) {
        boolean intact = true;
        for (int dx = 0; dx < width; dx++) {
            for (int dy = 0; dy < height; dy++) {
                if (fabric[x + dx][y + dy] > 1){
                    intact = false;
                }
            }
        }
        return intact;
    }


    public static void main(String[] args) {
        List<String> claims = readLinesFromFile(fileName);

        for (String claim : claims) {
            String[] splitLine = claim.split(" ");
            String[] cooordinates = splitLine[2].split(",");
            int x = Integer.parseInt(cooordinates[0]);
            int y = Integer.parseInt(cooordinates[1].substring(0, cooordinates[1].length() - 1));
            String[] size = splitLine[3].split("x");
            int width = Integer.parseInt(size[0]);
            int height = Integer.parseInt(size[1]);
            claim(x, y, width, height);
        }

        System.out.println(countOverlaps());

        for (String claim : claims) {
            String[] splitLine = claim.split(" ");
            String[] cooordinates = splitLine[2].split(",");
            int x = Integer.parseInt(cooordinates[0]);
            int y = Integer.parseInt(cooordinates[1].substring(0, cooordinates[1].length() - 1));
            String[] size = splitLine[3].split("x");
            int width = Integer.parseInt(size[0]);
            int height = Integer.parseInt(size[1]);
            if (intact(x, y, width, height)) {
                System.out.println(splitLine[0].substring(1));
                break;
            }

        }

    }

}
