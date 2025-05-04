package org.incava.mmonkeys.words

open class Attempt(val words: List<Word>, val totalKeyStrokes: Long, val numAttempts: Int) {
    constructor(word: Word, totalKeyStrokes: Long, numAttempts: Int) : this(listOf(word), totalKeyStrokes, numAttempts)
    constructor(totalKeyStrokes: Long, numAttempts: Int) : this(emptyList(), totalKeyStrokes, numAttempts)

    fun hasMatch() = words.isNotEmpty()
}