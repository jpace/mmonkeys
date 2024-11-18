package org.incava.mmonkeys.mky.corpus

open class Corpus(val words: List<String>) {
    val matched = mutableSetOf<Int>()

    fun match(word: String): Int {
        words.indices.forEach { index ->
            // not sure which condition is faster:
            if (words[index] == word && !matched.contains(index)) {
                return index
            }
        }
        return -1
    }

    fun isMatched(index: Int) = matched.contains(index)

    fun removeAt(index: Int) {
        matched.add(index)
    }

    fun hasUnmatched(): Boolean = matched.size < words.size

    fun isEmpty(): Boolean = matched.size == words.size
}
