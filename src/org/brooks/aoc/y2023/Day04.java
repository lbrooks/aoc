package org.brooks.aoc.y2023;

import org.brooks.aoc.FileUtil;
import org.brooks.aoc.Solution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day04 {
    protected static Set<Long> parseLine(String line){
        return Stream.of(line.split(" +"))
                .mapToLong(Long::parseLong)
                .boxed()
                .collect(Collectors.toSet());
    }

    protected record Game (
            long id,
            Set<Long> winners,
            Set<Long> card
    ){
        private static Game parse(String line) {
            String[] idGame = line.split(": +");
            long id = Long.parseLong(idGame[0].split(" +")[1]);
            String[] winHave = idGame[1].split(" +\\| +");
            Set<Long> winners = parseLine(winHave[0]);
            Set<Long> card = parseLine(winHave[1]);
            return new Game(id, winners, card);
        }

        private long numberOfWinners(){
            return card.stream().filter(winners::contains).count();
        }
    }

    public static class Phase1 implements Solution<Long> {
        @Override
        public Long solve(Stream<String> input) {
            return input
                    .map(Game::parse)
                    .map(g -> g.numberOfWinners() - 1)
                    .filter(o -> o >= 0)
                    .map(o -> Math.pow(2, o))
                    .mapToLong(Double::longValue)
                    .sum();
        }
    }

    public static class Phase2 implements Solution<Long> {
        @Override
        public Long solve(Stream<String> input) {
            final Map<Long, Long> cardCounts = new HashMap<>();
            List<Game> games = input.map(Game::parse).toList();
            for(Game g : games){
                cardCounts.put(g.id, cardCounts.getOrDefault(g.id, 0L) + 1);
                long copies = cardCounts.get(g.id);
                for(long i = 1; i <= g.numberOfWinners(); i++){
                    cardCounts.put(g.id+i, cardCounts.getOrDefault(g.id+i, 0L) + copies);
                }
            }
            return cardCounts.values().stream().mapToLong(o->o).sum();
        }
    }

    public static void main(String[] args) {
        FileUtil.runInputThroughFunction("Phase 1 - Example -", "2023/04-Example-01.txt", new Phase1());
        FileUtil.runInputThroughFunction("Phase 1 - Input -", "2023/04.txt", new Phase1());
        FileUtil.runInputThroughFunction("Phase 2 - Example -", "2023/04-Example-01.txt", new Phase2());
        FileUtil.runInputThroughFunction("Phase 2 - Input -", "2023/04.txt", new Phase2());
    }
}
