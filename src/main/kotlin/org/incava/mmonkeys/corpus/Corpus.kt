package org.incava.mmonkeys.corpus

open class Corpus(val words: List<String>) {
    val matched = mutableSetOf<Int>()

    fun isMatched(index: Int) = matched.contains(index)

    fun setMatched(index: Int) = matched.add(index)

    fun hasUnmatched(): Boolean = matched.size < words.size

    fun isEmpty(): Boolean = !hasUnmatched()

    fun findMatch(word: String): Int? {
        return words.indices.find { index ->
            words[index] == word && !matched.contains(index)
        }
    }
}
