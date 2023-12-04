package org.brooks.aoc;

import java.util.stream.Stream;

public class Template {
    public static class Phase1 implements Solution<Long> {
        @Override
        public Long solve(Stream<String> input) {
            return 0L;
        }
    }

    public static class Phase2 implements Solution<Long> {
        @Override
        public Long solve(Stream<String> input) {
            return 0L;
        }
    }

    public static void main(String[] args) {
        FileUtil.runInputThroughFunction("Phase 1 - Example -", "2023/01-Example-01.txt", new Phase1());
        FileUtil.runInputThroughFunction("Phase 1 - Input -", "2023/01.txt", new Phase1());
        FileUtil.runInputThroughFunction("Phase 2 - Example -", "2023/01-Example-02.txt", new Phase2());
        FileUtil.runInputThroughFunction("Phase 2 - Input -", "2023/01.txt", new Phase2());
    }
}
