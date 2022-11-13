package org.incava.mmonkeys.match.corpus

class Corpus(vararg ary: String) {
    val words = mutableListOf(*ary)

    fun hasWord(word: String): Pair<Boolean, Int> {
        val index = words.indexOf(word)
        return if (index >= 0)
            true to index
        else
            false to -1
    }
}