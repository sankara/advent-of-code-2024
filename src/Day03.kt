fun main() {
    fun part1(input: List<String>): Int {
        val matches = Regex("(mul\\((\\d{1,3}),(\\d{1,3})\\))").findAll(input.joinToString(""))
        return matches.fold(0) { sum, match ->
            val (a, b) = match.groupValues.slice(2..3).map { it.toInt() }
            sum + a * b
        }
    }

    fun part2(input: List<String>): Int {
        val matches = Regex("(do\\(\\)|don't\\(\\)|(mul\\((\\d{1,3}),(\\d{1,3})\\)))").findAll(input.joinToString(""))
        return matches.fold(Pair(0, true)) { (sum, enabled), match ->
            when (match.groupValues[0]) {
                "don't()" -> Pair(sum, false)
                "do()" -> Pair(sum, true)
                else -> {
                    val (a, b) = match.groupValues.slice(3..4).map { it.toInt() }
                    if (enabled) Pair(sum + a * b, enabled) else (Pair(sum, enabled))
                }
            }
        }.first
    }

    val testInput1 = listOf("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")
    val testInput2 = listOf("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")

    // Test if implementation meets criteria from the description, like:
    check(part1(testInput1) == 161)
    check(part2(testInput2) == 48)


    // Read the input from the `src/Day01.txt` file.
    val input = readInput("03")
    part1(input).println()
    part2(input).println()
}
