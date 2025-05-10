package org.incava.mmonkeys.corpus

abstract class Corpus(private val words: List<String>) {
    val matches = Matches()

    fun words() = words

    fun numWords() = words.size

    fun matches(): Set<Int> = matches.indices

    fun isMatched(index: Int) = matches.isMatched(index)

    open fun setMatched(index: Int, word: String) {
        matches.setMatched(index)
    }

    fun hasUnmatched(): Boolean = matches.indices.size < numWords()

    fun isEmpty(): Boolean = !hasUnmatched()

    fun wordAtIndex(index: Int) = words[index]

    fun lengthAtIndex(index: Int) = wordAtIndex(index).length
}
