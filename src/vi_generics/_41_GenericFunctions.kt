package vi_generics

import util.TODO
import java.util.*

fun task41(): Nothing = TODO(
    """
        Task41.
        Add a 'partitionTo' function that splits a collection into two collections according to a predicate.
        Uncomment the commented invocations of 'partitionTo' below and make them compile.

        There is a 'partition()' function in the standard library that always returns two newly created lists.
        You should write a function that splits the collection into two collections given as arguments.
        The signature of the 'toCollection()' function from the standard library may help you.
    """,
        references = { l: List<Int> ->
            l.partition { it > 0 }
            l.toCollection(HashSet<Int>())
        }
)

fun <T, C: MutableCollection<in T>> Iterable<T>.partitionTo(
        p1: C, p2: C, f: (T) -> Boolean): Pair<C, C> {
    for (x in this) { if (f(x)) { p1.add(x) } else { p2.add(x) } }
    return Pair(p1, p2)
}

fun List<String>.partitionWordsAndLines(): Pair<List<String>, List<String>> {
    return partitionTo(mutableListOf<String>(), mutableListOf<String>()) { s -> !s.contains(" ") }
}

fun Set<Char>.partitionLettersAndOtherSymbols(): Pair<Set<Char>, Set<Char>> {
    return partitionTo(mutableSetOf<Char>(), mutableSetOf<Char>()) { c -> c in 'a'..'z' || c in 'A'..'Z'}
}