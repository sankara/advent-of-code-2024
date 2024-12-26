fun main() {
    fun parseInput(input: List<String>): Pair<List<List<Int>>, List<List<Int>>> {
        val rules = mutableListOf<List<Int>>()
        val updates = mutableListOf<List<Int>>()

        var processingSection = Pair(rules, '|')
        input.forEach { line ->
            if (line == "") processingSection = Pair(updates, ',')
            else processingSection.first.add(line.split(processingSection.second).map { it.toInt() })
        }

        return Pair(rules, updates)
    }

    fun isValidUpdate(update: List<Int>, rules: List<List<Int>>): Boolean {
        return rules.all { rule ->
            val (a, b) = rule.map { update.indexOf(it) }
            a < 0 || b < 0 || a < b
        }
    }

    fun <T> List<T>.getMiddle(): T = get(size / 2)

    fun rulesComparator(rules: List<List<Int>>): Comparator<Int> {
        return java.util.Comparator<Int> { n1: Int, n2: Int ->
            for ((a, b) in rules) {
                if (a == n1 && b == n2) return@Comparator -1
                else if (a == n2 && b == n1) return@Comparator 1
                else continue
            }
            return@Comparator 0
        }
    }

    fun part1(input: List<String>): Int {
        val (rules, updates) = parseInput(input)
        return updates.filter { isValidUpdate(it, rules) }.sumOf { it.getMiddle() }
    }

    fun part2(input: List<String>): Int {
        val (rules, updates) = parseInput(input)
        val comparator: Comparator<Int> = rulesComparator(rules)
        val correctedUpdates = updates.filterNot { isValidUpdate(it, rules) }.map { it.sortedWith(comparator) }
        return correctedUpdates.println().sumOf { it.getMiddle() }
    }

    val testInput = """
47|53
97|13
97|61
97|47
75|29
61|13
75|53
29|13
97|29
53|29
61|53
97|53
61|29
47|13
75|47
97|75
47|61
75|61
47|29
75|13
53|13

75,47,61,53,29
97,61,53,29,13
75,29,13
75,97,47,61,53
61,13,29
97,13,75,29,47
""".trim().split("\n")

    // Test if implementation meets criteria from the description, like:
    check(part1(testInput) == 143)
    check(part2(testInput) == 123)


    // Read the input from the `src/Day01.txt` file.
    val input = readInput("05")
    part1(input).println()
    part2(input).println()
}
