package org.brooks.aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileUtil {
    public static <A> void runInputThroughFunction(String prefix, String filename, Solution<A> method) {
        try (Stream<String> stream = Files.lines(Paths.get(ClassLoader.getSystemResource(filename).toURI()))) {
            System.out.printf("%s Result: %s\n", prefix, method.solve(stream));
        } catch (IOException | URISyntaxException e) {
            System.err.println(e.getMessage());
        }
    }

}
