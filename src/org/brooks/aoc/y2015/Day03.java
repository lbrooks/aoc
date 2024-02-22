package org.brooks.aoc.y2015;

import org.brooks.aoc.FileUtil;
import org.brooks.aoc.Solution;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Day03 {
    static class Point {
        long x;
        long y;

        Point(){
            x = 0;
            y = 0;
        }

        void moveDirection(String dir) {
            switch (dir) {
                case "^":
                    y += 1;
                    break;
                case "<":
                    x -= 1;
                    break;
                case ">":
                    x += 1;
                    break;
                case "v":
                    y -= 1;
                    break;
            }
        }

        public String toString(){
            return String.format("%d-%d", x, y);
        }
    }
    public static class Phase1 implements Solution<Long> {
        @Override
        public Long solve(Stream<String> input) {
            String[] line = input.findFirst().orElse("").split("");
            Map<String, Long> visited = new HashMap<>();
            Point santa = new Point();
            visited.put(santa.toString(), 1L);
            for (String dir : line) {
                santa.moveDirection(dir);
                String coord = santa.toString();
                visited.put(coord, visited.getOrDefault(coord, 0L));
            }
            return (long)visited.size();
        }
    }

    public static class Phase2 implements Solution<Long> {
        @Override
        public Long solve(Stream<String> input) {
            String[] line = input.findFirst().orElse("").split("");
            Map<String, Long> visited = new HashMap<>();
            Point santa = new Point();
            Point robo = new Point();
            visited.put(santa.toString(), visited.getOrDefault(santa.toString(), 0L));
            visited.put(robo.toString(), visited.getOrDefault(robo.toString(), 0L));
            boolean santaTurn = true;
            for (String dir : line) {
                if(santaTurn) {
                    santa.moveDirection(dir);
                    visited.put(santa.toString(), visited.getOrDefault(santa.toString(), 0L));
                } else {
                    robo.moveDirection(dir);
                    visited.put(robo.toString(), visited.getOrDefault(robo.toString(), 0L));
                }
                santaTurn = !santaTurn;
            }
            return (long)visited.size();
        }
    }

    public static void main(String[] args) {
//        FileUtil.runInputThroughFunction("Phase 1 - Example -", "2023/03-Example-01.txt", new Phase1());
        FileUtil.runInputThroughFunction("Phase 1 - Input -", "2015/03.txt", new Phase1());
//        FileUtil.runInputThroughFunction("Phase 2 - Example -", "2023/03-Example-01.txt", new Phase2());
        FileUtil.runInputThroughFunction("Phase 2 - Input -", "2015/03.txt", new Phase2());
    }
}
