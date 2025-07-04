package org.incava.mmonkeys.words

object AttemptFactory {
    fun failed(keyStrokes: Int) = Attempt(keyStrokes.toLong())
    fun failed(str: String) = Attempt(str.length.toLong() + 1)
    fun succeeded(word: Word) = Attempt(word, word.string.length.toLong() + 1)
}