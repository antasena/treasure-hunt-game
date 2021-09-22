package com.company.treasurehunt;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        //construct the map
        String[][] map = {
                {"#", "#", "#", "#", "#", "#", "#", "#"},
                {"#", ".", ".", ".", ".", ".", ".", "#"},
                {"#", ".", "#", "#", "#", ".", ".", "#"},
                {"#", ".", ".", ".", "#", ".", "#", "#"},
                {"#", "X", "#", ".", ".", ".", ".", "#"},
                {"#", "#", "#", "#", "#", "#", "#", "#"}
        };

        int[] start = new int[1];

        //locate the starting point
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                if (map[x][y] == "X") {
                    start = new int[]{x, y};
                }
            }
        }

        List<int[]> treasure = new ArrayList<>();
        int colLength = map[0].length;
        int rowLength = map.length;
        int startRow = start[0];
        int startColumn = start[1];

        //holds previous coordinate for north, east, and south
        int prevNorthRow = 0, prevNorthCol = 0, prevEastRow = 0, prevEastCol = 0, prevSouthRow = 0, prevSouthCol = 0;

        //lets iterate from 1 to starting row
        for (int row = 1; row <= startRow; row++) {
            int currRow = startRow;
            int currCol = startColumn;
            //go north first
            boolean validNorth = false;
            int north = 0;
            while (north < rowLength - row) {
                north++;
                currRow = startRow - north;
                currCol = startColumn;
                validNorth = isValidLocation(map, currRow, currCol);
                if (validNorth && currRow != prevNorthRow && currCol != prevNorthCol) { //north location is valid and its not yet check
                    prevNorthRow = currRow;
                    prevNorthCol = currCol;
                    break; //break here, check east
                }
            }
            if (!validNorth) {
                continue;
            }

            //go east
            int east = 0;
            boolean validEast = false;
            while (east < colLength - currCol) {
                east++;
                currRow = startRow - north;
                currCol = startColumn + east;
                validEast = isValidLocation(map, currRow, currCol);
                if (validEast && currRow != prevEastRow && currCol != prevEastCol) { //east location is valid and its not yet check
                    prevEastRow = currRow;
                    prevEastCol = currCol;
                    break; //break here, check south
                }
            }
            if (!validEast) {
                continue;
            }

            //go south
            int south = 0;
            boolean validSouth = false;
            while (south < row) {
                south++;
                currRow += south;
                validSouth = isValidLocation(map, currRow, currCol);
                if (validSouth && currRow != prevSouthRow && currCol != prevSouthCol) { //south location is valid and its not yet check
                    prevSouthRow = currRow;
                    prevSouthCol = currCol;
                    break;
                }
            }
            if (validSouth) {
                treasure.add(new int[]{currRow, currCol});
            }

        }

        printMap(map, treasure);
    }

    private static boolean isValidLocation(String[][] map, int row, int col) {
        // check valid location, its inside the boundary and not equals to #
        if (row < 0 || row >= map.length || col < 0 || col >= map[0].length
                || map[row][col].equals("#")) {
            return false;
        }
        return true;
    }

    private static void printMap(String[][] map, List<int[]> treasure) {
        System.out.println("Treasure Location:");
        for (int[] coordinate : treasure) {
            System.out.printf("(%d, %d)\n", coordinate[0], coordinate[1]);
            map[coordinate[0]][coordinate[1]] = "$";
        }

        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                String val = map[x][y];
                if (treasure.contains(new int[]{x, y})) {
                    val = "$";
                }
                System.out.print(val);
            }
            System.out.println();
        }
    }
}
