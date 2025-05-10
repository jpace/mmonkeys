package org.incava.mmonkeys.words

open class Attempt(val word: Word?, val totalKeyStrokes: Long) {
    constructor(totalKeyStrokes: Long) : this(null, totalKeyStrokes)

    fun hasMatch() = word != null
    override fun toString(): String {
        return "Attempt(word=$word, totalKeyStrokes=$totalKeyStrokes)"
    }
}