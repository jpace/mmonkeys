package org.incava.mmonkeys.words

class Attempts(val words: List<Word>, val totalKeyStrokes: Long, val numAttempts: Int) {
    fun asWords(): Words {
        return Words(words, totalKeyStrokes, numAttempts)
    }
}