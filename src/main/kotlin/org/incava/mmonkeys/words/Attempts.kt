package org.incava.mmonkeys.words

class Attempts(val words: List<Word>, val totalKeyStrokes: Long, val numAttempts: Int) {
    override fun toString(): String {
        return "Attempts(words=$words, totalKeyStrokes=$totalKeyStrokes, numAttempts=$numAttempts)"
    }
}