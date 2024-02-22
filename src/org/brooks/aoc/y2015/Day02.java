package org.brooks.aoc.y2015;

import org.brooks.aoc.FileUtil;
import org.brooks.aoc.Solution;

import java.util.stream.Stream;

public class Day02 {
    public static class Phase1 implements Solution<Long> {
        @Override
        public Long solve(Stream<String> input) {
            return input.mapToLong((dim) -> {
                String[] sides = dim.split("x");
                long l = Long.parseLong(sides[0]);
                long w = Long.parseLong(sides[1]);
                long h = Long.parseLong(sides[2]);

                long buffer = Math.min(l*w, Math.min(l*h, w*h));
                return 2*l*w + 2*l*h + 2*w*h + buffer;
            }).sum();
        }
    }

    public static class Phase2 implements Solution<Long> {
        @Override
        public Long solve(Stream<String> input) {
            return input.mapToLong((dim) -> {
                long[] sides = Stream.of(dim.split("x")).mapToLong(Long::parseLong).sorted().toArray();
                return sides[0]*2 + sides[1]*2 + sides[0]*sides[1]*sides[2];
            }).sum();
        }
    }

    public static void main(String[] args) {
//        FileUtil.runInputThroughFunction("Phase 1 - Example -", "2023/02-Example-01.txt", new Phase1());
        FileUtil.runInputThroughFunction("Phase 1 - Input -", "2015/02.txt", new Phase1());
//        FileUtil.runInputThroughFunction("Phase 2 - Example -", "2023/02-Example-01.txt", new Phase2());
        FileUtil.runInputThroughFunction("Phase 2 - Input -", "2015/02.txt", new Phase2());
    }
}
