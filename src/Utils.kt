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
