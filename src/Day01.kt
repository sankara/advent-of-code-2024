import kotlin.math.abs

fun main() {
    fun parseInput(input: List<String>): Pair<List<Int>, List<Int>> {
        return input.map {
            it.split("   ")
                .map(String::toInt)
                .zipWithNext().single()
        }.unzip()
    }

    fun part1(input: List<String>): Int {
        val (array1, array2) = parseInput(input)
        return array1.sorted().zip(array2.sorted()) { a, b -> abs(a - b) }.sum()
    }

    fun part2(input: List<String>): Int {
        val (array1, array2) = parseInput(input)
        return array1.sumOf { a -> array2.filter { b -> a == b }.sum() }
    }

    val testInput = listOf(
        "3   4",
        "4   3",
        "2   5",
        "1   3",
        "3   9",
        "3   3"
    )

    // Test if implementation meets criteria from the description, like:
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    val input = readInput("01")

    part1(input).println()
    part2(input).println()
}
