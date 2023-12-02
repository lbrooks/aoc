package org.brooks.aoc;

import java.util.stream.Stream;

public interface Solution<A> {
    A solve(Stream<String> input);
}
