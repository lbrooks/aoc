package org.brooks.aoc.y2023;

import org.brooks.aoc.FileUtil;
import org.brooks.aoc.Solution;

import java.util.*;
import java.util.stream.Stream;

public class Day02 {
    private record Draw(int blue, int red, int green) {
    }

    private record Game(int id, List<Draw> draws) {
    }

    private static Game parse(String s) {
        String[] p = s.split(":");
        int id = Integer.parseInt(p[0].split(" ")[1].trim());
        String[] draws = p[1].trim().split(";");

        List<Draw> dws = new ArrayList<>();

        for (String d : draws) {
            int blue = 0, green = 0, red = 0;
            for (String hand : d.split(",")) {
                String[] cl = hand.trim().split(" ");
                int num = Integer.parseInt(cl[0].trim());
                switch (cl[1].trim()) {
                    case "red":
                        red = num;
                        break;
                    case "blue":
                        blue = num;
                        break;
                    case "green":
                        green = num;
                        break;
                }
            }
            dws.add(new Draw(blue, red, green));
        }

        return new Game(id, dws);
    }

    public static class Phase1 implements Solution<Long> {
        @Override
        public Long solve(Stream<String> input) {
            return input.map(Day02::parse).filter(g -> g.draws.stream().allMatch(d -> d.blue <= 14 && d.green <= 13 && d.red <= 12)).mapToLong(g -> g.id).sum();
        }
    }

    public static class Phase2 implements Solution<Long> {
        @Override
        public Long solve(Stream<String> input) {
            return input.map(Day02::parse).mapToLong(g -> {
                long mBlue = 0, mRed = 0, mGreen = 0;
                for (Draw d : g.draws) {
                    mBlue = Math.max(mBlue, d.blue);
                    mRed = Math.max(mRed, d.red);
                    mGreen = Math.max(mGreen, d.green);
                }
                return mBlue * mRed * mGreen;
            }).sum();
        }
    }

    public static void main(String[] args) {
        FileUtil.runInputThroughFunction("Phase 1 - Example -", "2023/02-Example-01.txt", new Phase1());
        FileUtil.runInputThroughFunction("Phase 1 - Input -", "2023/02.txt", new Phase1());
        FileUtil.runInputThroughFunction("Phase 2 - Example -", "2023/02-Example-01.txt", new Phase2());
        FileUtil.runInputThroughFunction("Phase 2 - Input -", "2023/02.txt", new Phase2());
    }
}
