fun main() {
    fun Pair<Int, Int>.toWord(data: List<CharArray>, iInc: Int, jInc: Int, length: Int = 4): String =
        (0..<length).map { i -> data[this.first + iInc * i][this.second + jInc * i] }.joinToString("")

    fun part1(input: List<String>): Int {
        val data = input.map { it.toCharArray() }
        val l = mutableListOf<String>()
        for (i in data.indices) {
            for (j in data[i].indices) {
                if (data[i][j] != 'X' && data[i][j] != 'S') continue
                //Horizontal
                if (j < data[i].size - 3) l.add(Pair(i, j).toWord(data, 0, 1))

                //Vertical
                if (i < data.size - 3) l.add(Pair(i, j).toWord(data, 1, 0))

                //Diagonal
                //Right Down Diag
                if (j < data[i].size - 3 && i < data.size - 3) l.add(Pair(i, j).toWord(data, 1, 1))
                //Left Down Diag
                if (j >= 3 && i < data.size - 3) l.add(Pair(i, j).toWord(data, 1, -1))
            }
        }
        return l.filter { it == "XMAS" || it == "SAMX" }.println().size
    }

    fun part2(input: List<String>): Int {
        val data = input.map { it.toCharArray() }
        val l = mutableListOf<Pair<String, String>>()
        for (i in 1..<(data.size - 1)) {
            for (j in 1..<(data[i].size - 1)) {
                if (data[i][j] != 'A') continue
                l.add(
                    Pair(
                        //Diagonal \
                        Pair(i - 1, j - 1).toWord(data, 1, 1, 3),
                        //Diagonal /
                        Pair(i - 1, j + 1).toWord(data, 1, -1, 3)
                    )
                )
            }
        }

        return l.filter { (a, b) -> (a == "MAS" || a == "SAM") && (b == "MAS" || b == "SAM") }.println().size
    }

    val testInput = """
MMMSXXMASM
MSAMXMSMSA
AMXSXMAAMM
MSAMASMSMX
XMASAMXAMM
XXAMMXXAMA
SMSMSASXSS
SAXAMASAAA
MAMMMXMMMM
MXMXAXMASX
""".trim().split("\n")

    // Test if implementation meets criteria from the description, like:
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)


    // Read the input from the `src/Day01.txt` file.
    val input = readInput("04")
    part1(input).println()
    part2(input).println()
}
