package com.company.treasurehunt;

import java.util.*;

public class Main {

    static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x &&
                    y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    static final int[][] DIRECTIONS = {
            {0, -1}, //UP
            {1, 0}, //RIGHT
            {-1, 0}, //LEFT
            {0, 1}  //DOWN
    };

    public static void main(String[] args) {
        List<Point> obstacles = new ArrayList<>();
        Point start = new Point(0, 0);

        //construct the map
        String[][] map = {
                {"#", "#", "#", "#", "#", "#", "#", "#"},
                {"#", ".", ".", ".", ".", ".", ".", "#"},
                {"#", ".", "#", "#", "#", ".", ".", "#"},
                {"#", ".", ".", ".", "#", ".", "#", "#"},
                {"#", "X", "#", ".", ".", ".", ".", "#"},
                {"#", "#", "#", "#", "#", "#", "#", "#"}
        };

        //get the column and row size
        int columnSize = map.length;
        int rowSize = map[0].length;

        //locate the obstacles, clearPaths, and starting point
        for (int x = 0; x < columnSize; x++) {
            for (int y = 0; y < rowSize; y++) {
                if (map[x][y] == "#") {
                    obstacles.add(new Point(x, y));
                }
                if (map[x][y] == "X") {
                    start = new Point(x, y);
                }
            }
        }

        List<Point> treasures = findTreasureLocation(obstacles, columnSize, rowSize, start);
        printMap(map, treasures);
    }

    private static List<Point> findTreasureLocation(List<Point> obstacles, int columnSize, int rowSize, Point start) {
        List<Point> treasures = new ArrayList<>();
        List<Point> visited = new ArrayList<>();
        LinkedList<Point> nextToVisit = new LinkedList<>();
        nextToVisit.add(start);

        while (!nextToVisit.isEmpty()) {
            Point current = nextToVisit.remove();

            Point newLoc = null;
            //steps
            for (int[] direction : DIRECTIONS) {
                newLoc = new Point(current.x + direction[0], current.y + direction[1]);
                if (!isValidLocation(current.x, current.y, columnSize, rowSize) ||
                        visited.contains(current)) {
                    continue;
                }
                if (obstacles.contains(current)) {
                    visited.add(current);
                    continue;
                }
                nextToVisit.add(newLoc);
                visited.add(current);
            }
            treasures.add(newLoc);
        }

        return treasures;
    }

    private static boolean isValidLocation(int x, int y, int columnSize, int rowSize) {
        if (x < 0 || x >= columnSize || y < 0 || y >= rowSize) {
            return false;
        }
        return true;
    }

    private static void printMap(String[][] map, List<Point> treasures) {
        System.out.print("Treasure Location:");
        for (Point p : treasures) {
            System.out.printf("(%d, %d) ", p.x, p.y);
        }

        System.out.println();
        //Print the map and treasure location
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                String val = map[x][y];
                if (treasures.contains(new Point(y, x))) {
                    val = "$";
                }
                System.out.print(val);
            }
            System.out.println();
        }
    }
}
