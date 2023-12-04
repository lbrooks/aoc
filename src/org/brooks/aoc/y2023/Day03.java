package org.brooks.aoc.y2023;

import org.brooks.aoc.FileUtil;
import org.brooks.aoc.Solution;

import java.util.*;
import java.util.stream.Stream;
public class Day03 {
    private static boolean isSymbol(String str) {
        if (str.matches("\\d")) {
            // is digit
            return false;
        }
        return !str.equals(".");
    }

    private static boolean isDigit(String str) {
        return str.matches("\\d");
    }

    public static class Phase1 implements Solution<Long> {
        private static Set<String> getAdjacentCharacters(List<List<String>> grid, int rowIdx, int startingColIdx, int endingColIdx) {
            int checkStart = startingColIdx - 1;
            if (checkStart < 0) {
                checkStart = 0;
            }
            int checkEnd = endingColIdx + 1;

            Set<String> surrounding = new HashSet<>();

            if ((rowIdx - 1) >= 0) {
                List<String> aboveRow = grid.get(rowIdx - 1);
                for (int i = checkStart; i <= checkEnd && i < aboveRow.size(); i++) {
                    surrounding.add(aboveRow.get(i));
                }
            }
            List<String> currentRow = grid.get(rowIdx);
            if (startingColIdx > 0) {
                surrounding.add(currentRow.get(startingColIdx - 1));
            }
            if (endingColIdx + 1 < currentRow.size()) {
                surrounding.add(currentRow.get(endingColIdx + 1));
            }
            if ((rowIdx + 1) < grid.size()) {
                List<String> belowRow = grid.get(rowIdx + 1);
                for (int i = checkStart; i <= checkEnd && i < belowRow.size(); i++) {
                    surrounding.add(belowRow.get(i));
                }
            }

            return surrounding;
        }

        @Override
        public Long solve(Stream<String> input) {
            List<List<String>> grid = input
                    .map(line->line.split(""))
                    .map(Arrays::asList)
                    .toList();

            long sum = 0L;

            for (int rowIdx = 0; rowIdx < grid.size(); rowIdx++) {
                List<String> row = grid.get(rowIdx);
                for (int colIdx = 0; colIdx < row.size();) {
                    if (!isDigit(row.get(colIdx))){
                        colIdx += 1;
                        continue;
                    }

                    int endIdx = colIdx + 1;
                    while(endIdx < row.size() && isDigit(row.get(endIdx))){
                        endIdx++;
                    }
                    endIdx--;

                    StringBuilder sb = new StringBuilder();
                    for (int i = colIdx; i <= endIdx; i++) {
                        sb.append(row.get(i));
                    }
                    long number = Long.parseLong(sb.toString());

                    Set<String> surrounding = getAdjacentCharacters(grid, rowIdx, colIdx, endIdx);
                    if (surrounding.stream().anyMatch(Day03::isSymbol)) {
                        sum += number;
                    }

                    colIdx = endIdx + 1;
                }
            }
            return sum;
        }
    }

    public static class Phase2 implements Solution<Long> {

        private int getIndexFirstNumber(List<String> row, int fromIdx){
            int i = fromIdx;
            while(i >= 0 && isDigit(row.get(i))) {
                i--;
            }
            i++;
            return i;
        }

        private int getIndexLastNumber(List<String> row, int fromIdx){
            int i = fromIdx;
            while(i < row.size() && isDigit(row.get(i))) {
                i++;
            }
            i--;
            return i;
        }

        private List<Long> getSurroundingNumber(List<List<String>> grid, int rowIdx, int colIdx) {
            List<Long> numbers = new ArrayList<>();

            if(rowIdx > 0) {
                List<String> row = grid.get(rowIdx-1);

                int first = getIndexFirstNumber(row, colIdx-1);
                int last = getIndexLastNumber(row, colIdx+1);

                StringBuilder b = new StringBuilder();
                for(int i = first; i <= last; i++){
                    b.append(row.get(i));
                }
                Stream.of(b.toString().split("[\\D]")).filter(o->!o.isEmpty()).mapToLong(Long::parseLong).forEach(numbers::add);
            }
            {
                List<String> row = grid.get(rowIdx);

                int first = getIndexFirstNumber(row, colIdx-1);
                int last = getIndexLastNumber(row, colIdx+1);

                StringBuilder b = new StringBuilder();
                for(int i = first; i <= last; i++){
                    b.append(row.get(i));
                }
                Stream.of(b.toString().split("[\\D]")).filter(o->!o.isEmpty()).mapToLong(Long::parseLong).forEach(numbers::add);
            }
            if(rowIdx < grid.size() - 1) {
                List<String> row = grid.get(rowIdx+1);

                int first = getIndexFirstNumber(row, colIdx-1);
                int last = getIndexLastNumber(row, colIdx+1);

                StringBuilder b = new StringBuilder();
                for(int i = first; i <= last; i++){
                    b.append(row.get(i));
                }
                Stream.of(b.toString().split("[\\D]")).filter(o->!o.isEmpty()).mapToLong(Long::parseLong).forEach(numbers::add);
            }

            return numbers.size() > 1 ? numbers : new ArrayList<>();
        }

        @Override
        public Long solve(Stream<String> input) {
            List<List<String>> grid = input
                    .map(line -> line.split(""))
                    .map(Arrays::asList)
                    .toList();

            long sum = 0L;

            for (int rowIdx = 0; rowIdx < grid.size(); rowIdx++) {
                List<String> row = grid.get(rowIdx);
                for (int colIdx = 0; colIdx < row.size(); colIdx++) {
                    if (!row.get(colIdx).equals("*")) {
                        // is not gear
                        continue;
                    }

                    List<Long> numbers = getSurroundingNumber(grid, rowIdx, colIdx);
                    sum += numbers.stream().mapToLong(o->o).reduce((p, n) -> p*n).orElse(0L);
                }
            }
            return sum;
        }
    }

    public static void main(String[] args) {
        FileUtil.runInputThroughFunction("Phase 1 - Example -", "2023/03-Example-01.txt", new Phase1());
        FileUtil.runInputThroughFunction("Phase 1 - Input -", "2023/03.txt", new Phase1());
        FileUtil.runInputThroughFunction("Phase 2 - Example -", "2023/03-Example-01.txt", new Phase2());
        FileUtil.runInputThroughFunction("Phase 2 - Input -", "2023/03.txt", new Phase2());
    }
}
