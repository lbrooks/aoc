package org.brooks.aoc.y2023;

import org.brooks.aoc.FileUtil;
import org.brooks.aoc.Solution;

import java.util.*;
import java.util.stream.Stream;

public class Day08 {

    enum Direction {
        R,
        L
    }

    final static class Node {
        String id;
        Node left;
        Node right;

        Node(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "[" + id + "]" +
                    " L [" + (left == null ? "-" : left.id) + "]" +
                    " R [" + (right == null ? "-" : right.id) + "]";
        }

        Node go(Direction d) {
            return switch (d) {
                case L -> this.left;
                case R -> this.right;
            };
        }
    }

    final static class TruncatedPath {
        String id;
        long stepsToNext;
        TruncatedPath next;

        TruncatedPath(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return String.format("[%s] -%d-> (%s)", id, stepsToNext,
                    (next == null ? "-" : next.id));
        }
    }

    public static class Phase1 implements Solution<Long> {
        @Override
        public Long solve(Stream<String> input) {
            List<String> inList = input.toList();

            List<Direction> path = Stream.of(inList.getFirst().split("")).map(Direction::valueOf).toList();

            Map<String, Node> allNodes = getNodeMap(inList, "[^A-Z]+");

            Node currentNode = allNodes.get("AAA");
            if (currentNode == null) {
                return -1L;
            }
            long stepCount = 0;
            while (true) {
                for (Direction d : path) {
                    currentNode = currentNode.go(d);
                    stepCount += 1;

                    if (currentNode.id.equals("ZZZ")) {
                        return stepCount;
                    }
                }
                if (stepCount > 1_000_000_000_000_000_000L) {
                    return -1L;
                }
            }
        }
    }

    public static class Phase2 implements Solution<Long> {
        @Override
        public Long solve(Stream<String> input) {
            List<String> inList = input.toList();

            List<Direction> path = Stream.of(inList.getFirst().split("")).map(Direction::valueOf).toList();

            Map<String, Node> allNodes = getNodeMap(inList, "[^A-Z0-9]+");

            TruncatedPath[] truncatedPaths = allNodes.entrySet()
                    .stream()
                    .filter(e -> e.getKey().endsWith("A"))
                    .map(e -> {
                        Map<String, TruncatedPath> sp = new HashMap<>();
                        sp.put(e.getKey(), new TruncatedPath(e.getKey()));

                        String startingAt = e.getKey();
                        Node c = e.getValue();
                        long steps = 0L;

                        loopingPaths:
                        while (true) {
                            for (Direction d : path) {
                                c = c.go(d);
                                steps += 1;
                                if (c.id.endsWith("Z")) {
                                    boolean visitedNext = sp.containsKey(c.id);
                                    TruncatedPath next = sp.computeIfAbsent(c.id, TruncatedPath::new);

                                    TruncatedPath start = sp.get(startingAt);
                                    start.stepsToNext = steps;
                                    start.next = next;

                                    steps = 0L;
                                    startingAt = c.id;

                                    if (visitedNext) {
                                        break loopingPaths;
                                    }
                                }
                            }
                        }
                        System.out.println(sp);
                        return sp.get(e.getKey());
                    })
                    .toArray(TruncatedPath[]::new);

//            long[] distances = new long[truncatedPaths.length];

            for (TruncatedPath p : truncatedPaths) {
                Set<String> occurred = new HashSet<>();
                TruncatedPath c = p;
                while (!occurred.contains(c.id)) {
                    occurred.add(c.id);
                    System.out.printf("[%s] -%d-> ", c.id, c.stepsToNext);
                    c = c.next;
                }
                System.out.println("<- " + c.id);
            }

//            while(!allSameDistance(distances) || distances[0] == 0){
//                long largestDistance = LongStream.of(distances).max().orElse(0L);
//                for(int i = 0; i < distances.length; i++) {
//                    while(distances[i] < largestDistance) {
//                        distances[i] = distances[i] + truncatedPaths[i].stepsToNext;
//                        truncatedPaths[i] = truncatedPaths[i].next;
//                    }
//                }
//            }
//            return distances[0];
            return 0L;
        }
    }

    private static Map<String, Node> getNodeMap(List<String> inList, String regex) {
        Map<String, Node> allNodes = new HashMap<>();
        for (int i = 2; i < inList.size(); i++) {
            String[] nodeIds = inList.get(i).split(regex);

            Node current = allNodes.computeIfAbsent(nodeIds[0], Node::new);
            Node left = allNodes.computeIfAbsent(nodeIds[1], Node::new);
            Node right = allNodes.computeIfAbsent(nodeIds[2], Node::new);

            current.left = left;
            current.right = right;
        }
        return allNodes;
    }

    public static void main(String[] args) {
        FileUtil.runInputThroughFunction("Phase 1 - Example -", "2023/08-Example-01.txt", new Phase1());
        FileUtil.runInputThroughFunction("Phase 1 - Example -", "2023/08-Example-02.txt", new Phase1());
        FileUtil.runInputThroughFunction("Phase 1 - Input -", "2023/08.txt", new Phase1());
//        FileUtil.runInputThroughFunction("Phase 2 - Example -", "2023/08-Example-01.txt", new Phase2());
//        FileUtil.runInputThroughFunction("Phase 2 - Example -", "2023/08-Example-02.txt", new Phase2());
        FileUtil.runInputThroughFunction("Phase 2 - Example -", "2023/08-Example-03.txt", new Phase2());
//        FileUtil.runInputThroughFunction("Phase 2 - Input -", "2023/08.txt", new Phase2());
    }
}
