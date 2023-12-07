package org.brooks.aoc.y2023;

import org.brooks.aoc.FileUtil;
import org.brooks.aoc.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Day06 {
    private record Race(
            long time,
            long distance
    ){}

    public static class Phase1 implements Solution<Long> {
        @Override
        public Long solve(Stream<String> input) {
            List<List<Long>> contents = input
                    .map(l -> l.substring(l.indexOf(":")+1))
                    .map(String::trim)
                    .map(l -> l.split(" +"))
                    .map(l-> Stream.of(l).map(Long::parseLong).toList())
                    .toList();

            List<Race> races = new ArrayList<>();
            for(int i = 0; i < contents.get(0).size(); i++){
                races.add(new Race(contents.get(0).get(i), contents.get(1).get(i)));
            }

            return races.stream()
                    .map((r) -> LongStream.rangeClosed(0, r.time)
                            .map(w -> w*(r.time-w))
                            .filter(d -> d > r.distance)
                            .count())
                    .reduce((a,b) -> a*b)
                    .orElse(0L);
        }
    }

    public static class Phase2 implements Solution<Long> {
        @Override
        public Long solve(Stream<String> input) {
            List<List<Long>> contents = input
                    .map(l -> l.substring(l.indexOf(":")+1))
                    .map(String::trim)
                    .map(l -> l.replaceAll(" ", ""))
                    .map(l-> Stream.of(l).map(Long::parseLong).toList())
                    .toList();

            List<Race> races = new ArrayList<>();
            for(int i = 0; i < contents.get(0).size(); i++){
                races.add(new Race(contents.get(0).get(i), contents.get(1).get(i)));
            }

            return races.stream()
                    .map((r) -> LongStream.rangeClosed(0, r.time)
                            .map(w -> w*(r.time-w))
                            .filter(d -> d > r.distance)
                            .count())
                    .reduce((a,b) -> a*b)
                    .orElse(0L);
        }
    }

    public static void main(String[] args) {
        FileUtil.runInputThroughFunction("Phase 1 - Example -", "2023/06-Example-01.txt", new Phase1());
        FileUtil.runInputThroughFunction("Phase 1 - Input -", "2023/06.txt", new Phase1());
        FileUtil.runInputThroughFunction("Phase 2 - Example -", "2023/06-Example-01.txt", new Phase2());
        FileUtil.runInputThroughFunction("Phase 2 - Input -", "2023/06.txt", new Phase2());
    }
}
