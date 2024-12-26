import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/main/resources/day${name}.txt").readText().trim().lines()

/**
 * The cleaner shorthand for printing output.
 */
fun <T> T.println(): T {
    println(this)
    return this
}

fun <L> Iterable<L>.withoutItemAt(index: Int) = filterIndexed { i, _ -> i != index }

data class Coord(val x: Int, val y: Int) {
    operator fun plus(other: Coord) = Coord(x + other.x, y + other.y)
    operator fun minus(other: Coord) = Coord(x - other.x, y - other.y)
    operator fun times(scalar: Int) = Coord(x * scalar, y * scalar)

    override fun toString(): String {
        return "($x, $y)"
    }
}
