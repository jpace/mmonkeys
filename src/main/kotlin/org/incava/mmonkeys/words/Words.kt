package org.incava.mmonkeys.words

data class Words(val words: List<Word>, val totalKeyStrokes: Long, val numAttempts: Int) {
    constructor(totalKeyStrokes: Long, numAttempts: Int) : this(emptyList(), totalKeyStrokes, numAttempts)

    fun hasMatch() = words.isNotEmpty()
}
