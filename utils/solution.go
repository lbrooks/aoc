package utils

import "bufio"

type Solution[K int64 | string] interface {
	Solve(reader *bufio.Scanner) (K, error)
}
