package org.incava.mmonkeys.corpus

abstract class Corpus(private val words: List<String>) {
    val matches = Matches()
    private val wordCount = words.size

    fun words() = words

    fun numWords() = wordCount

    open fun setMatched(index: Int, word: String) {
        matches.setMatched(index)
    }

    fun wordAtIndex(index: Int) = words[index]

    fun lengthAtIndex(index: Int) = wordAtIndex(index).length
}
