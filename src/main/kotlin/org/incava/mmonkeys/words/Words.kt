package org.incava.mmonkeys.words

data class Words(val words: List<Word>, val totalKeyStrokes: Long) {
    constructor(totalKeyStrokes: Long) : this(emptyList(), totalKeyStrokes)

    fun hasMatch() = words.isNotEmpty()
}
