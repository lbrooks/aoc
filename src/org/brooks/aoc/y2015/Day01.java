package org.brooks.aoc.y2015;

import org.brooks.aoc.FileUtil;
import org.brooks.aoc.Solution;

import java.util.stream.Stream;

public class Day01 {
    public static class Phase1 implements Solution<Long> {
        @Override
        public Long solve(Stream<String> input) {
            String line = input.findFirst().orElse("");
            long sum = 0L;
            for(String c : line.split("")){
                if ("(".equals(c)) {
                    sum += 1L;
                }
                if (")".equals(c)) {
                    sum -= 1L;
                }
            }
            return sum;
        }
    }

    public static class Phase2 implements Solution<Long> {
        @Override
        public Long solve(Stream<String> input) {
            String[] line = input.findFirst().orElse("").split("");
            long sum = 0L;
            for(int i = 0; i < line.length; i++){
                if (sum == -1 ){
                    return (long)i;
                }
                if ("(".equals(line[i])) {
                    sum += 1L;
                }
                if (")".equals(line[i])) {
                    sum -= 1L;
                }
            }
            return 0L;
        }
    }

    public static void main(String[] args) {
//        FileUtil.runInputThroughFunction("Phase 1 - Example -", "2015/01-Example-01.txt", new Phase1());
        FileUtil.runInputThroughFunction("Phase 1 - Input -", "2015/01.txt", new Phase1());
//        FileUtil.runInputThroughFunction("Phase 2 - Example -", "2015/01-Example-01.txt", new Phase2());
        FileUtil.runInputThroughFunction("Phase 2 - Input -", "2015/01.txt", new Phase2());
    }
}
