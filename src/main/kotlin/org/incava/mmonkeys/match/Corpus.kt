package org.incava.mmonkeys.match

class Corpus(vararg ary: String) {
    val words = mutableListOf<String>()

    init {
        words.addAll(ary)
    }

    fun hasWord(word: String): Pair<Boolean, Int> {
        return false to -1
    }
}