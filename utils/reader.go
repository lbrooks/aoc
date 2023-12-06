package utils

import (
	"bufio"
	"fmt"
	"os"
)

func RunInputThroughFunction[K int64 | string](prefix, filename string, solution Solution[K]) error {
	readFile, err := os.Open(filename)
	defer readFile.Close()

	if err != nil {
		return err
	}
	fileScanner := bufio.NewScanner(readFile)
	fileScanner.Split(bufio.ScanLines)

	val, err := solution.Solve(fileScanner)
	if err != nil {
		return err
	}
	fmt.Printf("%s - Result: %v\n", prefix, val)
	return nil
}
