package org.brooks.aoc.y2023;

import org.brooks.aoc.FileUtil;
import org.brooks.aoc.Solution;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day01 {
    public static class Phase1 implements Solution<Long> {
        @Override
        public Long solve(Stream<String> input) {
            return input
                    .map((s) -> s.replaceAll("[^0-9]", ""))
                    .map((s) -> {
                        if (s.isEmpty()) {
                            return "0";
                        }
                        if (s.length() == 1) {
                            return s + s;
                        }
                        if (s.length() == 2) {
                            return s;
                        }
                        return String.valueOf(s.charAt(0)) + s.charAt(s.length() - 1);
                    })
                    .mapToLong(Long::parseLong)
                    .sum();
        }
    }

    public static class Phase2 implements Solution<Long> {
        List<String> allValues = Arrays.asList(
                "one", "1",
                "two", "2",
                "three", "3",
                "four", "4",
                "five", "5",
                "six", "6",
                "seven", "7",
                "eight", "8",
                "nine", "9"
        );
        Map<String, String> keyVal = Map.of(
                "one", "1",
                "two", "2",
                "three", "3",
                "four", "4",
                "five", "5",
                "six", "6",
                "seven", "7",
                "eight", "8",
                "nine", "9"
        );

        @Override
        public Long solve(Stream<String> input) {
            return input
                    .map((s) -> allValues.stream()
                            .map((v) -> new Phase2.IndexAndValue(s.indexOf(v), s.lastIndexOf(v), v))
                            .filter((v) -> v.firstIndex >= 0)
                            .collect(Collectors.teeing(
                                    Collectors.minBy(Comparator.comparing(o -> o.firstIndex)),
                                    Collectors.maxBy(Comparator.comparing(o -> o.lastIndex)),
                                    (min, max) -> new Phase2.Result(
                                            min.map(o -> o.value).map(o -> keyVal.getOrDefault(o, o)).orElse("0"),
                                            max.map(o -> o.value).map(o -> keyVal.getOrDefault(o, o)).orElse("0"))
                            ))
                    )
                    .map((o) -> o.firstValue + o.secondValue)
                    .mapToLong(Long::parseLong)
                    .sum();
        }

        private record Result(String firstValue, String secondValue) {
        }

        private record IndexAndValue(int firstIndex, int lastIndex, String value) {
        }
    }

    public static void main(String[] args) {
        FileUtil.runInputThroughFunction("Phase 1 - Example -", "2023/01-Example-01.txt", new Phase1());
        FileUtil.runInputThroughFunction("Phase 1 - Input -", "2023/01.txt", new Phase1());
        FileUtil.runInputThroughFunction("Phase 2 - Example -", "2023/01-Example-02.txt", new Phase2());
        FileUtil.runInputThroughFunction("Phase 2 - Input -", "2023/01.txt", new Phase2());
    }
}
