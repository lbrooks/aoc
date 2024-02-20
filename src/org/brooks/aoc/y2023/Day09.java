package org.brooks.aoc.y2023;

import org.brooks.aoc.FileUtil;
import org.brooks.aoc.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Stream;

public class Day09 {
    public static class Phase1 implements Solution<Long> {
        @Override
        public Long solve(Stream<String> input) {
            return input.mapToLong((row) -> {
                Stack<List<Long>> allValues = new Stack<>();
                allValues.add(Stream.of(row.split(" ")).map(Long::parseLong).toList());
                while(!allValues.peek().stream().allMatch(v -> v==0)){
                    List<Long> previous = allValues.peek();
                    List<Long> next = new ArrayList<>();
                    for(int i = 0; i < previous.size()-1; i++){
                        next.add(previous.get(i+1) - previous.get(i));
                    }
                    allValues.push(next);
                }
                if (!allValues.isEmpty()) {
                    //discard the zero row
                    allValues.pop();
                }
                long diff = 0;
                while(!allValues.isEmpty()) {
                    List<Long> line = allValues.pop();
                    diff = diff + line.getLast();
                }
                return diff;
            }).sum();
        }
    }

    public static class Phase2 implements Solution<Long> {
        @Override
        public Long solve(Stream<String> input) {
            return input.mapToLong((row) -> {
                Stack<List<Long>> allValues = new Stack<>();
                allValues.add(Stream.of(row.split(" ")).map(Long::parseLong).toList());
                while(!allValues.peek().stream().allMatch(v -> v==0)){
                    List<Long> previous = allValues.peek();
                    List<Long> next = new ArrayList<>();
                    for(int i = 0; i < previous.size()-1; i++){
                        next.add(previous.get(i+1) - previous.get(i));
                    }
                    allValues.push(next);
                }
                if (!allValues.isEmpty()) {
                    //discard the zero row
                    allValues.pop();
                }
                long diff = 0;
                while(!allValues.isEmpty()) {
                    List<Long> line = allValues.pop();
                    diff = line.getFirst() - diff;
                }
                return diff;
            }).sum();
        }
    }

    public static void main(String[] args) {
        FileUtil.runInputThroughFunction("Phase 1 - Example -", "2023/09-Example-01.txt", new Phase1());
        FileUtil.runInputThroughFunction("Phase 1 - Input -", "2023/09.txt", new Phase1());
        FileUtil.runInputThroughFunction("Phase 2 - Example -", "2023/09-Example-01.txt", new Phase2());
        FileUtil.runInputThroughFunction("Phase 2 - Input -", "2023/09.txt", new Phase2());
    }
}
