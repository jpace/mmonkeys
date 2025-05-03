package org.incava.mmonkeys.words

open class Attempt(val words: List<Word>, val totalKeyStrokes: Long, val numAttempts: Int) {
    fun toWords() : Words {
        return if (hasMatch()) Words(words, totalKeyStrokes, numAttempts) else Words(totalKeyStrokes, numAttempts)
    }

    fun hasMatch() = words.isNotEmpty()
}