package main

import (
	"bufio"
	"fmt"
	"regexp"
	"strconv"
	"strings"

	"github.com/lbrooks/aoc/utils"
)

type Day01_Phase1 struct{}

func (d Day01_Phase1) Solve(input *bufio.Scanner) (int64, error) {
	re := regexp.MustCompile("[^0-9]")

	var sum int64 = 0
	for input.Scan() {
		line := input.Text()
		line = re.ReplaceAllString(line, "")

		num, err := strconv.Atoi(string(line[0]) + string(line[len(line)-1]))
		if err != nil {
			return 0, err
		}

		sum += int64(num)
	}
	return sum, nil
}

type Day01_Phase2 struct{}

func (d Day01_Phase2) Solve(input *bufio.Scanner) (int64, error) {
	matchOn := []string{"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "1", "2", "3", "4", "5", "6", "7", "8", "9"}
	translate := map[string]int64{"one": 1, "two": 2, "three": 3, "four": 4, "five": 5, "six": 6, "seven": 7, "eight": 8, "nine": 9, "1": 1, "2": 2, "3": 3, "4": 4, "5": 5, "6": 6, "7": 7, "8": 8, "9": 9}

	var sum int64 = 0
	for input.Scan() {
		line := input.Text()

		var first, second int64 = 0, 0

		for i, k := 0, len(line)-1; i <= k; i, k = i+1, k-1 {
			if first == 0 {
				for _, n := range matchOn {
					if strings.HasPrefix(line[i:], n) {
						first = translate[n] * 10
					}
				}
			}
			if second == 0 {
				for _, n := range matchOn {
					if strings.HasSuffix(line[0:k+1], n) {
						second = translate[n]
					}
				}
			}
		}

		sum += (first + second)
	}
	return sum, nil
}

func main() {
	err := utils.RunInputThroughFunction("Phase 1 Example", "../resources/2023/01-Example-01.txt", &Day01_Phase1{})
	if err != nil {
		fmt.Println("Error: %s", err.Error())
	}
	err = utils.RunInputThroughFunction("Phase 1", "../resources/2023/01.txt", &Day01_Phase1{})
	if err != nil {
		fmt.Println("Error: %s", err.Error())
	}

	err = utils.RunInputThroughFunction("Phase 2 Example", "../resources/2023/01-Example-02.txt", &Day01_Phase2{})
	if err != nil {
		fmt.Println("Error: %s", err.Error())
	}
	err = utils.RunInputThroughFunction("Phase 2", "../resources/2023/01.txt", &Day01_Phase2{})
	if err != nil {
		fmt.Println("Error: %s", err.Error())
	}
}
