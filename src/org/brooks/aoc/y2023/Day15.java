package org.brooks.aoc.y2023;

import org.brooks.aoc.FileUtil;
import org.brooks.aoc.Solution;

import java.util.stream.Stream;

public class Day15 {
    public static class Phase1 implements Solution<Long> {
        @Override
        public Long solve(Stream<String> input) {
            return input.flatMap(line -> Stream.of(line.split(",")))
                    .mapToLong(word -> word.chars().reduce(0, (prev, next) -> ((prev + next) * 17) % 256))
                    .sum();
        }
    }

    public static class Phase2 implements Solution<Long> {
        @Override
        public Long solve(Stream<String> input) {
            return 0L;
        }
    }

    public static void main(String[] args) {
        FileUtil.runInputThroughFunction("Phase 1 - Example -", "2023/15-Example-01.txt", new Phase1());
        FileUtil.runInputThroughFunction("Phase 1 - Input -", "2023/15.txt", new Phase1());
//        FileUtil.runInputThroughFunction("Phase 2 - Example -", "2023/15-Example-01.txt", new Phase2());
//        FileUtil.runInputThroughFunction("Phase 2 - Input -", "2023/15.txt", new Phase2());
    }
}
