package main

import (
	"bufio"
	"fmt"
	"regexp"
	"strconv"
	"strings"

	"github.com/lbrooks/aoc/utils"
)

type Transform struct {
	sourceStart, sourceEnd, change int64
}

func (t Transform) canTransform(num int64) bool {
	return num >= t.sourceStart && num < t.sourceEnd
}

func (t Transform) transform(num int64) int64 {
	return num + t.change
}

func NewTransform(destStart, sourceStart, rangeLength int64) Transform {
	return Transform{
		sourceStart: sourceStart,
		sourceEnd:   sourceStart + rangeLength,
		change:      destStart - sourceStart,
	}
}

type Terraform struct {
	name       string
	transforms []Transform
}

type SeedInfo struct {
	starting, count int64
}

func (s SeedInfo) getSeeds() []int64 {
	result := make([]int64, s.count)
	for i := 0; i < int(s.count); i++ {
		result[i] = s.starting + int64(i)
	}
	return result
}

func getAllTransformations(contents []string) ([]int64, []Terraform) {
	seeds := make([]int64, 0)
	terras := make([]Terraform, 0)

	re := regexp.MustCompile((" +"))
	sas := re.Split(contents[0][7:], -1)
	for _, s := range sas {
		n, _ := strconv.ParseInt(s, 10, 64)
		seeds = append(seeds, n)
	}

	t := Terraform{"", nil}
	for i := 2; i < len(contents); i++ {
		if strings.HasSuffix(contents[i], ":") {
			t = Terraform{contents[i], nil}
		} else if len(contents[i]) == 0 {
			terras = append(terras, t)
		} else {
			numbs := strings.Split(contents[i], " ")

			dest, _ := strconv.ParseInt(strings.Trim(numbs[0], " "), 10, 64)
			start, _ := strconv.ParseInt(strings.Trim(numbs[1], " "), 10, 64)
			count, _ := strconv.ParseInt(strings.Trim(numbs[2], " "), 10, 64)

			t.transforms = append(t.transforms, NewTransform(dest, start, count))
		}
	}
	terras = append(terras, t)
	return seeds, terras
}

func readInputToSlice(input *bufio.Scanner) (contents []string) {
	for input.Scan() {
		contents = append(contents, input.Text())
	}
	return
}

type Day05_Phase1 struct{}

func (d Day05_Phase1) Solve(input *bufio.Scanner) (int64, error) {
	contents := readInputToSlice(input)

	seeds, terraforms := getAllTransformations(contents)

	min, minSet := int64(0), false
	for _, s := range seeds {
		workingNum := s
		for _, t := range terraforms {
			for _, tf := range t.transforms {
				if tf.canTransform(workingNum) {
					workingNum = tf.transform(workingNum)
					break
				}
			}
		}
		if !minSet {
			min = workingNum
			minSet = true
		} else if workingNum < min {
			min = workingNum
		}
	}
	return min, nil
}

type Day05_Phase2 struct{}

func (d Day05_Phase2) Solve(input *bufio.Scanner) (int64, error) {
	contents := readInputToSlice(input)

	rawSeedData, terraforms := getAllTransformations(contents)

	sis := make([]SeedInfo, 0)
	for i := 0; i < len(rawSeedData); i = i + 2 {
		sis = append(sis, SeedInfo{rawSeedData[0], rawSeedData[1]})
	}

	min, minSet := int64(0), false
	for id, si := range sis {
		for i := int64(0); i < si.count; i++ {
			workingNum := si.starting + i
			for _, t := range terraforms {
				for _, tf := range t.transforms {
					if tf.canTransform(workingNum) {
						workingNum = tf.transform(workingNum)
						break
					}
				}
			}
			if !minSet {
				min = workingNum
				minSet = true
			} else if workingNum < min {
				min = workingNum
			}
		}
		fmt.Printf("Completed %d of %d seeds\n", id+1, len(sis))
	}
	return min, nil
}

func main() {
	err := utils.RunInputThroughFunction("Phase 1 Example", "../resources/2023/05-Example-01.txt", &Day05_Phase1{})
	if err != nil {
		fmt.Println("Error: %s", err.Error())
	}

	err = utils.RunInputThroughFunction("Phase 1", "../resources/2023/05.txt", &Day05_Phase1{})
	if err != nil {
		fmt.Println("Error: %s", err.Error())
	}

	err = utils.RunInputThroughFunction("Phase 2 Example", "../resources/2023/05-Example-01.txt", &Day05_Phase2{})
	if err != nil {
		fmt.Println("Error: %s", err.Error())
	}

	err = utils.RunInputThroughFunction("Phase 2", "../resources/2023/05.txt", &Day05_Phase2{})
	if err != nil {
		fmt.Println("Error: %s", err.Error())
	}
}
