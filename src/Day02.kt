import kotlin.math.abs
import kotlin.math.sign

fun main() {
    fun parseInput(input: List<String>): List<List<Int>> {
        return input.map { it.split(" ").map(String::toInt) }
    }

    fun isSafe(arr: List<Int>): Boolean {
        for (i in 1..<arr.lastIndex) {
            if ((arr[i - 1] - arr[i]).sign != (arr[i] - arr[i + 1]).sign ||
                abs(arr[i - 1] - arr[i]) !in 1..3 ||
                abs(arr[i] - arr[i + 1]) !in 1..3
            ) return false
        }
        return true
    }

    fun part1(input: List<String>): Int {
        return parseInput(input).count { isSafe(it) }
    }

    fun part2(input: List<String>): Int {
        val (safe, unsafe) = parseInput(input).partition { isSafe(it) }
        val madeSafe = unsafe.filter { arr ->
            for (i in arr.indices) {
                if (isSafe(arr.withoutItemAt(i))) {
                    return@filter true
                }
            }
            return@filter false
        }
        return safe.size + madeSafe.size
    }


    val testInput = """
7 6 4 2 1
1 2 7 8 9
9 7 6 2 1
1 3 2 4 5
8 6 4 4 1
1 3 6 7 9
    """.trim().split("\n")


    // Test if implementation meets criteria from the description, like:
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)


    // Read the input from the `src/Day01.txt` file.
    val input = readInput("02")
    part1(input).println()
    part2(input).println()
}
