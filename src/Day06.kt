import Dir.N
import State.*

enum class Dir(val symbol: Char) {
    N('^'), E('>'), S('v'), W('<');

    companion object {
        fun fromChar(symbol: Char) =
            entries.find { it.symbol == symbol } ?: throw IllegalArgumentException("Invalid direction $symbol")
    }

    fun move(pos: Coord): Coord {
        val (i, j) = pos
        return when (this) {
            N -> Coord(i - 1, j)
            E -> Coord(i, j + 1)
            S -> Coord(i + 1, j)
            W -> Coord(i, j - 1)
        }
    }

    fun rotate(): Dir {
        return when (this) {
            N -> E
            E -> S
            S -> W
            W -> N
        }
    }
}

enum class State(val value: String) {
    T("X"), O("#"), C(".")
}


fun main() {
    data class MapState(var state: State, var traversedDirs: MutableList<Dir>)
    data class Pos(val pos: Coord, val dir: Dir)

    class FloorMap(private val n: Int, private val m: Int) {
        private val map = Array(n) { Array(m) { MapState(C, mutableListOf()) } }
        val indices = map.indices
        override fun toString() = map.joinToString("\n") { row -> row.joinToString("") { (state, _) -> state.value } }
        operator fun get(i: Int) = map[i]
        fun sumOf(selector: (Array<MapState>) -> Int): Int = map.sumOf(selector)

        fun copy(): FloorMap {
            val newFloorMap = FloorMap(this.n, this.m)
            for (i in 0..<n)
                for (j in 0..<m)
                    newFloorMap[i][j] = MapState(map[i][j].state, mutableListOf())
            return newFloorMap
        }
    }

    fun parseInput(lines: List<String>): Pair<FloorMap, Pos> {
        val floorMap = FloorMap(lines.size, lines.first().length)
        var position = Pos(Coord(0, 0), N)
        for (i in lines.indices) for (j in lines[i].indices) when {
            lines[i][j] == '.' -> continue
            lines[i][j] == '#' -> floorMap[i][j].state = O
            else -> {
                val dir = Dir.fromChar(lines[i][j])
                position = Pos(Coord(i, j), dir)
                floorMap[i][j].state = T
                floorMap[i][j].traversedDirs.add(dir)
            }
        }

        return Pair(floorMap, position)
    }

    fun traverse(m: FloorMap, startingPosition: Pos): Pair<FloorMap, Boolean> {
        var (pos, dir) = startingPosition
        val map = m.copy()
        while (true) {
            val newPos = dir.move(pos)
            if (!(newPos.x in map.indices && newPos.y in map[newPos.x].indices)) {
                return Pair(map, false)
            } else if (map[newPos.x][newPos.y].traversedDirs.contains(dir)) {
                return Pair(map, true)
            }

            if (map[newPos.x][newPos.y].state == O) dir = dir.rotate() else pos = newPos
            map[pos.x][pos.y].state = T
            map[pos.x][pos.y].traversedDirs.add(dir)
        }
    }

    fun part1(input: List<String>): Int {
        val (floorMap, position) = parseInput(input)
        val (traversedMap, _) = traverse(floorMap, position)
        return traversedMap.sumOf { it.count { s -> s.state == T } }
    }

    fun part2(input: List<String>): Int {
        val (floorMap, position) = parseInput(input)
        var count = 0
        for (i in floorMap.indices) {
            for (j in floorMap[i].indices) {
                if (floorMap[i][j].state == C) {
                    floorMap[i][j].state = O
                    if (traverse(floorMap, position).second) count++
                    floorMap[i][j].state = C
                }
            }
        }
        return count
    }

    val testInput = """
....#.....
.........#
..........
..#.......
.......#..
..........
.#..^.....
........#.
#.........
......#...        
""".trim().split("\n")

    // Test if implementation meets criteria from the description, like:
    check(part1(testInput) == 41)
    check(part2(testInput) == 6)


    // Read the input from the `src/main/resources/day01.txt` file.
    val input = readInput("06")
    part1(input).println()
    part2(input).println()
}
