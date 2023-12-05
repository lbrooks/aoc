package org.brooks.aoc.y2023;

import org.brooks.aoc.FileUtil;
import org.brooks.aoc.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Day05 {
    private record Transform(
            long sourceStart,
            long sourceEnd,
            long transform
    ) {
        public static Transform create(long destStart, long sourceStart, long range) {
            return new Transform(
                    sourceStart,
                    sourceStart + range,
                    destStart - sourceStart
            );
        }

        public boolean canTransform(long num) {
            return num >= sourceStart && num < sourceEnd;
        }

        public long transform(long num) {
            return num + transform;
        }
    }

    private record Terraform(
            String name,
            List<Transform> transforms
    ) {
    }

    private record SeedInfo(
            long starting,
            long count
    ) {
        private Stream<Long> getSeeds() {
            return LongStream.range(starting, starting + count).boxed();
        }
    }

    private static List<Terraform> getAllTransformations(List<String> contents) {
        final List<Terraform> terraforms = new ArrayList<>();
        Terraform t = new Terraform("temp", new ArrayList<>());
        for (int i = 2; i < contents.size(); i++) {
            if (contents.get(i).endsWith(":")) {
                //beginning of Terraform
                t = new Terraform(contents.get(i), new ArrayList<>());
            } else if (contents.get(i).isBlank()) {
                //end of terraform
                terraforms.add(t);
            } else {
                List<Long> numbers = Stream.of(contents.get(i).split(" +")).map(Long::parseLong).toList();

                t.transforms.add(Transform.create(numbers.get(0), numbers.get(1), numbers.get(2)));
            }
        }
        terraforms.add(t);
        return terraforms;
    }

    public static class Phase1 implements Solution<Long> {
        @Override
        public Long solve(Stream<String> input) {
            List<String> contents = input.toList();

            List<Long> seeds = Stream.of(contents.get(0)
                            .substring(7)
                            .split(" +"))
                    .map(Long::parseLong)
                    .toList();

            final List<Terraform> terraforms = getAllTransformations(contents);

            return seeds.stream().mapToLong(s -> {
                long num = s;
                for (Terraform t : terraforms) {
                    final long current = num;
                    List<Transform> allMatch = t.transforms.stream().filter(o -> o.canTransform(current)).toList();
                    if (allMatch.isEmpty()) {
                        continue;
                    }
                    if (allMatch.size() > 1) {
                        System.out.println("MULTIPLE MATCH!!");
                        continue;
                    }
                    num = allMatch.get(0).transform(num);
                }
                return num;
            }).min().orElse(0L);
        }
    }

    public static class Phase2 implements Solution<Long> {
        @Override
        public Long solve(Stream<String> input) {
            List<String> contents = input.toList();

            List<Long> seeds = Stream.of(contents.get(0)
                            .substring(7)
                            .split(" +"))
                    .map(Long::parseLong)
                    .toList();
            List<SeedInfo> seedData = new ArrayList<>();
            for (int i = 0; i < seeds.size(); ) {
                seedData.add(new SeedInfo(seeds.get(i), seeds.get(i + 1)));
                i += 2;
            }

            final List<Terraform> terraforms = getAllTransformations(contents);

            final AtomicLong a = new AtomicLong(0L);

            return seedData.parallelStream().flatMap(SeedInfo::getSeeds).mapToLong(s -> {
                long num = s;
                for (Terraform t : terraforms) {
                    final long current = num;
                    List<Transform> allMatch = t.transforms.stream().filter(o -> o.canTransform(current)).toList();
                    if (allMatch.isEmpty()) {
                        continue;
                    }
                    if (allMatch.size() > 1) {
                        System.out.println("MULTIPLE MATCH!!");
                        continue;
                    }
                    num = allMatch.get(0).transform(num);
                }
                long at = a.addAndGet(1L);
                if (at%10000000 == 0){
                    System.out.printf("Processed %,d records, %s\n", at, Thread.currentThread().getName());
                }
                return num;
            }).reduce(Long.MAX_VALUE, Math::min);
        }
    }

    public static void main(String[] args) {
        FileUtil.runInputThroughFunction("Phase 1 - Example -", "2023/05-Example-01.txt", new Phase1());
        FileUtil.runInputThroughFunction("Phase 1 - Input -", "2023/05.txt", new Phase1());
        FileUtil.runInputThroughFunction("Phase 2 - Example -", "2023/05-Example-01.txt", new Phase2());
        FileUtil.runInputThroughFunction("Phase 2 - Input -", "2023/05.txt", new Phase2());
    }
}
